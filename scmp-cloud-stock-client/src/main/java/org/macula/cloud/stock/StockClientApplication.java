package org.macula.cloud.stock;

import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@SpringBootApplication
public class StockClientApplication {

	private final String bindingName = "sendStockMessage-out-0";
	private BlockingQueue<PersonEvent> unbounded = new LinkedBlockingQueue<>();

	private AtomicInteger index = new AtomicInteger();

	@Autowired
	private StreamBridge streamBridge;

	public static void main(String[] args) {
		SpringApplication.run(StockClientApplication.class, args);
	}

	@GetMapping("/s")
	public String sendMessage() {
		PersonEvent personEvent = new PersonEvent();
		personEvent.setType("CreatePerson");
		personEvent.setName("Name -> " + index.incrementAndGet());
		unbounded.offer(personEvent);
		personEvent = new PersonEvent();
		personEvent.setType("CreatePerson");
		personEvent.setName("Name -> " + index.incrementAndGet());
		streamBridge.send(bindingName, MessageBuilder.withPayload(personEvent).setHeader(RocketMQHeaders.KEYS, personEvent.getId())
				.setHeader(RocketMQHeaders.TAGS, personEvent.getClass().getName()).build());
		return "Person sent " + index.get();
	}

	@Bean
	public Supplier<PersonEvent> sendStockMessage() {
		return () -> unbounded.poll();
	}

	@Bean
	public Consumer<PersonEvent> receiveStockMessage() {
		return s -> {
			System.out.println(" ================== receiveStockMessage consumer: " + s);
			log.info("receiveStockMessage consumer: " + s);
		};
	}

	@Bean
	public Consumer<Message<PersonEvent>> receiveStockMessage2() {
		return s -> {
			System.out.println(" ================== receiveStockMessage2 consumer: " + s);
			log.info("receiveStockMessage consumer: " + s);
		};
	}

	static class PersonEvent {

		String name;
		String type;

		public String getId() {
			return String.format("%s-%s", type, name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", PersonEvent.class.getSimpleName() + "[", "]").add("name='" + name + "'").add("type='" + type + "'")
					.toString();
		}
	}

}