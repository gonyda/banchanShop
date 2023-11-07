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

	public List<BanchanIngredientEntity> findByIngredientName(String name) {

		return banchanIngredientRepository.findByIngredientName(name);
	}

	public BanchanIngredientEntity findByIngredientId(long id) {
		return banchanIngredientRepository.findById(id).orElse(null);
	}

	@Transactional
	public BanchanIngredientEntity updateQuantity(Long banchanIngredientId, int newQuantity) {
		return banchanIngredientRepository.findById(banchanIngredientId).orElse(null).updateIngredientQuantity(newQuantity);
	}
}
