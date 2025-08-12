package com.matheusvsdev.dataprocessor.repository;

import com.matheusvsdev.dataprocessor.entities.UserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProjectEntity, String> {
}
