package com.bbsk.banchanshop.order.dto;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ResponseOrderDto {

    private Long orderId; // 주문번호
    private OrderType orderType; // 주문종류
    private LocalDateTime orderDate; // 주문일시
    private int orderPrice; // 주문 가격

    private List<ResponseOrderItemDto> orderItem = new ArrayList<>();

    public ResponseOrderDto(OrdersEntity order) {
        this.orderId = order.getOrderId();
        this.orderType = order.getOrderType();
        this.orderDate = order.getOrderDate();
        this.orderPrice = order.getTotalPrice();
        order.getOrderItems().forEach(e -> {
            this.orderItem.add(new ResponseOrderItemDto(e));
        });
    }
}
