package rw.auca.wastemanagement.entity;

import jakarta.persistence.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name="waste_load")
public class WasteLoad {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    private String material;
    private String note;
    private int weightKg;
    private LocalDate weighedDate;

    @ManyToOne
    @JoinColumn(name="pickup_id")
    private Pickup pickup;

    public WasteLoad(){

    }
    public UUID getId(){
        return id;
    }
    public void setId(UUID v){
        id=v;
    }
    public String getMaterial(){
        return material;
    }
    public void setMaterial(String v){
        material=v;
    }
    public String getNote(){
        return note;
    }
    public void setNote(String v){
        note=v;
    }
    public int getWeightKg(){
        return weightKg;
    }
    public void setWeightKg(int v){
        weightKg=v;
    }
    public LocalDate getWeighedDate(){
        return weighedDate;
    }
    public void setWeighedDate(LocalDate v){
        weighedDate=v;
    }
    public Pickup getPickup(){
        return pickup;
    }
    public void setPickup(Pickup v){
        pickup=v;
    }
}
