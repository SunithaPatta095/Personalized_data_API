package com.niqactivate.controller;

import com.niqactivate.entity.Product;
import com.niqactivate.entity.Shopper;
import com.niqactivate.exception.ResourceNotFoundException;
import com.niqactivate.service.ProductService;
import com.niqactivate.service.ShopperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
public class InternalApiController {

	private final ShopperService shopperService;
	private final ProductService productService;

	@Autowired
	public InternalApiController(ShopperService shopperService, ProductService productService) {
		this.shopperService = shopperService;
		this.productService = productService;
	}

	@PostMapping("/shopper")
	public ResponseEntity<?> storeShopperPersonalizedProductList(@RequestBody Shopper shopper) {
		shopperService.saveShopperPersonalizedProductList(shopper);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/product")
	public ResponseEntity<?> storeProductMetadata(@RequestBody Product product) {
		productService.saveProductMetadata(product);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProductsByShopper(@RequestParam String shopperId,
			@RequestParam(required = false) String category, @RequestParam(required = false) String brand,
			@RequestParam(defaultValue = "10") int limit) {

		List<Product> products = productService.getProductsByShopper(shopperId, category, brand, limit);
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("No products found for the given criteria");
		}
		return ResponseEntity.ok(products);
	}
}
