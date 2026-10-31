package rw.auca.wastemanagement.repository;

import org.springframework.data.jpa.repository.*;
import rw.auca.wastemanagement.entity.*;
import java.util.*;

public interface TruckRepository extends JpaRepository<Truck,UUID> {

 @Query
         ("SELECT t FROM Truck t WHERE NOT EXISTS " +
                 "(SELECT c FROM Collector c WHERE c.truck=t) " +
                 "ORDER BY t.truckNumber ASC")
 List<Truck> findFreeTrucks();
 Optional<Truck> findByTruckNumber(int truckNumber);
}
