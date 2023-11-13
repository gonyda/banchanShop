package com.bbsk.banchanshop.cart.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserCartItemsDto {

    private Long banchanId; // 반찬 아이디
    private String banchanName; // 반찬이름
    private int banchanPrice; // 반찬가격

    private int banchanQuantity; // 상품 갯수
    private int banchanTotalPrice; // 상품 가격
}
