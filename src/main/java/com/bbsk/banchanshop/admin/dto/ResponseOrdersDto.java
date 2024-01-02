package com.bbsk.banchanshop.admin.dto;

import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ResponseOrdersDto {

    private Long orderId;
    private String userId;
    private List<ResponseOrderItemDto> orderItemList = new ArrayList<>();
    private LocalDateTime orderDate;
    private String orderType;
    private String paymentType;
    private String paymentCompany;
    private int totalPrice;

    public ResponseOrdersDto updateOrders(OrdersEntity entity) {
        this.orderId = entity.getOrderId();
        this.userId = entity.getUser().getUserId();
        this.orderDate = entity.getOrderDate();
        this.orderType = entity.getOrderType().getName();
        this.paymentType = entity.getPaymentType().getName();
        this.paymentCompany = entity.getPaymentCompany();
        this.totalPrice = entity.getTotalPrice();

        for (OrderItemEntity e : entity.getOrderItems()) {
            this.orderItemList.add(
                new ResponseOrderItemDto(e)
            );
        }

        return this;
    }
}
