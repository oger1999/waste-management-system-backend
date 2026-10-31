package rw.auca.wastemanagement.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.wastemanagement.entity.WasteType;
import rw.auca.wastemanagement.service.WasteTypeService;
import java.util.*;

@RestController
@RequestMapping("/api/waste-types")
public class WasteTypeController {

 private final WasteTypeService service;

 public WasteTypeController(WasteTypeService s){
     service=s;
 }

 @PostMapping("/save")
 public ResponseEntity<String> save(@RequestBody WasteType x){
     service.save(x);
     return ResponseEntity
             .status(201)
             .body("Waste type saved successfully");
 }

 @GetMapping("/all")
 public List<WasteType> all(){
     return service.findAll();
 }

 @GetMapping("/{id}")
 public ResponseEntity<?> get(@PathVariable UUID id){
     Optional<WasteType> x=service.findById(id);
     return x.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The waste type with that id does not exist"));
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> update(
         @PathVariable UUID id,
         @RequestBody WasteType x){
     Optional<WasteType> r=service.update(id,x);
     return r.<ResponseEntity<?>>map(ResponseEntity::ok)
             .orElseGet(()->ResponseEntity
                     .status(404)
                     .body("The waste type with that id does not exist"));
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> delete(@PathVariable UUID id){
     return service.delete(id)?ResponseEntity.ok("Waste type deleted successfully"):ResponseEntity
             .status(404)
             .body("The waste type with that id does not exist");
 }

 @GetMapping("/unused")
 public List<WasteType> unused(){
     return service.unused();
 }
}
