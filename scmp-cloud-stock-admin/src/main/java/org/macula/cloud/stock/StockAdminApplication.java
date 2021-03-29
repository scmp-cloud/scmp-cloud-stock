package org.macula.cloud.stock;

import java.util.StringJoiner;
import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@EnableAsync
@EntityScan
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@Slf4j
public class StockAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockAdminApplication.class, args);
	}

	@Bean
	public Consumer<PersonEvent> receiveStockMessage() {
		return s -> {
			System.out.println(" ================== receiveStockMessage consumer: " + s);
			log.info("receiveStockMessage consumer: " + s);
		};
	}

	static class PersonEvent {

		String name;
		String type;

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