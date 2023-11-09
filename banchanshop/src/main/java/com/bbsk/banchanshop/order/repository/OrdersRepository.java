package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    OrdersEntity findTop1RecentOrderByUserUserIdOrderByOrderDateDesc(String userId);

    List<OrdersEntity> findAllByUserUserId(String userId);
}
