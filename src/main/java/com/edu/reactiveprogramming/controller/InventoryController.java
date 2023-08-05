package com.edu.reactiveprogramming.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.reactiveprogramming.model.Inventory;
import com.edu.reactiveprogramming.service.InventoryService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@PostMapping("/save")
	public Mono<Inventory> save(@RequestBody Inventory inventory) {
		return inventoryService.save(inventory);
	}
	
	@GetMapping("/all")
	public Flux<Inventory> getAll() {
		return inventoryService.getAll();
	}

	@PutMapping("/update")
	public Mono<Inventory> update(@RequestBody Inventory inventory) {
		return inventoryService.update(inventory);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteInventoryById(@PathVariable Integer id) {
		return inventoryService.deleteInventoryById(id);
	}
	
	@GetMapping("/car/{id}")
	public Mono<Inventory> findByCar(@PathVariable Integer id) {
		return inventoryService.findByCar(id);
	}
	
	@GetMapping("/{id}")
	public Mono<Inventory> findById(@PathVariable Integer id) {
		return inventoryService.findById(id);
	}

}
