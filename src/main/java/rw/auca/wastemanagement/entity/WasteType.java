package rw.auca.wastemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="waste_type")
public class WasteType {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy="wasteTypes")
    @JsonIgnore
    private List<Collector> collectors=new ArrayList<>();

    public WasteType(){

    }
    public UUID getId(){
        return id;
    }
    public void setId(UUID v){
        id=v;
    }
    public String getName(){
        return name;
    }
    public void setName(String v){
        name=v;
    }
    public List<Collector> getCollectors(){
        return collectors;
    }
    public void setCollectors(List<Collector> v){
        collectors=v;
    }
}
