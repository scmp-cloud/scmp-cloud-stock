package org.macula.cloud.stock;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Tag(name = "通用服务服务")
@RestController("/api/scmp/common")
public class ImageCaptchController {

	private ImageCaptchService service;

	@GetMapping("/url2image")
	@Operation(description = "网页转图片")
	public void captchaUrl(@Parameter(description = "输入参数") @RequestBody ImageCaptch settings, HttpServletResponse response) throws IOException {
		BufferedImage br = service.createImage(settings);
		ImageIO.write(br, "png", response.getOutputStream());
	}
}
