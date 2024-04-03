package com.niqactivate.service;

import com.niqactivate.entity.Product;

import java.util.List;

public interface ProductService {

	Product createProduct(Product product);

	Product saveProductMetadata(Product product);

	List<Product> getProductsByShopper(String shopperId, String category, String brand, int limit);
}
