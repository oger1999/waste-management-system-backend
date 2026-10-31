package rw.auca.wastemanagement.service;

import org.springframework.stereotype.Service;
import rw.auca.wastemanagement.entity.WasteType;
import rw.auca.wastemanagement.repository.WasteTypeRepository;
import java.util.*;

@Service
public class WasteTypeService {

    private final WasteTypeRepository repository;

    public WasteTypeService(WasteTypeRepository r){
        repository=r;
    }
    public WasteType save(WasteType x){
        return repository.save(x);
    }
    public List<WasteType> findAll(){
        return repository.findAll();
    }
    public Optional<WasteType> findById(UUID id){
        return repository.findById(id);
    }
    public Optional<WasteType> update(UUID id,WasteType x){
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
    public List<WasteType> unused(){
        return repository.findUnusedWasteTypes();
    }
}
