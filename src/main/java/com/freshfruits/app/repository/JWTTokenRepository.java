package com.freshfruits.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.freshfruits.app.entity.JWTToken;

@Repository
public interface JWTTokenRepository extends JpaRepository<JWTToken, Integer>{

	@Query("SELECT t FROM JWTToken t WHERE t.user.userId=:userId")
	public JWTToken findByUser_UserId(int userId);

	public Optional<JWTToken> findByToken(String token);
}
