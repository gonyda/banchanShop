package com.bbsk.banchanshop.admin.controller;

import com.bbsk.banchanshop.admin.dto.RequestBancanDto;
import com.bbsk.banchanshop.admin.dto.RequestBanchanIngredienDto;
import com.bbsk.banchanshop.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String orders(Model model, Long orderId, String userId, String orderDate, String banchanName) {

        model.addAttribute("orders", adminService.findAllOrders(orderId
                                                                            // 쿼리에서 사용하기 위해 null 세팅
                                                                            , "".equals(userId) ? null : userId
                                                                            , "".equals(orderDate) ? null : orderDate
                                                                            , "".equals(banchanName) ? null : banchanName));

        return "admin/orders";
    }

    @GetMapping("/management")
    public String management(Model model) {

        model.addAttribute("banchanList", adminService.findAllBanchan());
        return "admin/banchanManagement";
    }

    @PutMapping("/banchan/{id}/name/{newName}")
    public ResponseEntity<?> updateBanchanName(@PathVariable("id") Long banchanId, @PathVariable("newName") String newName) {
        try {
            adminService.updateBanchanName(banchanId, newName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/banchan/{id}/quantity/{newQuantity}")
    public ResponseEntity<?> updateBanchanQuantity(@PathVariable("id") Long banchanId, @PathVariable("newQuantity") int newQuantity) {
        try {
            adminService.updateBanchanQuantity(banchanId, newQuantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/banchan/{id}/price/{newPrice}")
    public ResponseEntity<?> updateBanchanPrice(@PathVariable("id") Long banchanId, @PathVariable("newPrice") int newPrice) {
        try {
            adminService.updateBanchanPrice(banchanId, newPrice);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/banchan")
    public String banchan() {

        return "admin/banchan";
    }

    @PostMapping("/banchan")
    public String postBanchan(@ModelAttribute RequestBancanDto bancanDto, RedirectAttributes ra) {

        adminService.saveBanchan(bancanDto);

        /*
        * 반찬 등록 성공
        * */
        ra.addFlashAttribute("isSuccsee", true);

        return "redirect:/admin/banchan";
    }
}
