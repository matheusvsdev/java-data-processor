package com.matheusvsdev.dataprocessor.service;

import com.matheusvsdev.dataprocessor.dto.ProjectDTO;
import com.matheusvsdev.dataprocessor.dto.UserDTO;
import com.matheusvsdev.dataprocessor.entities.ProjectEntity;
import com.matheusvsdev.dataprocessor.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Map<String, ProjectEntity> syncProjects(List<UserDTO> users) {
        Set<String> projectNames = users
                .stream()
                .flatMap(u -> u.team().projects().stream())
                .map(ProjectDTO::name)
                .collect(Collectors.toSet());

        Map<String, ProjectEntity> existingProjects = repository.findByNameIn(projectNames)
                .stream()
                .collect(Collectors.toMap(ProjectEntity::getName, project -> project));

        List<ProjectEntity> newProjects = projectNames.stream()
                .filter(name -> !existingProjects.containsKey(name))
                .map(name -> {
                    ProjectEntity project = new ProjectEntity();
                    project.setName(name);
                    return project;
                })
                .toList();

        if (!newProjects.isEmpty()) {
            repository.saveAll(newProjects);
            newProjects.forEach(project -> existingProjects.put(project.getName(), project));
        }
        return existingProjects;
    }
}
