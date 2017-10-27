package com.cloud.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.cloud.pojo.App_Category;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.pojo.Data_Dictionary;
import com.cloud.service.AppService;
import com.cloud.service.DataService;
import com.cloud.util.IHandle;

@Controller
@RequestMapping(value = "/manager/backend/app")
public class BkdController {

	static Logger logger = Logger.getLogger(BkdController.class);

	@Autowired
	AppService appService;
	@Autowired
	DataService dataService;

	// 选择查询条件来初始化list
	@RequestMapping(value = "/list")
	public String query(Model model, HttpSession session,
			@RequestParam(value = "queryCategoryLevel1", required = false) String lv1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String lv2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String lv3,
			@RequestParam(value = "querySoftwareName", required = false) String stnm,
			@RequestParam(value = "pageIndex", required = false) String curNo,
			@RequestParam(value = "queryFlatformId", required = false) String ftfmId) {

		logger.info("BkdController--query--(lv1):" + lv1);
		logger.info("BkdController--query--(lv2):" + lv2);
		logger.info("BkdController--query--(lv3):" + lv3);
		logger.info("BkdController--query--(stnm):" + stnm);
		logger.info("BkdController--query--(curNo):" + curNo);
		logger.info("BkdController--query--(ftfmId):" + ftfmId);

		// 初始化
		List<Data_Dictionary> flatFormList = dataService.getFlatForm();
		List<App_Category> categoryLevel1List = appService.getAppCategory(0L);
		List<App_Category> categoryLevel2List = null;
		List<App_Category> categoryLevel3List = null;
		App_Info app_Info = new App_Info();
		// 填充实体数据
		try {
			app_Info.setStatus(1L);
			app_Info.setSoftwarename(stnm);
			app_Info.setFlatformid(IHandle.stringToLong(ftfmId));
			app_Info.setCategorylevel1(IHandle.stringToLong(lv1));
			app_Info.setCategorylevel2(IHandle.stringToLong(lv2));
			app_Info.setCategorylevel3(IHandle.stringToLong(lv3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 将要显示的查询信息和pages信息写入model
		appService.showAppInfoByPage(curNo, model, app_Info);
		// 数据回写
		model.addAttribute("queryCategoryLevel1", lv1);
		model.addAttribute("queryCategoryLevel2", lv2);
		model.addAttribute("queryCategoryLevel3", lv3);
		model.addAttribute("queryFlatformId", ftfmId);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		// 是否显示二级或三级信息
		if (lv2 != null && !lv2.equals("")) {
			categoryLevel2List = appService.getAppCategory(IHandle.stringToLong(lv1));
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if (lv3 != null && !lv3.equals("")) {
			categoryLevel3List = appService.getAppCategory(IHandle.stringToLong(lv2));
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		return "backend/applist";
	}

	// 将二级或三级信息以json对象形式传入js
	@ResponseBody
	@RequestMapping(value = "/categorylevellist.json", method = RequestMethod.GET)
	public List<App_Category> categoryLevel(Long pid) {
		
		logger.info("BkdController--categoryLevel--(pid):" + pid);
		
		return appService.getAppCategory(pid);
	}

	// 核对appinfo
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(@RequestParam(value = "aid", required = false) String appId,
			@RequestParam(value = "vid", required = false) String versionId, Model model) {
		
		logger.info("BkdController--check--(appId):" + appId);
		logger.info("BkdController--check--(versionId):" + versionId);
		
		App_Info appInfo = null;
		App_Version appVersion = null;
		// 填充实体数据
		try {
			appInfo = appService.getAppInfo(IHandle.stringToLong(appId));
			appVersion = appService.getAppVersion(IHandle.stringToLong(versionId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 补充实体数据
		dataService.setAppInfoLogoName(appInfo);
		// 写入model
		model.addAttribute("appVersion", appVersion);
		model.addAttribute("appInfo", appInfo);

		return "backend/appcheck";
	}
	
	// 保存核对结果
	@RequestMapping(value = "/checksave", method = RequestMethod.POST)
	public String checkSave(App_Info appInfo) {
		
		logger.info("BkdController--checksave--(appInfo):" + JSONArray.toJSONString(appInfo));

		// 更新appInfo
		try {
			if (appService.updateAppStatus(appInfo) > 0) {
				return "redirect:/manager/backend/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "backend/appcheck";
	}
}
