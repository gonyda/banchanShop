package com.bbsk.banchanshop.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingRepositoryCustom<T>{

    Page<T> findAllByPaging(Pageable page);
}
