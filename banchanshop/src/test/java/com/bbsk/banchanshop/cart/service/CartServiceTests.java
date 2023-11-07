package com.bbsk.banchanshop.cart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.service.BanchanService;
import com.bbsk.banchanshop.contant.UserType;
import com.bbsk.banchanshop.user.entity.UserEntity;
import com.bbsk.banchanshop.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class CartServiceTests {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BanchanService banchanService;
	
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
							 .banchanStockQuantity(1)
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
		
		
		BanchanEntity registBanchan = banchanService.registBanchan(entity, list);
		// ====================================================================
		
		assertEquals("김치찌게", registBanchan.getBanchanName());
		
	}
	
	@Transactional
	@Order(3)
	@DisplayName("장바구니 상품 넣기 테스트")
	@Test
	public void putCart() {
		log.info(" =================== 장바구니 상품 넣기 테스트 ================== ");
		UserEntity userEntity = userService.findUserById("test");
		BanchanEntity banchanEntity = banchanService.findBybanchanName("김치찌게");
		
		cartService.putCart(userEntity, banchanEntity, 2);
		
		UserEntity afterUserEntity = userService.findUserById("test");
		log.info("카트 :: {}", afterUserEntity.getCart().toString());
		log.info("카트 아이템 :: {}", afterUserEntity.getCart().getCartItem().toString());
		
		cartService.putCart(userEntity, banchanEntity, 10);
		
		afterUserEntity = userService.findUserById("test");
		log.info("카트 :: {}", afterUserEntity.getCart().toString());
		log.info("카트 아이템 :: {}", afterUserEntity.getCart().getCartItem().toString());
		
		//assertEquals("test", afterUserEntity.getUserId()); // test 라는 유저가
		//assertEquals("김치찌게", afterUserEntity.getCart().getCartItem().get(0).getBanchan().getBanchanName()); // 김치찌게를 장바구니에 담았다
		//assertEquals(2, afterUserEntity.getCart().getCartItem().get(0).getBanchanQuantity()); // 2개를 담았고
		//assertEquals(2, afterUserEntity.getCart().getCartTotalQuantity());// 장바구니 총 갯수는 2
		//assertEquals(27000, afterUserEntity.getCart().getCartTotalPrice());// 장바구니 총 가격은 27000원이다
	}
	
}
