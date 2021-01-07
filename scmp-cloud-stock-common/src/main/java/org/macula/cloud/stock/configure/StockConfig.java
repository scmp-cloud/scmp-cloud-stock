package org.macula.cloud.stock.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "macula.cloud.stock")
public class StockConfig {

	private String stockTopic;

}
