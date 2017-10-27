package com.cloud.service;

import java.util.List;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.pojo.Backend_User;
import com.cloud.pojo.Data_Dictionary;

/**
 * 
 * @author ninth-group
 * @function describe
 */
// 检索字典
public interface DataService {
	// 设置后台用户角色类型中文名
	public Backend_User setBkdUserTypeName(Backend_User backend_User);

	// 设置app_info所有标识对应中文名
	public App_Info setAppInfoLogoName(App_Info app_Info);

	// 设置app_version所有标识对应中文名
	public App_Version setAppVersionLogoName(App_Version app_Version);

	// 获取所有app平台
	public List<Data_Dictionary> getFlatForm();

	// 获取所有app状态
	public List<Data_Dictionary> getAppStatus();

	// 获取数据字典通过type_code
	public List<Data_Dictionary> getByTypeCode(String typecode);

}
