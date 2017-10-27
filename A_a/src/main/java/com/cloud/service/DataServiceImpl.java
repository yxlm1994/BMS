package com.cloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.cloud.dao.App_CategoryMapper;
import com.cloud.dao.App_InfoMapper;
import com.cloud.dao.Data_DictionaryMapper;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.pojo.Backend_User;
import com.cloud.pojo.Data_Dictionary;
import com.cloud.util.Constants;

@Service
@Transactional
public class DataServiceImpl implements DataService {
	@Autowired
	Data_DictionaryMapper dMapper;
	@Autowired
	App_CategoryMapper aMapper;
	@Autowired
	App_InfoMapper infoMapper;

	@Override
	public Backend_User setBkdUserTypeName(Backend_User backend_User) {
		// TODO Auto-generated method stub
		List<Data_Dictionary> dictionarys = dMapper.selectByTypeCode(Constants.USER_TYPE);
		for (Data_Dictionary data_Dictionary : dictionarys) {
			if (data_Dictionary.getValueid() == backend_User.getUsertype()) {
				backend_User.setUsertypename(data_Dictionary.getValuename());
			}
		}
		return backend_User;
	}

	@Override
	public App_Info setAppInfoLogoName(App_Info app_Info) {
		// TODO Auto-generated method stub
		if (app_Info.getFlatformname() == null && app_Info.getFlatformid() != null) {
			List<Data_Dictionary> dictionarys = dMapper.selectByTypeCode(Constants.APP_FLATFORM);
			for (Data_Dictionary data_Dictionary : dictionarys) {
				if (data_Dictionary.getValueid() == app_Info.getFlatformid()) {
					app_Info.setFlatformname(data_Dictionary.getValuename());
				}
			}
		}
		if (app_Info.getStatusname() == null && app_Info.getStatus() != null) {
			List<Data_Dictionary> dictionarys = dMapper.selectByTypeCode(Constants.APP_STATUS);
			for (Data_Dictionary data_Dictionary : dictionarys) {
				if (data_Dictionary.getValueid() == app_Info.getStatus()) {
					app_Info.setStatusname(data_Dictionary.getValuename());
				}
			}
		}
		if (app_Info.getCategorylevel1name() == null && app_Info.getCategorylevel1() != null) {
			app_Info.setCategorylevel1name(aMapper.selectByPrimaryKey(app_Info.getCategorylevel1()).getCategoryname());
		}
		if (app_Info.getCategorylevel2name() == null && app_Info.getCategorylevel2() != null) {
			app_Info.setCategorylevel2name(aMapper.selectByPrimaryKey(app_Info.getCategorylevel2()).getCategoryname());
		}
		if (app_Info.getCategorylevel3name() == null && app_Info.getCategorylevel3() != null) {
			app_Info.setCategorylevel3name(aMapper.selectByPrimaryKey(app_Info.getCategorylevel3()).getCategoryname());
		}
		return app_Info;
	}

	@Override
	public List<Data_Dictionary> getFlatForm() {
		// TODO Auto-generated method stub
		return dMapper.selectByTypeCode(Constants.APP_FLATFORM);
	}

	@Override
	public List<Data_Dictionary> getAppStatus() {
		// TODO Auto-generated method stub
		return dMapper.selectByTypeCode(Constants.APP_STATUS);
	}

	@Override
	public App_Version setAppVersionLogoName(App_Version app_Version) {
		// TODO Auto-generated method stub
		String appname = infoMapper.selectByPrimaryKey(app_Version.getAppid()).getSoftwarename();
		List<Data_Dictionary> dictionarys = dMapper.selectByTypeCode(Constants.PUBLISH_STATUS);
		for (Data_Dictionary data_Dictionary : dictionarys) {
			if (data_Dictionary.getValueid() == app_Version.getPublishstatus()) {
				app_Version.setPublishstatusname(data_Dictionary.getValuename());
			}
		}
		app_Version.setAppname(appname);
		return app_Version;
	}

	@Override
	public List<Data_Dictionary> getByTypeCode(String typecode) {
		// TODO Auto-generated method stub
		return dMapper.selectByTypeCode(typecode);
	}
}
