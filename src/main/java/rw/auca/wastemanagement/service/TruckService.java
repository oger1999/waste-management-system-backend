package rw.auca.wastemanagement.service;

import org.springframework.stereotype.Service;
import rw.auca.wastemanagement.entity.Truck;
import rw.auca.wastemanagement.repository.TruckRepository;
import java.util.*;

@Service
public class TruckService {
    private final TruckRepository repository;

    public TruckService(TruckRepository r){
        repository=r;
    }
    public Truck save(Truck x){
        return repository.save(x);
    }
    public List<Truck> findAll(){
        return repository.findAll();
    }
    public Optional<Truck> findById(UUID id){
        return repository.findById(id);
    }
    public Optional<Truck> update(UUID id,Truck x){
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
    public List<Truck> free(){
        return repository.findFreeTrucks();
    }
    public Optional<Truck> byNumber(int n){
        return repository.findByTruckNumber(n);
    }
}
