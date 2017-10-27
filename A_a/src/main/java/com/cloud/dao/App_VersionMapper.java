package com.cloud.dao;

import java.util.List;

import com.cloud.pojo.App_Version;

public interface App_VersionMapper {
	int deleteByPrimaryKey(Long id);

	int insert(App_Version record);

	int insertSelective(App_Version record);

	App_Version selectByPrimaryKey(Long id);

	List<App_Version> selectByAppId(Long appid);

	int updateByPrimaryKeySelective(App_Version record);

	int updateByPrimaryKey(App_Version record);
}