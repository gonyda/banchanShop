package com.bbsk.banchanshop.payment.service;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.cart.service.CartService;
import com.bbsk.banchanshop.contant.*;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.bbsk.banchanshop.order.service.OrdersService;
import com.bbsk.banchanshop.payment.service.account.ShinhanBank;
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

@SpringBootTest
@Slf4j
class PaymentServiceTests {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private BanchanService banchanService;

    @Autowired
    private OrdersService ordersService;

    @Transactional
    @DisplayName("결제-주문 테스트")
    @Test
    public void payment() {
        log.info(" ==== 결제 테스트 ==== ");
        UserEntity user = saveUser();
        BanchanEntity banchan = saveFirstBanchan();

        saveCart(user, banchan, 5);

        saveOrderByPreOrder();

        OrdersEntity order = ordersService.findRecentOrderByUserId("test");
        assertEquals(CardCompany.KAKAOPAY.name(), order.getPaymentCompany());
        assertEquals(OrderType.PREORDER, order.getOrderType());
        assertEquals(10000*5, order.getTotalPrice());

        saveOrderByOrder();
        order = ordersService.findRecentOrderByUserId("test");
        assertEquals(BankCompany.SHINHANBANK.name(), order.getPaymentCompany());
        assertEquals(OrderType.ORDER, order.getOrderType());
        assertEquals(10000*5, order.getTotalPrice());
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

    private void saveOrderByPreOrder() {
        // 결제 진행하는 유저
        String userId = "test";
        // 결제 종류
        PaymentType paymentType = PaymentType.CARD;
        // 카드 정보
        KakaoCard kakaoCard = KakaoCard.builder()
                .cardNumber(1111L)
                .cardPw(444)
                .build();
        // 주문 종류
        OrderType orderType = OrderType.PREORDER;

        //paymentService.startPayToOrder(userId, paymentType, kakaoCard, orderType);
    }

    private void saveOrderByOrder() {
        // 결제 진행하는 유저
        String userId = "test";
        // 결제 종류
        PaymentType paymentType = PaymentType.ACCOUNTTRANSFER;
        // 카드 정보
        ShinhanBank bank = ShinhanBank.builder()
                .userName("백승권")
                .accountNumber("110337163077")
                .build();
        // 주문 종류
        OrderType orderType = OrderType.ORDER;
        // 주문 옵션 X

        //paymentService.startPayToOrder(userId, paymentType, bank, orderType);
    }
}