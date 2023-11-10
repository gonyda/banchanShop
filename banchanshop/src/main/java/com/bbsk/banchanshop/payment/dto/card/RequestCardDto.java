package com.bbsk.banchanshop.payment.dto.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCardDto {

    private Long cardNumber;
    private int cardCvc;
    private String userName;

}
