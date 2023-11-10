package com.bbsk.banchanshop.payment.service.account;

import com.bbsk.banchanshop.contant.BankCompany;
import com.bbsk.banchanshop.payment.service.card.CardStrategy;

public interface AccountStrategy {

    /**
     * 결제프로세스 진행
     * 1. 은행사에게 계좌 정보 유효성 체크
     * 2. 은행사에게 결제승인 요청
     * 3. 주문생성
     * 4. 은행사에게 출금 요청
     * @param bank
     * @return
     */
    boolean startPayProcess(AccountStrategy bank);

    /**
     * 주문생성 완료 후 은행사에게 출금요청
     * @return
     */
    void responseResult();

    String getAccountNumber();

    String getUserName();

    BankCompany getBankCompany();
}
