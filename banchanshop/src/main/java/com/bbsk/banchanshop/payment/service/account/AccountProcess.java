package com.bbsk.banchanshop.payment.service.account;

import com.bbsk.banchanshop.payment.service.card.CardStrategy;

public abstract class AccountProcess {

    /**
     * 결제 프로세스 순서
     * @param bank
     * @return
     */
    public boolean processPay(AccountStrategy bank) {
        if (checkBankAndAccountNumber(bank.getAccountNumber(), bank.getUserName())) {
            return requestPay();
        } else {
            return false;
        }
    }

    /**
     * 은행사 - 계좌번호 유효성 체크
     * @param bankName
     * @param accountNumber
     * @param userName
     * @return
     */
    protected abstract boolean checkBankAndAccountNumber( String accountNumber, String userName);

    /**
     * 은행사에게 결제요청
     */
    protected abstract boolean requestPay();
}
