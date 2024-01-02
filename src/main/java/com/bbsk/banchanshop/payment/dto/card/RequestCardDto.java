package com.bbsk.banchanshop.payment.dto.card;

import com.bbsk.banchanshop.contant.CardCompany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestCardDto {

    private CardCompany cardCompany; // 카드사

    private Long cardNumber; // 카드번호
    private int cardPw; // 카드비밀번호
    private int cardCVC; // 카드 CVC

}
