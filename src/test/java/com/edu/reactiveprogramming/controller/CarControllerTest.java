package com.edu.reactiveprogramming.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edu.reactiveprogramming.model.UtilTest;
import com.edu.reactiveprogramming.service.CarService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

	@InjectMocks
	CarController carController;
	
	@Mock
	CarService carService;

	@Test
	public void getCarBySerie() {
		when(carService.getCarBySerie(any())).thenReturn(Mono.just(UtilTest.buildCar()));
		StepVerifier.create(carController.getCarBySerie(1))
			.assertNext(r -> r.getId().equals(1))
			.verifyComplete();
	}
	
	@Test
	public void getById() {
		when(carService.findById(any())).thenReturn(Mono.just(UtilTest.buildCar()));
		StepVerifier.create(carController.getById(1))
			.assertNext(r -> r.getColor().equals("blanco"))
			.verifyComplete();
	}

	@Test
	public void getAll() {
		when(carService.getCars()).thenReturn(Flux.just(UtilTest.buildCar()));
		StepVerifier.create(carController.getAll())
			.assertNext(r -> r.getSerie().equals(12345))
			.verifyComplete();
	}

	@Test
	public void save() {
		when(carService.save(any())).thenReturn(Mono.just(UtilTest.buildCar()));
		StepVerifier.create(carController.save(UtilTest.buildCar()))
			.expectComplete();
	}

	@Test
	public void update() {
		when(carService.update(any())).thenReturn(Mono.just(UtilTest.buildCar()));
		StepVerifier.create(carController.update(UtilTest.buildCar()))
			.expectComplete();
	}

	@Test
	public void deleteCarBySerie() {
		when(carService.deleteCarBySerie(any())).thenReturn(Mono.empty());
		StepVerifier.create(carController.deleteCarBySerie(1))
			.verifyComplete();
	}

}
