package com.edu.reactiveprogramming.model;

public class UtilTest {
	
	public static Car buildCar() {
		return Car.builder()
				.id(1)
				.serie(12345)
				.marca("mazda")
				.modelo(2023)
				.precio(1234569)
				.color("blanco")
				.build();
	}
	
	public static Sale buildSale() {
		return Sale.builder()
				.id(1)
				.car(1)
				.cc(123569)
				.client("prueba")
				.build();
	}
	
	public static Inventory buildInventory() {
		return Inventory.builder()
				.id(1)
				.car(1)
				.inventory(1000)
				.sales(20)
				.build();
	}

}
