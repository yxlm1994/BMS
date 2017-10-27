package com.cloud.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.cloud.pojo.App_Category;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;

public interface AppService {
	
	// 获取app_info通过appid
	public App_Info getAppInfo(Long id);

	// 获取app_version通过id
	public App_Version getAppVersion(Long id);

	// 获取app_version通过appid
	public List<App_Version> getAppVersionByAID(Long appid);

	// 更新app状态,返回更新的id
	public int updateAppStatus(App_Info app_Info);

	// 获取所有拥有相同父节点的app分类
	public List<App_Category> getAppCategory(Long parentId);

	// 获取指定条数满足查询条件的待审核app
	public List<App_Info> getSectionMeetingPendingApp(App_Info app_Info, Integer startPos);

	// 获取满足查询条件的待审核app的数目
	public int getMeetingCountPendingApp(App_Info app_Info);

	// 获取所有待审核的app
	public List<App_Info> getAllPendingApp();

	// 分页显示app_info
	public void showAppInfoByPage(String currentPageNo, Model model, App_Info app_Info);

	// 设置apkFileName
	public String setAPKFileName(App_Version app_Version);

	// 设置apkdownloadlink前缀
	public String setDownLoadLinkPrefix(HttpServletRequest request);

	// 设置apklocalpath
	public String setLocalPathPrefix(HttpServletRequest request);

}
