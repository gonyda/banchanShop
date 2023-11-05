package com.bbsk.banchanshop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbsk.banchanshop.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
