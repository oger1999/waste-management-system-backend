package rw.auca.wastemanagement.controller;

import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.wastemanagement.entity.*;
import rw.auca.wastemanagement.service.PickupService;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/api/pickups")
public class PickupController {

 private final PickupService s;
 public PickupController(PickupService s){
     this.s=s;
 }

 @PostMapping("/save")
 public ResponseEntity<String> save(@RequestBody Pickup p){
     String r=s.save(p);
     if(r.equals("PAST"))
         return ResponseEntity
                 .badRequest()
                 .body("Pickup date cannot be in the past");
     if(r.equals("BUSY"))
         return ResponseEntity
                 .status(409)
                 .body("Collector already has a pickup on that date");
     return ResponseEntity
             .status(201)
             .body("Pickup saved successfully");
 }

 @GetMapping("/all")
 public List<Pickup> all(){
     return s.all();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> get(@PathVariable UUID id){
     Optional<Pickup>x=s.one(id);
     return x.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The pickup with that id does not exist"));
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> update(
         @PathVariable UUID id,
         @RequestBody Pickup p){
     Optional<Pickup>x=s.update(id,p);
     return x.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The pickup with that id does not exist"));
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> delete(@PathVariable UUID id){
     String r=s.delete(id);
     if(r.equals("MISSING"))
         return ResponseEntity
                 .status(404)
                 .body("The pickup with that id does not exist");
     if(r.equals("LOADS"))
         return ResponseEntity
                 .status(409)
                 .body("That pickup has waste loads, delete them first");
     return ResponseEntity.ok("Pickup deleted successfully");
 }

 @GetMapping("/by-status")
 public List<Pickup> byStatus(@RequestParam PickupStatus status){
     return s.byStatus(status);
 }

 @GetMapping("/between")
 public List<Pickup> between(
         @RequestParam String start,
         @RequestParam String end){
     return s.between(LocalDate.parse(start),LocalDate.parse(end));
 }

 @PatchMapping("/{id}/status")
 public ResponseEntity<String> change(
         @PathVariable UUID id,
         @RequestParam PickupStatus status){
     String r=s.change(id,status);
     if(r.equals("MISSING"))
         return ResponseEntity
                 .status(404)
                 .body("The pickup with that id does not exist");
     if(r.startsWith("INVALID")){String[] a=r.split(":");
         return ResponseEntity
                 .badRequest()
                 .body("Cannot change status from "+a[1]+" to "+a[2]);
     }
     return ResponseEntity.ok("Pickup status updated successfully");
 }

 @GetMapping("/stats/by-status")
 public List<Object[]> stats(){
     return s.stats();
 }

 @PatchMapping("/cancel-day")
 public ResponseEntity<String> cancel(
         @RequestParam UUID collectorId,
         @RequestParam String date){
     String r=s.cancel(collectorId,LocalDate.parse(date));
     if(r.equals("COLLECTOR_MISSING"))
         return ResponseEntity
                 .status(404)
                 .body("The collector with that id does not exist");
     return ResponseEntity.ok(r);
 }

 @GetMapping("/page")
 public Page<Pickup> page(
         @RequestParam(defaultValue="0") int page,
         @RequestParam(defaultValue="5") int size,
         @RequestParam(defaultValue="pickupDate,desc") String sort){
     String[] a=sort.split(",",2);
     Sort.Direction d=a.length>1&&a[1].equalsIgnoreCase("asc")?Sort.Direction.ASC:Sort.Direction.DESC;
     return s.page(PageRequest.of(page,size,Sort.by(d,a[0])));
 }

}
