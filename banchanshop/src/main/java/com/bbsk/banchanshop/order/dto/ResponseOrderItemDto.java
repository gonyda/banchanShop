package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.OrderOption;
import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ResponseOrderItemDto {

    private String banchanName;
    private int quantity;
    private int banchanTotalPrice;

    private String amount = null;
    private String spicy = null;
    private LocalDateTime pickUp = null;

    public ResponseOrderItemDto(OrderItemEntity orderItem) {
        this.banchanName = orderItem.getBanchan().getBanchanName();
        this.quantity = orderItem.getQuantity();
        this.banchanTotalPrice = orderItem.getTotalPrice();
        if(orderItem.getOrderOption() != null) {
            this.amount = orderItem.getOrderOption().getAmount().getName();
            this.spicy = orderItem.getOrderOption().getSpicy().getName();
            this.pickUp = orderItem.getOrderOption().getPickUp();
        }
    }
}
