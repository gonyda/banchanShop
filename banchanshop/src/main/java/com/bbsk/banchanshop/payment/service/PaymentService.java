package com.bbsk.banchanshop.payment.service;

import com.bbsk.banchanshop.contant.BankCompany;
import com.bbsk.banchanshop.contant.CardCompany;
import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.service.OrdersService;
import com.bbsk.banchanshop.payment.dto.RequestPaymentDto;
import com.bbsk.banchanshop.payment.service.account.AccountStrategy;
import com.bbsk.banchanshop.payment.service.account.KookminBank;
import com.bbsk.banchanshop.payment.service.account.ShinhanBank;
import com.bbsk.banchanshop.payment.service.card.CardStrategy;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final OrdersService ordersService;
    private final UserService userService;

    @Transactional
    public void startPayToOrder(RequestPaymentDto requestPaymentDto, UserEntity user) {

        switch (requestPaymentDto.getPaymentType()) {
            case CARD -> {
                startPayToOrderByCard(requestPaymentDto, user);
            }
            case ACCOUNTTRANSFER -> {
                startPayToOrderByAccount(requestPaymentDto, user);
            }
        }
    }

    /**
     * 결제 - 계좌이체
     * 결제 승인 요청 후 주문 생성
     * 주문 생성 후 은행사에게 출금 요청
     *
     * @param requestPaymentDto
     * @param user
     */
    @Transactional
    private void startPayToOrderByAccount(RequestPaymentDto requestPaymentDto, UserEntity user) {
        AccountStrategy accountPayment = (AccountStrategy) getPayment(requestPaymentDto);

        if (accountPayment.startPayProcess()) {
            /*
             * 결제승인 요청 후 주문생성
             * */
            ordersService.createOrder(user.getUserId(), requestPaymentDto.getPaymentType(), requestPaymentDto.getAccount().getBankCompany().name(), requestPaymentDto.getRequestOrderDto().getOrderType(), requestPaymentDto.getRequestOrderDto().getRequestOrderOptionDto());

            /*
             * 주문생성 후 은행사에게 출금요청
             * */
            accountPayment.responseResult();
        } else {
            throw new IllegalArgumentException("결제를 진행하는 중에 오류가 발생했습니다. 잠시후 다시 시도해주세요.");
        }
    }

    /**
     * 카드 결제 진행, 결제 완료 시 주문생성
     *
     * @param requestPaymentDto
     * @param user
     */
    @Transactional
    public void startPayToOrderByCard(RequestPaymentDto requestPaymentDto, UserEntity user) {
        CardStrategy cardPayment = (CardStrategy) getPayment(requestPaymentDto);
        if (cardPayment.startPayProcess()) {
            /*
             * 결제 성공 후 주문생성
             * */
            ordersService.createOrder(user.getUserId(), requestPaymentDto.getPaymentType(), requestPaymentDto.getCard().getCardCompany().name(), requestPaymentDto.getRequestOrderDto().getOrderType(), requestPaymentDto.getRequestOrderDto().getRequestOrderOptionDto());
        } else {
            throw new IllegalArgumentException("결제를 진행하는 중에 오류가 발생했습니다. 잠시후 다시 시도해주세요.");
        }
    }

    private PaymentStrategy getPayment(RequestPaymentDto requestPaymentDto) {
        /*
        * 카드결제
        * */
        if(requestPaymentDto.getPaymentType() == PaymentType.CARD) {
            if(requestPaymentDto.getCard().getCardCompany() == CardCompany.SHINHANCARD) {

            } else if (requestPaymentDto.getCard().getCardCompany() == CardCompany.KAKAOPAY) {

            }

        /*
        * 계좌이체
        * */
        } else if (requestPaymentDto.getPaymentType() == PaymentType.ACCOUNTTRANSFER) {
            if(requestPaymentDto.getAccount().getBankCompany() == BankCompany.KOOKMINBANK) {
                return KookminBank.builder()
                        .userName(requestPaymentDto.getAccount().getUserName())
                        .accountNumber(requestPaymentDto.getAccount().getAccountNumber())
                        .accountPw(requestPaymentDto.getAccount().getAccountPw())
                        .bank(requestPaymentDto.getAccount().getBankCompany())
                        .build();
            } else if (requestPaymentDto.getAccount().getBankCompany() == BankCompany.SHINHANBANK) {
                return ShinhanBank.builder()
                        .userName(requestPaymentDto.getAccount().getUserName())
                        .accountNumber(requestPaymentDto.getAccount().getAccountNumber())
                        .accountPw(requestPaymentDto.getAccount().getAccountPw())
                        .bank(requestPaymentDto.getAccount().getBankCompany())
                        .build();
            }
        }

        throw new IllegalArgumentException("결제방법이 올바르지 않습니다. 다시 확인해주세요.");
    }
}
