package com.bbsk.banchanshop.banchan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbsk.banchanshop.banchan.entity.BanchanIngredientEntity;
import com.bbsk.banchanshop.banchan.repository.BanchanIngredientRepository;

@Service
@Transactional(readOnly = true)
public class BanchanIngredientService {

	private final BanchanIngredientRepository banchanIngredientRepository;
	
	public BanchanIngredientService(final BanchanIngredientRepository banchanIngredientRepository) {
		super();
		this.banchanIngredientRepository = banchanIngredientRepository;
	}

	/**
	 * 반찬재료 조회
	 * @param name 반찬이름
	 * @return
	 */
	public List<BanchanIngredientEntity> findByIngredientName(String name) {

		return banchanIngredientRepository.findByIngredientName(name);
	}

	/**
	 * 반찬재료 조회
	 * @param id 반찬아이디
	 * @return
	 */
	public BanchanIngredientEntity findByIngredientId(long id) {
		return banchanIngredientRepository.findById(id).orElse(null);
	}

	/**
	 * 반찬재고 수량 업데이트
	 * @param banchanIngredientId 반찬재료 아이디
	 * @param newQuantity 반찬재고 수량
	 * @return
	 */
	@Transactional
	public BanchanIngredientEntity updateQuantity(Long banchanIngredientId, int newQuantity) {
		return banchanIngredientRepository.findById(banchanIngredientId).orElse(null).updateIngredientQuantity(newQuantity);
	}
}
