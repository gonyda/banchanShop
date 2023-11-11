package com.bbsk.banchanshop.payment.service;

import com.bbsk.banchanshop.contant.BankCompany;
import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.dto.OrderOptionDto;
import com.bbsk.banchanshop.order.service.OrdersService;
import com.bbsk.banchanshop.payment.service.account.AccountStrategy;
import com.bbsk.banchanshop.payment.service.card.CardStrategy;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final OrdersService ordersService;
    private final UserService userService;

    public void startPayToOrder(String userId, PaymentType paymentType, PaymentStrategy payment, OrderType orderType, List<OrderOptionDto> orderOption) {
        switch (paymentType) {
            case CARD -> {
                startPayToOrderByCard(userId, paymentType, (CardStrategy) payment, orderType, orderOption);
            }
            case ACCOUNTTRANSFER -> {
                startPayToOrderByAccount(userId, paymentType, (AccountStrategy) payment, orderType, orderOption);
            }
        }
    }

    /**
     * 카드 결제 진행, 결제 완료 시 주문생성
     * @param userId 결제 유저
     * @param paymentType 결제 방식
     * @param card 결제 카드
     * @param orderType 주문 방식
     * @param orderOption 주문 옵션
     */
    @Transactional
    public void startPayToOrderByCard(String userId, PaymentType paymentType, CardStrategy card, OrderType orderType, List<OrderOptionDto> orderOption) {
        if (card.startPayProcess()) {
            /*
             * 결제 성공 후 주문생성
             * */
            ordersService.createOrder(userService.findUserById(userId), paymentType, card.getCardCompany().name(), orderType, orderOption);
        } else {
            throw new IllegalArgumentException("결제를 진행하는 중에 오류가 발생했습니다. 잠시후 다시 시도해주세요.");
        }
    }

    /**
     * 결제 - 계좌이체
     * 결제 승인 요청 후 주문 생성
     * 주문 생성 후 은행사에게 출금 요청
     * @param userId 결제 유저
     * @param paymentType 결제 방식
     * @param bank 계좌이체 은행
     * @param orderType 주문 방식
     * @param orderOption 주문 옵션
     */
    @Transactional
    public void startPayToOrderByAccount(String userId, PaymentType paymentType, AccountStrategy bank, OrderType orderType, List<OrderOptionDto> orderOption) {
        if (bank.startPayProcess()) {
            /*
            * 결제승인 요청 후 주문생성
            * */
            ordersService.createOrder(userService.findUserById(userId), paymentType, bank.getBankCompany().name(), orderType, orderOption);

            /*
            * 주문생성 후 은행사에게 출금요청
            * */
            bank.responseResult();
        } else {
            throw new IllegalArgumentException("결제를 진행하는 중에 오류가 발생했습니다. 잠시후 다시 시도해주세요.");
        }
    }
}
