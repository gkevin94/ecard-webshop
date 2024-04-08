package com.ecard.ecardwebshop;

import com.ecard.ecardwebshop.report.ShippedProductReport;
import com.ecard.ecardwebshop.report.OrderReport;
import com.ecard.ecardwebshop.report.ReportController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ReportTest {


        @Autowired
        ReportController reportController;


        @Test
        public void shippedProductReportTest(){
            int price = 97000;
            int count = 3;
            int total = price*count;
            ShippedProductReport spr = new ShippedProductReport(2019, 6, "Shark", price, count, total);

            assertEquals(spr.getYear(), 2019);
            assertEquals(spr.getMonth(), 6);
            assertEquals(spr.getProductname(), "Shark");
            assertEquals(spr.getPrice(), 97000);
            assertEquals(spr.getCount(), 3);
            assertEquals(spr.getTotal(), 291000);
        }

        @Test
        public void orderRiportTest(){
            OrderReport or = new OrderReport(2019, 2, "ACTIVE", 210000, 1);
            assertEquals(or.getYear(), 2019);
            assertEquals(or.getMonth(), 2);
            assertEquals(or.getOrderStatus(), "ACTIVE");
            assertEquals(or.getTotal(), 210000);
            assertEquals(or.getCount(), 1);
        }

        @Test
        public void contextLoads(){

            // When (examining the content)
            List<OrderReport> orderReports = reportController.getMonthlyIncomeOfOrders();
            List<ShippedProductReport> shippedProductReports = reportController.getShippedProducts();

            //Then (both lists have content)
            boolean orderReportIsNotEmpty = !orderReports.equals(Collections.emptyList());
            boolean shippedProductReportIsNotEmpty= !shippedProductReports.equals(Collections.emptyList());


            assertTrue(orderReportIsNotEmpty);
            assertTrue(shippedProductReportIsNotEmpty);

        }



}
