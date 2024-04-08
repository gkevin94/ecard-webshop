package com.ecard.ecardwebshop.report;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports/orders")
    public List<OrderReport> getMonthlyIncomeOfOrders(){
        return reportService.getMonthlyIncomeOfOrders();
    }

    @GetMapping("/reports/products")
    public List<ShippedProductReport> getShippedProducts(){
        return reportService.getShippedProducts();
    }

}
