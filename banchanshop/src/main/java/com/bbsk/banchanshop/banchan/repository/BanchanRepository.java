package com.bbsk.banchanshop.banchan.repository;

import com.bbsk.banchanshop.common.repository.PagingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;

public interface BanchanRepository extends JpaRepository<BanchanEntity, Long>, PagingRepositoryCustom {
	BanchanEntity findBybanchanName(String name);
}
