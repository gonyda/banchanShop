package com.bbsk.banchanshop.payment.service;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.dto.OrderOptionDto;
import com.bbsk.banchanshop.order.service.OrdersService;
import com.bbsk.banchanshop.payment.dto.CardStrategy;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrdersService ordersService;
    private final UserService userService;

    /**
     * 결제 진행, 결제 완료 시 주문생성
     * @param payCard
     * @param userId
     * @param orderType
     */
    public void startPayToOrder(String userId, PaymentType paymentType, CardStrategy payCard, OrderType orderType, List<OrderOptionDto> orderOption) {

        switch (paymentType) {
            case CARD:
                payToCard(userId, PaymentType.CARD, payCard, orderType, orderOption);
                break;
            case ACCOUNTTRANSFER:
                payToAccountTransfer();
                break;
            case BANKTRANSFER:
                payToBankTransfer();
                break;
            default:
                throw new IllegalArgumentException("결제방법을 다시 선택해주세요.");
        }
    }

    private void payToCard(String userId, PaymentType paymentType, CardStrategy payCard, OrderType orderType, List<OrderOptionDto> orderOption) {
        if (payCard.startPayToOrder(payCard)) {
            /*
             * 결제 성공 후 주문생성
             * */
            ordersService.createOrder(userService.findUserById(userId), paymentType, payCard.getCardCompany(), orderType, orderOption);
        } else {
            throw new IllegalArgumentException("카드 정보를 다시 확인해주세요.");
        }
    }

    private void payToAccountTransfer() {
    }

    private void payToBankTransfer() {
    }
}
