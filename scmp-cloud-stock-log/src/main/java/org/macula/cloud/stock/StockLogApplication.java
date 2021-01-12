package org.macula.cloud.stock;

import org.macula.cloud.stock.stream.StockChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@EnableJpaAuditing
@EnableJpaRepositories
@SpringCloudApplication
@EnableBinding(StockChannel.class)
@SuppressWarnings("deprecation")
public class StockLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockLogApplication.class, args);
	}
}
