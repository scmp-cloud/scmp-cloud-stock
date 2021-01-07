package org.macula.cloud.stock;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan
@EnableAsync
@ComponentScan
@EnableScheduling
public class StockApplicationTest {

}
