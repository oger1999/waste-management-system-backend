package rw.auca.wastemanagement.service;

import org.springframework.stereotype.Service;
import rw.auca.wastemanagement.entity.PickupStatus;
import rw.auca.wastemanagement.repository.*;
import java.util.*;

@Service
public class DashboardService {

    private final ResidentRepository residents;
    private final CollectorRepository collectors;
    private final TruckRepository trucks;
    private final PickupService pickups;
    private final WasteLoadRepository loads;

    public DashboardService(
            ResidentRepository r,
            CollectorRepository c,
            TruckRepository t,
            PickupService p,
            WasteLoadRepository w){
        residents=r;
        collectors=c;
        trucks=t;
        pickups=p;
        loads=w;
    }

    public Map<String,Long> summary(){
        Map<String,Long> m=new LinkedHashMap<>();
        m.put("residents",residents.count());
        m.put("collectors",collectors.count());
        m.put("freeTrucks",(long)trucks.findFreeTrucks().size());
        m.put("pickupsToday",pickups.today());
        m.put("requested",pickups.count(PickupStatus.REQUESTED));
        m.put("wasteLoads",loads.count());
        return m;
    }
}
