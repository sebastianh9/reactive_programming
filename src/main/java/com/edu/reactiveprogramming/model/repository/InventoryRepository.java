package com.edu.reactiveprogramming.model.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.edu.reactiveprogramming.model.Inventory;

import reactor.core.publisher.Mono;

@Repository
public interface InventoryRepository extends R2dbcRepository<Inventory, Integer> {
	
	Mono<Inventory> findByCar(Integer id);

}
