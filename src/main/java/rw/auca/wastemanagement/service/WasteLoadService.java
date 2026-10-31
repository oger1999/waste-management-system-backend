package rw.auca.wastemanagement.service;

import org.springframework.stereotype.Service;
import rw.auca.wastemanagement.entity.*;
import rw.auca.wastemanagement.repository.*;
import java.time.*;
import java.util.*;

@Service
public class WasteLoadService {

    private final WasteLoadRepository repo;
    private final PickupRepository pickups;

    public WasteLoadService(
            WasteLoadRepository r,
            PickupRepository p){
        repo=r;
        pickups=p;
    }
    public String save(
            UUID pickupId,
            WasteLoad load){
        Optional<Pickup> p=pickups.findById(pickupId);
        if(p.isEmpty())
            return "MISSING";
        if(p.get().getStatus()!=PickupStatus.
                COLLECTED)
            return "NOT_COLLECTED";
        load.setPickup(
                p.get());
        load.setWeighedDate(LocalDate
                .now());
        repo.save(load);
        return "SAVED";
    }
    public List<WasteLoad> all(){
        return repo.findAll();
    }
    public List<WasteLoad> history(String code){
        return repo.findResidentWasteLoadHistory(code);
    }
    public boolean delete(UUID id){
        if(!repo.existsById(id))
            return false;
        repo.deleteById(id);
        return true;
    }
}
