package com.niqactivate.controller;

import com.niqactivate.entity.Product;
import com.niqactivate.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/external/api")
public class ExternalApiController {

	private final ProductService productService;

	@Autowired
	public ExternalApiController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProductsByShopper(@RequestParam String shopperId,
			@RequestParam(required = false) String category, @RequestParam(required = false) String brand,
			@RequestParam(defaultValue = "10") int limit) {

		List<Product> products = productService.getProductsByShopper(shopperId, category, brand, limit);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}
