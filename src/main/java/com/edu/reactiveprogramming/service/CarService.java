package com.edu.reactiveprogramming.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.edu.reactiveprogramming.model.Car;
import com.edu.reactiveprogramming.model.repository.CarRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CarService {

	private final CarRepository carRepository;
	
	public Mono<Car> findById(Integer id) {
		return carRepository.findById(id);
	}

	public Mono<Car> getCarBySerie(Integer serial) {
		return carRepository.findBySerie(serial);
	}

	public Flux<Car> getCars() {
		return carRepository.findAll();
	}

	public Mono<Car> save(Car car) {
		return carRepository.save(car);
	}

	public Mono<Car> update(Car car) {
		return carRepository.save(car);
	}

	public Mono<Void> deleteCarBySerie(Integer id) {
		return carRepository.findBySerie(id)
				.map(Objects::requireNonNull)
				.flatMap(car -> carRepository.deleteById(car.getId()));
	}

}
