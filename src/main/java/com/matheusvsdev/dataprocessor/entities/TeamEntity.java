package com.matheusvsdev.dataprocessor.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_teams")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "tb_team_leaders",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> leaders = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<UserEntity> members = new ArrayList<>();

    public TeamEntity() {
    }

    public TeamEntity(String id, String name) {
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

    public List<UserEntity> getLeaders() {
        return leaders;
    }

    public List<UserEntity> getMembers() {
        return members;
    }
}
