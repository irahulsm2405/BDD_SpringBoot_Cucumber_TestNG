package com.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.products.entity.ProductEntity;
import com.products.service.ProductService;

@RestController
public class ProductsController {

	@Autowired
	private ProductService productService;

	@PostMapping("/product")
	public ProductEntity addNewProduct(@RequestBody ProductEntity productEntity) {
		return productService.addNewProduct(productEntity);
	}

	@GetMapping("/product")
	public List<ProductEntity> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/product/{id}")
	public ProductEntity getProductById(@PathVariable int id) {
		return productService.getProductById(id);
	}

	@DeleteMapping("/product/{id}")
	public void deleteProductById(@PathVariable int id) {
		productService.deleteProductById(id);
	}

	@DeleteMapping("/product")
	public void deleteAllProduct() {
		productService.deleteAllProducts();
	}
	
	@PutMapping("/product/{id}")
	public ProductEntity updateExistingProduct(@PathVariable int id, @RequestBody ProductEntity entity) {
		return productService.updateProduct(id, entity);
	}
}
