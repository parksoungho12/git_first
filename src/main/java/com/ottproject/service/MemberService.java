package com.ottproject.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.ottproject.dto.AdminDTO;
import com.ottproject.dto.MemberDTO;
import com.ottproject.mapper.MemberMapper;



@Service
public class MemberService {
	private MemberMapper mapper;
	public MemberService(MemberMapper mapper) {
		this.mapper = mapper;
	}
	public MemberDTO login(String email, String passwd) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("passwd", passwd);
		return mapper.login(map);
	}
	public AdminDTO adminLogin(String email, String passwd) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", email);
		map.put("passwd", passwd);
		return mapper.adminLogin(map);
	}
	public int insertMember(MemberDTO dto) {
		
		return mapper.insertMember(dto);
	}
	public int checkPasswd(MemberDTO dto) {
		
		return mapper.checkPasswd(dto);
	}
	public int deleteMember(String email) {
		
		return mapper.deleteMember(email);
	}
	public int updateNick(String nick, String email) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nick", nick);
		map.put("email", email);
		
		return mapper.updateNick(map);
	}
	public int updateEmail(String email, String aemail) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("aemail", aemail);
		map.put("email", email);
		return mapper.updateEmail(map);
	}
	public int updatePasswd(String pwd, String email) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("passwd", pwd);
		map.put("email", email);
		return mapper.updatePasswd(map);
	}
	
	
	
	
	
	
	
	
	
}