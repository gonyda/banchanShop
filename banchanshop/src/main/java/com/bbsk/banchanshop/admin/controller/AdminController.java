package com.bbsk.banchanshop.admin.controller;

import com.bbsk.banchanshop.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String orders(Model model, Long orderId, String userId, String orderDate) {

        model.addAttribute("orders", adminService.findAllOrders(orderId
                                                                            // 쿼리에서 사용하기 위해 null 세팅
                                                                            , "".equals(userId) ? null : userId
                                                                            , "".equals(orderDate) ? null : orderDate));

        return "admin/orders";
    }

}
