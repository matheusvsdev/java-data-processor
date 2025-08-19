package com.matheusvsdev.dataprocessor.service;

import com.matheusvsdev.dataprocessor.dto.LogDTO;
import com.matheusvsdev.dataprocessor.dto.UserDTO;
import com.matheusvsdev.dataprocessor.entities.LogEntity;
import com.matheusvsdev.dataprocessor.entities.UserEntity;
import com.matheusvsdev.dataprocessor.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LogService {

    private final LogRepository repository;

    public LogService(LogRepository repository) {
        this.repository = repository;
    }

    public void persistLog(List<UserDTO> loadedUsers, Map<String, UserEntity> users) {
        List<LogEntity> logs = new ArrayList<>();
        for (UserDTO user : loadedUsers) {
            UserEntity savedUser = users.get(user.id());
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
            repository.saveAll(logs);
        }
    }
}
