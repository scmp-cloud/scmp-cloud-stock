package org.macula.cloud.stock.util;

import java.security.Principal;

public class SecurityUtils {

	public static Principal getCurrentPrincipal() {
		return new Principal() {

			@Override
			public String getName() {
				return "admin";
			}
		};
	}

}
