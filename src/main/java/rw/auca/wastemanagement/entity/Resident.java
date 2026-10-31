package rw.auca.wastemanagement.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="resident")
public class Resident {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique=true,nullable=false)
    private String householdCode;
    private String phone,village;

    public Resident(){

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
    public String getHouseholdCode(){
        return householdCode;
    }
    public void setHouseholdCode(String v){
        householdCode=v;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String v){
        phone=v;
    }
    public String getVillage(){
        return village;
    }
    public void setVillage(String v){
        village=v;
    }
}
