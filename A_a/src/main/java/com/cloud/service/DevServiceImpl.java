package com.cloud.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.cloud.controller.BkdController;
import com.cloud.dao.App_InfoMapper;
import com.cloud.dao.App_VersionMapper;
import com.cloud.dao.Dev_UserMapper;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.pojo.Dev_User;

/**
 * @author ninth-group
 *
 */
@Service
@Transactional
public class DevServiceImpl implements DevService {
	static Logger logger = Logger.getLogger(BkdController.class);
	@Autowired
	Dev_UserMapper dev_UserMapper;

	@Autowired
	App_InfoMapper app_InfoMapper;

	@Autowired
	App_VersionMapper app_VersionMapper;

	/**
	 * @see com.cloud.service.DevService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Dev_User login(String user_code, String password) {
		// TODO Auto-generated method stub
		Dev_User dev_User = dev_UserMapper.selectByDevCode(user_code);
		if ((dev_User != null) && (dev_User.getDevpassword().equals(password))) {
			return dev_User;
		} else {
			return null;
		}
	}

	@Override
	public Long modifyAppVersion(App_Version app_Version) {
		// TODO Auto-generated method stub
		App_Info app_Info = app_InfoMapper.selectByPrimaryKey(app_Version.getAppid());
		app_Info.setVersionid(app_Version.getId());
		app_Info.setUpdatedate(app_Version.getModifydate());
		app_Info.setSoftwaresize(app_Version.getVersionsize());
		app_Info.setModifyby(app_Version.getModifyby());
		app_Info.setModifydate(app_Version.getModifydate());
		app_VersionMapper.updateByPrimaryKeySelective(app_Version);
		app_InfoMapper.updateByPrimaryKeySelective(app_Info);
		return app_Version.getId();
	}

	@Override
	public Long addAppVersion(App_Version app_Version) {
		// TODO Auto-generated method stub
		app_VersionMapper.insertSelective(app_Version);
		Long vid = app_Version.getId();
		App_Info app_Info = app_InfoMapper.selectByPrimaryKey(app_Version.getAppid());
		app_Info.setUpdatedate(app_Version.getModifydate());
		app_Info.setModifyby(app_Version.getModifyby());
		app_Info.setModifydate(app_Version.getModifydate());
		app_Info.setSoftwaresize(app_Version.getVersionsize());
		app_Info.setVersionid(vid);
		app_InfoMapper.updateByPrimaryKeySelective(app_Info);

		return vid;
	}

	@Override
	public int modifyAppInfo(App_Info app_Info) {
		// TODO Auto-generated method stub
		return app_InfoMapper.updateByPrimaryKeySelective(app_Info);
	}

	@Override
	public int addAppInfo(App_Info app_Info) {
		// TODO Auto-generated method stub
		return app_InfoMapper.insertSelective(app_Info);
	}

	@Override
	public int deleteApp(Long id) {
		// TODO Auto-generated method stub
		logger.info("DevServiceImpl--deleteApp--(id):" + id);
		List<App_Version> app_Versions = app_VersionMapper.selectByAppId(id);
		for (App_Version app_Version : app_Versions) {
			app_VersionMapper.deleteByPrimaryKey(app_Version.getId());
			logger.info("DevServiceImpl--deleteApp--(app_Version):" + JSONArray.toJSONString(app_Version));
		}
		return app_InfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public boolean isApkNameExit(String apkname) {
		// TODO Auto-generated method stub
		App_Info app_Info = app_InfoMapper.selectByApkName(apkname);
		if (app_Info != null) {
			return true;
		} else {
			return false;
		}
	}
}
