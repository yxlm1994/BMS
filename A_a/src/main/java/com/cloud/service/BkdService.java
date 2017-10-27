package com.cloud.service;

import com.cloud.pojo.Backend_User;

/**
 * @author ninth-group
 * @function describe
 */
// 后台人员业务
public interface BkdService {

	// 用户登录验证
	public Backend_User login(String usercode, String password);
}