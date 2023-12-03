package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.QBanchanEntity;
import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.bbsk.banchanshop.order.entity.QOrderItemEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

import static com.bbsk.banchanshop.banchan.entity.QBanchanEntity.*;
import static com.bbsk.banchanshop.order.entity.QOrderItemEntity.*;
import static com.bbsk.banchanshop.order.entity.QOrdersEntity.*;
import static com.querydsl.core.types.dsl.Expressions.*;

public class OrdersRepositoryImpl implements OrdersRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrdersRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 유저의 주문내역
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public Page<OrdersEntity> findAllByPaging(String userId, Pageable pageable) {
        List<OrdersEntity> orders = queryFactory
                .selectFrom(ordersEntity)
                .where(ordersEntity.user.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(ordersEntity.orderId.desc())
                .fetch();

        Long totalCount = queryFactory
                .select(ordersEntity.count())
                .from(ordersEntity)
                .where(ordersEntity.user.userId.eq(userId))
                .fetchOne();

        return new PageImpl<>(orders, pageable, totalCount == null ? 0 : totalCount);
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

    /**
     * 최근 6개월 주문건수 조회
     * @return
     */
    @Override
    public List<Tuple> findOrderCountBy6Months() {
        /*"select date_format(order_date, '%Y%m') as month" +
                        ", count(1) as orderCount " +
                    "from orders " +
                "group by date_format(order_date, '%Y%m') " +
                "order by date_format(order_date, '%Y%m') desc " +
                "limit 6"*/

        return queryFactory
                .select(
                        dateTemplate(String.class, "DATE_FORMAT({0}, '%Y%m')", ordersEntity.orderDate)
                        , ordersEntity.orderDate.count()
                )
                .from(ordersEntity)
                .groupBy(dateTemplate(String.class, "DATE_FORMAT({0}, '%Y%m')", ordersEntity.orderDate))
                .orderBy(dateTemplate(String.class, "DATE_FORMAT({0}, '%Y%m')", ordersEntity.orderDate).desc())
                .limit(6)
                .fetch();
    }

    /**
     * 주문현황 - 검색기능
     * @param orderId
     * @param userId
     * @param orderDate
     * @param banchanName
     * @return
     */
    @Override
    public List<OrdersEntity> findAllOrders(Long orderId, String userId, String orderDate, String banchanName) {
        /*"select o " +
                     "from OrdersEntity o " +
                    "where (:orderId is null or o.orderId = :orderId) " +
                      "and (:userId is null or o.user.userId = :userId) " +
                      "and (:orderDate is null or function('date_format', o.orderDate, '%Y-%m-%d') = function('date_format', :orderDate, '%Y-%m-%d')) " +
                      "and (:banchanName is null or o.orderId in (SELECT oi.order.id FROM OrderItemEntity oi JOIN oi.banchan b where b.banchanName = :banchanName))" +
                 "order by o.orderDate desc"*/

        return queryFactory
                .selectFrom(ordersEntity)
                .where(
                        eqOrderId(orderId),
                        eqUserId(userId),
                        eqOrderDate(orderDate),
                        eqBanchanName(banchanName)
                )
                .orderBy(ordersEntity.orderDate.desc())
                .fetch();
    }

    private BooleanExpression eqBanchanName(String banchanName) {
        if (banchanName == null) {
            return null;
        }

        return ordersEntity.orderId.in(
                queryFactory
                        .select(orderItemEntity.order.orderId)
                        .from(orderItemEntity)
                        .join(orderItemEntity.banchan, banchanEntity)
                        .where(banchanEntity.banchanName.eq(banchanName))
        );
    }

    private BooleanExpression eqOrderDate(String orderDate) {
        if (orderDate == null) {
            return null;
        }
        return dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", ordersEntity.orderDate)
                .eq(dateTemplate(String.class, "DATE_FORMAT({0}, '%Y-%m-%d')", orderDate));
    }

    private BooleanExpression eqUserId(String userId) {
        if (userId == null) {
            return null;
        }

        return ordersEntity.user.userId.eq(userId);
    }

    private BooleanExpression eqOrderId(Long orderId) {
        if (orderId == null) {
            return null;
        }

        return ordersEntity.orderId.eq(orderId);
    }
}
