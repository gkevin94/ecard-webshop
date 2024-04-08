package com.ecard.ecardwebshop.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DashboardController {

    private DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public Dashboard listOfResults(){
        return dashboardService.listOfResult();
    }
}
