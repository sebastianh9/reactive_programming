package com.edu.reactiveprogramming.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import com.edu.reactiveprogramming.model.Sale;
import com.google.gson.Gson;

import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Service
@Log
public class KafkaConsumerService {

	@Value("${kafka.topic}")
	private String topic;

	public Mono<List<Sale>> getMessages() {
		return propertiesConsumer()
				.flatMap(this::createKafkaConsumer)
				.flatMap(this::proccessConsumer);
	}

	private Mono<Properties> propertiesConsumer() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return Mono.just(properties);
	}

	private Mono<KafkaConsumer<String, String>> createKafkaConsumer(Properties properties) {
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
		kafkaConsumer.subscribe(Arrays.asList(topic));
		return Mono.just(kafkaConsumer);
	}

	private Mono<List<Sale>> proccessConsumer(KafkaConsumer<String, String> consumer) {
		var records = consumer.poll(Duration.ofSeconds(10));
		var gson = new Gson();
		var list = new ArrayList<Sale>();
		for (ConsumerRecord<String, String> consumerRecord : records) {
			log.log(Level.INFO, "Record {0}", consumerRecord.value());
			var s = gson.fromJson(consumerRecord.value(), Sale.class);
			list.add(s);
		}
		return Mono.just(list);
	}

}
