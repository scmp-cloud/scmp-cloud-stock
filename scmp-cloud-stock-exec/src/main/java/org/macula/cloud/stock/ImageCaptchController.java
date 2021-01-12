package org.macula.cloud.stock;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ImageCaptchController {

	private ImageCaptchService service;

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/url2image")
	public void captchaUrl(@RequestBody ImageCaptch settings, HttpServletResponse response) throws IOException {
		BufferedImage br = service.createImage(settings);
		ImageIO.write(br, "png", response.getOutputStream());
	}
}
