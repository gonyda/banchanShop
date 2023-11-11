package com.bbsk.banchanshop.payment.service.card;

import com.bbsk.banchanshop.contant.CardCompany;
import com.bbsk.banchanshop.payment.service.PaymentStrategy;

/**
 * 카드결제를 위한 인터페이스
 */
public interface CardStrategy extends PaymentStrategy {

    Long getCardNumber();

    int getCardCvc();

    CardCompany getCardCompany();
}
