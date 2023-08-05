package com.edu.reactiveprogramming.model.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.edu.reactiveprogramming.model.Car;

import reactor.core.publisher.Mono;

@Repository
public interface CarRepository extends R2dbcRepository<Car, Integer> {

	Mono<Car> findBySerie(Integer serial);

}
