package com.edu.reactiveprogramming.model;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccionesSale {
	
	Mono<Sale> save(Sale sale);
	Flux<Sale> getAll();

}
