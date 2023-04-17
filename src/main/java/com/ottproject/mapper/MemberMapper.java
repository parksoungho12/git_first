package com.ottproject.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.ottproject.dto.AdminDTO;
import com.ottproject.dto.MemberDTO;


@Mapper
public interface MemberMapper {

	MemberDTO login(HashMap<String, Object> map);

	AdminDTO adminLogin(HashMap<String, Object> map);

	int insertMember(MemberDTO dto);

	int checkPasswd(MemberDTO dto);

	int deleteMember(String email);

	int updateNick(String nick);

	int updateNick(HashMap<String, Object> map);

	int updateEmail(HashMap<String, Object> map);

	int updatePasswd(HashMap<String, Object> map);

}