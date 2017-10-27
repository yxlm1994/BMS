package com.cloud.dao;

import com.cloud.pojo.Backend_User;

public interface Backend_UserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Backend_User record);

	int insertSelective(Backend_User record);

	Backend_User selectByPrimaryKey(Long id);

	Backend_User selectByBkdCode(String bkdcode);

	int updateByPrimaryKeySelective(Backend_User record);

	int updateByPrimaryKey(Backend_User record);
}