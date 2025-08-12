package com.matheusvsdev.dataprocessor.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @OneToMany(mappedBy = "project")
    private List<UserProjectEntity> userProjectEntities = new ArrayList<>();

    public ProjectEntity() {
    }

    public ProjectEntity(String id, String name, Boolean completed) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserProjectEntity> getUserProjectEntities() {
        return userProjectEntities;
    }
}
