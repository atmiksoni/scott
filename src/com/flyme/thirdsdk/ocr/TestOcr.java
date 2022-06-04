package com.flyme.thirdsdk.ocr;

import java.io.File;
import java.io.IOException;

public class TestOcr {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 输入图片地址
		String path = "c://test//A.jpg";
		try {
			String valCode = new OCR().recognizeText(new File(path), "jpeg");
			System.out.println(valCode);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}