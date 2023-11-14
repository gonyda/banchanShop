package com.bbsk.banchanshop.cart.dto;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import lombok.*;

@Getter
@ToString
public class ResponseUserCartItemsDto {

    private ResponseUserCartItemBanchanDto responseUserCartItemBanchanDto;
    private int banchanQuantity; // 상품 갯수
    private int banchanTotalPrice; // 상품 가격

    public ResponseUserCartItemsDto(BanchanEntity banchan, int banchanQuantity, int banchanTotalPrice) {
        this.banchanTotalPrice = banchanTotalPrice;
        this.banchanQuantity = banchanQuantity;
    }

    public ResponseUserCartItemsDto(CartItemEntity cartItem) {
        this.banchanTotalPrice = cartItem.getBanchanTotalPrice();
        this.banchanQuantity = cartItem.getBanchanQuantity();
        this.responseUserCartItemBanchanDto = new ResponseUserCartItemBanchanDto(cartItem.getBanchan());
    }
}
