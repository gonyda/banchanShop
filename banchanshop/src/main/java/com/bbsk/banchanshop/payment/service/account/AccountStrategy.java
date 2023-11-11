package com.bbsk.banchanshop.payment.service.account;

import com.bbsk.banchanshop.contant.BankCompany;
import com.bbsk.banchanshop.payment.service.PaymentStrategy;

public interface AccountStrategy extends PaymentStrategy {

    /**
     * 주문생성 완료 후 은행사에게 출금요청
     * @return
     */
    void responseResult();

    String getAccountNumber();

    String getUserName();

    BankCompany getBankCompany();

}
