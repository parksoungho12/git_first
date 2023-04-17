package com.ottproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("inquiryAnswer")
public class InquiryAnswerDTO {
	private int inquiryNum;
	private String title;
	private String content;
	private String wdate;
	public InquiryAnswerDTO(int inquiryNum, String title, String content, String wdate) {
		super();
		this.inquiryNum = inquiryNum;
		this.title = title;
		this.content = content;
		this.wdate = wdate;
	}
	public InquiryAnswerDTO() {	}
	public int getInquiryNum() {
		return inquiryNum;
	}
	public void setInquiryNum(int inquiryNum) {
		this.inquiryNum = inquiryNum;
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
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	@Override
	public String toString() {
		return "InquiryAnswerDTO [inquiryNum=" + inquiryNum + ", title=" + title + ", content=" + content + ", wdate="
				+ wdate + "]";
	}
	
	
}