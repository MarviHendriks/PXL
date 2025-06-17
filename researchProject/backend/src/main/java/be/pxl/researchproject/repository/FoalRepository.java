package be.pxl.researchproject.repository;

import be.pxl.researchproject.domain.Foal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoalRepository extends JpaRepository<Foal, Long> {
    Optional<Foal> findByName(String name);
    List<Foal> findByNameStartingWith(String name);
}