package com.bbsk.banchanshop.cart.dto;

import com.bbsk.banchanshop.cart.entity.CartEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

@Getter
@ToString
@Slf4j
public class ResponseCartDto {

    private int cartTotalPrice; // 장바구니에 담긴 상품 총액
    private int cartTotalQuantity; // 장바구니에 담긴 상품 총갯수

    private List<ResponseCartItemsDto> cartItems = new ArrayList<>(); // 장바구니에 담긴 장바구니 아이템

    public ResponseCartDto toDto(CartEntity cart) {
        this.cartTotalPrice = cart.getCartTotalPrice();
        this.cartTotalQuantity = cart.getCartTotalQuantity();
        this.cartItems = cart.getCartItem().stream()
                .map(ResponseCartItemsDto::new)
                .collect(toList());

        return this;
    }

}
