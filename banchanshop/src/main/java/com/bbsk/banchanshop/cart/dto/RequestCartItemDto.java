package com.bbsk.banchanshop.cart.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class RequestCartItemDto {

    private Long banchanId;
    private int quantity;

}
