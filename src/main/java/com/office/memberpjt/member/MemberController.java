package com.office.memberpjt.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	
	/*
	 * 회원가입 확인
	 */
	@PostMapping("/member_join")
	public Object memberJoin(MemberDto memberDto, @RequestParam(value = "m_profile_img", required = false) MultipartFile m_profile_img) {
		log.info("memberJoin()");
		
		Map<String, Object> responseMap = memberService.memberJoin(memberDto, m_profile_img);
		
		return responseMap;
		
	}
	
	/*
	 * 로그인 확인
	 */
	@PostMapping("/member_login")
	public Object memberLogin(MemberDto memberDto, HttpSession session) {
		log.info("memberLogin()");
		
		Map<String, Object> responseMap = memberService.memberLogin(memberDto);
		
		if (responseMap.get("selectedMemberDto") != null) {
			session.setAttribute("selectedMemberDto", responseMap.get("selectedMemberDto"));
			
		}
		
		String sesstionID = session.getId();
		log.info("sesstionID: {}", sesstionID);
		responseMap.put("sessionID", sesstionID);
		
		return responseMap;
		
	}
	
	/*
	 * 로그아웃 확인
	 */
	@PostMapping("/member_logout")
	public Object memberLogout(MemberDto memberDto, HttpSession session) {
		log.info("memberLogout()");
		
		session.invalidate();
		
		return null;
		
		
	}
	
	/*
	 * 계정수정 확인
	 */
	@PostMapping("/member_modify")
	public Object memberModify(MemberDto memberDto, @RequestParam(value = "m_profile_img", required = false) MultipartFile m_profile_img, HttpSession session) {
		log.info("memberModify()");
		
		Map<String, Object> responseMap = memberService.memberModify(memberDto, m_profile_img);
		
		if ((int) responseMap.get("result") > 0)
			session.setAttribute("selectedMemberDto", memberDto);
		
		return responseMap;
		
	}
	
	/*
	 * 계정삭제 확인
	 */
	@PostMapping("/member_delete")
	public Object memberDelete(MemberDto memberDto, @RequestParam("sessionID") String sessionID, HttpSession session) {
		log.info("memberModify()");
		
		Map<String, Object> responseMap = null;
		
		if (sessionID.equals(session.getId())) {
			log.info("The session has not expired!!");
			
			responseMap = memberService.memberDelete(memberDto);
			
		} else {
			log.info("The session has expired!!");
			
		}
		
		return responseMap;
		
	}
	
	/*
	 * 회원정보 가져오기
	 */
	@PostMapping("/get_member")
	public Object getMember(@RequestParam("sessionID") String sessionID, HttpSession session) {
		log.info("getMember()");
		
		Map<String, Object> responseMap = null;
		
		if (sessionID.equals(session.getId())) {
			log.info("The session has not expired!!");
			
			MemberDto selectedMemberDto = (MemberDto) session.getAttribute("selectedMemberDto");
			responseMap = memberService.getMember(selectedMemberDto.getM_id());
			
		} else {
			log.info("The session has expired!!");
			
		}
		
		return responseMap;
		
	}
	
	/*
	 * 프로필 이미지 삭제 확인
	 */
	@PostMapping("/delete_profile_thum")
	public Object deleteProfileThum(MemberDto memberDto) {
		log.info("memberModify()");
		
		Map<String, Object> responseMap = memberService.deleteProfileThum(memberDto.getM_id());
		
		return responseMap;
		
	}
	
	/*
	 * 프로필 이미지 가져오기
	 */
	@PostMapping("/get_profile_thums")
	public Object getProfileThums(MemberDto memberDto) {
		log.info("getProfileThums()");
		
		Map<String, Object> responseMap = memberService.getProfileThums(memberDto.getM_id());
		
		return responseMap;
		
	}
	
	
}
