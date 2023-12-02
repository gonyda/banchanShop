package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.querydsl.core.Tuple;

import java.util.List;

public interface OrdersRepositoryCustom {

    int sumTotalPriceByUserUserId(String userId);

    Long findOrderCountByToday();

    List<Tuple> findOrderCountBy7Day();

    List<Tuple> findOrderCountBy6Months();

    List<OrdersEntity> findAllOrders(Long orderId, String userId, String orderDate, String banchanName);
}
