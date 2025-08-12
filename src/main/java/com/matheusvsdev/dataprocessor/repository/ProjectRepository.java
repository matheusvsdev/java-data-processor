package com.matheusvsdev.dataprocessor.repository;

import com.matheusvsdev.dataprocessor.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

    Optional<ProjectEntity> findByName(String name);

    List<ProjectEntity> findByNameIn(Collection<String> names);
}
