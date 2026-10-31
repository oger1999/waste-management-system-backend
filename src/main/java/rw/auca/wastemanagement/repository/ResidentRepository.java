package rw.auca.wastemanagement.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import rw.auca.wastemanagement.entity.*;
import java.util.*;

public interface ResidentRepository extends JpaRepository<Resident,UUID> {

    List<Resident> findByVillageIgnoreCaseOrderByLastNameAsc(String village);
    @Query
            ("SELECT DISTINCT p.resident FROM Pickup p WHERE p.collector.id=:id")
    List<Resident> findResidentsByCollectorId(
            @Param("id") UUID id);
}
