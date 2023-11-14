package com.bbsk.banchanshop.cart.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class RequestInsertCartDto {

    private Long banchanId;
    private int quantity;

}
