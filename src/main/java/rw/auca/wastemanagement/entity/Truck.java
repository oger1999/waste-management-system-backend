package rw.auca.wastemanagement.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="truck")
public class Truck {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    private int truckNumber;
    private String plateNumber;

    public Truck(){

    }
    public UUID getId(){
        return id;
    }
    public void setId(UUID v){
        id=v;
    }
    public int getTruckNumber(){
        return truckNumber;
    }
    public void setTruckNumber(int v){
        truckNumber=v;
    }
    public String getPlateNumber(){
        return plateNumber;
    }
    public void setPlateNumber(String v){
        plateNumber=v;
    }
}
