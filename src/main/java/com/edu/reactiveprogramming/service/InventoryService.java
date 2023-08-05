package com.edu.reactiveprogramming.service;

import java.util.Objects;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.edu.reactiveprogramming.model.AccionesInventory;
import com.edu.reactiveprogramming.model.Inventory;
import com.edu.reactiveprogramming.model.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log
public class InventoryService implements AccionesInventory {
	
	private final InventoryRepository inventoryRepository;
	private final CarService carService;
	
	private static final String ERROR_CAR_NOT_FOUND = "No existe el carro para inventario";
	
	public Mono<Inventory> save(Inventory inventory) {
		return carService.findById(inventory.getCar())
				.switchIfEmpty(Mono.error(new Throwable(ERROR_CAR_NOT_FOUND)))
				.doOnSuccess(r -> log.log(Level.INFO, r.toString()))
				.flatMap(car -> inventoryRepository.save(inventory));
	}
	
	public Mono<Inventory> findByCar(Integer id) {
		return inventoryRepository.findByCar(id);
	}
	
	public Flux<Inventory> getAll() {
		return inventoryRepository.findAll();
	}

	public Mono<Inventory> update(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	public Mono<Void> deleteInventoryById(Integer id) {
		return inventoryRepository.findById(id)
				.map(Objects::requireNonNull)
				.flatMap(inv -> inventoryRepository.deleteById(inv.getId()));
	}

	public Mono<Inventory> findById(Integer id) {
		return inventoryRepository.findById(id);
	}

}
