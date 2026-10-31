package rw.auca.wastemanagement.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.wastemanagement.entity.*;
import rw.auca.wastemanagement.repository.*;
import java.time.*;
import java.util.*;

@Service
public class PickupService {
    private final PickupRepository repo;
    private final WasteLoadRepository loads;
    private final CollectorRepository collectors;

    public PickupService(
            PickupRepository r,
            WasteLoadRepository w,
            CollectorRepository c){
        repo=r;
        loads=w;
        collectors=c;
    }
    public String save(Pickup p){
        if(p.getPickupDate()==null||
                p.getPickupDate().
                        isBefore(LocalDate.now()))
            return "PAST";
        if(p.getCollector()!=null&&
                repo.existsByCollector_IdAndPickupDateAndStatusNot(
                        p.getCollector().getId(),
                        p.getPickupDate(),
                        PickupStatus.SKIPPED))
            return "BUSY";
        p.setStatus(PickupStatus.REQUESTED);
        repo.save(p);
        return "SAVED";
    }
    public List<Pickup> all(){
        return repo.findAll();
    }
    public Optional<Pickup> one(UUID id){
        return repo.findById(id);
    }
    public Optional<Pickup> update(UUID id,Pickup p){
        Optional<Pickup> old=repo.findById(id);
        if(old.isEmpty())
            return Optional.empty();
        Pickup x=old.get();
        x.setPickupDate(p.getPickupDate());
        x.setAddress(p.getAddress());
        x.setResident(p.getResident());
        x.setCollector(p.getCollector());
        return Optional.of(repo.save(x));
    }
    public String delete(UUID id){
        if(!repo.existsById(id))
            return "MISSING";
        if(loads.existsByPickup_Id(id))
            return "LOADS";
        repo.deleteById(id);
        return "DELETED";
    }

    public List<Pickup> byStatus(PickupStatus s){
        return repo.findByStatusOrderByPickupDateAsc(s);
    }
    public List<Pickup> between(LocalDate a,LocalDate b){
        return repo.findByPickupDateBetweenOrderByPickupDateAsc(a,b);
    }
    public String change(UUID id,PickupStatus n){
        Optional<Pickup> f=repo.findById(id);
        if(f.isEmpty())
            return "MISSING";
        PickupStatus o=f.get().getStatus();
        boolean ok=(o==PickupStatus
                .REQUESTED&&(n==PickupStatus
                .ASSIGNED||n==PickupStatus
                .SKIPPED))||(o==PickupStatus
                .ASSIGNED&&(n==PickupStatus
                .COLLECTED||n==PickupStatus
                .SKIPPED));
        if(!ok)
            return "INVALID:"+o+":"+n;
        f.get().setStatus(n);
        repo.save(f.get());
        return "UPDATED";
    }
    public List<Object[]> stats(){
        return repo.countByStatus();
    }
    @Transactional
    public String cancel(UUID id,LocalDate d){
        if(!collectors.existsById(id))
            return "COLLECTOR_MISSING";
        return repo.cancelCollectorDay(id,d)+" pickups cancelled";
    }
    public boolean collectorExists(UUID id){
        return collectors.existsById(id);
    }public Page<Pickup> page(Pageable p){
        return repo.findAll(p);
    }
    public long count(PickupStatus s){
        return repo.countByStatus(s);
    }
    public long today(){
        return repo.findByPickupDate(LocalDate
                .now())
                .size();
    }
}
