package com.freshfruits.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freshfruits.app.entity.Product;
import com.freshfruits.app.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173") // Allow frontend access
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/getProducts")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@PostMapping("/add")
	public Product addProduct(@RequestBody Product product) {
	    return productService.addProduct(product);
	}

	@PutMapping("/update/{id}")
	public Product editProduct(@PathVariable Long id, @RequestBody Product product) {
		product.setId(id);
		return productService.updateProduct(product);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}
}
