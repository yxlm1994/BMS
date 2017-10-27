package com.cloud.dao;

import java.util.List;

import com.cloud.pojo.App_Category;

public interface App_CategoryMapper {
	int deleteByPrimaryKey(Long id);

	int insert(App_Category record);

	int insertSelective(App_Category record);

	App_Category selectByPrimaryKey(Long id);

	List<App_Category> selectByParentId(Long parentId);

	int updateByPrimaryKeySelective(App_Category record);

	int updateByPrimaryKey(App_Category record);
}