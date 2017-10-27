package com.cloud.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.cloud.dao.App_CategoryMapper;
import com.cloud.dao.App_InfoMapper;
import com.cloud.dao.App_VersionMapper;
import com.cloud.pojo.App_Category;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.util.Constants;
import com.cloud.util.Page;

@Service
@Transactional
public class AppServiceImpl implements AppService {
	@Autowired
	App_InfoMapper app_InfoMapper;

	@Autowired
	App_VersionMapper app_VersionMapper;

	@Autowired
	DataService dataService;

	@Autowired
	App_CategoryMapper app_CategoryMapper;

	@Override
	public App_Info getAppInfo(Long id) {
		// TODO Auto-generated method stub
		return app_InfoMapper.selectByPrimaryKey(id);
	}

	public List<App_Version> getAppVersionByAID(Long appid) {
		return app_VersionMapper.selectByAppId(appid);
	}

	@Override
	public int updateAppStatus(App_Info app_Info) {
		// TODO Auto-generated method stub

		return app_InfoMapper.updateByPrimaryKeySelective(app_Info);
	}

	@Override
	public List<App_Category> getAppCategory(Long parentId) {
		// TODO Auto-generated method stub
		return app_CategoryMapper.selectByParentId(parentId);
	}

	@Override
	public List<App_Info> getSectionMeetingPendingApp(App_Info app_Info, Integer startPos) {
		// TODO Auto-generated method stub
		return app_InfoMapper.selectByPage(app_Info.getDevid(), app_Info.getStatus(), app_Info.getSoftwarename(),
				app_Info.getFlatformid(), app_Info.getCategorylevel1(), app_Info.getCategorylevel2(),
				app_Info.getCategorylevel3(), startPos, Constants.PAGESIZE);
	}

	@Override
	public int getMeetingCountPendingApp(App_Info app_Info) {
		// TODO Auto-generated method stub
		return app_InfoMapper.getALLByPage(app_Info.getDevid(), app_Info.getStatus(), app_Info.getSoftwarename(),
				app_Info.getFlatformid(), app_Info.getCategorylevel1(), app_Info.getCategorylevel2(),
				app_Info.getCategorylevel3()).size();
	}

	@Override
	public void showAppInfoByPage(String currentPageNo, Model model, App_Info app_Info) {
		// TODO Auto-generated method stub
		Page page = new Page();
		int totalCount = getMeetingCountPendingApp(app_Info);
		page.setTotalCount(totalCount);
		if (currentPageNo == null) {
			page.setCurrentPageNo(1);
		} else {
			page.setCurrentPageNo(Integer.parseInt(currentPageNo));
		}
		List<App_Info> app_Infos = getSectionMeetingPendingApp(app_Info, page.getStartPos());
		for (App_Info info : app_Infos) {
			dataService.setAppInfoLogoName(info);
		}
		model.addAttribute("pages", page);
		model.addAttribute("appInfoList", app_Infos);
	}

	@Override
	public List<App_Info> getAllPendingApp() {
		// TODO Auto-generated method stub
		return app_InfoMapper.selectByStatus(1L);
	}

	@Override
	public App_Version getAppVersion(Long id) {
		// TODO Auto-generated method stub
		return app_VersionMapper.selectByPrimaryKey(id);
	}

	@Override
	public String setAPKFileName(App_Version app_Version) {
		// TODO Auto-generated method stub
		String apkname = app_InfoMapper.selectByPrimaryKey(app_Version.getAppid()).getApkname();
		String apkFileName = apkname + "-" + app_Version.getVersionno() + ".apk";
		return apkFileName;
	}

	@Override
	public String setDownLoadLinkPrefix(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String prefix = request.getContextPath() + "/statics/uploadfiles/";
		return prefix;
	}

	public String setLocalPathPrefix(HttpServletRequest request) {
		String prefix = request.getSession().getServletContext().getRealPath("") + File.separator + "statics"
				+ File.separator + "uploadfiles" + File.separator;
		return prefix;
	}
}
