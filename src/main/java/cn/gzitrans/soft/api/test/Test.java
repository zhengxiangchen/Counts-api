package cn.gzitrans.soft.api.test;

import cn.gzitrans.soft.api.utils.ImageHelper;

public class Test {

	public static void main(String[] args) {
		
		ImageHelper imgHelper = new ImageHelper();
		String sourceImagePath = "C://Users/Administrator/Desktop/IMG_1958.PNG";
		String destinationPath = "C://Users/Administrator/Desktop/123.jpg";
		double scale = 0.5;
		String format = "jpg";
		
		imgHelper.scaleImage(sourceImagePath, destinationPath, scale, format);
	}

}
