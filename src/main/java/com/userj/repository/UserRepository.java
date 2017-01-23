package com.userj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userj.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserId(String userId);
}
