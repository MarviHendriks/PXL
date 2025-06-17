package be.pxl.researchproject.repository;

import be.pxl.researchproject.domain.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntry, Long> {
}
