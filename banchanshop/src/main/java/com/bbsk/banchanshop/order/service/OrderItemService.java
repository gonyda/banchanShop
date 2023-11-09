package com.bbsk.banchanshop.order.service;

import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    /**
     * 해당 유저의 주문의 주문 상세 아이템 전체 조회
     * @param orderId
     * @return
     */
    public List<OrderItemEntity> findAllOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAllOrderItemsByOrderOrderId(orderId);
    }

    /**
     * order_item 단건 조회
     * @param orderItemId
     * @return
     */
    public OrderItemEntity findByOrderItemId(long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElse(null);
    }
}
