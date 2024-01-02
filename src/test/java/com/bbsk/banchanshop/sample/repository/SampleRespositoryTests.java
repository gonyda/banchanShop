package com.bbsk.banchanshop.sample.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bbsk.banchanshop.sample.entity.SampleEntity;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 해당 설정을 사용하지 않으면 EmbededDatabase를 사용한다
@Slf4j
public class SampleRespositoryTests {

	@Autowired
	private SampleRepository repository;
	
	@DisplayName("삽입 및 조회 테스트")
	@Test
	public void get() {
		log.info("===============================");
		SampleEntity entity = SampleEntity.builder().name("백승권").age(29).email("bbsk3939@gmail.com").build();
		
		log.info("=== insert start");
		repository.save(entity);
		log.info("=== entity.id : {}", entity.getId());
		log.info("=== entity.toString() : {}", entity.toString());
		log.info("=== insert end");
		log.info("===============================");
		assertThat(repository.findById(entity.getId()).orElse(null).getName()).isEqualTo("백승권");
	}
	
	@DisplayName("삭제 테스트")
	@Test
	public void delete() {
		SampleEntity entity = SampleEntity.builder().name("테스트").age(29).email("bbsk3939@gmail.com").build();
		
		repository.save(entity);
		assertEquals(repository.findById(entity.getId()).orElse(null).getName(), "테스트");
		
		repository.deleteById(entity.getId());
		assertNull(repository.findById(entity.getId()).orElse(null));
	}
	
	@DisplayName("수정 테스트")
	@Test
	public void update() {
		SampleEntity entity = SampleEntity.builder().name("테스트").age(29).email("bbsk3939@gmail.com").build();
		
		repository.save(entity);
		assertEquals(repository.findById(entity.getId()).orElse(null).getName(), "테스트");
		
		entity.updateEntity("테스트1111");
		assertEquals(repository.findById(entity.getId()).orElse(null).getName(), "테스트1111");
	}
}
