package rw.auca.wastemanagement.service;

import org.springframework.stereotype.Service;
import rw.auca.wastemanagement.entity.Resident;
import rw.auca.wastemanagement.repository.CollectorRepository;
import rw.auca.wastemanagement.repository.ResidentRepository;
import java.util.*;

@Service
public class ResidentService {
    private final ResidentRepository repository;
    private final CollectorRepository collectors;

    public ResidentService(
            ResidentRepository r,
            CollectorRepository c){
        repository=r;
        collectors=c;
    }

    public Resident save(Resident x){
        return repository.save(x);
    }
    public List<Resident> findAll(){
        return repository.findAll();
    }
    public Optional<Resident> findById(UUID id){
        return repository.findById(id);
    }
    public Optional<Resident> update(UUID id,Resident x){
        if(!repository.existsById(id))
            return Optional.empty();
        x.setId(id);
        return Optional.of(repository.save(x));
    }
    public boolean delete(UUID id){
        if(!repository.existsById(id))
            return false;
        repository.deleteById(id);
        return true;
    }
    public List<Resident> byVillage(String v){
        return repository.findByVillageIgnoreCaseOrderByLastNameAsc(v);
    }
    public List<Resident> byCollector(UUID id){
        return repository.findResidentsByCollectorId(id);
    }
    public boolean collectorExists(UUID id){
        return collectors.existsById(id);
    }
}
