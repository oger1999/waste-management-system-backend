package rw.auca.wastemanagement.repository;

import org.springframework.data.jpa.repository.*;
import rw.auca.wastemanagement.entity.*;
import java.util.*;

public interface WasteTypeRepository extends JpaRepository<WasteType,UUID> {
 @Query
         ("SELECT w FROM WasteType w WHERE w.collectors IS EMPTY")
 List<WasteType> findUnusedWasteTypes();
}
