package rw.auca.wastemanagement.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import rw.auca.wastemanagement.entity.*;
import java.util.*;

public interface CollectorRepository extends JpaRepository<Collector,UUID> {

    @Query
            ("SELECT DISTINCT c FROM Collector c JOIN c.wasteTypes w WHERE LOWER(w.name)=LOWER(:name)")
    List<Collector> findByWasteTypeName(
            @Param("name")
            String name);
 boolean existsByTruck_Id(UUID truckId);
}
