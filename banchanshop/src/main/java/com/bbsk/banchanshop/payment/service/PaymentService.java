package com.bbsk.banchanshop.payment.service;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.service.OrdersService;
import com.bbsk.banchanshop.payment.dto.CardStrategy;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrdersService ordersService;
    private final UserService userService;

    /**
     * 결제 진행, 결제 완료 시 주문생성
     * @param card
     * @param userId
     * @param orderType
     */
    public void startPayToOrder(CardStrategy card, String userId, OrderType orderType) {
        if (card.startPayToOrder(card)) {
            /*
            * 결제 성공 후 주문생성
            * */
            ordersService.createOrder(userService.findUserById(userId), orderType, PaymentType.CARD, card.getCardCompany());
        } else {
            throw new IllegalArgumentException("카드 정보를 다시 확인해주세요.");
        }

    }
}
