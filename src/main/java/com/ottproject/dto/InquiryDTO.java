package com.ottproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("inquiry")
public class InquiryDTO {
	private int inquiryNum;
	private String type;
	private String email;
	private String title;
	private String content;
	private String date;
	private int status;
	public InquiryDTO() {	}
	public InquiryDTO(int inquiryNum, String type, String email, String title, String content, String date,
			int status) {
		super();
		this.inquiryNum = inquiryNum;
		this.type = type;
		this.email = email;
		this.title = title;
		this.content = content;
		this.date = date;
		this.status = status;
	}
	public int getInquiryNum() {
		return inquiryNum;
	}
	public void setInquiryNum(int inquiryNum) {
		this.inquiryNum = inquiryNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String detailType) {
		this.type = detailType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "InquiryDTO [inquiryNum=" + inquiryNum + ", type=" + type + ", email=" + email + ", title="
				+ title + ", content=" + content + ", date=" + date + ", status=" + status + "]";
	}
	
	
	
}