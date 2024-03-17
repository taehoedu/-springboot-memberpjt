package com.office.memberpjt.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProfileThumDao {

	public int insertNewProfileThum(ProfileThumDto profileThumDto);

	public List<ProfileThumDto> getProfileThums(String m_id);

}
