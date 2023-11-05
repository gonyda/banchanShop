package com.bbsk.banchanshop.user.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.cart.repository.CartRepository;
import com.bbsk.banchanshop.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 해당 설정을 사용하지 않으면 EmbededDatabase를 사용한다
@Slf4j
public class UserEntityTests {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@BeforeEach
	public void createEntity() {
		userRepo.save(UserEntity.builder()
				.userId("bbsk3939")
				.userPw("zxc156156")
				.userEmail("test@test.com")
				.userName("백승권")
				.address("서울특별시")
				.adminYn(false)
				.phoneNumber("01011112222")
				.build());
		
		userRepo.flush();
		
	}
	
	@DisplayName("유저 엔티티 조회 테스트")
	@Test
	public void find() {
		log.info("====================유저 엔티티 조회 테스트");
		UserEntity user = userRepo.findById("bbsk3939").orElse(null);
		
		assertEquals("백승권", user.getUserName());
		
	}
	
	@DisplayName("유저 엔티티 변경 테스트")
	@Test
	public void update() {
		log.info("====================유저 엔티티 변경 테스트");
		UserEntity user = userRepo.findById("bbsk3939").orElse(null);
		
		user.changePw("1234");
		
		assertEquals("1234", user.getUserPw());
		
	}
	
	@DisplayName("유저 및 카트 insert 테스트")
	@Test
	public void userAndCartCreate() {
		log.info("====================유저 및 카트 insert 테스트");
		CartEntity cart = cartRepo.save(CartEntity.builder()
												.cartId("ventosan")
												.totalQuantity(0)
												.totalSum(0)
												.build());
		
		UserEntity user = userRepo.save(UserEntity.builder()
												.userId("ventosan")
												.userPw("zxc156156")
												.userEmail("test111@test.com")
												.userName("백승권")
												.address("서울특별시")
												.adminYn(false)
												.phoneNumber("01011112222")
												.cart(cart)
												.build());
		cartRepo.flush();
		userRepo.flush();
		
		assertEquals(user.getCart().getCartId(), cart.getCartId());
		assertEquals("ventosan", cart.getCartId());
	}
}
