package rw.auca.wastemanagement.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.wastemanagement.entity.*;
import java.time.*;
import java.util.*;

public interface PickupRepository extends JpaRepository<Pickup,UUID> {

    List<Pickup> findByStatusOrderByPickupDateAsc(PickupStatus status);
    List<Pickup> findByPickupDateBetweenOrderByPickupDateAsc(LocalDate start,LocalDate end);

    boolean existsByCollector_IdAndPickupDateAndStatusNot(
            UUID collectorId,
            LocalDate pickupDate,
            PickupStatus status
    );

    @Query
            ("SELECT p.status, COUNT(p) FROM Pickup p GROUP BY p.status")
    List<Object[]> countByStatus();

    @Modifying
    @Transactional
    @Query
            ("UPDATE Pickup p SET p.status=rw.auca.wastemanagement" +
                    ".entity.PickupStatus.SKIPPED WHERE " +
                    "p.collector.id=:collectorId AND " +
                    "p.pickupDate=:date AND " +
                    "p.status<>rw.auca.wastemanagement.entity.PickupStatus.COLLECTED")

    int cancelCollectorDay(
            @Param("collectorId") UUID collectorId,
            @Param("date") LocalDate date);
    long countByStatus(PickupStatus status);
    List<Pickup> findByPickupDate(LocalDate date);
}
