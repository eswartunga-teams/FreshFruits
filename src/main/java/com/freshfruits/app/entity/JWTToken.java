package com.freshfruits.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jwt_tokens")
public class JWTToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tokenId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	private String token;

	@Column
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime expairesAt;

	public JWTToken() {

	}

	public JWTToken(Integer tokenId, User user, String token, LocalDateTime createdAt, LocalDateTime expairesAt) {
		this.tokenId = tokenId;
		this.user = user;
		this.token = token;
		this.createdAt = createdAt;
		this.expairesAt = expairesAt;
	}

	public JWTToken(User user, String token, LocalDateTime createdAt, LocalDateTime expairesAt) {
		this.user = user;
		this.token = token;
		this.createdAt = createdAt;
		this.expairesAt = expairesAt;
	}

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getExpairesAt() {
		return expairesAt;
	}

	public void setExpairesAt(LocalDateTime expairesAt) {
		this.expairesAt = expairesAt;
	}

}
