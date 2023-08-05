package com.edu.reactiveprogramming.model;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccionesInventory {
	
	Mono<Inventory> save(Inventory inventory);
	Mono<Inventory> findByCar(Integer id);
	Flux<Inventory> getAll();
	Mono<Inventory> update(Inventory inventory);
	Mono<Void> deleteInventoryById(Integer id);
	Mono<Inventory> findById(Integer id);

}
