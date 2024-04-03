package com.niqactivate.service;

import com.niqactivate.entity.Product;
import com.niqactivate.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final EntityManager entityManager;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, EntityManager entityManager) {
		this.productRepository = productRepository;
		this.entityManager = entityManager;
	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product saveProductMetadata(Product product) {
		return productRepository.save(product);
	}

	@Override
	public List<Product> getProductsByShopper(String shopperId, String category, String brand, int limit) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> root = cq.from(Product.class);

		Predicate shopperIdPredicate = cb.equal(root.get("shopperId"), shopperId);

		Predicate categoryPredicate = null;
		if (category != null) {
			categoryPredicate = cb.equal(root.get("category"), category);
		}

		Predicate brandPredicate = null;
		if (brand != null) {
			brandPredicate = cb.equal(root.get("brand"), brand);
		}

		Predicate combinedPredicate = null;
		if (categoryPredicate != null && brandPredicate != null) {
			combinedPredicate = cb.or(categoryPredicate, brandPredicate);
		} else if (categoryPredicate != null) {
			combinedPredicate = categoryPredicate;
		} else if (brandPredicate != null) {
			combinedPredicate = brandPredicate;
		}

		Predicate finalPredicate = combinedPredicate != null ? cb.and(shopperIdPredicate, combinedPredicate)
				: shopperIdPredicate;

		CriteriaQuery<Product> selectQuery = cq.select(root).where(finalPredicate);
		List<Product> products = entityManager.createQuery(selectQuery).setMaxResults(limit).getResultList();

		return products;
	}
}
