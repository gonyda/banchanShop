package com.bbsk.banchanshop.order.repository;

import com.bbsk.banchanshop.admin.mapping.Recently6MonthsOrderCountMapping;
import com.bbsk.banchanshop.admin.mapping.Recently7DaysOrderCountMapping;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    OrdersEntity findTop1RecentOrderByUserUserIdOrderByOrderDateDesc(String userId);

    List<OrdersEntity> findAllByUserUserIdOrderByOrderIdDesc(String userId);

    @Query(value = "select ifnull(sum(total_price), 0) from orders where user_id = :userId", nativeQuery = true)
    int sumTotalPriceByUserUserId(String userId);

    @Query(value = "select count(1) " +
                     "from orders " +
                    "where date_format(order_date, '%Y%m%d') = date_format(now(), '%Y%m%d') " +
                 "group by date_format(order_date, '%Y%m%d')"
            , nativeQuery = true)
    int findOrderCountByToday();

    @Query(value = "select date_format(order_date, '%Y%m%d') as day" +
                        ", count(1) as orderCount " +
                    "from orders " +
                "group by date_format(order_date, '%Y%m%d') " +
                "order by date_format(order_date, '%Y%m%d') desc " +
                   "limit 7"
            , nativeQuery = true)
    List<Recently7DaysOrderCountMapping> findOrderCountBy7Day();

    @Query(value = "select date_format(order_date, '%Y%m') as month" +
                        ", count(1) as orderCount " +
                    "from orders " +
                "group by date_format(order_date, '%Y%m') " +
                "order by date_format(order_date, '%Y%m') desc " +
                "limit 6"
            , nativeQuery = true)
    List<Recently6MonthsOrderCountMapping> findOrderCountBy6Months();

    @Query(value = "select o " +
                     "from OrdersEntity o " +
                    "where (:orderId is null or o.orderId = :orderId) " +
                      "and (:userId is null or o.user.userId = :userId) " +
                      "and (:orderDate is null or function('date_format', o.orderDate, '%Y-%m-%d') = function('date_format', :orderDate, '%Y-%m-%d'))")
    List<OrdersEntity> findAllOrders(Long orderId, String userId, String orderDate);
}
