package com.edu.reactiveprogramming.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.reactiveprogramming.model.Sale;
import com.edu.reactiveprogramming.service.SqsService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sqs")
@RequiredArgsConstructor
public class SqsController {
	
	private final SqsService sqsService;
	
	@PostMapping("/createQueue/{name}")
	public Mono<String> create(@PathVariable String name) {
		return sqsService.create(name);
	}
	
	@PostMapping("/publishMessages/{queueName}")
	public Mono<String> publishMessages(@PathVariable String queueName) {
		return sqsService.publish(queueName);
	}
	
	@GetMapping("/getMessages/{queueName}")
	public Mono<List<Sale>> getMessages(@PathVariable String queueName) {
		return sqsService.getMessages(queueName);
	}

}
