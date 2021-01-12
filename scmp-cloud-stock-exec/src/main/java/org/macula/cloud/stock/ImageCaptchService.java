package org.macula.cloud.stock;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

@Service
public class ImageCaptchService {

	public BufferedImage createImage(ImageCaptch settings) {
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments(String.format("--window-size=%d,%d", settings.getWidth(), settings.getHeight()));
		List<String> headers = settings.getHeaders();
		if (headers != null) {
			chromeOptions.addArguments(headers);
		}

		Map<String, String> mobileEmulation = new HashMap<>();
		mobileEmulation.put("deviceName", settings.getDeviceName());
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

		final ChromeDriver driver = new ChromeDriver(chromeOptions);

		Map<String, String> cookies = settings.getCookies();
		if (cookies != null) {
			cookies.forEach((k, v) -> {
				Cookie cookie = new Cookie(k, v);
				driver.manage().addCookie(cookie);
			});
		}

		driver.get(settings.getUrl());
		new WebDriverWait(driver, 300).until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

		String elements = settings.getRemoveClassNames();
		if (elements != null) {
			for (String el : elements.split(",")) {
				driver.executeScript("try { document.getElementsByClassName('" + el + "')[0].remove();} catch(e) { }");
			}
		}
		elements = settings.getRemoveIds();
		if (elements != null) {
			for (String el : elements.split(",")) {
				driver.executeScript("try { document.getElementById('" + el + "').remove();} catch(e) { }");
			}
		}
		BufferedImage bi = new AShot()
				.shootingStrategy(ShootingStrategies.viewportRetina(400, settings.getCutHeader(), settings.getCutFooter(), settings.getDpr()))
				.takeScreenshot(driver).getImage();
		return bi;
	}
}
