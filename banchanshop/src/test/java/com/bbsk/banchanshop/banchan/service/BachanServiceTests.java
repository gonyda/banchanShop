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

@SpringBootTest
@Slf4j
public class BachanServiceTests {

	@Autowired
	private BanchanService banchanService;
	
	@DisplayName("반찬등록 테스트")
	@Test
	public void insertBanchan() {
		log.info("=================== 반찬등록 테스트 =================");
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
		
		
		BanchanEntity registBanchan = banchanService.registBanchan(entity, list);
		// ====================================================================
		
		log.info("김치찌게: {}", registBanchan.toString());
		log.info("김치찌게 재료: {}", registBanchan.getBanchanIngredient().toString());
		registBanchan.getBanchanIngredient().forEach(e -> {
			log.info("해당 반찬재료의 요리: {}", e.getBanchan().toString());
		});
		assertEquals("김치찌게", registBanchan.getBanchanName());
		
	}
	
	@DisplayName("반찬 조회 By 반찬이름")
	@Test
	public void findByName() {
		log.info("=================== 반찬 조회 By 반찬이름 테스트 =================");
		BanchanEntity findBanchanByName = banchanService.findBybanchanName("김치찌게");
		
		assertEquals(1, findBanchanByName.getBanchanId());
	}
	
	@DisplayName("반찬 조회 By 반찬아이디")
	@Test
	public void findById() {
		log.info("=================== 반찬 조회 By 반찬아이디 테스트 =================");
		BanchanEntity findBanchanByName = banchanService.findBanchanById(1L);
		
		assertEquals("김치찌게", findBanchanByName.getBanchanName());
	}
	
	@DisplayName("반찬 재고 수량 업데이트")
	@Test
	public void updateQuantity() {
		log.info("=================== 반찬 재고 수량 업데이트 =================");
		BanchanEntity banchanEntity = banchanService.findBanchanById(1L);
		banchanEntity.updateBanchanQuantity(5); // 1 -> 5
		
		BanchanEntity updateEntity = banchanService.updateQuantity(banchanEntity.getBanchanId(), 5);

		assertEquals("김치찌게", updateEntity.getBanchanName());
		assertEquals(5, updateEntity.getBanchanStockQuantity());
	}
}
