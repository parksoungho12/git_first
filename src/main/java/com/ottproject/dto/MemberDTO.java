package com.ottproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("member")
public class MemberDTO {
	private String email;
	private String passwd;
	private String name;
	private String nick;
	private String phoneNumber;
	private int point;
	private int membership;

	public MemberDTO() {	}
	

	public MemberDTO(String email, String passwd, String name, String nick, String phoneNumber, int point,
			int membership) {
		super();
		this.email = email;
		this.passwd = passwd;
		this.name = name;
		this.nick = nick;
		this.phoneNumber = phoneNumber;
		this.point = point;
		this.membership = membership;
	}


	public String getNick() {
		return nick;
	}


	public void setNick(String nick) {
		this.nick = nick;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getMembership() {
		return membership;
	}

	public void setMembership(int membership) {
		this.membership = membership;
	}

	@Override
	public String toString() {
		return "MemberDTO [email=" + email + ", passwd=" + passwd + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", point=" + point + ", membership=" + membership + "]";
	}
	
	
	
	
	
}