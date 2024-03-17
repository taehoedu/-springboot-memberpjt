package com.office.memberpjt.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDao {

	public MemberDto selectMemberByMId(String m_id);

	public int insertNewMember(MemberDto memberDto);

	public int updateCurrentMember(MemberDto memberDto);

	public int deleteCurrentMember(MemberDto memberDto);
	
	public int deleteProfileThum(String m_id);

}
