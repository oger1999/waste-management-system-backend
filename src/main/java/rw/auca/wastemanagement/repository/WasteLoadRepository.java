package rw.auca.wastemanagement.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import rw.auca.wastemanagement.entity.*;
import java.util.*;

public interface WasteLoadRepository extends JpaRepository<WasteLoad,UUID> {

    @Query
            ("SELECT w FROM WasteLoad w WHERE w.pickup.resident.householdCode=:code " +
                    "ORDER BY w.weighedDate DESC")
    List<WasteLoad> findResidentWasteLoadHistory(
            @Param("code") String code);
 boolean existsByPickup_Id(UUID pickupId);
}
