package com.bbsk.banchanshop.banchan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;

public interface BanchanRepository extends JpaRepository<BanchanEntity, Long>, BanchanRepositoryCustom {
	BanchanEntity findBybanchanName(String name);
}
