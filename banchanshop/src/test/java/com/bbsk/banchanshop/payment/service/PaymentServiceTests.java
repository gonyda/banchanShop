package com.bbsk.banchanshop.payment.service;

import com.bbsk.banchanshop.order.service.OrdersService;
import com.bbsk.banchanshop.payment.dto.KakaoCard;
import com.bbsk.banchanshop.payment.dto.RequestCardDto;
import com.bbsk.banchanshop.payment.dto.ShinhanCard;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class PaymentServiceTests {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private PaymentService paymentService;

    @DisplayName("결제 테스트")
    @Test
    public void payment() {
      log.info(" ==== 결제 테스트 ==== ");
        // ================== 컨트롤러에서 받은 Dto
        RequestCardDto dto = new RequestCardDto();
        dto.setCardNumber(11111L);
        dto.setCardCvc(111);
        // ==================

        // ================== 컨트롤러에서 받은 Dto
        RequestCardDto dto1 = new RequestCardDto();
        dto1.setCardNumber(123456789011L);
        dto1.setCardCvc(123);
        dto1.setUserName("백승권");
        // ==================

        // 카카오페이
        paymentService.startPayToOrder(KakaoCard.builder()
                                            .cardNumber(dto.getCardNumber())
                                            .cardCvc(dto.getCardCvc())
                                            .build());
        // 신한카드
        paymentService.startPayToOrder(ShinhanCard.builder()
                                              .cardNumber(dto1.getCardNumber())
                                              .cardCvc(dto1.getCardCvc())
                                              .userName(dto1.getUserName())
                                              .build());

    }
}