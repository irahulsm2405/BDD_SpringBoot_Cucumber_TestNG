package com.products.service;

import com.products.exception.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.entity.ProductEntity;
import com.products.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductEntity addNewProduct(ProductEntity productEntity) {

		Double price = productEntity.getPrice();
		if (productEntity.getName() == null || productEntity.getName().trim().length() == 0 || productEntity.getName().equalsIgnoreCase("null")) {
			throw new InvalidNameException("Input name cannot be null");
		} 
		else if (productEntity.getDescription() == null || productEntity.getDescription().trim().length() == 0 || productEntity.getDescription().equalsIgnoreCase("null")) {
			throw new InvalidDescriptionException("Input description cannot be null");
		} 
		else if (price==null || price<=0.00) {
			throw new InvalidPriceException("Input price cannot be null or less than 0");
		} 
		else {
			return productRepository.save(productEntity);
		}
	}

	@Override
	public List<ProductEntity> getAllProducts() {
		List<ProductEntity> products = productRepository.findAll();
		if(products.isEmpty()) {
			throw new ProductDoesNotExistException("No products available");
		}else {
			return products;
		}
	}

	@Override
	public ProductEntity getProductById(int id) {
		return productRepository.findById(id).orElseThrow(()-> new ProductDoesNotExistException("Product does not exist"));
	}

	@Override
	public void deleteProductById(int id) {
		productRepository.deleteById(id);
	}

	@Override
	public void deleteAllProducts() {
		productRepository.deleteAll();
		
	}

	@Override
	public ProductEntity updateProduct(int id, ProductEntity productEntity) {
		
		try{
			Double price = productEntity.getPrice();
			if (productEntity.getName() == null || productEntity.getName().trim().length() == 0 || productEntity.getName().equalsIgnoreCase("null")) {
				throw new InvalidNameException("Input name cannot be null");
			} 
			else if (productEntity.getDescription() == null || productEntity.getDescription().trim().length() == 0 || productEntity.getDescription().equalsIgnoreCase("null")) {
				throw new InvalidDescriptionException("Input description cannot be null");
			} 
			else if (price==null || price<=0.00) {
				throw new InvalidPriceException("Input price cannot be null or less than 0");
			} 
			else {
				productEntity.setId(id);
				return productRepository.save(productEntity);
			}
		}catch(Exception e){
			throw new ProductDoesNotExistException("Product does not exists");
		}
		
	}

}
