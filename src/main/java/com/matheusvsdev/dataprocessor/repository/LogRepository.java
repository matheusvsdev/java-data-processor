package com.matheusvsdev.dataprocessor.repository;

import com.matheusvsdev.dataprocessor.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, String> {
}
