package com.cloud.service;

import com.cloud.pojo.Backend_User;

/**
 * @author ninth-group
 * @function describe
 */
// ��̨��Աҵ��
public interface BkdService {

	// �û���¼��֤
	public Backend_User login(String usercode, String password);
}