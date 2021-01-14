package org.macula.cloud.stock;

import org.macula.cloud.stock.configure.StockConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ StockConfig.class })
public class StockExecApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockExecApplication.class, args);
	}
}