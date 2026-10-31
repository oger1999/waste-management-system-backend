package rw.auca.wastemanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.wastemanagement.entity.*;
import rw.auca.wastemanagement.repository.*;
import java.util.*;

@Service
public class CollectorService {
    private final CollectorRepository repo;
    private final WasteTypeRepository waste;
    private final TruckRepository trucks;

    public CollectorService(
            CollectorRepository r,
            WasteTypeRepository w,
            TruckRepository t){
        repo=r;
        waste=w;
        trucks=t;
    }
    public Collector save(Collector x){
        return repo.save(x);
    }
    public List<Collector> all(){
        return repo.findAll();
    }
    public Optional<Collector> one(UUID id){
        return repo.findById(id);
    }
    public Optional<Collector> update(
            UUID id,
            Collector x){
        if(!repo.existsById(id))
            return Optional.empty();
        x.setId(id);
        return Optional.of(repo.save(x));
    }
    public boolean delete(UUID id){
        if(!repo.existsById(id))
            return false;
        repo.deleteById(id);
        return true;
    }
    public List<Collector> byWasteType(String name){
        return repo.findByWasteTypeName(name);
    }

    @Transactional
    public String addWaste(UUID cid,UUID wid){
        Optional<Collector> c=repo.findById(cid);
        if(c.isEmpty())
            return "COLLECTOR_NOT_FOUND";
        Optional<WasteType>w=waste.findById(wid);
        if(w.isEmpty())
            return "WASTE_NOT_FOUND";
        if(c.get().getWasteTypes()
                .stream()
                .anyMatch(x->x.getId()
                        .equals(wid)))
            return "DUPLICATE";
        c.get().getWasteTypes().add(w.get());
        repo.save(c.get());
        return "ADDED";
    }
 @Transactional
 public String moveTruck(UUID cid,int num){
        Optional<Collector> c=repo.findById(cid);
        if(c.isEmpty())
            return "COLLECTOR_NOT_FOUND";
        Optional<Truck> t=trucks.findByTruckNumber(num);
        if(t.isEmpty())
            return "TRUCK_NOT_FOUND";
        if(repo.existsByTruck_Id(t.get().getId())&&(c.get().getTruck()==null||
                !c.get().getTruck().getId().equals(t.get().getId())))
            return "TAKEN";
        c.get().setTruck(t.get());
        repo.save(c.get());
        return "CHANGED";
    }
}
