package com.bbsk.banchanshop.order.service;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.cart.service.CartService;
import com.bbsk.banchanshop.contant.*;
import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.bbsk.banchanshop.payment.service.card.KakaoCard;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class OrdersServiceTests {

    @Autowired
    private OrdersService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private BanchanService banchanService;
    @Autowired
    private OrderItemService orderItemService;

    @DisplayName("주문 생성시 반찬 재고 테스트")
    @Transactional
    @Test
    public void checkStockQuantity() {
        UserEntity user = saveUser();
        BanchanEntity banchan = saveFirstBanchan();
        saveCart(user, banchan, 5);

        // 재고 변경
        user.getCart().getCartItem().get(0).getBanchan().updateBanchanQuantity(1);

        // 결제 진행하는 유저
        String userId = "test";
        // 결제 종류
        PaymentType paymentType = PaymentType.CARD;
        // 카드 정보
        KakaoCard kakaoCard = KakaoCard.builder()
                .cardNumber(111111111L)
                .cardCvc(342)
                .build();
        // 주문 종류
        OrderType orderType = OrderType.PREORDER;

        assertThrows(IllegalArgumentException.class, () -> {
            // 주문생성
            orderService.createOrder(userService.findUserById(userId), paymentType, kakaoCard.getCardCompany().name(), orderType);
        });
    }

    @Transactional
    @DisplayName("주문 생성 및 재고차감 테스트")
    @Test
    public void careteOrder() {
        UserEntity user = saveUser();
        BanchanEntity banchan = saveFirstBanchan();
        saveCart(user, banchan, 5);

        saveOrder();
        saveOrder();

        assertEquals(user.getCart().getCartTotalPrice(), orderService.findRecentOrderByUserId(user.getUserId()).getTotalPrice());
        assertEquals(15-10, banchanService.findBanchanById(1L).getBanchanStockQuantity());
    }

    @Transactional
    @DisplayName("해당 유저의 주문 전체 조회 테스트")
    @Test
    public void findAll() {
        UserEntity user = saveUser();
        BanchanEntity banchan = saveFirstBanchan();
        saveCart(user, banchan, 5);

        saveOrder();

        assertEquals(1, orderService.findAllByUserId(user.getUserId()).size());
    }

    @Transactional
    @DisplayName("해당 유저의 주문에 해당하는 주문상세내역 전체 조회 테스트")
    @Test
    public void findAllOrderItems() {
        UserEntity user = saveUser();
        BanchanEntity banchan = saveFirstBanchan();
        saveCart(user, banchan, 5);

        saveOrder();

        OrdersEntity order = orderService.findRecentOrderByUserId(user.getUserId());

        List<OrderItemEntity> orderItems = orderItemService.findAllOrderItemsByOrderId(order.getOrderId());

        assertEquals(1, orderItems.size());
    }

    private UserEntity saveUser(){
        return userService.registUser(UserEntity.builder()
                .userId("test")
                .userPw("test")
                .userEmail("bbsk3939@gmil.com")
                .name("백승권")
                .address("서울특별실 양천구")
                .adminYn(UserType.N)
                .phoneNumber("01064629657")
                .build());
    }

    private BanchanEntity saveFirstBanchan() {
        BanchanEntity entity = BanchanEntity.builder()
                .banchanStockQuantity(15)
                .banchanName("김치찌게")
                .banchanPrice(10000)
                .createDate(LocalDateTime.now())
                .build();
        entity.plusExpirationDate(3L);

        // ====================================================================
        List<BanchanIngredientEntity> list = new ArrayList<>();
        BanchanIngredientEntity 고춧가루 = BanchanIngredientEntity.builder()
                .ingredientName("고춧가루")
                .quantity(5)
                .inputDate(LocalDateTime.now())
                .build();
        고춧가루.plusExpirationDate(10L);

        BanchanIngredientEntity 간장 = BanchanIngredientEntity.builder()
                .ingredientName("간장")
                .quantity(5)
                .inputDate(LocalDateTime.now())
                .build();
        간장.plusExpirationDate(20L);

        BanchanIngredientEntity 소금 = BanchanIngredientEntity.builder()
                .ingredientName("소금")
                .quantity(5)
                .inputDate(LocalDateTime.now())
                .build();
        소금.plusExpirationDate(20L);

        list.add(고춧가루);
        list.add(간장);
        list.add(소금);

        return banchanService.registBanchan(entity, list);
    }

    private BanchanEntity saveSecondBanchan() {
        BanchanEntity entity = BanchanEntity.builder()
                .banchanStockQuantity(15)
                .banchanName("된장찌게")
                .banchanPrice(5000)
                .createDate(LocalDateTime.now())
                .build();
        entity.plusExpirationDate(3L);

        // ====================================================================
        List<BanchanIngredientEntity> list = new ArrayList<>();
        BanchanIngredientEntity 된장 = BanchanIngredientEntity.builder()
                .ingredientName("된장")
                .quantity(5)
                .inputDate(LocalDateTime.now())
                .build();
        된장.plusExpirationDate(10L);

        BanchanIngredientEntity 양파 = BanchanIngredientEntity.builder()
                .ingredientName("양파")
                .quantity(5)
                .inputDate(LocalDateTime.now())
                .build();
        양파.plusExpirationDate(20L);

        list.add(된장);
        list.add(양파);

        return banchanService.registBanchan(entity, list);
    }

    private void saveCart(UserEntity user, BanchanEntity banchan, int quantity) {
        cartService.addBanchanInCart(user, banchan, quantity);
    }

    private void saveOrder() {
        // 결제 진행하는 유저
        String userId = "test";
        // 결제 종류
        PaymentType paymentType = PaymentType.CARD;
        // 카드 정보
        KakaoCard kakaoCard = KakaoCard.builder()
                .cardNumber(1111L)
                .cardCvc(444)
                .build();
        // 주문 종류
        OrderType orderType = OrderType.PREORDER;

        orderService.createOrder(userService.findUserById(userId), paymentType, kakaoCard.getCardCompany().name(), orderType);
    }
}
