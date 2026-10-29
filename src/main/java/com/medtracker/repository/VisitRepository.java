package com.medtracker.repository;

import com.medtracker.model.Visit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @EntityGraph(attributePaths = "medicines")
    List<Visit> findAllByOrderByVisitDateDescIdDesc();

    @Override
    @EntityGraph(attributePaths = "medicines")
    Optional<Visit> findById(Long id);
}
