package com.bbsk.banchanshop.payment.dto.card;

import com.bbsk.banchanshop.contant.BankCompany;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class RequestBankDto {
    private BankCompany bankCompany;
    private String accountNumber;
    private String userName;
}
