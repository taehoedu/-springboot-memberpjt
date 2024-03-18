package com.office.memberpjt.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.office.memberpjt.util.UploadFileService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberService {

	@Autowired
	IMemberDao iMemberDao;
	
	@Autowired
	IProfileThumDao iProfileThumDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UploadFileService uploadFileService;
	
	public Map<String, Object> memberJoin(MemberDto memberDto, MultipartFile m_profile_img) {
		log.info("memberJoin()");
		
		Map<String, Object> map = new HashMap<>();
		
		String savedFileName = null;
		if (m_profile_img != null) {
			savedFileName = uploadFileService.upload(memberDto.getM_id(), m_profile_img);
			if (savedFileName != null) {
				memberDto.setM_profile_thum(savedFileName);
			}
		}
		
		memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));
		
		int result = 0;
		try {
			result = iMemberDao.insertNewMember(memberDto);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		insertNewProfileThum(savedFileName, result, memberDto.getM_id());
		
		map.put("result", result);
		
		return map;
		
	}

	public Map<String, Object> memberLogin(MemberDto memberDto) {
		log.info("memberLogin()");
		
		Map<String, Object> map = new HashMap<>();
		
		MemberDto selectedMemberDto = iMemberDao.selectMemberByMId(memberDto.getM_id());
		map.put("selectedMemberDto", selectedMemberDto);
		
		if (selectedMemberDto == null || !passwordEncoder.matches(memberDto.getM_pw(), selectedMemberDto.getM_pw()))
			map.put("selectedMemberDto", null);
		
		return map;
	}

	public Map<String, Object> memberModify(MemberDto memberDto, MultipartFile m_profile_img) {
		log.info("memberModify()");
		
		Map<String, Object> map = new HashMap<>();
		
		String savedFileName = null;
		if (m_profile_img != null) {
			savedFileName = uploadFileService.upload(memberDto.getM_id(), m_profile_img);
			if (savedFileName != null) {
				memberDto.setM_profile_thum(savedFileName);
			}
		}
		
		int result = iMemberDao.updateCurrentMember(memberDto);
		
		insertNewProfileThum(savedFileName, result, memberDto.getM_id());
		
		map.put("result", result);
		
		return map;
		
	}

	public Map<String, Object> getMember(String m_id) {
		log.info("getMember()");
		
		Map<String, Object> map = new HashMap<>();
		
		MemberDto selectedMemberDto = iMemberDao.selectMemberByMId(m_id);
		
		map.put("selectedMemberDto", selectedMemberDto);
		
		return map;
		
	}

	public Map<String, Object> memberDelete(MemberDto memberDto) {
		log.info("memberDelete()");
		
		Map<String, Object> map = new HashMap<>();
		
		int result = iMemberDao.deleteCurrentMember(memberDto);
		
		map.put("result", result);
		
		return map;
	}
	
	private void insertNewProfileThum(String savedFileName, int result, String m_id) {
		log.info("insertNewProfileThum()");
		
		if (savedFileName != null && result > 0) {
			
			ProfileThumDto profileThumDto = new ProfileThumDto();
			profileThumDto.setM_id(m_id);
			profileThumDto.setPt_thum(savedFileName);
			
			int insertNewProfileThumResult = iProfileThumDao.insertNewProfileThum(profileThumDto);
			if (insertNewProfileThumResult > 0)
				log.info("insertNewProfileThum SUCCESS!!");
			else
				log.info("insertNewProfileThum FAIl!!");
			
		}
			
	}

	public Map<String, Object> deleteProfileThum(String m_id) {
		log.info("deleteProfileThum()");
		
		Map<String, Object> map = new HashMap<>();
		
		int result = iMemberDao.deleteProfileThum(m_id);
		
		map.put("result", result);
		
		return map;
		
	}

	public Map<String, Object> getProfileThums(String m_id) {
		log.info("getProfileThums()");
		
		Map<String, Object> map = new HashMap<>();
		
		List<ProfileThumDto> profileThumDtos = iProfileThumDao.getProfileThums(m_id);
		
		if (profileThumDtos.size() > 0) {
			map.put("profileThumDtos", profileThumDtos);
			return map;
			
		}
		
		return null;
		
	}

}