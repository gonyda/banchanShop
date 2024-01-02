package com.bbsk.banchanshop.admin.dto;

import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ResponseOrderItemDto {

    private String banchanName;
    private int quantity;

    private String amount = null;
    private String spicy = null;
    private LocalDateTime pickUp = null;

    public ResponseOrderItemDto(OrderItemEntity orderItem) {
        this.banchanName = orderItem.getBanchan().getBanchanName();
        this.quantity = orderItem.getQuantity();
        if(orderItem.getOrderOption() != null) {
            this.amount = orderItem.getOrderOption().getAmount().getName();
            this.spicy = orderItem.getOrderOption().getSpicy().getName();
            this.pickUp = orderItem.getOrderOption().getPickUp();
        }
    }
}
