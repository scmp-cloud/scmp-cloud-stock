package org.macula.cloud.stock.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
@EnableConfigurationProperties({ StockConfig.class })
public class StockConfiguration {

	@Bean
	public DefaultRedisScript<String> stockReserveScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/reserve.lua")));
		redisScript.setResultType(String.class);
		return redisScript;
	}

	@Bean
	public DefaultRedisScript<String> stockCommitScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/commit.lua")));
		redisScript.setResultType(String.class);
		return redisScript;
	}

	@Bean
	public DefaultRedisScript<String> stockReleaseScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/release.lua")));
		redisScript.setResultType(String.class);
		return redisScript;
	}

	@Bean
	public DefaultRedisScript<String> stockFulfillmentScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/fulfillment.lua")));
		redisScript.setResultType(String.class);
		return redisScript;
	}

	@Bean
	public DefaultRedisScript<String> stockIncreaseScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/increase.lua")));
		redisScript.setResultType(String.class);
		return redisScript;
	}

	@Bean
	public DefaultRedisScript<String> stockReduceScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/reduce.lua")));
		redisScript.setResultType(String.class);
		return redisScript;
	}

	@Bean
	public DefaultRedisScript<String> stockRefreshScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/refresh.lua")));
		redisScript.setResultType(String.class);
		return redisScript;
	}

	@Bean
	public DefaultRedisScript<String> stockStatusScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/status.lua")));
		redisScript.setResultType(String.class);
		return redisScript;
	}
}
