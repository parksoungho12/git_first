package com.ottproject.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ottproject.dto.FileDTO;
import com.ottproject.dto.InquiryAnswerDTO;
import com.ottproject.dto.InquiryDTO;
import com.ottproject.dto.NoticeCommentDTO;
import com.ottproject.dto.NoticeDTO;


@Mapper
public interface ServiceMapper {

	int insertInquiry(InquiryDTO dto);

	int selectInquiryNum();

	List<InquiryDTO> selectAllInquiry(String email);

	InquiryDTO selectInquiry(int inquiryNum);

	InquiryAnswerDTO selectInquiryAnswer(int inquiryNum);

	int deleteInquiry(int inquiryNum);

	int updateInquiry(InquiryDTO dto);

	List<InquiryDTO> selectAllAdminInquiry();

	int insertAdminInquiry(InquiryAnswerDTO adto);

	int updateInquiryStatus(int inquiryNum);

	int deleteInquiryAnswer(int inquiryNum);

	List<InquiryDTO> inquiryAdminSort(HashMap<String, Object> map);

	int insertFile(FileDTO fileDTO);

	int selectImageFileNum();

	List<FileDTO> selectFileList(int inquiryNum);

	void insertBoardImage(HashMap<String, Object> map);

	FileDTO selectFile(HashMap<String, Object> map);

	FileDTO selectImageFile(int fileNum);

	List<NoticeDTO> selectAllNotice();

	int updateNoticeCount(int noticeNum);

	NoticeDTO selectNotice(int noticeNum);

	List<NoticeCommentDTO> selectNoticeComment(int noticeNum);

	int insertNoticeComment(NoticeCommentDTO cdto);

	Object selectNoticeLike(int noticeNum);

	int insertNoticeLike(HashMap<String, Object> map);

	int deleteNoticeLike(HashMap<String, Object> map);

	int insertNoticeHate(HashMap<String, Object> map);

	int deleteNoticeHate(HashMap<String, Object> map);

	Object selectNoticeHate(int noticeNum);

	int countComment(int noticeNum);

	List<NoticeDTO> searchNotice(String search);

	int deleteComment(HashMap<String, Object> map);

	int deleteNotice(int noticeNum);

	int selectNoticeNum();

	int addNotice(NoticeDTO dto);

	int updateNotice(NoticeDTO dto);

	int noticeCommentAllDelete(int noticeNum);

	int noticeLikeHateAllDelete(int noticeNum);

	int noticeLikeAllDelete(int noticeNum);

	int noticeHateAllDelete(int noticeNum);

	List<NoticeDTO> inquiryTypeSort(HashMap<String, Object> map);

}