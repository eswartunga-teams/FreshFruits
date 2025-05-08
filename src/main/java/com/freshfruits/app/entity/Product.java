package com.freshfruits.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	@Column
	private String name;
	@Column
	private String image_url;
	@Column
	private int price;
	@Column
	private String category;
	@Column
	private int stock;

	public Product() {

	}

	public Product(Long id, String name, String image_url, int price, String category, int stock) {
		this.id = id;
		this.name = name;
		this.image_url = image_url;
		this.price = price;
		this.category = category;
		this.stock = stock;
	}

	public Product(String name, String image_url, int price, String category, int stock) {
		super();
		this.name = name;
		this.image_url = image_url;
		this.price = price;
		this.category = category;
		this.stock = stock;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
