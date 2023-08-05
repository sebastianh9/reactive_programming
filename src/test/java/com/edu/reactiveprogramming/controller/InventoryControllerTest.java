package com.edu.reactiveprogramming.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edu.reactiveprogramming.model.UtilTest;
import com.edu.reactiveprogramming.service.InventoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class InventoryControllerTest {

	@InjectMocks
	InventoryController inventoryController;

	@Mock
	InventoryService inventoryService;

	@Test
	public void save() {
		when(inventoryService.save(any())).thenReturn(Mono.just(UtilTest.buildInventory()));
		StepVerifier.create(inventoryController.save(UtilTest.buildInventory()))
			.assertNext(r -> r.getCar().equals(1))
			.verifyComplete();
	}

	@Test
	public void getAll() {
		when(inventoryService.getAll()).thenReturn(Flux.just(UtilTest.buildInventory()));
		StepVerifier.create(inventoryController.getAll())
			.assertNext(r -> r.getCar().equals(1))
			.verifyComplete();
	}

	@Test
	public void update() {
		when(inventoryService.update(any())).thenReturn(Mono.just(UtilTest.buildInventory()));
		StepVerifier.create(inventoryController.update(UtilTest.buildInventory()))
			.assertNext(r -> r.getCar().equals(1))
			.verifyComplete();
	}

	@Test
	public void deleteInventoryById() {
		when(inventoryService.deleteInventoryById(any())).thenReturn(Mono.empty());
		StepVerifier.create(inventoryController.deleteInventoryById(1))
			.verifyComplete();
	}

	@Test
	public void findByCar() {
		when(inventoryService.findByCar(any())).thenReturn(Mono.just(UtilTest.buildInventory()));
		StepVerifier.create(inventoryController.findByCar(1))
			.assertNext(r -> r.getInventory().equals(1000))
			.verifyComplete();
	}

	@Test
	public void findById() {
		when(inventoryService.findById(any())).thenReturn(Mono.just(UtilTest.buildInventory()));
		StepVerifier.create(inventoryController.findById(1))
			.assertNext(r -> r.getSales().equals(20))
			.verifyComplete();
	}

}
