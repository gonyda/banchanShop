package com.bbsk.banchanshop.banchan.repository;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BanchanRepositoryCustom {

    Page<BanchanEntity> findAllByPaging(Pageable page);
}
