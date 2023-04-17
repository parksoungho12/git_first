package com.ottproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("ncomment")
public class NoticeCommentDTO {
	private int commentNum;
	private int noticeNum;
	private String writer;
	private String content;
	private int clike;
	private int chate;
	public NoticeCommentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NoticeCommentDTO(int commentNum, int noticeNum, String writer, String content, int clike, int chate) {
		super();
		this.commentNum = commentNum;
		this.noticeNum = noticeNum;
		this.writer = writer;
		this.content = content;
		this.clike = clike;
		this.chate = chate;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public int getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String string) {
		this.writer = string;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getClike() {
		return clike;
	}
	public void setClike(int clike) {
		this.clike = clike;
	}
	public int getChate() {
		return chate;
	}
	public void setChate(int chate) {
		this.chate = chate;
	}
	@Override
	public String toString() {
		return "NoticeCommentDTO [commentNum=" + commentNum + ", noticeNum=" + noticeNum + ", writer=" + writer
				+ ", content=" + content + ", clike=" + clike + ", chate=" + chate + "]";
	}
	
}