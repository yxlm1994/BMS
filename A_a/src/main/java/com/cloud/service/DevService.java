package com.cloud.service;

import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.pojo.Dev_User;

/**
 * @author ninth-group
 * @function describe
 */
// ������Աҵ��
public interface DevService {

	// dev�û���¼
	public Dev_User login(String usercode, String password);

	// very important: app_version�޸Ļ����ʱ,Ҫͬ������appinfo
	// �޸�app�汾��Ϣ
	public Long modifyAppVersion(App_Version app_Version);

	// ����app�汾��Ϣ
	public Long addAppVersion(App_Version app_Version);

	// �޸�app������Ϣ
	public int modifyAppInfo(App_Info app_Info);

	// �ж�apkname�Ƿ����
	public boolean isApkNameExit(String apkname);

	// ����app������Ϣ
	public int addAppInfo(App_Info app_Info);

	// ɾ��appinfo���������appversion
	public int deleteApp(Long id);

}
