package com.bbsk.banchanshop.payment.service.account;

import com.bbsk.banchanshop.payment.service.PaymentStrategy;

public interface AccountStrategy extends PaymentStrategy {

    /**
     * 주문생성 완료 후 은행사에게 출금요청
     */
    void responseResult();

}
