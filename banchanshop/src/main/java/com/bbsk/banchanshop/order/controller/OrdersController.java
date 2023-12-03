package com.bbsk.banchanshop.order.controller;

import com.bbsk.banchanshop.cart.dto.ResponseCartDto;
import com.bbsk.banchanshop.cart.service.CartService;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.service.OrdersService;
import com.bbsk.banchanshop.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {

    private final OrdersService ordersService;
    private final CartService cartService;

    @GetMapping("/form")
    public String viewOrder(Model model, @AuthenticationPrincipal UserEntity user) {

        model.addAttribute("cart", new ResponseCartDto().toDto(cartService.findByCartId(user.getUserId())));
        model.addAttribute("user", user);
        model.addAttribute("paymentList", PaymentType.values());

        return "order/orderview";
    }

    @GetMapping("/myorder")
    public String myOrder(@AuthenticationPrincipal UserEntity user, Model model, @RequestParam(required = false, defaultValue = "1") int page) {
        log.info("=== 나의 주문내역 ===");

        OrdersService.MyOrderDto dto = ordersService.findAllByPaging(user.getUserId(), page);
        model.addAttribute("orderList", dto.getList());
        model.addAttribute("totalPage", dto.getTotalPage());
        model.addAttribute("totalPrice", ordersService.getTotalPrice(user.getUserId()));

        return "order/myorder";
    }
}
