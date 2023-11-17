package com.bbsk.banchanshop.payment.controller;

import com.bbsk.banchanshop.order.dto.RequestOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private static final String PAYMENT_CARD_URL = "payment/card";
    private static final String PAYMENT_ACCOUNTTRANSFER_URL = "payment/accountTransfer";

    @PostMapping("/form")
    public String paymentForm(RequestOrderDto requestOrderDto, Model model) {

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
}
