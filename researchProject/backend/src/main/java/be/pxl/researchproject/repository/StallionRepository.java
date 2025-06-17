package be.pxl.researchproject.repository;

import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Stallion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StallionRepository extends JpaRepository<Stallion, Long> {
    Optional<Stallion> findByName(String name);

    List<Stallion> findByNameStartingWith(String name);

    List<Stallion> findBycolorCodeStartingWith(String colorCode);

}
