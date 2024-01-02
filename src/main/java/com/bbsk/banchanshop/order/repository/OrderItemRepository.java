package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findAllOrderItemsByOrderOrderId(Long orderId);
}
