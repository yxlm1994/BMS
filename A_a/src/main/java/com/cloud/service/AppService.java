package com.cloud.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.cloud.pojo.App_Category;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;

public interface AppService {
	
	// ��ȡapp_infoͨ��appid
	public App_Info getAppInfo(Long id);

	// ��ȡapp_versionͨ��id
	public App_Version getAppVersion(Long id);

	// ��ȡapp_versionͨ��appid
	public List<App_Version> getAppVersionByAID(Long appid);

	// ����app״̬,���ظ��µ�id
	public int updateAppStatus(App_Info app_Info);

	// ��ȡ����ӵ����ͬ���ڵ��app����
	public List<App_Category> getAppCategory(Long parentId);

	// ��ȡָ�����������ѯ�����Ĵ����app
	public List<App_Info> getSectionMeetingPendingApp(App_Info app_Info, Integer startPos);

	// ��ȡ�����ѯ�����Ĵ����app����Ŀ
	public int getMeetingCountPendingApp(App_Info app_Info);

	// ��ȡ���д���˵�app
	public List<App_Info> getAllPendingApp();

	// ��ҳ��ʾapp_info
	public void showAppInfoByPage(String currentPageNo, Model model, App_Info app_Info);

	// ����apkFileName
	public String setAPKFileName(App_Version app_Version);

	// ����apkdownloadlinkǰ׺
	public String setDownLoadLinkPrefix(HttpServletRequest request);

	// ����apklocalpath
	public String setLocalPathPrefix(HttpServletRequest request);

}
