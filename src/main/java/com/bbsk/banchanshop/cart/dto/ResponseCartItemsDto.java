package com.bbsk.banchanshop.cart.dto;

import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import lombok.*;

@Getter
@ToString
public class ResponseCartItemsDto {

    private ResponseBanchanDto responseBanchanDto;
    private Long cartItemId; // 장바구니 아이템 아이디
    private int banchanQuantity; // 상품 갯수
    private int banchanTotalPrice; // 상품 가격

    public ResponseCartItemsDto(CartItemEntity cartItem) {
        this.cartItemId = cartItem.getCartItemId();
        this.banchanTotalPrice = cartItem.getBanchanTotalPrice();
        this.banchanQuantity = cartItem.getBanchanQuantity();
        this.responseBanchanDto = new ResponseBanchanDto(cartItem.getBanchan());
    }
}
