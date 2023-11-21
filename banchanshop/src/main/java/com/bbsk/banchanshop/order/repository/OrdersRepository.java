package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    OrdersEntity findTop1RecentOrderByUserUserIdOrderByOrderDateDesc(String userId);

    List<OrdersEntity> findAllByUserUserIdOrderByOrderIdDesc(String userId);

    @Query(value = "select sum(total_price) from orders where user_id = :userId", nativeQuery = true)
    int sumTotalPriceByUserUserId(String userId);
}
