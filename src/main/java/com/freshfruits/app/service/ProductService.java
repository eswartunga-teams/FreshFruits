package com.freshfruits.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.freshfruits.app.entity.Product;
import com.freshfruits.app.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product addProduct(Product product) {
	    return productRepository.save(product);
	}


	public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
    	productRepository.deleteById(id);
    }
}
