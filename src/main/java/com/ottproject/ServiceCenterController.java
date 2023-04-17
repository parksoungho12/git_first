package com.ottproject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Server;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ottproject.dto.FileDTO;
import com.ottproject.dto.InquiryAnswerDTO;
import com.ottproject.dto.InquiryDTO;
import com.ottproject.dto.MemberDTO;
import com.ottproject.dto.NoticeCommentDTO;
import com.ottproject.dto.NoticeDTO;
import com.ottproject.service.ServiceCenterService;



@Controller
public class ServiceCenterController {
	private ServiceCenterService serviceService;

	public ServiceCenterController(ServiceCenterService serviceService) {
		this.serviceService = serviceService;
	}

	@RequestMapping("/service/main/view")
	public ModelAndView serviceMain(ModelAndView view) {
		List<NoticeDTO> list = serviceService.selectAllNotice();
		view.addObject("list", list);
		view.setViewName("service_center_main");
		return view;
	}

	@RequestMapping("/inquiry")
	public ModelAndView inquiry(HttpSession session, ModelAndView view) {
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		if (dto != null) {
			view.addObject("dto", dto);
			view.setViewName("service_center_inquiry");
			return view;
		} else {
			view.setViewName("service_center_inquiry");
			return view;
		}
	}

	@RequestMapping("/inquiry/list")
	public ModelAndView inquiryList(HttpSession session) {
		ModelAndView view = new ModelAndView();
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		if (dto != null) {
			String email = dto.getEmail();
			List<InquiryDTO> list = serviceService.selectAllInquiry(email);
			view.addObject("list", list);
			view.setViewName("service_center_inquiry_list");
			return view;
		} else {
			view.setViewName("service_center_inquiry_list");
			return view;
		}

	}

	@RequestMapping("/inquiry/answer/{inquiryNum}")
	public ModelAndView inquriyView(@PathVariable("inquiryNum") int inquiryNum) {
		ModelAndView view = new ModelAndView();
		InquiryDTO dto = serviceService.selectInquiry(inquiryNum);
		InquiryAnswerDTO adto = serviceService.selectInquiryAnswer(inquiryNum);
		List<FileDTO> flist = serviceService.selectFileList(inquiryNum);
		System.out.println(flist.toString());
		view.addObject("adto", adto);
		view.addObject("dto", dto);
		view.addObject("flist", flist);
		view.setViewName("service_center_inquiry_answer");

		return view;

	}

