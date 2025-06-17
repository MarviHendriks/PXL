package be.pxl.researchproject.repository;

import be.pxl.researchproject.domain.DiaryEntry;
import be.pxl.researchproject.domain.Foal;
import be.pxl.researchproject.domain.Mare;
import be.pxl.researchproject.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN TRUE ELSE FALSE END FROM Notification n WHERE n.title = :title AND n.date = :date AND ((n.mare IS NULL AND :mare IS NULL) OR n.mare = :mare) AND ((n.foal IS NULL AND :foal IS NULL) OR n.foal = :foal)")
    boolean existsByTitleAndDateAndMareAndFoal(@Param("title") String title, @Param("date") LocalDate date, @Param("mare") Mare mare, @Param("foal") Foal foal);


    List<Notification> findByMare(Mare mare);

    List<Notification> findByFoal(Foal foal);
}
