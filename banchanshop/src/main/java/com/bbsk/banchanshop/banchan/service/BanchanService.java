package com.bbsk.banchanshop.banchan.service;

import java.util.List;

import com.bbsk.banchanshop.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.repository.BanchanIngredientRepository;
import com.bbsk.banchanshop.banchan.repository.BanchanRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BanchanService {
	
	private final BanchanRepository banchanRepository;
	private final BanchanIngredientRepository banchanIngredientRepository;
	private final CartItemRepository cartItemRepository;

	/**
	 * 반찬 및 반찬재료 등록
	 * @param banchan 반찬
	 * @param ingredientList 반찬에 들어가는 재료들
	 * @return
	 */
	@Transactional
	public BanchanEntity registBanchan(BanchanEntity banchan, List<BanchanIngredientEntity> ingredientList) {
		ingredientList.forEach(e -> {
			e.setBanchan(banchan);
		});

		BanchanEntity banchanEntity = banchanRepository.save(banchan);
		banchanIngredientRepository.saveAll(ingredientList);

		return banchanEntity;
	}

	/**
	 * 반찬조회
	 * @param banchanName
	 * @return 
	 */
	public BanchanEntity findBybanchanName(String banchanName) {
		return banchanRepository.findBybanchanName(banchanName);
		
	}

	/**
	 * 반찬조회
	 * @param banchanId
	 * @return
	 */
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

	public List<BanchanEntity> findAll() {
		return banchanRepository.findAll();
	}
}
