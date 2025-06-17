package be.pxl.researchproject.repository;

import be.pxl.researchproject.api.response.MareDTO;
import be.pxl.researchproject.domain.Mare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MareRepository extends JpaRepository<Mare, Long> {
    Optional<Mare> findByName(String name);
    List<Mare> findByNameStartingWith(String name);

    Optional<Mare> findByFoalId(Long foalId);
    @Query("SELECT m FROM Mare m WHERE (:isPregnant is null OR m.isPregnant = :isPregnant) AND " +
            "(:dueDateFrom is null OR m.dueDate >= :dueDateFrom) AND " +
            "(:dueDateTo is null OR m.dueDate <= :dueDateTo)")
    List<Mare> filterMares(@Param("isPregnant") Boolean isPregnant,
                              @Param("dueDateFrom") LocalDate dueDateFrom,
                              @Param("dueDateTo") LocalDate dueDateTo);
}
