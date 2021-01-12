package org.macula.cloud.stock;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCaptch {

	private String url;
	private int width = 360;
	private int height = 640;
	private int cutHeader = 0;
	private int cutFooter = 0;
	private String deviceName = "Galaxy S5";
	private float dpr = 3.0f;
	private String removeClassNames;
	private String removeIds;
	private List<String> headers;
	private Map<String, String> cookies;
}
