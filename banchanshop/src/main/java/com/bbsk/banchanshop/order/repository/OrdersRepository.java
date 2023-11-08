package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.order.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
}
