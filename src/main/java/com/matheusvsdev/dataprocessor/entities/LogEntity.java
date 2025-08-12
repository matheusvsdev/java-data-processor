package com.matheusvsdev.dataprocessor.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_logs")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String date;
    private String action;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public LogEntity() {
    }

    public LogEntity(String id, String date, String action, UserEntity user) {
        this.id = id;
        this.date = date;
        this.action = action;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
