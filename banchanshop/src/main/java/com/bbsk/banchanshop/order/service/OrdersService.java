package com.bbsk.banchanshop.order.service;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.bbsk.banchanshop.order.repository.OrderItemRepository;
import com.bbsk.banchanshop.order.repository.OrdersRepository;
import com.bbsk.banchanshop.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * 결제 완료 후 주문 생성
     * @param user
     * @param orderType
     * @param paymentType
     * @param paymentCompany
     */
    @Transactional
    public void createOrder(UserEntity user, PaymentType paymentType, String paymentCompany, OrderType orderType) {
        /*
        * 주문 시 반찬재고 체크
        * */
        if (OrderType.ORDER == orderType) {
            checkStockQuantity(user);
        }

        /*
        * 1. Orders 테이블 저장
        * 2. Order_Item 테이블 저장
        * */
        saveOrderItem(user, saveOrder(user, paymentType, paymentCompany, orderType));

        /*
         * 일반주문 시 재고차감
         * 예약주문은 주문들어온 뒤에 반찬을 만들기 때문에 재고관리가 필요없음
         * */
        if (OrderType.ORDER == orderType) {
            subtractBanchanQuantity(user);
        }
    }

    /**
     * 해당 유저의 가장 최근 주문 단건 가져오기
     * @param userId
     * @return
     */
    public OrdersEntity findRecentOrderByUserId(String userId) {
        return orderRepository.findTop1RecentOrderByUserUserIdOrderByOrderDateDesc(userId);
    }

    /**
     * 해당 유저의 주문 내역 전체 조회
     * @param userId
     * @return
     */
    public List<OrdersEntity> findAllByUserId(String userId) {
        return orderRepository.findAllByUserUserId(userId);
    }

    /**
     * orders 테이블 INSERT
     * @param user
     * @param paymentType
     * @param cardCompany
     * @param orderType
     * @return
     */
    private OrdersEntity saveOrder(UserEntity user, PaymentType paymentType, String cardCompany, OrderType orderType) {
        return orderRepository.save(
                OrdersEntity.builder()
                        .user(user)
                        .orderType(orderType)
                        .paymentType(paymentType)
                        .address(user.getAddress())
                        .totalPrice(user.getCart().getCartTotalPrice())
                        .paymentCompany(cardCompany)
                        .build()
        );
    }

    /**
     * order_item 테이블 INSERT
     * @param user
     * @param saveOrder
     */
    private void saveOrderItem(UserEntity user, OrdersEntity saveOrder) {
        user.getCart().getCartItem().forEach(e -> {
            orderItemRepository.save(
                    OrderItemEntity.builder()
                            .order(saveOrder)
                            .banchan(e.getBanchan())
                            .quantity(e.getBanchanQuantity())
                            .totalPrice(e.getBanchanTotalPrice())
                            .build()
            );
        });
    }

    /**
     * 주문시점에서 반찬재고 체크
     * @param user
     */
    private void checkStockQuantity(UserEntity user) {
        user.getCart().getCartItem().forEach(e -> {
            if (e.getBanchan().getBanchanStockQuantity() < e.getBanchanQuantity()) {
                throw new IllegalArgumentException(e.getBanchan().getBanchanName() + "의 재고수량이 변경되었습니다. 수량을 다시 선택해주세요.");
            }
        });
    }

    /**
     * 주문 성공시 반찬 재고 차감
     * @param user
     */
    private void subtractBanchanQuantity(UserEntity user) {
        user.getCart().getCartItem().forEach(e -> {
            e.getBanchan().updateBanchanQuantity(e.getBanchan().getBanchanStockQuantity() - e.getBanchanQuantity());
        });
    }
}
