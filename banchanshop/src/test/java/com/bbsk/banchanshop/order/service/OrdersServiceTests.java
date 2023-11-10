package com.bbsk.banchanshop.order.service;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.cart.service.CartService;
import com.bbsk.banchanshop.contant.*;
import com.bbsk.banchanshop.order.dto.OrderOptionDto;
import com.bbsk.banchanshop.order.entity.OrderItemEntity;
import com.bbsk.banchanshop.order.entity.OrdersEntity;
import com.bbsk.banchanshop.order.repository.OrdersRepository;
import com.bbsk.banchanshop.payment.dto.KakaoCard;
import com.bbsk.banchanshop.payment.dto.RequestCardDto;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
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

    @DisplayName("회원가입 테스트")
    @Order(1)
    @Test
    public void registUser() {
        log.info("==================== 회원가입 테스트");

        UserEntity user = UserEntity.builder()
                .userId("test")
                .userPw("test")
                .userEmail("bbsk3939@gmail.com")
                .userName("백승권")
                .address("서울특별실 양천구")
                .adminYn(UserType.N)
                .phoneNumber("01064629667")
                .build();

        UserEntity registEntity = userService.registUser(user);

        assertEquals("test", registEntity.getUserId());
        assertEquals("백승권", registEntity.getUserName());
        assertEquals(UserType.N, registEntity.getAdminYn());
        assertEquals("test", registEntity.getCart().getCartId());
    }

    @Order(2)
    @DisplayName("반찬등록 테스트")
    @Test
    public void insertBanchan() {
        log.info("=================== 반찬등록 테스트 =================");
        BanchanEntity entity = BanchanEntity.builder()
                .banchanStockQuantity(100)
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


        BanchanEntity firstBanchan = banchanService.registBanchan(entity, list);

        // ====================================================================
        // ====================================================================
        // 된장찌게 반찬 추가

        BanchanEntity entity1 = BanchanEntity.builder()
                .banchanStockQuantity(5)
                .banchanName("된장찌게")
                .banchanPrice(5000)
                .createDate(LocalDateTime.now())
                .build();
        entity1.plusExpirationDate(3L);

        // ====================================================================
        List<BanchanIngredientEntity> list1 = new ArrayList<>();
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

        list1.add(된장);
        list1.add(양파);


        BanchanEntity secondBanchan = banchanService.registBanchan(entity1, list1);
        // ====================================================================

        assertEquals("김치찌게", firstBanchan.getBanchanName());
        assertEquals("된장찌게", secondBanchan.getBanchanName());

    }

    @Order(3)
    @DisplayName("장바구니 상품 넣기 테스트")
    @Test
    public void putCart() {
        log.info(" =================== 장바구니 상품 넣기 테스트 ================== ");
        UserEntity userEntity = userService.findUserById("test");
        BanchanEntity banchanEntity = banchanService.findBybanchanName("김치찌게");

        cartService.addBanchanInCart(userEntity, banchanEntity, 2);

        UserEntity afterUserEntity = userService.findUserById("test");
        assertEquals("test", afterUserEntity.getUserId()); // test 라는 유저가
        assertEquals("김치찌게", afterUserEntity.getCart().getCartItem().get(0).getBanchan().getBanchanName()); // 김치찌게를 장바구니에 담았다
        assertEquals(2, afterUserEntity.getCart().getCartItem().get(0).getBanchanQuantity()); // 2개를 담았고
        assertEquals(2, afterUserEntity.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 2
        assertEquals(2*10000, afterUserEntity.getCart().getCartTotalPrice());// 장바구니 총 가격은 20000원이다

        // ======================================================================
        // ======================================================================
        log.info(" =================== 장바구니 같은 상품 넣기 테스트 ================== ");

        cartService.addBanchanInCart(userEntity, banchanEntity, 12);

        afterUserEntity = userService.findUserById("test");

        assertEquals("test", afterUserEntity.getUserId()); // test 라는 유저가
        assertEquals("김치찌게", afterUserEntity.getCart().getCartItem().get(0).getBanchan().getBanchanName()); // 김치찌게를 장바구니에 담았다
        assertEquals(12, afterUserEntity.getCart().getCartItem().get(0).getBanchanQuantity()); // 12개를 담았고
        assertEquals(12, afterUserEntity.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 12
        assertEquals(12*10000, afterUserEntity.getCart().getCartTotalPrice());// 장바구니 총 가격은 120000원


        // ======================================================================
        // ======================================================================
        log.info(" =================== 장바구니 다른 상품 넣기 테스트 ================== ");

        BanchanEntity 된장찌게 = banchanService.findBybanchanName("된장찌게");

        cartService.addBanchanInCart(userEntity, 된장찌게, 5);
        afterUserEntity = userService.findUserById("test");

        assertEquals("test", afterUserEntity.getUserId()); // test 라는 유저가
        assertEquals("된장찌게", afterUserEntity.getCart().getCartItem().get(1).getBanchan().getBanchanName()); // 된장찌게를 장바구니에 담았다
        assertEquals(5, afterUserEntity.getCart().getCartItem().get(1).getBanchanQuantity()); // 5개를 담았고
        assertEquals(12+5, afterUserEntity.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 12 + 5
        assertEquals(120000+25000, afterUserEntity.getCart().getCartTotalPrice());// 장바구니 총 가격은 120000 + 25000원
    }

    /*
    * 테스트 완료
    * */
    @Disabled
    @Order(4)
    @DisplayName("주문 생성시 반찬 재고 테스트")
    @Test
    public void checkStockQuantity() {
        UserEntity user = userService.findUserById("test");
        // 재고 변경
        user.getCart().getCartItem().get(1).getBanchan().updateBanchanQuantity(2);

        // ================== 컨트롤러에서 받은 Dto
        RequestCardDto dto = new RequestCardDto();
        dto.setCardNumber(11111L);
        dto.setCardCvc(111);
        // ==================
        // 결제 진행하는 유저
        String userId = "test";
        // 결제 종류
        PaymentType paymentType = PaymentType.CARD;
        // 카드 정보
        KakaoCard kakaoCard = KakaoCard.builder()
                .cardNumber(dto.getCardNumber())
                .cardCvc(dto.getCardCvc())
                .build();
        // 주문 종류
        OrderType orderType = OrderType.PREORDER;
        // 주문 옵션
        List<OrderOptionDto> orderOptions = new ArrayList<>();
        OrderOptionDto orderOption = OrderOptionDto.builder()
                .orderItemId(2L)
                .optionAmount(OrderOption.SMALL)
                .optionSpicy(OrderOption.SPICY)
                .optionPickUp(LocalDateTime.now())
                .build();
        orderOptions.add(orderOption);
        orderService.createOrder(userService.findUserById(userId), paymentType, kakaoCard.getCardCompany(), orderType, orderOptions);
    }

    @Order(5)
    @DisplayName("주문 생성 및 재고차감 테스트")
    @Test
    public void careteOrder() {
        // ================== 컨트롤러에서 받은 Dto
        RequestCardDto dto = new RequestCardDto();
        dto.setCardNumber(11111L);
        dto.setCardCvc(111);
        // ==================
        // 결제 진행하는 유저
        String userId = "test";
        // 결제 종류
        PaymentType paymentType = PaymentType.CARD;
        // 카드 정보
        KakaoCard kakaoCard = KakaoCard.builder()
                .cardNumber(dto.getCardNumber())
                .cardCvc(dto.getCardCvc())
                .build();
        // 주문 종류
        OrderType orderType = OrderType.PREORDER;
        // 주문 옵션
        List<OrderOptionDto> orderOptions = new ArrayList<>();
        OrderOptionDto orderOption = OrderOptionDto.builder()
                .orderItemId(2L)
                .optionAmount(OrderOption.SMALL)
                .optionSpicy(OrderOption.SPICY)
                .optionPickUp(LocalDateTime.now())
                .build();
        orderOptions.add(orderOption);

        orderService.createOrder(userService.findUserById(userId), paymentType, kakaoCard.getCardCompany(), orderType, orderOptions);

        UserEntity user = userService.findUserById("test");

        assertEquals(user.getCart().getCartTotalPrice(), orderService.findRecentOrderByUserId(user.getUserId()).getTotalPrice());
        assertEquals(100-12, banchanService.findBanchanById(1L).getBanchanStockQuantity());
        assertEquals(5-5, banchanService.findBanchanById(2L).getBanchanStockQuantity());
    }

    @Order(6)
    @DisplayName("해당 유저의 주문 전체 조회 테스트")
    @Test
    public void findAll() {
        UserEntity user = userService.findUserById("test");

        List<OrdersEntity> orders = orderService.findAllByUserId(user.getUserId());

        assertEquals(1, orders.size());
    }

    @Order(7)
    @DisplayName("해당 유저의 주문에 해당하는 주문상세내역 전체 조회 테스트")
    @Test
    public void findAllOrderItems() {
        UserEntity user = userService.findUserById("test");
        OrdersEntity order = orderService.findRecentOrderByUserId(user.getUserId());

        List<OrderItemEntity> orderItems = orderItemService.findAllOrderItemsByOrderId(order.getOrderId());

        assertEquals(2, orderItems.size());
    }

    /*
    * 테스트 완료
    * */
    @Disabled
    @Order(8)
    @DisplayName("주문 시 반찬이 없으면(재고가 없으면) 주문실패 테스트")
    @Test
    public void banchanNullCheck() {

        banchanService.updateQuantity(2L, 0);
        orderService.createOrder(userService.findUserById("test"), PaymentType.CARD, CardCompany.KAKAO, OrderType.ORDER, null);
    }
}
