package com.edu.reactiveprogramming.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.reactiveprogramming.model.Sale;
import com.edu.reactiveprogramming.service.KafkaConsumerService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class ConsumerKafkaController {
	
	private final KafkaConsumerService kafkaConsumerService;
	
	@GetMapping("/consumer")
	public Mono<List<Sale>> getMessages() {
		return kafkaConsumerService.getMessages();
	}

}
