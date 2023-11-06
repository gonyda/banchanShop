package com.bbsk.banchanshop.banchan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.repository.BanchanIngredientRepository;
import com.bbsk.banchanshop.banchan.repository.BanchanRepository;

@Service
public class BanchanService {
	
	private final BanchanRepository banchanRepository;
	private final BanchanIngredientRepository banchanIngredientRepository;
	
	public BanchanService(final BanchanRepository banchanRepository,
			final BanchanIngredientRepository banchanIngredientRepository) {
		super();
		this.banchanRepository = banchanRepository;
		this.banchanIngredientRepository = banchanIngredientRepository;
	}

	/**
	 * 반찬 등록
	 * @param banchan 반찬
	 * @param ingredientList 반찬에 들어가는 재료들
	 * @return
	 */
	@Transactional
	public BanchanEntity registBanchan(BanchanEntity banchan, List<BanchanIngredientEntity> ingredientList) {
		BanchanEntity banchanEntity = banchanRepository.save(banchan);
		ingredientList.forEach(e -> {
			e.setBanchan(banchanEntity);
		});
		List<BanchanIngredientEntity> ingredientListEntity = banchanIngredientRepository.saveAll(ingredientList);
		banchanEntity.setIngredientList(ingredientListEntity);
		
		return banchanEntity;
	}

	/**
	 * 반찬조회
	 * @param banchanName
	 * @return 
	 */
	@Transactional(readOnly = true)
	public BanchanEntity findBanchanByName(String banchanName) {
		return banchanRepository.findBybanchanName(banchanName);
		
	}

	/**
	 * 반찬조회
	 * @param banchanId
	 * @return
	 */
	@Transactional(readOnly = true)
	public BanchanEntity findBanchanById(long banchanId) {
		return banchanRepository.findById(banchanId).orElse(null);
	}

	/**
	 * 반찬 재고 수량 업데이트
	 * @param banchanId 반찬 아이디
	 * @param newQuantity 반찬 재고 수량
	 * @return 새로운 반찬 엔티티
	 */
	@Transactional
	public BanchanEntity updateQuantity(Long banchanId, int newQuantity) {
		return banchanRepository.findById(banchanId).orElse(null).updateBanchanQuantity(newQuantity);
	}
}
