package com.edu.reactiveprogramming.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edu.reactiveprogramming.model.UtilTest;
import com.edu.reactiveprogramming.service.SaleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SalesControllerTest {
	
	@InjectMocks
	SalesController salesController;
	
	@Mock
	SaleService saleService;

	@Test
	public void save() {
		when(saleService.save(any())).thenReturn(Mono.just(UtilTest.buildSale()));
		StepVerifier.create(salesController.save(UtilTest.buildSale()))
			.assertNext(r -> r.getCar().equals(1))
			.verifyComplete();
	}
	
	@Test
	public void getAll() {
		when(saleService.getAll()).thenReturn(Flux.just(UtilTest.buildSale()));
		StepVerifier.create(salesController.getAll())
			.assertNext(r -> r.getCar().equals(1))
			.verifyComplete();
	}

}
