package com.bbsk.banchanshop.payment.controller;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.order.dto.RequestOrderDto;
import com.bbsk.banchanshop.payment.dto.RequestPaymentDto;
import com.bbsk.banchanshop.payment.service.PaymentService;
import com.bbsk.banchanshop.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    private static final String PAYMENT_CARD_URL = "payment/card";
    private static final String PAYMENT_ACCOUNTTRANSFER_URL = "payment/accountTransfer";

    @PostMapping("/form")
    public String paymentForm(RequestOrderDto requestOrderDto, Model model) {

        if(requestOrderDto.getOrderType() == OrderType.ORDER) {
            // 일반주문 일 경우, opder_option 초기화
            requestOrderDto.initOrderOption();
        }

        model.addAttribute("order", requestOrderDto);

        switch (requestOrderDto.getPaymentType()) {
            case CARD -> {
                return PAYMENT_CARD_URL;
            }
            case ACCOUNTTRANSFER -> {
                return PAYMENT_ACCOUNTTRANSFER_URL;
            }
            default -> {
                return "error";
            }
        }
    }

    @PostMapping("/process")
    public String payment(RequestPaymentDto requestPaymentDto, @AuthenticationPrincipal UserEntity user) {

        log.info(requestPaymentDto.toString());
        paymentService.startPayToOrder(requestPaymentDto, user);

        return "redirect:/";
    }
}
