package com.ottproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("notice")
public class NoticeDTO {
	private int noticeNum;
	private String title;
	private String content;
	private String wdate;
	private int viewCount;
	private int nlike;
	private int nhate;
	private int commentCount;
	
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getNlike() {
		return nlike;
	}
	public void setNlike(int nlike) {
		this.nlike = nlike;
	}
	public int getNhate() {
		return nhate;
	}
	public void setNhate(int nhate) {
		this.nhate = nhate;
	}
	public int getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
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
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public NoticeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NoticeDTO(int noticeNum, String title, String content, String wdate, int viewCount, int nlike, int nhate, int commentCount) {
		super();
		this.noticeNum = noticeNum;
		this.title = title;
		this.content = content;
		this.wdate = wdate;
		this.viewCount = viewCount;
		this.nlike = nlike;
		this.nhate = nhate;
		this.commentCount = commentCount;
	}
	@Override
	public String toString() {
		return "NoticeDTO [noticeNum=" + noticeNum + ", title=" + title + ", content=" + content + ", wdate=" + wdate
				+ ", viewCount=" + viewCount + ", nlike=" + nlike + ", nhate=" + nhate + ", commentCount="
				+ commentCount + "]";
	}
	
		
	
	
}