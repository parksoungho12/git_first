package com.ottproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("admin")
public class AdminDTO {
	private String id;
	private String passwd;
	public AdminDTO() {	}
	public AdminDTO(String id, String passwd) {
		super();
		this.id = id;
		this.passwd = passwd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@Override
	public String toString() {
		return "AdminDTO [id=" + id + ", passwd=" + passwd + "]";
	}
	
	
}