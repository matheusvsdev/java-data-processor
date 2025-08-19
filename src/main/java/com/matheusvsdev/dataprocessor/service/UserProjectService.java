package com.matheusvsdev.dataprocessor.service;

import com.matheusvsdev.dataprocessor.dto.ProjectDTO;
import com.matheusvsdev.dataprocessor.dto.UserDTO;
import com.matheusvsdev.dataprocessor.entities.ProjectEntity;
import com.matheusvsdev.dataprocessor.entities.UserEntity;
import com.matheusvsdev.dataprocessor.entities.UserProjectEntity;
import com.matheusvsdev.dataprocessor.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserProjectService {

    private final UserProjectRepository repository;

    public UserProjectService(UserProjectRepository repository) {
        this.repository = repository;
    }

    public void projectsByUser(List<UserDTO> loadedUsers,
                               Map<String, UserEntity> users,
                               Map<String, ProjectEntity> projects
    ) {
        List<UserProjectEntity> userProjects = new ArrayList<>();
        for (UserDTO user : loadedUsers) {
            UserEntity savedUsers = users.get(user.id());
            for (ProjectDTO projectDTO : user.team().projects()) {
                ProjectEntity projectEntity = projects.get(projectDTO.name());
                if (projectEntity != null && savedUsers != null) {
                    UserProjectEntity userProjectEntity = new UserProjectEntity();
                    userProjectEntity.setUser(savedUsers);
                    userProjectEntity.setProject(projectEntity);
                    userProjectEntity.setCompleted(projectDTO.completed());
                    userProjects.add(userProjectEntity);
                }
            }
        }
        if (!userProjects.isEmpty()) {
            repository.saveAll(userProjects);
        }
    }
}
