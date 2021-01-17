package org.macula.cloud.stock;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Tag(name = "通用服务服务")
@RestController("/api/scmp/common")
public class ScreenshotController {

	private ScreenshotService service;
	private WxcpRobotClient client;

	@GetMapping("/screenshot")
	@Operation(description = "网页转图片")
	public void getTakeScreenshot(ScreenshotSettings settings, HttpServletResponse response) throws IOException {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-store");
		response.setContentType("image/png");
		BufferedImage br = service.createImage(settings);
		ImageIO.write(br, "png", response.getOutputStream());
	}

	@PostMapping("/screenshot")
	@Operation(description = "网页转图片")
	public void postTakeScreenshot(@RequestBody ScreenshotSettings settings, HttpServletResponse response) throws IOException {
		getTakeScreenshot(settings, response);
	}

	@PostMapping("/sendscreenshot/{key}")
	@Operation(description = "发送网页图片到微信群")
	public String sendScreenshotMessage(@PathVariable("key") String key, @RequestBody ScreenshotSettings settings) throws IOException {
		BufferedImage br = service.createImage(settings);
		String base64 = ImageUtils.getBase64Image(br, "png", settings.getSize());
		String message = WxcpMessageUtils.getImageMessage(base64);
		return client.sendMessage(key, message);
	}
}
