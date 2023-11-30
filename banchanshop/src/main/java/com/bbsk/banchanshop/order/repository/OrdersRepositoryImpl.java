package com.bbsk.banchanshop.order.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Objects;

import static com.bbsk.banchanshop.order.entity.QOrdersEntity.*;
import static com.querydsl.core.types.dsl.Expressions.*;

public class OrdersRepositoryImpl implements OrdersRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrdersRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 해당 유저의 총 구매금액
     * @param userId
     * @return
     */
    @Override
    public int sumTotalPriceByUserUserId(String userId) {

        return Objects.requireNonNull(queryFactory
                .select(ordersEntity.totalPrice.sum().coalesce(0))
                .from(ordersEntity)
                .where(ordersEntity.user.userId.eq(userId))
                .fetchOne(), "쿼리의 결과값이 null입니다.");
    }

    /**
     * 당일 주문건수 조회
     * @return
     */
    @Override
    public Long findOrderCountByToday() {
        /*"select count(1) " +
                "from orders " +
                "where date_format(order_date, '%Y%m%d') = date_format(now(), '%Y%m%d')"*/

        return queryFactory
                .select(ordersEntity.count())
                .from(ordersEntity)
                .where(
                        dateTemplate(String.class, "DATE_FORMAT({0}, '%Y%m%d')", ordersEntity.orderDate)
                        .eq(dateTemplate(String.class, "DATE_FORMAT(NOW(), '%Y%m%d')"))
                )
                .fetchOne();
    }

    /**
     * 최근 7일 주문건수 조회
     * @return
     */
    @Override
    public List<Tuple> findOrderCountBy7Day() {
        /*"select date_format(order_date, '%Y%m%d') as day" +
                ", count(1) as orderCount " +
                "from orders " +
                "group by date_format(order_date, '%Y%m%d') " +
                "order by date_format(order_date, '%Y%m%d') desc " +
                "limit 7"*/

        return queryFactory
                .select(
                        dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", ordersEntity.orderDate)
                        , ordersEntity.orderDate.count()
                )
                .from(ordersEntity)
                .groupBy(dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", ordersEntity.orderDate))
                .orderBy(dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", ordersEntity.orderDate).desc())
                .limit(7)
                .fetch();
    }
}
