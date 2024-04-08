package com.ecard.ecardwebshop.report;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private ReportDao reportDao;

    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public List<OrderReport> getMonthlyIncomeOfOrders() {
        return reportDao.getMonthlyIncomeOfOrders();
    }

    public List<ShippedProductReport> getShippedProducts() {
        return reportDao.getShippedProducts();
    }
}
