package org.macula.cloud.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EntityScan
@EnableScheduling
@EnableFeignClients
@SpringCloudApplication
public class StockAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockAdminApplication.class, args);
	}
}