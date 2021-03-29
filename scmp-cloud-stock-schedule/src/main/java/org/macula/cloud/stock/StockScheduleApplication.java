package org.macula.cloud.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class StockScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockScheduleApplication.class, args);
	}
}