package com.bbsk.banchanshop.payment.dto;

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

    String getCardCompany();
}
