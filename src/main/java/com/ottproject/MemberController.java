package com.ottproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ottproject.dto.AdminDTO;
import com.ottproject.dto.MemberDTO;
import com.ottproject.service.MemberService;

@Controller
public class MemberController {
	
	private static String CLITENT_ID = "";
	private static String CLITENT_SECRET = "";
	private MemberService memberService;
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/login/view")
	public ModelAndView loginView(HttpServletRequest request, ModelAndView view) {
		String referer = request.getHeader("Referfer");
		System.out.println(referer);
		view.addObject("referer",referer);
		view.setViewName("login");
		return view;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpSession session, String email, String passwd, HttpServletResponse response, HttpServletRequest request, String referer, ModelAndView view) throws IOException {
		
		MemberDTO dto = memberService.login(email, passwd);
		AdminDTO addto = memberService.adminLogin(email, passwd);
		System.out.println(referer.substring(21));
		
		if(dto != null) {
			session.setAttribute("dto", dto);
			view.setViewName("redirect:/"+referer.substring(22));
		}
		if(dto == null &&  addto != null) {
			session.setAttribute("addto", addto);
			view.setViewName("redirect/");
		}
		if(dto == null && addto == null) {
			response.setContentType("text/html; charset=euc-kr");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('아이디, 비밀번호를 확인해주십시오'); history.back();</script>");
			out.flush();	
			view.setViewName("redirect:/login/view");
		}
		return view;
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect/";
	}
	
	@RequestMapping("/member/register/view")
	public String memberRegisterView() {
		
		return "create_account";
	}
	
	@RequestMapping("/member/register")
	public String memberRegister(MemberDTO dto) {
		int result = memberService.insertMember(dto);
		
		return "redirect:/login/view";
	}
	
	@RequestMapping("member/check/view")
	public String memberCheckView() {
		
		return "check_pwd";
	}
	
	@RequestMapping("/member/update/view")
	public String memberUpdateView() {
		
		return "change_main";
	}
	
	@RequestMapping("/check/passwd/{passwd}")
	public ResponseEntity<String> checkPasswd(@PathVariable(name = "passwd") String passwd, HttpSession session) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		String check = dto.getPasswd();
		if(check.equals(passwd)) {
			map.put("code", 1);
		}else {
			map.put("code", 0);
		}
		
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	@RequestMapping("member/email/change/view")
	public String updateEmailView() {
		
		return "change_email";
	}
	
	@RequestMapping("/member/passwd/change/view")
	public String updatePasswdView() {
		
		return "change_pwd";
	}
	
	@RequestMapping("/member/update/nick")
	public String updateNick(String nick, String email, HttpSession session) {
		int result  = memberService.updateNick(nick, email);
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		dto.setNick(nick);
		session.setAttribute("dto", dto);
		return "redirect:/member/update/view";
	}
	@RequestMapping("/member/update/email")
	public String updateEmail(String aemail, String email, HttpSession session) {
		int result = memberService.updateEmail(email, aemail);
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		dto.setEmail(email);
		session.setAttribute("dto", dto);
		return "redirect:/member/update/view";
	}
	
	@RequestMapping("/member/update/passwd")
	public String updatePasswd(String email, String pwd, HttpSession session) {
		int result = memberService.updatePasswd(pwd, email);
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		dto.setPasswd(pwd);
		session.setAttribute("dto", dto);
		return "redirect:/member/update/view";
	}
	
}
