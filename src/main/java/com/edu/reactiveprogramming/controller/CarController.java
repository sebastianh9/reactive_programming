package com.edu.reactiveprogramming.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.reactiveprogramming.model.Car;
import com.edu.reactiveprogramming.service.CarService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

	private final CarService carService;

	@GetMapping("/serie/{id}")
	public Mono<Car> getCarBySerie(@PathVariable Integer id) {
		return carService.getCarBySerie(id);
	}
	
	@GetMapping("/by/{id}")
	public Mono<Car> getById(@PathVariable Integer id) {
		return carService.findById(id);
	}


	@GetMapping("/all")
	public Flux<Car> getAll() {
		return carService.getCars();
	}

	@PostMapping()
	public Mono<Car> save(@RequestBody Car car) {
		return carService.save(car);
	}

	@PutMapping()
	public Mono<Car> update(@RequestBody Car car) {
		return carService.update(car);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteCarBySerie(@PathVariable Integer id) {
		return carService.deleteCarBySerie(id);
	}

}

