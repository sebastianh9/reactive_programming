package com.edu.reactiveprogramming.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.edu.reactiveprogramming.model.Sale;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
@Log
public class SqsService {
	
	private final SqsAsyncClient sqsAsyncClient;
	private final KafkaConsumerService kafkaConsumerService;
	
	private static final String CREATE_QUEUE = "Cola creada correctamente: ";
	private static final String SUCCESS_PUBLISH_SQS = "Se publicaron todos los mensajes de kafka en sqs";
	private static final String PUBLISH_SQS_MESSAGE = "Publish sqs message: {0}";
	private static final String ERROR_PUBLISH_MESSAGE_EMPTY = "No hay mensajes de kafka para publicar";
	
	/**
	 * Metodo para crear cola en SQS
	 * @param name
	 * @return
	 */
	public Mono<String> create(String name) {
		return buildCreateQueueRequest(name)
				.flatMap(req -> Mono.fromFuture(sqsAsyncClient.createQueue(req))
						.doOnSuccess(r -> log.log(Level.INFO, r.queueUrl()))
						.flatMap(resp -> Mono.just(CREATE_QUEUE.concat(resp.queueUrl()))));
	}
	
	/**
	 * Metodo para publicar mensajes de kafka en cola SQS
	 * @return
	 */
	public Mono<String> publish(String queueName) {
		return kafkaConsumerService.getMessages()
		.flatMap(resp -> this.publishMessage(resp, queueName));
	}
	
	/**
	 * Metodo para obtener los mensjaes de la cola SQS
	 * @param queueName
	 * @return
	 */
	public Mono<List<Sale>> getMessages(String queueName) {
		return getUrlQueue(queueName)
				.flatMap(this::buildReceiveMessageRequest)
				.flatMap(recieveReq -> Mono.fromFuture(sqsAsyncClient.receiveMessage(recieveReq))
						.flatMap(this::responseMessagesSqs));
	}
	
	private Mono<List<Sale>> responseMessagesSqs(ReceiveMessageResponse resp) {
		List<Sale> list = new ArrayList<>();
		for (Message message : resp.messages()) {
			list.add(new Gson().fromJson(message.body(), Sale.class));
		}
		return Mono.just(list);
	}
	
	private Mono<CreateQueueRequest> buildCreateQueueRequest(String queueName) {
		return Mono.just(CreateQueueRequest.builder()
				.queueName(queueName)
				.build());
	}
	
	/**
	 * Metodo que publica uno a uno de los mensajes en sqs
	 * @param list
	 * @return
	 */
	private Mono<String> publishMessage(List<Sale> list, String queueName) {
		if(list.isEmpty()) {
			return Mono.error(new Exception(ERROR_PUBLISH_MESSAGE_EMPTY));
		}
		return getUrlQueue(queueName)
				.flatMap(url -> {
					var gson = new Gson();
					for (Sale sale : list) {
						sqsAsyncClient.sendMessage(buildSendMessageRequest(url, gson.toJson(sale)));
						log.log(Level.INFO, PUBLISH_SQS_MESSAGE, sale);
					}
					return Mono.just(SUCCESS_PUBLISH_SQS);
				});
	}
	
	private Mono<String> getUrlQueue(String queueName) {
		return buildGetQueueUrlRequest(queueName)
				.flatMap(urlReq -> Mono.fromFuture(sqsAsyncClient.getQueueUrl(urlReq))
						.flatMap(r -> Mono.just(r.queueUrl())));
	}
	
	private Mono<GetQueueUrlRequest> buildGetQueueUrlRequest(String queueName) {
		return Mono.just(GetQueueUrlRequest.builder()
				.queueName(queueName)
				.build());
	}
	
	private SendMessageRequest buildSendMessageRequest(String queueUrl, String body) {
		return SendMessageRequest.builder()
				.messageBody(body)
				.queueUrl(queueUrl)
				.build();
	}

	private Mono<ReceiveMessageRequest> buildReceiveMessageRequest(String queueUrl) {
		return Mono.just(ReceiveMessageRequest.builder()
				.queueUrl(queueUrl)
				.build());
	}
	
}
