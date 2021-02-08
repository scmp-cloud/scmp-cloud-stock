package org.macula.cloud.stock;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CanalMessageBindingBeans {

	@Bean
	public Consumer<String> receiveCanalMessage() {
		return s -> {
			System.out.println(" ================== ReceiveCanalMessage consumer: " + s);
			log.info("ReceiveCanalMessage consumer: " + s);
		};
	}

}
