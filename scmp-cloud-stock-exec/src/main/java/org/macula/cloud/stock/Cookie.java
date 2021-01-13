package org.macula.cloud.stock;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cookie implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String value;
	private String path;
	private String domain;
	private Date expiry;
	private boolean isSecure;
	private boolean isHttpOnly;

}
