package com.matheusvsdev.dataprocessor.service;

import com.matheusvsdev.dataprocessor.dto.UserDTO;
import com.matheusvsdev.dataprocessor.entities.TeamEntity;
import com.matheusvsdev.dataprocessor.entities.UserEntity;
import com.matheusvsdev.dataprocessor.mapper.UserMapper;
import com.matheusvsdev.dataprocessor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    public UserService(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public Map<String, UserEntity> saveUsers(List<UserDTO> users, Map<String, TeamEntity> teams) {
        List<UserEntity> entitiesToSave = new ArrayList<>();
        Map<String, UserEntity> savedUsers = new HashMap<>();
        for (UserDTO user : users) {
            UserEntity entity = userMapper.toEntity(user);
            entity.setTeam(teams.get(user.team().name()));
            entitiesToSave.add(entity);
        }

        List<UserEntity> savedEntities = repository.saveAll(entitiesToSave);
        for (UserEntity saved : savedEntities) {
            savedUsers.put(saved.getId(), saved);
        }
        return savedUsers;
    }
}
