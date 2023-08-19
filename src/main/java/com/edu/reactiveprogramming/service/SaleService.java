package com.edu.reactiveprogramming.service;

import java.time.LocalDate;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.edu.reactiveprogramming.model.AccionesSale;
import com.edu.reactiveprogramming.model.Sale;
import com.edu.reactiveprogramming.model.repository.SalesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log
public class SaleService implements AccionesSale {
	
	private final InventoryService inventoryService;
	private final SalesRepository salesRepository;
	
	private static final String ERROR_INVENTARY= "No existe inventario para el carro a vender";
	
	public Mono<Sale> save(Sale sale) {
		return inventoryService.findByCar(sale.getCar())
		.switchIfEmpty(Mono.error(new Exception(ERROR_INVENTARY)))
		.flatMap(inv -> {
			if (inv.getInventory() == 0) {
				return Mono.error(new Exception(ERROR_INVENTARY));
			}
			sale.setDate(LocalDate.now().toString());
			return salesRepository.save(sale)
					.doOnSuccess(s -> log.log(Level.INFO, s.toString()))
					.flatMap(respSale -> {
						inv.setInventory(inv.getInventory() - 1);
						inv.setSales(inv.getSales() + 1);
						return inventoryService.update(inv)
								.flatMap(res -> Mono.just(respSale));
					});
		});
	}
	
	public Flux<Sale> getAll() {
		return salesRepository.findAll();
	}
	
}
