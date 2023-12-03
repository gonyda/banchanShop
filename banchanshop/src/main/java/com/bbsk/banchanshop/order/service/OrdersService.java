package com.bbsk.banchanshop.order.service;

import com.bbsk.banchanshop.contant.OrderType;
import com.bbsk.banchanshop.contant.PaymentType;
import com.bbsk.banchanshop.order.dto.CreateOrderDto;
import com.bbsk.banchanshop.order.dto.RequestOrderOptionDto;
import com.bbsk.banchanshop.order.dto.ResponseOrderDto;
import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.bbsk.banchanshop.order.repository.OrderItemRepository;
import com.bbsk.banchanshop.order.repository.OrdersRepository;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private static final int LIMIT = 5;

    private final OrdersRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    /**
     * 결제 완료 후 주문 생성
     *
     * @param dto
     */
    @Transactional
    public void createOrder(CreateOrderDto dto) {
        UserEntity user = userRepository.findById(dto.getUserId()).orElse(null);

        /*
        * 주문 시 반찬재고 체크
        * */
        if (OrderType.ORDER == dto.getOrderType()) {
            checkStockQuantity(user);
        }

        /*
        * 1. Orders 테이블 저장
        * 2. Order_Item 테이블 저장
        * */
        saveOrderItem(user, saveOrder(user, dto.getPaymentType(), dto.getPaymentCompanyName(), dto.getOrderType()), dto.getRequestOrderOptionDtoList());

        /*
         * 일반주문 시 재고차감
         * 예약주문은 주문들어온 뒤에 반찬을 만들기 때문에 재고관리가 필요없음
         * */
        if (OrderType.ORDER == dto.getOrderType()) {
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
     *
     * @param userId
     * @return
     */
    public MyOrderDto findAllByPaging(String userId, int pageNum) {
        Page<OrdersEntity> paging = orderRepository.findAllByPaging(userId, PageRequest.of(pageNum - 1, LIMIT));

        return new MyOrderDto(
                paging.getTotalPages(),
                paging.getContent().stream()
                                   .map(ResponseOrderDto::new)
                                   .toList()
        );
    }

    /**
     * orders 테이블 INSERT
     * @param user
     * @param paymentType
     * @param cardCompany
     * @param orderType
     * @return
     */
    @Transactional
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
     *
     * @param user
     * @param saveOrder
     * @param orderOptions
     */
    @Transactional
    private void saveOrderItem(UserEntity user, OrdersEntity saveOrder, List<RequestOrderOptionDto> orderOptions) {
        // TODO 지저분한 로직 리팩토링 필요
        user.getCart().getCartItem().forEach(e -> {
            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .order(saveOrder)
                    .banchan(e.getBanchan())
                    .quantity(e.getBanchanQuantity())
                    .totalPrice(e.getBanchanTotalPrice())
                    .build();

            if(saveOrder.getOrderType() == OrderType.PREORDER) {
                orderOptions.stream().forEach(orderOption -> {
                    if (orderOption.getBanchanId().equals(orderItem.getBanchan().getBanchanId())) {
                        orderItem.updateOrderOption(orderOption);
                    }
                });
            }

            orderItemRepository.save(orderItem);
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

    /**
     * 사용자의 주문 총액 조회
     * @param userId
     * @return
     */
    public int getTotalPrice(String userId) {
        return orderRepository.sumTotalPriceByUserUserId(userId);
    }

    /**
     * 나의주문내역 - return용
     */
    @Getter
    public class MyOrderDto {
        private int totalPage;
        private List<ResponseOrderDto> list;

        public MyOrderDto(int totalPage, List<ResponseOrderDto> list) {
            this.totalPage = totalPage;
            this.list = list;
        }
    }
}
