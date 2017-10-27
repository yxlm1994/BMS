package com.cloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloud.pojo.Dev_User;

public interface Dev_UserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Dev_User record);

	int insertSelective(Dev_User record);

	Dev_User selectByPrimaryKey(Long id);

	Dev_User selectByDevCode(String devcode);

	List<Dev_User> selectByPage(@Param(value = "startPos") Integer startPos,
			@Param(value = "pageSize") Integer pageSize);

	int updateByPrimaryKeySelective(Dev_User record);

	int updateByPrimaryKey(Dev_User record);
}