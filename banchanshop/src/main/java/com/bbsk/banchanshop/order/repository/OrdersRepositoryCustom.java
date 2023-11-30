package com.bbsk.banchanshop.order.repository;

import com.querydsl.core.Tuple;

import java.util.List;

public interface OrdersRepositoryCustom {

    int sumTotalPriceByUserUserId(String userId);

    Long findOrderCountByToday();

    List<Tuple> findOrderCountBy7Day();
}
