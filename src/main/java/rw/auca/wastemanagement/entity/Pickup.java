package rw.auca.wastemanagement.entity;

import jakarta.persistence.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name="pickup")
public class Pickup {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    private LocalDate pickupDate;

    @Enumerated(EnumType.STRING)
    private PickupStatus status;
    private String address;

    @ManyToOne
    @JoinColumn(name="resident_id")
    private Resident resident;

    @ManyToOne
    @JoinColumn(name="collector_id")
    private Collector collector;

    public Pickup(){

    } public UUID getId(){
        return id;
    }
    public void setId(UUID v){
        id=v;
    }
    public LocalDate getPickupDate(){
        return pickupDate;
    }
    public void setPickupDate(LocalDate v){
        pickupDate=v;
    }
    public PickupStatus getStatus(){
        return status;
    }
    public void setStatus(PickupStatus v){
        status=v;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String v){
        address=v;
    }
    public Resident getResident(){
        return resident;
    }
    public void setResident(Resident v){
        resident=v;
    }
    public Collector getCollector(){
        return collector;
    }
    public void setCollector(Collector v){
        collector=v;
    }
}
