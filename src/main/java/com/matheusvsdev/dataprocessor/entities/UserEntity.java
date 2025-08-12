package com.matheusvsdev.dataprocessor.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_users")
public class UserEntity {

    @Id
    private String id;
    private String name;
    private Integer age;
    private Integer score;
    private Boolean active;
    private String country;

    @ManyToMany(mappedBy = "leaders")
    private List<TeamEntity> leadersOf = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @OneToMany(mappedBy = "user")
    private List<UserProjectEntity> userProjectEntities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LogEntity> logs = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(String id, String name, Integer age, Integer score, Boolean active, String country) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
        this.active = active;
        this.country = country;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<TeamEntity> getLeadersOf() {
        return leadersOf;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public List<UserProjectEntity> getUserProjectEntities() {
        return userProjectEntities;
    }

    public List<LogEntity> getLogs() {
        return logs;
    }
}
