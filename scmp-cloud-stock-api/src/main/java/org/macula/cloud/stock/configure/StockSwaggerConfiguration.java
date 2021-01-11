package org.macula.cloud.stock.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class StockSwaggerConfiguration {

	@Bean
	public OpenAPI stockCenterOpenAPI() {
		return new OpenAPI().info(new Info().title("Stock Center Open API"));
	}
}
