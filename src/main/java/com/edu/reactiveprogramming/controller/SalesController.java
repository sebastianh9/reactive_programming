package com.edu.reactiveprogramming.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.reactiveprogramming.model.Sale;
import com.edu.reactiveprogramming.service.SaleService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SalesController {
	
	private final SaleService saleService;


	@PostMapping("/save")
	public Mono<Sale> save(@RequestBody Sale sale) {
		return saleService.save(sale);
	}
	
	@GetMapping("/all")
	public Flux<Sale> getAll() {
		return saleService.getAll();
	}

}
