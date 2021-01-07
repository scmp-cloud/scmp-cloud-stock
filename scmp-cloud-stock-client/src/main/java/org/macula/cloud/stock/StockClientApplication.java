package org.macula.cloud.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringCloudApplication
public class StockClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockClientApplication.class, args);
	}
}