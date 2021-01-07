package org.macula.cloud.stock.configure;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.macula.cloud.stock.util.SecurityUtils;
import org.macula.cloud.stock.util.SystemUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;

@Configuration
@Conditional(EnableJpaRepositoryCondition.class)
public class JpaAuditConfiguration {

	@Configuration
	static class CloudJpaAuditingConfiguration {
		@Bean("auditorAware")
		public AuditorAware<String> buildAuditorAwareImpl() {
			return new AuditorAware<String>() {

				@Override
				public Optional<String> getCurrentAuditor() {
					return Optional.of(SecurityUtils.getCurrentPrincipal().getName());
				}
			};
		}

		@Bean("dateTimeProvider")
		public DateTimeProvider buildDateTimeProvider() {
			return new DateTimeProvider() {

				@Override
				public Optional<TemporalAccessor> getNow() {
					return Optional.of(SystemUtils.getCurrentInstant());
				}

			};
		}
	}

}
