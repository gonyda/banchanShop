package com.bbsk.banchanshop.payment.service.account;

import com.bbsk.banchanshop.contant.BankCompany;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ShinhanBank extends AccountProcess implements AccountStrategy{

    private BankCompany bank;
    private String accountNumber;
    private int accountPw;
    private String userName;

    /**
     * 은행사에게 계좌정보 유효성 체크
     * @param accountNumber
     * @param userName
     * @return
     */
    @Override
    protected boolean checkBankAndAccountNumber(String accountNumber, String userName) {
        System.out.println(bank + " / " + accountNumber + " / " + userName);
        System.out.println("계좌번호 유효성 체크 완료");
        return true;
    }

    /**
     * 은행사에게 결제 승인 요청
     * @return
     */
    @Override
    public boolean requestPay() {
        System.out.println(bank + " 은행사에게 결제요청");
        return true;
    }

    /**
     * 은행사에게 출금 요청
     */
    @Override
    public void responseResult() {
        System.out.println("주문완료 후 " + bank + " 은행사에게 출금요청");
    }

    @Override
    public boolean startPayProcess() {
        return this.processPay(userName, accountNumber);
    }
}
