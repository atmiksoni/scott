package com.flyme.common.json.model;

import java.util.List;

import com.flyme.common.json.upload.MyFile;

/**
 * AJAX请求时返回的JSON对象
 */
public class Upload {
	private boolean success = false;// 是否成功
	private String fileName;
	private String filePath;
	private String fileFolder;
	private byte[] byteImage;
	private int width;
	private int height;

	private List<MyFile> files;
	private List<String> filsPaths;

	public String getFileFolder() {
		return fileFolder;
	}

	public void setFileFolder(String fileFolder) {
		this.fileFolder = fileFolder;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<MyFile> getFiles() {
		return files;
	}

	public void setFiles(List<MyFile> files) {
		this.files = files;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public byte[] getByteImage() {
		return byteImage;
	}

	public void setByteImage(byte[] byteImage) {
		this.byteImage = byteImage;
	}

	public List<String> getFilsPaths() {
		return filsPaths;
	}

	public void setFilsPaths(List<String> filsPaths) {
		this.filsPaths = filsPaths;
	}
    
	
}
