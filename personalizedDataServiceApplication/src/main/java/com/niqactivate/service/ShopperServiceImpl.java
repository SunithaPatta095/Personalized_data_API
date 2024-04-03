package com.niqactivate.service;

import com.niqactivate.entity.Shopper;
import com.niqactivate.exception.DuplicateEntryException;
import com.niqactivate.exception.ResourceNotFoundException;
import com.niqactivate.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

@Service
public class ShopperServiceImpl implements ShopperService {

	private final ShopperRepository shopperRepository;
	private final EntityManager entityManager;

	@Autowired
	public ShopperServiceImpl(ShopperRepository shopperRepository, EntityManager entityManager) {
		this.shopperRepository = shopperRepository;
		this.entityManager = entityManager;
	}

	@Override
	public Shopper createShopper(Shopper shopper) {
		Optional<Shopper> existingShopper = shopperRepository.findByShopperId(shopper.getShopperId());
		if (existingShopper.isPresent()) {
			throw new DuplicateEntryException(
					"Shopper with ShopperId '" + shopper.getShopperId() + "' already exists.");
		}
		return shopperRepository.save(shopper);
	}

	@Override
	public List<Shopper> getAllShoppers() {
		return shopperRepository.findAll();
	}

	@Override
	public Shopper getShopperById(Long id) {
		return shopperRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shopper", "id", id));
	}

	@Override
	public Shopper saveShopper(Shopper shopper) {
		return shopperRepository.save(shopper);
	}

	@Override
	public Shopper saveShopperPersonalizedProductList(Shopper shopper) {
		Shopper existingShopper = shopperRepository.findById(shopper.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Shopper"));

		existingShopper.setPersonalizedProductList(shopper.getPersonalizedProductList());

		return shopperRepository.save(existingShopper);
	}
}
