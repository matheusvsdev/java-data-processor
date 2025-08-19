package com.matheusvsdev.dataprocessor.service;

import com.matheusvsdev.dataprocessor.dto.TeamDTO;
import com.matheusvsdev.dataprocessor.dto.UserDTO;
import com.matheusvsdev.dataprocessor.entities.TeamEntity;
import com.matheusvsdev.dataprocessor.entities.UserEntity;
import com.matheusvsdev.dataprocessor.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Map<String, TeamEntity> syncTeams(List<UserDTO> users) {
        Set<String> teamNames = users
                .stream()
                .map(UserDTO::team)
                .filter(Objects::nonNull)
                .map(TeamDTO::name)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<String, TeamEntity> existingTeams = teamRepository.findByNameIn(teamNames)
                .stream()
                .collect(Collectors.toMap(TeamEntity::getName, team -> team));

        List<TeamEntity> newTeams = teamNames.stream()
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
        return existingTeams;
    }

    public void addTeamLeader(List<UserDTO> loadedUsers, Map<String, TeamEntity> teams, Map<String, UserEntity> users) {
        List<TeamEntity> teamsToUpdate = new ArrayList<>();
        for (UserDTO userDTO : loadedUsers) {
            if (userDTO.team().leader()) {
                TeamEntity team = teams.get(userDTO.team().name());
                UserEntity leader = users.get(userDTO.id());

                if (team != null && leader != null) {
                    team.getLeaders().add(leader);
                    teamsToUpdate.add(team);
                }
            }
        }
        if (!teamsToUpdate.isEmpty()) {
            teamRepository.saveAll(teamsToUpdate);
        }
    }
}
