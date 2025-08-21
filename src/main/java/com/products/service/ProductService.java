package com.products.service;

import java.util.List;

import com.products.entity.ProductEntity;

public interface ProductService {

	ProductEntity addNewProduct(ProductEntity productEntity);

	List<ProductEntity> getAllProducts();

	ProductEntity getProductById(int id);

	void deleteProductById(int id);

	void deleteAllProducts();

	ProductEntity updateProduct(int id, ProductEntity entity);

}
