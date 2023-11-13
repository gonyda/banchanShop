package com.bbsk.banchanshop.cart.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserCartDto {

    private int cartTotalPrice; // 장바구니에 담긴 상품 총액
    private int cartTotalQuantity; // 장바구니에 담긴 상품 총갯수
}
