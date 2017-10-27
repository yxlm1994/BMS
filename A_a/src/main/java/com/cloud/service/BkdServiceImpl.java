package com.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.dao.Backend_UserMapper;
import com.cloud.pojo.Backend_User;

@Service
@Transactional
public class BkdServiceImpl implements BkdService {
	@Autowired
	Backend_UserMapper backend_UserMapper;

	/**
	 * @see com.cloud.service.BkdService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Backend_User login(String user_code, String password) {
		// TODO Auto-generated method stub
		Backend_User bkd_User = backend_UserMapper.selectByBkdCode(user_code);
		if ((bkd_User != null) && (bkd_User.getUserpassword().equals(password))) {
			return bkd_User;
		} else {
			return null;
		}
	}
}
