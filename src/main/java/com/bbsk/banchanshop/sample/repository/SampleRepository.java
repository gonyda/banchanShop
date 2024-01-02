package com.bbsk.banchanshop.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository
;

import com.bbsk.banchanshop.sample.entity.SampleEntity;

public interface SampleRepository extends JpaRepository<SampleEntity, Integer>{

}
