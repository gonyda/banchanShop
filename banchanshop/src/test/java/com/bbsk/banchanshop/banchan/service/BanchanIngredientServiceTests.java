package com.bbsk.banchanshop.banchan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class BanchanIngredientServiceTests {
	
	@Autowired
	private BanchanIngredientService ingredientService;
	
	@Autowired
	private BanchanService banchanService;

	@Transactional
	@DisplayName("반찬 및 반찬 재료 등록")
	@Test
	@Rollback(value = false)
	public void registIngredient() {
		log.info("=================== 반찬등록 테스트 =================");
		BanchanEntity banchan = saveBanchan();

		assertEquals("김치찌게", banchan.getBanchanName());
		assertEquals("고춧가루", ingredientService.findByIngredientId(1L).getIngredientName());
	}

	@Transactional
	@DisplayName("반찬 재료 조회 By 재료이름")
	@Test
	public void findByName() {
		log.info("=================== 재료 조회 By 재료 이름 테스트 =================");
		BanchanEntity banchan = saveBanchan();

		List<BanchanIngredientEntity> list = ingredientService.findByIngredientName("고춧가루");

		assertEquals(1, list.size());
	}

	@Transactional
	@DisplayName("반찬 재료 조회 By 재료아이디")
	@Test
	public void findById() {
		log.info("=================== 재료 조회 By 재료 아이디 테스트 =================");
		BanchanEntity banchan = saveBanchan();

		BanchanIngredientEntity findEntity = ingredientService.findByIngredientId(1L);
		assertEquals("고춧가루", findEntity.getIngredientName());
	}

	@Transactional
	@DisplayName("반찬 재료 재고 수량 업데이트")
	@Test
	public void updateQuantity() {
		log.info("=================== 재료 재고 수량 업데이트 =================");
		BanchanEntity banchan = saveBanchan();

		BanchanIngredientEntity updateEntity = ingredientService.updateQuantity(1L, 1);

		assertEquals("고춧가루", updateEntity.getIngredientName());
		assertEquals(1, updateEntity.getQuantity());
	}


	/**
	 * 반찬 및 반찬재료 등록
	 */
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