	@RequestMapping("/filedown")
	public void fileDownload(int inquiryNum, int fileNum, HttpServletResponse response) {
		FileDTO dto = serviceService.selectFile(inquiryNum, fileNum);
		try(BufferedOutputStream bos = 
				new BufferedOutputStream(response.getOutputStream());
				FileInputStream fis = new FileInputStream(dto.getPath())){
			byte[] buffer = new byte[1024*1024];
			
			while(true) {
				int count = fis.read(buffer);
				if(count == -1) break;
				bos.write(buffer,0,count);
				bos.flush();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	@RequestMapping("/fileupload")
	public ResponseEntity<String> fileUpload(@RequestParam(value = "upload") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String originFileName = file.getOriginalFilename();
		String root = "c:\\fileupload\\";
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		String fileName = dto.getEmail() + originFileName.substring(originFileName.lastIndexOf('.'));
		File savefile = new File(root + fileName);
		int fno = serviceService.uploadImage(savefile.getAbsolutePath());
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			file.transferTo(savefile);
			map.put("uploaded", true);
			map.put("url", "/image/" + fno);
			map.put("bi_no", fno);

		} catch (IOException e) {
			map.put("uploaded", false);
			map.put("message", "파일 업로드 중 에러 발생");
		}
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@RequestMapping("/image/{fileNum}")
	public void imageDown(@PathVariable("fno") int fileNum, HttpServletResponse response) {
		FileDTO dto = serviceService.selectImageFile(fileNum);
		String path = dto.getPath();
		File file = new File(path);
		String fileName = dto.getFileName();
		try {
			fileName = URLEncoder.encode(fileName, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setHeader("Content-Disposition", "attachement;fileName=" + fileName);
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentLength((int) file.length());
		try (FileInputStream fis = new FileInputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());) {
			byte[] buffer = new byte[1024 * 1024];
			while (true) {
				int size = fis.read(buffer);
				if (size == -1)
					break;
				bos.write(buffer, 0, size);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/inquiry/add")
	public String InquiryWrite(InquiryDTO dto, @RequestParam("file") MultipartFile[] file) {
		int inquiryNum = serviceService.insertInquiry(dto);

		String root = "c:\\fileupload\\";
		for (int i = 0; i < file.length; i++) {
			if (file[i].getSize() == 0)
				continue;
			String originFileName = file[i].getOriginalFilename();
			String fileName = i + originFileName.substring(originFileName.lastIndexOf('.'));
			try {
				File saveFile = new File(root + fileName);
				file[i].transferTo(saveFile);
				serviceService.insertFile(new FileDTO(saveFile, inquiryNum, i + 1));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "redirect:/inquiry/list";
	}

	@RequestMapping("/inquiry/delete/{inquiryNum}")
	public String inquiryDelete(@PathVariable(name = "inquiryNum") int inquiryNum) {

		int result = serviceService.inquiryDelete(inquiryNum);
		int result1 = serviceService.inquiryAnswerDelete(inquiryNum);

		return "redirect:/inquiry/list";
	}

	@RequestMapping("/inquiry/update/view/{inquiryNum}")
	public ModelAndView inquiryupdateview(@PathVariable(name = "inquiryNum") int inquiryNum) {
		ModelAndView view = new ModelAndView();
		InquiryDTO dto = serviceService.selectInquiry(inquiryNum);
		view.addObject("dto", dto);
		view.setViewName("service_center_inquiry_update");

		return view;

	}

	@RequestMapping("/inquiry/update")
	public String inquiryupdate(InquiryDTO dto) {
		int result = serviceService.updateInquiry(dto);

		return "redirect:/inquiry/answer/" + dto.getInquiryNum();

	}

	@RequestMapping("/inquiry/admin/list")
	public ModelAndView inquiryAdminList(ModelAndView view, HttpSession session, String type) {

		List<InquiryDTO> list = serviceService.selectAllAdminInquiry();

		view.addObject("list", list);
		view.setViewName("service_center_admin_inquiry_list");

		return view;
	}

	@RequestMapping("/inquiry/admin/answer/{inquiryNum}")
	public ModelAndView inquriyAdminView(@PathVariable("inquiryNum") int inquiryNum) {
		ModelAndView view = new ModelAndView();
		InquiryDTO dto = serviceService.selectInquiry(inquiryNum);
		InquiryAnswerDTO adto = serviceService.selectInquiryAnswer(inquiryNum);
		view.addObject("adto", adto);
		view.addObject("dto", dto);
		view.setViewName("service_center_admin_inquiry_answer");

		return view;

	}

	@RequestMapping("/inquiry/admin/answer")
	public String inquriyAdminAnswer(InquiryAnswerDTO adto, InquiryDTO dto) {
		int inquiryNum = serviceService.InsertAdminInquiry(adto);
		int status = serviceService.updateInquiryStatus(adto.getInquiryNum());

		return "redirect:/inquiry/admin/answer/" + dto.getInquiryNum();

	}

	@RequestMapping("/inquiry/admin/sort")
	public ModelAndView inquiryAdminSort(String type, int answer, ModelAndView view) {
		List<InquiryDTO> list = serviceService.inquiryAdminSort(type, answer);
		view.addObject("list", list);
		view.setViewName("service_center_admin_inquiry_list");
		return view;
	}
	
	
	@RequestMapping("/notice/view")
	public ModelAndView noticeView(ModelAndView view) {
		List<NoticeDTO> list = serviceService.selectAllNotice();
		
		System.out.println(list.toString());
		
		view.addObject("list", list);
		view.setViewName("notice");
		
		
		return view;
	}
	
	@RequestMapping("/notice/detail/{noticeNum}")
	public ModelAndView noticeDetail(@PathVariable("noticeNum") int noticeNum, ModelAndView view, HttpSession session) {
		HashSet<Integer> set = (HashSet<Integer>) session.getAttribute("history");
		if(set == null) {
			set = new HashSet<Integer>();
			session.setAttribute("history", set);
		}
		if(set.add(noticeNum)) serviceService.updateNoticeCount(noticeNum);
		
		NoticeDTO ndto = serviceService.selectNotice(noticeNum);
		ndto.setNlike((int) serviceService.selectNoticeLike(noticeNum));
		ndto.setNhate((int) serviceService.selectNoticeHate(noticeNum));
		System.out.println(ndto.toString());
		List<NoticeCommentDTO> cdto = serviceService.selectNoticeComment(noticeNum);
		int count = serviceService.countComment(noticeNum);
		
		view.addObject("count", count);
		view.addObject("ndto", ndto);
		view.addObject("cdto",cdto);
		view.setViewName("notice_detail");
		return view;
	}
	@RequestMapping("/notice/comment/add")
	public String noticeCommentAdd(NoticeCommentDTO cdto, String content, HttpSession session) {
		
		MemberDTO mdto = (MemberDTO) session.getAttribute("dto");
		cdto.setWriter(mdto.getNick());
		
		int result = serviceService.insertNoticeComment(cdto);
		System.out.println(cdto.toString());
		return "redirect:/notice/detail/"+cdto.getNoticeNum();
	}
	@RequestMapping("/notice/like/{noticeNum}")
	public ResponseEntity<String> noticeLikeCount(@PathVariable(name = "noticeNum") int noticeNum, HttpSession session){
		HashMap<String, Object> map = new HashMap<>();
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		int result = serviceService.noticeLike(noticeNum, dto.getEmail());
		
		if(result != 0) {
			map.put("msg", "해당 공지사항에 좋아요를 누르셨습니다.");
		}else {
			map.put("msg", "해당 공지사항에 좋아요를 취소하셨습니다.");
		}
		map.put("nlike", serviceService.selectNoticeLike(noticeNum));
		
		return new ResponseEntity(map, HttpStatus.OK);
	}
	@RequestMapping("/notice/hate/{noticeNum}")
	public ResponseEntity<String> noticeHateCount(@PathVariable(name = "noticeNum") int noticeNum, HttpSession session){
		HashMap<String, Object> map = new HashMap<>();
		MemberDTO dto = (MemberDTO) session.getAttribute("dto");
		int result = serviceService.noticeHate(noticeNum, dto.getEmail());
		
		if(result != 0) {
			map.put("msg", "해당 공지사항에 싫어요를 누르셨습니다.");
		}else {
			map.put("msg", "해당 공지사항에 싫어요를 취소하셨습니다.");
		}
		map.put("nhate", serviceService.selectNoticeHate(noticeNum));
		
		return new ResponseEntity(map, HttpStatus.OK);
	}
	@RequestMapping("/service/search")
	public ModelAndView servcieSearch(String search, ModelAndView view) {
		List<NoticeDTO> nlist = serviceService.searchNotice(search);
		System.out.println(nlist.toString());
		view.addObject("search", search);
		view.addObject("nlist", nlist);
		view.setViewName("service_search_result");
		return view;
	}
	
	@RequestMapping("/comment/delete/{commentNum}/{noticeNum}")
	public ResponseEntity<String> commentDelete(@PathVariable(name = "commentNum") int commentNum, @PathVariable(name = "noticeNum") int noticeNum){
		HashMap<String, Object> map = new HashMap<String, Object>();
		int result = serviceService.deleteComment(noticeNum, commentNum);
		System.out.println(result);
		if(result != 0) {
			map.put("msg", "댓글을 삭제하셨습니다.");
		}else {
			map.put("msg", "댓글삭제가 실패하였습니다.");
		}
		
		return new ResponseEntity(map,HttpStatus.OK);
	}
	
	@RequestMapping("/admin/notice/add")
	public String adminNoticAddView(){
		
		return "notice_add";
	}
	
	@RequestMapping("/notice/add")
	public String noticeAdd(NoticeDTO dto) {
		int result = serviceService.addNotice(dto);
		
		return "redirect:/notice/view";
	}
	
	@RequestMapping("/notice/delete/{noticeNum}")
	public String noticeDelete(@PathVariable(name = "noticeNum") int noticeNum) {
		int result = serviceService.noticeDelete(noticeNum);
		int result1 = serviceService.noticeCommentAllDelete(noticeNum);
		int result2 = serviceService.noticeLikeAllDelete(noticeNum);
		int result3 = serviceService.noticeHateAllDelete(noticeNum);
		
		
		return "redirect:/notice/view";
	}
	@RequestMapping("/notice/update/{noticeNum}")
	public ModelAndView noticeUpdateView(@PathVariable(name = "noticeNum") int noticeNum, ModelAndView view) {
		
		NoticeDTO dto = serviceService.selectNotice(noticeNum);
		
		view.addObject("dto", dto);
		view.setViewName("notice_Update");
		return view;
	}
	@RequestMapping("notice/update")
	public String noticeUpdate(NoticeDTO dto) {
		int result = serviceService.noticeUpdate(dto);
		System.out.println(dto.toString());
		return "redirect:/notice/view";
	}
	
	@RequestMapping("/admin/inquiry/sort/{type}/{answer}")
	public ResponseEntity<String> inquiryTypeSort(@PathVariable(name = "type") String type, @PathVariable(name = "answer") String answer) {
		System.out.println(type);
		if(type.equals("all"))
			type = null;
		if(answer.equals("all"))
			answer = null;
		System.out.println(type);
		List<NoticeDTO> list = serviceService.inquiryTypeSort(type, answer);
		
		
		return new ResponseEntity(list,HttpStatus.OK);
	}
}