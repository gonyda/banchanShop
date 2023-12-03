package com.bbsk.banchanshop.payment.service;

public interface PaymentStrategy {

    /**
     * 결제 프로세스 진행 후 결제가 완료되면 주문테이블 INSERT(주문완료)
     *
     * @return
     */
    boolean startPayProcess();
}
