package com.cloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cloud.pojo.App_Info;

public interface App_InfoMapper {
	int deleteByPrimaryKey(Long id);

	int insert(App_Info record);

	int insertSelective(App_Info record);

	App_Info selectByPrimaryKey(Long id);

	List<App_Info> selectByPage(@Param(value = "devid") Long devid, @Param(value = "status") Long status,
			@Param(value = "softwareName") String softwareName, @Param(value = "flatformid") Long flatformid,
			@Param(value = "ctgylv1") Long ctgylv1, @Param(value = "ctgylv2") Long ctgylv2,
			@Param(value = "ctgylv3") Long ctgylv3, @Param(value = "startPos") Integer startPos,
			@Param(value = "pageSize") Integer pageSize);

	List<App_Info> getALLByPage(@Param(value = "devid") Long devid, @Param(value = "status") Long appStatus,
			@Param(value = "softwareName") String softwareName, @Param(value = "flatformid") Long flatformid,
			@Param(value = "ctgylv1") Long ctgylv1, @Param(value = "ctgylv2") Long ctgylv2,
			@Param(value = "ctgylv3") Long ctgylv3);

	List<App_Info> selectByStatus(Long status);

	int updateByPrimaryKeySelective(App_Info record);

	int updateByPrimaryKey(App_Info record);

	App_Info selectByApkName(String APKName);
}