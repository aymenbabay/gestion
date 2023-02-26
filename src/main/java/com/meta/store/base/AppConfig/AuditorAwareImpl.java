package com.meta.store.base.AppConfig;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// should get user name from security
		return Optional.empty();
	}

}
