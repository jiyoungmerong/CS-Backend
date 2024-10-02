package com.project.ity.domain.cs.repository;

import com.project.ity.domain.cs.dto.CS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CsRepository extends JpaRepository<CS, Long> {
    Optional<CS> findById(Long id);
}
