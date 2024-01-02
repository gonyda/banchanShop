package com.bbsk.banchanshop.payment.dto.account;

import com.bbsk.banchanshop.contant.BankCompany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestAccountDto {

    private String userName; // 계좌이체 명의자 이름
    private BankCompany bankCompany; // 계좌이체 은행사
    private String accountNumber; // 계좌번호
    private int accountPw; // 계좌 비밀번호

}
