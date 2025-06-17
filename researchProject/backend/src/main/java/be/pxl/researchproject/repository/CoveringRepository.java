package be.pxl.researchproject.repository;

import be.pxl.researchproject.domain.Covering;
import be.pxl.researchproject.domain.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoveringRepository extends JpaRepository<Covering, Long> {
}
