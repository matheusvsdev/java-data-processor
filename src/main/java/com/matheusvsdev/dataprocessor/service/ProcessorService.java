package com.matheusvsdev.dataprocessor.service;

import com.matheusvsdev.dataprocessor.dto.*;
import com.matheusvsdev.dataprocessor.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProcessorService {

    private final JsonReaderService jsonReaderService;
    private final TeamService teamService;
    private final ProjectService projectService;
    private final UserProjectService userProjectService;
    private final LogService logService;

    private final UserService userService;


    public ProcessorService(JsonReaderService jsonReaderService, TeamService teamService, ProjectService projectService, UserProjectService userProjectService, LogService logService, UserService userService) {
        this.jsonReaderService = jsonReaderService;
        this.teamService = teamService;
        this.projectService = projectService;
        this.userProjectService = userProjectService;
        this.logService = logService;
        this.userService = userService;

    }

    @Transactional
    public String process(FileDTO file) {
        try {
            Path path = Paths.get(file.path());
            if(!Files.exists(path)) {
                throw new IllegalArgumentException("Arquivo não encontrado: " + path);
            }
           List<UserDTO> loadedUsers = jsonReaderService.parseUsersFromJsonFile(path);
           Map<String, TeamEntity> teams = teamService.syncTeams(loadedUsers);
           Map<String, ProjectEntity> projects = projectService.syncProjects(loadedUsers);
           Map<String, UserEntity> users = userService.saveUsers(loadedUsers, teams);
           userProjectService.projectsByUser(loadedUsers, users, projects);
           logService.persistLog(loadedUsers, users);
           teamService.addTeamLeader(loadedUsers, teams, users);
           return "Arquivo processado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao processar arquivo: " + e.getMessage();
        }
    }
}
