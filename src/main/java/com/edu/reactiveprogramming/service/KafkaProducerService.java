package com.edu.reactiveprogramming.service;

import java.util.logging.Level;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.edu.reactiveprogramming.model.Sale;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log
public class KafkaProducerService {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String MESSAGE_SUCCESS = "Venta enviada al topico de Kafka {0}";

	public Mono<Void> sendKey(String topico, Integer key, Sale sale) {
		var gson = new Gson();
		return Mono.fromFuture(kafkaTemplate.send(topico, key.toString(), gson.toJson(sale)))
				.doOnError(exp -> log.log(Level.SEVERE, exp.getMessage()))
				.doOnSuccess(r -> log.log(Level.INFO, MESSAGE_SUCCESS, sale))
				.flatMap(r -> Mono.empty());
	}
}
