package com.cloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloud.pojo.Data_Dictionary;

public interface Data_DictionaryMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Data_Dictionary record);

	int insertSelective(Data_Dictionary record);

	Data_Dictionary selectByPrimaryKey(Long id);

	List<Data_Dictionary> selectByTypeCode(@Param(value = "typeCode") String typeCode);

	int updateByPrimaryKeySelective(Data_Dictionary record);

	int updateByPrimaryKey(Data_Dictionary record);
}