package com.matheusvsdev.dataprocessor.repository;

import com.matheusvsdev.dataprocessor.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {

    Optional<TeamEntity> findByName(String name);

    List<TeamEntity> findByNameIn(Collection<String> names);
}
