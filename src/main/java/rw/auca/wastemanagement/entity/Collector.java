package rw.auca.wastemanagement.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="collector")
public class Collector {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String phone;

    @ManyToOne
    @JoinColumn(name="truck_id")
    private Truck truck;

    @ManyToMany
    @JoinTable(
            name="collector_waste_type",
            joinColumns= @JoinColumn(name="collector_id"),
            inverseJoinColumns=@JoinColumn(name="waste_type_id"))
    private List<WasteType> wasteTypes=new ArrayList<>();

    public Collector(){

    }
    public UUID getId(){
        return id;
    }
    public void setId(UUID v){
        id=v;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String v){
        firstName=v;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String v){
        lastName=v;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String v){
        phone=v;
    }
    public Truck getTruck(){
        return truck;
    }
    public void setTruck(Truck v){
        truck=v;
    }
    public List<WasteType> getWasteTypes(){
        return wasteTypes;
    }
    public void setWasteTypes(List<WasteType> v){
        wasteTypes=v;
    }
}
