package rw.auca.wastemanagement.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.wastemanagement.entity.*;
import rw.auca.wastemanagement.service.CollectorService;
import java.util.*;

@RestController
@RequestMapping("/api/collectors")
public class CollectorController {

 private final CollectorService s;
 public CollectorController(CollectorService s){
     this.s=s;
 }

 @PostMapping("/save")
 public ResponseEntity<String> save(@RequestBody Collector x){
     s.save(x);
     return ResponseEntity
             .status(201)
             .body("Collector saved successfully");
 }

 @GetMapping("/all")
 public List<Collector> all(){
     return s.all();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> get(@PathVariable UUID id){
     Optional<Collector>x=s.one(id);
     return x.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The collector with that id does not exist"));
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> update(@PathVariable UUID id,@RequestBody Collector x){
     Optional<Collector>r=s.update(id,x);
     return r.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The collector with that id does not exist"));
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> delete(@PathVariable UUID id){
     return s.delete(id)?ResponseEntity.ok("Collector deleted successfully"):ResponseEntity
             .status(404)
             .body("The collector with that id does not exist");
 }

 @GetMapping("/by-waste-type")
 public List<Collector> byWaste(@RequestParam String name){
     return s.byWasteType(name);
 }

 @PutMapping("/{collectorId}/waste-types/{wasteTypeId}")
 public ResponseEntity<String> add(@PathVariable UUID collectorId,@PathVariable UUID wasteTypeId){
     String r=s.addWaste(collectorId,wasteTypeId);
     if(r.equals("COLLECTOR_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The collector with that id does not exist");
     if(r.equals("WASTE_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("That waste type does not exist");
     if(r.equals("DUPLICATE"))
         return ResponseEntity
                 .status(409)
                 .body("The collector already has that waste type");
     return ResponseEntity.ok("Waste type added to the collector");
 }

 @PatchMapping("/{collectorId}/truck")
 public ResponseEntity<String> move(
         @PathVariable UUID collectorId,
         @RequestParam int truckNumber){
     String r=s.moveTruck(collectorId,truckNumber);
     if(r.equals("COLLECTOR_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The collector with that id does not exist");
     if(r.equals("TRUCK_NOT_FOUND"))
         return ResponseEntity
                 .status(404)
                 .body("The truck with that truckNumber does not exist");
     if(r.equals("TAKEN"))
         return ResponseEntity
                 .status(409)
                 .body("That truck is taken by another collector");
     return ResponseEntity.ok("Truck changed successfully");
 }
}
