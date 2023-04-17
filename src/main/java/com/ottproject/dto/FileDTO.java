package com.ottproject.dto;

import java.io.File;

import org.apache.ibatis.type.Alias;
@Alias("file")
public class FileDTO {
	private String path;
	private String fileName;
	private int inquiryNum;
	private int fileNum;
	private String type;

	public FileDTO() {}

	public FileDTO(File file, int inquiryNum, int fileNum) {
		this.path = file.getAbsolutePath();
		this.fileName = file.getName();
		this.inquiryNum = inquiryNum;
		this.fileNum = fileNum;
		switch (fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()) {
		case "png":
		case "jpg":
		case "bmp":
		case "gif":
			this.type = "image";
			break;
		default:
			this.type = "normal";
		}

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		File file = new File(path);
		this.fileName = file.getName();
		switch (fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()) {
		case "png":
		case "jpg":
		case "bmp":
		case "gif":
			this.type = "image";
			break;
		default:
			this.type = "normal";
		}
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getInquiryNum() {
		return inquiryNum;
	}

	public void setInquiryNum(int inquiryNum) {
		this.inquiryNum = inquiryNum;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FileDTO [path=" + path + ", fileName=" + fileName + ", inquiryNum=" + inquiryNum + ", fileNum="
				+ fileNum + ", type=" + type + "]";
	}
	

}