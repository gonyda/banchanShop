package com.bbsk.banchanshop.banchan.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bbsk.banchanshop.banchan.repository.BanchanIngredientRepository;
import com.bbsk.banchanshop.banchan.repository.BanchanRepository;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class BanchanEntityTests {

	@Autowired
	private BanchanIngredientRepository biRepo;
	
	@Autowired
	private BanchanRepository banchanRepository;
	
	@DisplayName("반찬재료 등록 테스트")
	@Test
	public void insert() {
		BanchanIngredientEntity 고춧가루 = BanchanIngredientEntity.builder()
							   .ingredientName("고춧가루")
							   .quantity(5)
							   .inputDate(LocalDateTime.now())
							   .build();
		고춧가루.plusExpirationDate(10L);
		
		BanchanIngredientEntity entity = biRepo.save(고춧가루);
		
		log.info("고춧가루: {}", entity.toString());
		assertEquals("고춧가루", entity.getIngredientName());
		
	}
	
	@DisplayName("반찬 등록 테스트")
	@Test
	public void insertBanchan() {
		List<BanchanIngredientEntity> list = new ArrayList<>();
		
		BanchanEntity 김치찌게 = BanchanEntity.builder()
											 .banchanStockQuantity(1)
											 .banchanName("김치찌게")
											 .banchanPrice(13500)
											 .createDate(LocalDateTime.now())
											 .build();
		김치찌게.plusExpirationDate(3L);
		banchanRepository.save(김치찌게);
		
		BanchanIngredientEntity 고춧가루 = BanchanIngredientEntity.builder()
							   .banchan(김치찌게)
							   .ingredientName("고춧가루")
							   .quantity(5)
							   .inputDate(LocalDateTime.now())
							   .build();
		고춧가루.plusExpirationDate(10L);
		BanchanIngredientEntity entity1 = biRepo.save(고춧가루);
		list.add(entity1);
		
		BanchanIngredientEntity 간장 = BanchanIngredientEntity.builder()
						   .banchan(김치찌게)
						   .ingredientName("간장")
						   .quantity(5)
						   .inputDate(LocalDateTime.now())
						   .build();
		간장.plusExpirationDate(10L);
		BanchanIngredientEntity entity2 = biRepo.save(간장);
		list.add(entity2);
		
		BanchanIngredientEntity 소금 = BanchanIngredientEntity.builder()
				           .banchan(김치찌게)
						   .ingredientName("소금")
						   .quantity(5)
						   .inputDate(LocalDateTime.now())
						   .build();
		소금.plusExpirationDate(10L);
		BanchanIngredientEntity entity3 = biRepo.save(소금);
		list.add(entity3);
		
		김치찌게.setIngredientList(list);
		
		assertEquals("김치찌게", banchanRepository.findById(김치찌게.getBanchanId()).orElse(null).getBanchanName());
	}
}
