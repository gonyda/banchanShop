package com.bbsk.banchanshop.payment.dto;

import com.bbsk.banchanshop.contant.BankCompany;
import com.bbsk.banchanshop.contant.CardCompany;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.dto.RequestOrderDto;
import com.bbsk.banchanshop.payment.dto.account.RequestAccountDto;
import com.bbsk.banchanshop.payment.dto.card.RequestCardDto;
import com.bbsk.banchanshop.payment.service.PaymentStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RequestPaymentDto {

    private PaymentType paymentType; // 결제 방법

    private RequestAccountDto account; // 계좌이체 정보
    private RequestCardDto card; // 카드결제 정보

    private RequestOrderDto requestOrderDto; // 주문정보
}
