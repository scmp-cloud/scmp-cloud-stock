package org.macula.cloud.stock;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenshotSettings {

	private String url;
	private int width = 375;
	private int height = 812;
	private int timeout = 100;
	private String deviceName = "iPhone X";
	private String removeClassNames;
	private String removeIds;
	private List<String> arguments;
	private List<Cookie> cookies;
	private String userAgent;
	private long size;
}
