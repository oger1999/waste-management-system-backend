package rw.auca.wastemanagement.controller;

import org.springframework.web.bind.annotation.*;
import rw.auca.wastemanagement.service.DashboardService;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService s;
    public DashboardController(DashboardService s){
        this.s=s;
    }

    @GetMapping("/summary")
    public Map<String,Long> summary(){
        return s.summary();
    }
}
