package com.bbsk.banchanshop.banchan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class BanchanServiceTests {

	@Autowired
	private BanchanService banchanService;

	@Transactional
	@DisplayName("반찬등록 테스트")
	@Test
	public void insertBanchan() {
		log.info("=================== 반찬등록 테스트 =================");
		BanchanEntity registBanchan = saveBanchan();
		// ====================================================================
		
		assertEquals("김치찌게", registBanchan.getBanchanName());

	}

	@Transactional
	@DisplayName("반찬 조회 By 반찬이름")
	@Test
	public void findByName() {
		log.info("=================== 반찬 조회 By 반찬이름 테스트 =================");
		BanchanEntity banchan = saveBanchan();

		BanchanEntity findBanchanByName = banchanService.findBybanchanName(banchan.getBanchanName());

		assertEquals("김치찌게", findBanchanByName.getBanchanName());
	}

	@Transactional
	@DisplayName("반찬 조회 By 반찬아이디")
	@Test
	public void findById() {
		log.info("=================== 반찬 조회 By 반찬아이디 테스트 =================");
		BanchanEntity banchan = saveBanchan();

		BanchanEntity findBanchanByName = banchanService.findBanchanById(banchan.getBanchanId());

		assertEquals("김치찌게", findBanchanByName.getBanchanName());
	}

	@Transactional
	@DisplayName("반찬 재고 수량 업데이트")
	@Test
	public void updateQuantity() {
		log.info("=================== 반찬 재고 수량 업데이트 =================");
		BanchanEntity banchan = saveBanchan();

		BanchanEntity updateEntity = banchanService.updateQuantity(banchan.getBanchanId(), 5);

		assertEquals("김치찌게", updateEntity.getBanchanName());
		assertEquals(5, updateEntity.getBanchanStockQuantity());
	}

	private BanchanEntity saveBanchan() {
		BanchanEntity entity = BanchanEntity.builder()
				.banchanStockQuantity(1)
				.banchanName("김치찌게")
				.banchanPrice(13500)
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
}
