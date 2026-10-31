package rw.auca.wastemanagement.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.wastemanagement.entity.Resident;
import rw.auca.wastemanagement.service.ResidentService;
import java.util.*;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

 private final ResidentService service;

 public ResidentController(ResidentService s){
     service=s;
 }

 @PostMapping("/save")
 public ResponseEntity<String> save(@RequestBody Resident x){
     service.save(x);
     return ResponseEntity
             .status(201)
             .body("Resident saved successfully");
 }

 @GetMapping("/all")
 public List<Resident> all(){
     return service.findAll();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> get(@PathVariable UUID id){
     Optional<Resident> x=service.findById(id);
     return x.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The resident with that id does not exist"));
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> update(
         @PathVariable UUID id,
         @RequestBody Resident x){
     Optional<Resident> r=service.update(id,x);
     return r.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The resident with that id does not exist"));
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> delete(@PathVariable UUID id){
     return service.delete(id)?ResponseEntity.ok("Resident deleted successfully"):ResponseEntity
             .status(404)
             .body("The resident with that id does not exist");
 }

 @GetMapping("/by-village")
 public List<Resident> village(@RequestParam String village){
     return service.byVillage(village);
 }

 @GetMapping("/of-collector/{collectorId}")
 public ResponseEntity<?> byCollector(@PathVariable UUID collectorId){
     if(!service.collectorExists(collectorId))
         return ResponseEntity
                 .status(404)
                 .body("The collector with that id does not exist");
     return ResponseEntity.ok(service.byCollector(collectorId));
 }

}
