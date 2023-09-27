package com.edu.reactiveprogramming.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	@GetMapping("/user")
	public String yo(Principal principal) {
		return "Hola ".concat(principal.getName());
	}

}
