package com.matheusvsdev.dataprocessor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusvsdev.dataprocessor.dto.*;
import com.matheusvsdev.dataprocessor.entities.*;
import com.matheusvsdev.dataprocessor.mapper.UserMapper;
import com.matheusvsdev.dataprocessor.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessorService {
    private final ObjectMapper mapper = new ObjectMapper();

    private final UserRepository userRepository;
    private final LogRepository logRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;
    private final UserMapper userMapper;

    public ProcessorService(UserRepository userRepository, LogRepository logRepository, TeamRepository teamRepository, ProjectRepository projectRepository, UserProjectRepository userProjectRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.logRepository = logRepository;
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
        this.userProjectRepository = userProjectRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public String loadData(FileDTO file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file.path()))){
            List<UserDTO> users = mapper.readValue(reader, mapper.getTypeFactory()
                    .constructCollectionType(List.class, UserDTO.class)
            );

            Set<String> uniqueTeamNames = users
                    .stream()
                    .map(u -> u.team().name())
                    .collect(Collectors.toSet());

            Map<String, TeamEntity> existingTeams = teamRepository.findByNameIn(uniqueTeamNames)
                    .stream()
                    .collect(Collectors.toMap(TeamEntity::getName, team -> team));

            List<TeamEntity> newTeams = uniqueTeamNames.stream()
                    .filter(name -> !existingTeams.containsKey(name))
                    .map(name -> {
                        TeamEntity team = new TeamEntity();
                        team.setName(name);
                        return team;
                    })
                    .toList();

            if (!newTeams.isEmpty()) {
                teamRepository.saveAll(newTeams);
                newTeams.forEach(team -> existingTeams.put(team.getName(), team));
            }

            Set<String> uniqueProjectNames = users
                    .stream()
                    .flatMap(u -> u.team().projects().stream())
                    .map(ProjectDTO::name)
                    .collect(Collectors.toSet());

            Map<String, ProjectEntity> existingProjects = projectRepository.findByNameIn(uniqueProjectNames)
                    .stream()
                    .collect(Collectors.toMap(ProjectEntity::getName, project -> project));

            List<ProjectEntity> newProjects = uniqueProjectNames.stream()
                    .filter(name -> !existingProjects.containsKey(name))
                    .map(name -> {
                        ProjectEntity project = new ProjectEntity();
                        project.setName(name);
                        return project;
                    })
                    .toList();

            if (!newProjects.isEmpty()) {
                projectRepository.saveAll(newProjects);
                newProjects.forEach(project -> existingProjects.put(project.getName(), project));
            }

            Map<String, UserEntity> savedUsers = new HashMap<>();
            for (UserDTO user : users) {
                UserEntity entity = userMapper.toEntity(user);
                entity.setTeam(existingTeams.get(user.team().name()));
                entity = userRepository.save(entity);
                savedUsers.put(user.id(), entity);
            }

            List<UserProjectEntity> userProjects = new ArrayList<>();
            for (UserDTO user : users) {
                UserEntity savedUser = savedUsers.get(user.id());
                for (ProjectDTO projectDTO : user.team().projects()) {
                    ProjectEntity projectEntity = existingProjects.get(projectDTO.name());
                    if (projectEntity != null && savedUser != null) {
                        UserProjectEntity userProjectEntity = new UserProjectEntity();
                        userProjectEntity.setUser(savedUser);
                        userProjectEntity.setProject(projectEntity);
                        userProjectEntity.setCompleted(projectDTO.completed());
                        userProjects.add(userProjectEntity);
                    }
                }
            }

            if (!userProjects.isEmpty()) {
                userProjectRepository.saveAll(userProjects);
            }

            List<LogEntity> logs = new ArrayList<>();
            for (UserDTO user : users) {
                UserEntity savedUser = savedUsers.get(user.id());
                if (savedUser != null) {
                    for (LogDTO logDTO : user.logs()) {
                        LogEntity logEntity = new LogEntity();
                        logEntity.setUser(savedUser);
                        logEntity.setDate(logDTO.date());
                        logEntity.setAction(logDTO.action());
                        logs.add(logEntity);
                    }
                }
            }
            if (!logs.isEmpty()) {
                logRepository.saveAll(logs);
            }

            List<TeamEntity> teamsToUpdate = new ArrayList<>();
            for (UserDTO userDTO : users) {
                if (userDTO.team().leader()) {
                    TeamEntity team = existingTeams.get(userDTO.team().name());
                    UserEntity leader = savedUsers.get(userDTO.id());

                    if (team != null && leader != null) {
                        team.getLeaders().add(leader);
                        teamsToUpdate.add(team);
                    }
                }
            }

            if (!teamsToUpdate.isEmpty()) {
                teamRepository.saveAll(teamsToUpdate);
            }

            return "Arquivo processado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao processar arquivo: " + e.getMessage();
        }
    }
}
