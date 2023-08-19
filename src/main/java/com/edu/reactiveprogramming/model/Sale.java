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
public class Sale {
	
	@Id
	private Integer id;
	private Integer car;
	private Integer cc;
	private String client;
	private String date;

}
