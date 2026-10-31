package rw.auca.wastemanagement.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import rw.auca.wastemanagement.entity.WasteLoad;
import rw.auca.wastemanagement.service.WasteLoadService;
import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/waste-loads")
public class WasteLoadController {

    private final WasteLoadService s;

    public WasteLoadController(WasteLoadService s){
        this.s=s;
    }

 @PostMapping("/save")
 public ResponseEntity<String> save(
         @RequestParam UUID pickupId,
         @RequestBody WasteLoad w){
        String r=s.save(pickupId,w);
        if(r.equals("MISSING"))
            return ResponseEntity
                    .status(404)
                    .body("The pickup with that id does not exist");
        if(r.equals("NOT_COLLECTED"))
            return ResponseEntity
                    .badRequest()
                    .body("Waste loads are only for COLLECTED pickups");
        return ResponseEntity
                .status(201)
                .body("Waste load saved successfully");
    }

 @GetMapping("/all")
 public List<WasteLoad> all(){
        return s.all();
    }

 @GetMapping("/resident/{householdCode}")
 public List<WasteLoad> history(@PathVariable String householdCode){
        return s.history(householdCode);
    }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<String> delete(@PathVariable UUID id){
        return s.delete(id)?ResponseEntity.ok("Waste load deleted successfully"):ResponseEntity
                .status(404)
                .body("The waste load with that id does not exist");
    }
}
