package com.bbsk.banchanshop.admin.controller;

import com.bbsk.banchanshop.admin.dto.ResponseOrdersDto;
import com.bbsk.banchanshop.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("info", adminService.getFindDashboardInfo());
        return "admin/dashboard";
    }

    @GetMapping("/orders")
    public String orders(Model model
            , @RequestParam(name = "orderId", required = false) String orderId
            , @RequestParam(name = "orderDate", required = false) LocalDate orderDate
            , @RequestParam(name = "userId", required = false) String userId
            , @RequestParam(name = "banchanName", required = false) String banchanName) {

        model.addAttribute("orders", adminService.findAllOrders());

        return "admin/orders";
    }
}
