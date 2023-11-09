package com.bbsk.banchanshop.payment.service;

import com.bbsk.banchanshop.contant.CardCompany;

/**
 * 카드결제를 위한 인터페이스
 */
public interface CardStrategy {

    /**
     * 결제 프로세스 진행 후 결제가 완료되면 주문테이블 INSERT(주문완료)
     *
     * @param card
     * @return
     */
    boolean startPayToOrder(CardStrategy card);

    Long getCardNumber();

    int getCardCvc();

    CardCompany getCardCompany();
}
