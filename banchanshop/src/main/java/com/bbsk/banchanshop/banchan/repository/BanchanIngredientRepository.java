package com.bbsk.banchanshop.banchan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;

public interface BanchanIngredientRepository extends JpaRepository<BanchanIngredientEntity, Long>{
	List<BanchanIngredientEntity> findByIngredientName(String name);

	void deleteByBanchanBanchanId(Long banchanId);
}
