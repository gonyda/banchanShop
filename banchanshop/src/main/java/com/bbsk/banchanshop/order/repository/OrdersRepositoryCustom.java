package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersRepositoryCustom {

    int sumTotalPriceByUserUserId(String userId);

    Long findOrderCountByToday();

    List<Tuple> findOrderCountBy7Day();

    List<Tuple> findOrderCountBy6Months();

    List<OrdersEntity> findAllOrders(Long orderId, String userId, String orderDate, String banchanName);

    Page<OrdersEntity> findAllByPaging(String userId, Pageable pageable);
}
