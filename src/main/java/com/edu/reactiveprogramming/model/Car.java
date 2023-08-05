package com.edu.reactiveprogramming.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
	
	@Id
	private Integer id;
	private Integer serie;
	private String marca;
	private Integer modelo;
	private Integer precio;
	private String color;

}
