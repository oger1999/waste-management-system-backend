package rw.auca.wastemanagement.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.wastemanagement.entity.Truck;
import rw.auca.wastemanagement.service.TruckService;
import java.util.*;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {

 private final TruckService service;

 public TruckController(TruckService s){
     service=s;
 }

 @PostMapping("/save")
 public ResponseEntity<String> save(@RequestBody Truck x){
     service.save(x);
     return ResponseEntity
             .status(201)
             .body("Truck saved successfully");
 }

 @GetMapping("/all")
 public List<Truck> all(){
     return service.findAll();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> get(@PathVariable UUID id){
     Optional<Truck> x=service.findById(id);
     return x.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The truck with that id does not exist"));
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> update(
         @PathVariable UUID id,
         @RequestBody Truck x){
     Optional<Truck> r=service.update(id,x);
     return r.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The truck with that id does not exist"));
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> delete(@PathVariable UUID id){
     return service.delete(id)?ResponseEntity.ok("Truck deleted successfully"):ResponseEntity
             .status(404)
             .body("The truck with that id does not exist");
 }

 @GetMapping("/free")
 public List<Truck> free(){
     return service.free();
 }
}
