package com.bbsk.banchanshop.order.service;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void createOrder(UserEntity user, OrderType orderType, PaymentType paymentType, String cardCompany) {
        user.getCart().getCartItem().forEach(e -> {
            if (checkStockQuantity(e.getBanchan() ,e.getBanchanQuantity())) {
                throw new IllegalArgumentException(e.getBanchan().getBanchanName() + "의 재고수량이 변경되었습니다. 수량을 다시 선택해주세요.");
            }
        });

        /*
        * Orders 테이블 저장
        * */
        OrdersEntity saveOrder = orderRepository.save(
                OrdersEntity.builder()
                        .user(user)
                        .orderType(orderType)
                        .paymentType(paymentType)
                        .address(user.getAddress())
                        .totalPrice(user.getCart().getCartTotalPrice())
                        .cardCompany(cardCompany)
                        .build()
        );

        /*
        * Order_Item 테이블 저장
        * */
        user.getCart().getCartItem().stream().forEach(e -> {
                orderItemRepository.save(
                        OrderItemEntity.builder()
                                .order(saveOrder)
                                .banchan(e.getBanchan())
                                .quantity(e.getBanchanQuantity())
                                .totalPrice(e.getBanchanTotalPrice())
                                .build()
                );
        });

        //TODO 주문성공 시 반찬재고 차감

    }

    /**
     * 해당 유저의 가장 최근 결제 단건 가져오기
     * @param userId
     * @return
     */
    public OrdersEntity findRecentOrderByUserId(String userId) {
        return orderRepository.findTop1RecentOrderByUserUserIdOrderByOrderDateDesc(userId);
    }

    //TODO 해당 유저의 모든 주문내역 조회

    /**
     * 주문 시 반찬재고, 주문수량 체크
     * @param banchan
     * @param itemQuantity
     * @return
     */
    private boolean checkStockQuantity(BanchanEntity banchan, int itemQuantity) {
        return banchan.getBanchanStockQuantity() < itemQuantity;
    }
}
