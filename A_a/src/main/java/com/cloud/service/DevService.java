package com.cloud.service;

import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.pojo.Dev_User;

/**
 * @author ninth-group
 * @function describe
 */
// 开发人员业务
public interface DevService {

	// dev用户登录
	public Dev_User login(String usercode, String password);

	// very important: app_version修改或添加时,要同步更新appinfo
	// 修改app版本信息
	public Long modifyAppVersion(App_Version app_Version);

	// 新增app版本信息
	public Long addAppVersion(App_Version app_Version);

	// 修改app基础信息
	public int modifyAppInfo(App_Info app_Info);

	// 判断apkname是否存在
	public boolean isApkNameExit(String apkname);

	// 新增app基础信息
	public int addAppInfo(App_Info app_Info);

	// 删除appinfo及所有相关appversion
	public int deleteApp(Long id);

}
