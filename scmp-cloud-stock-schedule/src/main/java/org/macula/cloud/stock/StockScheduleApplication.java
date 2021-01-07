package org.macula.cloud.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableFeignClients
@EnableScheduling
@SpringCloudApplication
public class StockScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockScheduleApplication.class, args);
	}
}