package com.matheusvsdev.dataprocessor.repository;

import com.matheusvsdev.dataprocessor.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
