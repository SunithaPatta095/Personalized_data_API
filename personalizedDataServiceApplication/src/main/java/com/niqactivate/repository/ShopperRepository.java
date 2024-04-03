package com.niqactivate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PersonalizedDataAPIEntity.Shopper;

public interface ShopperRepository extends JpaRepository<Shopper, Long> {

	Optional<Shopper> findByShopperId(String shopperId);

}
