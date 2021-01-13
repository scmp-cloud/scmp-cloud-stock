package org.macula.cloud.stock;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtils {

	/**
	 * @param srcFile源图片、targetFile截好后图片全名、startAcross 开始截取位置横坐标、StartEndlong开始截图位置纵坐标、width截取的长，hight截取的高
	 * @Description:截图
	 * @author:liuyc
	 * @time:2016年5月27日 上午10:18:23
	 */
	public static void cutImage(String srcFile, String targetFile, int startAcross, int StartEndlong, int width, int hight) throws Exception {
		// 取得图片读入器
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");
		ImageReader reader = readers.next();
		// 取得图片读入流
		InputStream source = new FileInputStream(srcFile);
		ImageInputStream iis = ImageIO.createImageInputStream(source);
		reader.setInput(iis, true);
		// 图片参数对象
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(startAcross, StartEndlong, width, hight);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, targetFile.split("\\.")[1], new File(targetFile));
	}

	/**
	 * @param files 要拼接的文件列表
	 * @param type1 横向拼接， 2 纵向拼接
	 * @Description:图片拼接 （注意：必须两张图片长宽一致哦）
	 */
	public static BufferedImage mergeImage(BufferedImage[] images, int type) {
		if (images == null) {
			return null;
		}
		int len = images.length;
		if (len == 1) {
			return images[0];
		}
		int[][] imagesArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			imagesArrays[i] = new int[width * height];
			imagesArrays[i] = images[i].getRGB(0, 0, width, height, imagesArrays[i], 0, width);
		}
		int newHeight = 0;
		int newWidth = 0;
		for (int i = 0; i < images.length; i++) {
			if (type == 1) {
				newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
				newWidth += images[i].getWidth();
			} else if (type == 2) {// 纵向
				newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
				newHeight += images[i].getHeight();
			}
		}
		if (type == 1 && newWidth < 1) {
			return null;
		}
		if (type == 2 && newHeight < 1) {
			return null;
		}

		// 生成新图片
		BufferedImage mergedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		int height_i = 0;
		int width_i = 0;
		for (int i = 0; i < images.length; i++) {
			if (type == 1) {
				mergedImage.setRGB(width_i, 0, images[i].getWidth(), newHeight, imagesArrays[i], 0, images[i].getWidth());
				width_i += images[i].getWidth();
			} else if (type == 2) {
				mergedImage.setRGB(0, height_i, newWidth, images[i].getHeight(), imagesArrays[i], 0, newWidth);
				height_i += images[i].getHeight();
			}
		}
		return mergedImage;
	}

	/**
	 * @Description:小图片贴到大图片形成一张图(合成)
	 * @author:liuyc
	 * @time:2016年5月27日 下午5:51:20
	 */
	public static final void overlapImage(String bigPath, String smallPath, String outFile) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight()) / 2;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
