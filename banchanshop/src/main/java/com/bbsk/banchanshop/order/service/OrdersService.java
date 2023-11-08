package com.bbsk.banchanshop.order.service;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.bbsk.banchanshop.order.repository.OrderItemRepository;
import com.bbsk.banchanshop.order.repository.OrdersRepository;
import com.bbsk.banchanshop.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void createOrder(UserEntity user, OrderType orderType, PaymentType paymentType) {
        /*
        * Orders 테이블 저장
        * */
        OrdersEntity saveOrder = orderRepository.save(
                OrdersEntity.builder()
                        .user(user)
                        .orderType(orderType)
                        .paymentType(paymentType)
                        .address(user.getAddress())
                        .totalPrice(user.getCart().getCartTotalPrice())
                        .build()
        );

        /*
        * Order_Item 테이블 저장
        * */
        List<OrderItemEntity> orderItems = new ArrayList<>();
        user.getCart().getCartItem().stream().forEach(e -> {
                orderItems.add(
                        orderItemRepository.save(
                                OrderItemEntity.builder()
                                        .order(saveOrder)
                                        .banchan(e.getBanchan())
                                        .quantity(e.getBanchanQuantity())
                                        .totalPrice(e.getBanchanTotalPrice())
                                        .build()
                )
            );
        });
        saveOrder.setOrderItems(orderItems);
    }
}
