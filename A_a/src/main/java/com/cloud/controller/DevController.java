package com.cloud.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.pojo.App_Category;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.pojo.Data_Dictionary;
import com.cloud.pojo.Dev_User;
import com.cloud.service.AppService;
import com.cloud.service.DataService;
import com.cloud.service.DevService;
import com.cloud.util.Constants;
import com.cloud.util.IHandle;

@Controller
@RequestMapping(value = "/dev/flatform/app")
public class DevController {

	static Logger logger = Logger.getLogger(BkdController.class);

	@Autowired
	AppService appService;
	@Autowired
	DataService dataService;
	@Autowired
	DevService devService;

	// 选择查询条件来初始化list
	@RequestMapping(value = "/list")
	public String query(Model model, HttpSession session,
			@RequestParam(value = "queryCategoryLevel1", required = false) String lv1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String lv2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String lv3,
			@RequestParam(value = "querySoftwareName", required = false) String stnm,
			@RequestParam(value = "pageIndex", required = false) String curNo,
			@RequestParam(value = "queryFlatformId", required = false) String ftfmId,
			@RequestParam(value = "queryStatus", required = false) String sts) {

		logger.info("DevController--query--(lv1):" + lv1);
		logger.info("DevController--query--(lv2):" + lv2);
		logger.info("DevController--query--(lv3):" + lv3);
		logger.info("DevController--query--(stnm):" + stnm);
		logger.info("DevController--query--(curNo):" + curNo);
		logger.info("DevController--query--(ftfmId):" + ftfmId);
		logger.info("DevController--query--(sts):" + sts);

		// 初始化
		List<Data_Dictionary> flatFormList = dataService.getFlatForm();
		List<Data_Dictionary> statusList = dataService.getAppStatus();
		List<App_Category> categoryLevel1List = appService.getAppCategory(0L);
		List<App_Category> categoryLevel2List = null;
		List<App_Category> categoryLevel3List = null;
		App_Info app_Info = new App_Info();

		// 填充实体数据
		try {
			app_Info.setSoftwarename(stnm);
			// 通过session设置devid
			app_Info.setDevid(((Dev_User) session.getAttribute(Constants.DEV_USER_SESSION)).getId());
			app_Info.setFlatformid(IHandle.stringToLong(ftfmId));
			app_Info.setCategorylevel1(IHandle.stringToLong(lv1));
			app_Info.setCategorylevel2(IHandle.stringToLong(lv2));
			app_Info.setCategorylevel3(IHandle.stringToLong(lv3));
			app_Info.setStatus(IHandle.stringToLong(sts));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 将要显示的查询信息和pages信息写入model
		appService.showAppInfoByPage(curNo, model, app_Info);
		// 数据回写
		model.addAttribute("queryCategoryLevel1", lv1);
		model.addAttribute("queryCategoryLevel2", lv2);
		model.addAttribute("queryCategoryLevel3", lv3);
		model.addAttribute("queryStatus", sts);
		model.addAttribute("queryFlatformId", ftfmId);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("statusList", statusList);
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
		return "developer/appinfolist";
	}

	// 将app平台信息以json对象形式传入js
	@ResponseBody
	@RequestMapping(value = "/datadictionarylist.json", method = RequestMethod.GET)
	public List<Data_Dictionary> flatForm(String tcode) {

		logger.info("DevController--flatForm--(tcode):" + tcode);

		return dataService.getByTypeCode(tcode);
	}

	// 将二级或三级信息以json对象形式传入js
	@ResponseBody
	@RequestMapping(value = "/categorylevellist.json", method = RequestMethod.GET)
	public List<App_Category> categoryLevel(Long pid) {

		logger.info("DevController--categoryLevel--(pid):" + pid);

		return appService.getAppCategory(pid);
	}

	// 将apkname查询结果以json对象形式传入js
	@ResponseBody
	@RequestMapping(value = "/apkexist.json", method = RequestMethod.GET)
	public Map<String, String> apkExist(String APKName) {

		logger.info("DevController--categoryLevel--(APKName):" + APKName);

		// 逻辑判断apkname
		Map<String, String> map = new HashMap<>();
		if (APKName == null || APKName.equals("")) {
			map.put("APKName", "empty");
		} else if (devService.isApkNameExit(APKName)) {
			map.put("APKName", "exist");
		} else {
			map.put("APKName", "noexist");
		}
		return map;
	}

	// 将上下架结果以json数据返回
	@ResponseBody
	@RequestMapping(value = "/{appId}/sale.json", method = RequestMethod.PUT)
	public Map<String, Object> saleMsg(HttpSession session, @PathVariable(value = "appId") String aid) {

		logger.info("DevController--saleMsg--(aid):" + aid);

		Map<String, Object> map = new HashMap<>();
		// 填充实体数据
		try {
			App_Info app_Info = appService.getAppInfo(IHandle.stringToLong(aid));
			Long status = app_Info.getStatus();
			logger.info("DevController--saleMsg--(status):" + status);
			if (status == Constants.APP_STATUS_ONSALE) {
				// 设置修改者
				Long devid = ((Dev_User) session.getAttribute(Constants.DEV_USER_SESSION)).getId();
				app_Info.setModifyby(devid);
				// 设置下架
				app_Info.setStatus(Constants.APP_STATUS_OFFSALE);
				// 设置下架日期
				Date date = IHandle.curDate();
				app_Info.setOffsaledate(date);
				app_Info.setModifydate(date);
				Integer result = devService.modifyAppInfo(app_Info);
				if (result > 0) {
					logger.info("DevController--saleMsg--(...):offsale");
					map.put("resultMsg", "success");
					map.put("errorCode", "0");
				} else {
					map.put("resultMsg", "failed");
					map.put("errorCode", "exception000001");
				}

			} else if (status == Constants.APP_STATUS_PASS || status == Constants.APP_STATUS_OFFSALE) {
				// 设置修改者
				Long devid = ((Dev_User) session.getAttribute(Constants.DEV_USER_SESSION)).getId();
				app_Info.setModifyby(devid);
				// 设置上架
				app_Info.setStatus(Constants.APP_STATUS_ONSALE);
				// 设置上架日期
				Date date = IHandle.curDate();
				app_Info.setOnsaledate(date);
				app_Info.setModifydate(date);
				Integer result = devService.modifyAppInfo(app_Info);
				if (result > 0) {
					logger.info("DevController--saleMsg--(...):onsale");
					map.put("resultMsg", "success");
					map.put("errorCode", "0");
				} else {
					map.put("resultMsg", "failed");
					map.put("errorCode", "exception000001");
				}
			} else {
				map.put("resultMsg", "failed");
				map.put("errorCode", "param000001");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 跳转至appinfoadd页面
	@RequestMapping(value = "/appinfoadd")
	public String appInfoAdd() {
		return "developer/appinfoadd";
	}

	// 保存appinfo添加信息
	@RequestMapping(value = "appinfoaddsave")
	public String appInfoAddSave(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(value = "APKName", required = false) String apkname,
			@RequestParam(value = "softwareName", required = false) String stnm,
			@RequestParam(value = "supportROM", required = false) String rom,
			@RequestParam(value = "interfaceLanguage", required = false) String lan,
			@RequestParam(value = "softwareSize", required = false) String stsize,
			@RequestParam(value = "downloads", required = false) String downloads,
			@RequestParam(value = "flatformId", required = false) String ftfmid,
			@RequestParam(value = "categoryLevel1", required = false) String lv1,
			@RequestParam(value = "categoryLevel2", required = false) String lv2,
			@RequestParam(value = "categoryLevel3", required = false) String lv3,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "appInfo", required = false) String appinfo,
			@RequestParam(value = "a_logoPicPath", required = false) MultipartFile file) {

		logger.info("DevController--appInfoAddSave--(apkname):" + apkname);
		logger.info("DevController--appInfoAddSave--(stnm):" + stnm);
		logger.info("DevController--appInfoAddSave--(rom):" + rom);
		logger.info("DevController--appInfoAddSave--(lan):" + lan);
		logger.info("DevController--appInfoAddSave--(stsize):" + stsize);
		logger.info("DevController--appInfoAddSave--(downloads):" + downloads);
		logger.info("DevController--appInfoAddSave--(ftfmid):" + ftfmid);
		logger.info("DevController--appInfoAddSave--(lv1):" + lv1);
		logger.info("DevController--appInfoAddSave--(lv2):" + lv2);
		logger.info("DevController--appInfoAddSave--(lv3):" + lv3);
		logger.info("DevController--appInfoAddSave--(status):" + status);
		logger.info("DevController--appInfoAddSave--(appinfo):" + appinfo);

		App_Info app_Info = new App_Info();

		// 填充实体数据
		try {
			app_Info.setApkname(apkname);
			app_Info.setSoftwarename(stnm);
			app_Info.setSupportrom(rom);
			app_Info.setInterfacelanguage(lan);
			app_Info.setSoftwaresize(BigDecimal.valueOf(Double.parseDouble(stsize)));
			app_Info.setDownloads(IHandle.stringToLong(downloads));
			app_Info.setFlatformid(IHandle.stringToLong(ftfmid));
			app_Info.setCategorylevel1(IHandle.stringToLong(lv1));
			app_Info.setCategorylevel2(IHandle.stringToLong(lv2));
			app_Info.setCategorylevel3(IHandle.stringToLong(lv3));
			app_Info.setStatus(IHandle.stringToLong(status));
			app_Info.setAppinfo(appinfo);
			// 设置devid
			Long devid = ((Dev_User) session.getAttribute(Constants.DEV_USER_SESSION)).getId();
			app_Info.setDevid(devid);
			app_Info.setCreatedby(devid);
			// 设置创建时间
			Date date = IHandle.curDate();
			app_Info.setCreationdate(date);
			// 设置图片的服务器端路径
			String orifilename = file.getOriginalFilename();
			String suffix = orifilename.substring(orifilename.lastIndexOf(".") + 1).toLowerCase();
			app_Info.setLogopicpath(appService.setDownLoadLinkPrefix(request) + apkname + "." + suffix);
			// 保存图片
			IHandle.upLoad(file, appService.setLocalPathPrefix(request), apkname);
			// 设置图片的本地路径
			app_Info.setLogolocpath(appService.setDownLoadLinkPrefix(request) + apkname + "." + suffix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// appinfo写入数据库
		devService.addAppInfo(app_Info);
		return "forward:/dev/flatform/app/list";
	}

	// 跳转appinfo显示页面
	@RequestMapping(value = "/appview/{appinfoid}", method = RequestMethod.GET)
	public String appInfoView(Model model, @PathVariable("appinfoid") String appinfoid) {

		logger.info("DevController--appInfoView--(appinfoid):" + appinfoid);

		// 填充实体数据
		App_Info app_Info = appService.getAppInfo(IHandle.stringToLong(appinfoid));
		// 补充实体数据
		dataService.setAppInfoLogoName(app_Info);
		// 填充实体数据
		List<App_Version> app_Versions = appService.getAppVersionByAID(IHandle.stringToLong(appinfoid));
		// 补充实体数据集
		for (App_Version app_Version : app_Versions) {
			dataService.setAppVersionLogoName(app_Version);
		}
		// 写入model
		model.addAttribute("appInfo", app_Info);
		model.addAttribute("appVersionList", app_Versions);
		return "developer/appinfoview";
	}

	// 跳转至appinfo修改页面
	@RequestMapping(value = "/appinfomodify", method = RequestMethod.GET)
	public String appInfoModify(Model model, @RequestParam(value = "id") String appinfoid) {

		logger.info("DevController--appInfoModify--(appinfoid):" + appinfoid);

		// 填充实体数据
		App_Info app_Info = appService.getAppInfo(IHandle.stringToLong(appinfoid));
		// 补充实体数据
		dataService.setAppInfoLogoName(app_Info);
		// 写入model
		model.addAttribute("appInfo", app_Info);
		return "developer/appinfomodify";
	}

	// 保存appinfo修改
	@RequestMapping(value = "/appinfomodifysave", method = RequestMethod.POST)
	public String appInfoModifySave(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "SoftwareName", required = false) String stnm,
			@RequestParam(value = "supportROM", required = false) String rom,
			@RequestParam(value = "interfaceLanguage", required = false) String lan,
			@RequestParam(value = "softwareSize", required = false) String stsize,
			@RequestParam(value = "downloads", required = false) String downloads,
			@RequestParam(value = "flatformId", required = false) String ftfmid,
			@RequestParam(value = "categoryLevel1", required = false) String lv1,
			@RequestParam(value = "categoryLevel2", required = false) String lv2,
			@RequestParam(value = "categoryLevel3", required = false) String lv3,
			@RequestParam(value = "appInfo", required = false) String appinfo,
			@RequestParam(value = "attach", required = false) MultipartFile file) {

		logger.info("DevController--appInfoModifySave--(id):" + id);
		logger.info("DevController--appInfoModifySave--(stnm):" + stnm);
		logger.info("DevController--appInfoModifySave--(rom):" + rom);
		logger.info("DevController--appInfoModifySave--(lan):" + lan);
		logger.info("DevController--appInfoModifySave--(stsize):" + stsize);
		logger.info("DevController--appInfoModifySave--(downloads):" + downloads);
		logger.info("DevController--appInfoModifySave--(ftfmid):" + ftfmid);
		logger.info("DevController--appInfoModifySave--(lv1):" + lv1);
		logger.info("DevController--appInfoModifySave--(lv2):" + lv2);
		logger.info("DevController--appInfoModifySave--(lv3):" + lv3);
		logger.info("DevController--appInfoModifySave--(appinfo):" + appinfo);

		// 获取appinfo
		App_Info app_Info = appService.getAppInfo(IHandle.stringToLong(id));
		// 填充实体数据
		try {
			// 设置devid
			Long devid = ((Dev_User) session.getAttribute(Constants.DEV_USER_SESSION)).getId();
			app_Info.setModifyby(devid);
			// 填入形参属性
			app_Info.setSoftwarename(stnm);
			app_Info.setSupportrom(rom);
			app_Info.setInterfacelanguage(lan);
			app_Info.setSoftwaresize(BigDecimal.valueOf(Double.parseDouble(stsize)));
			app_Info.setDownloads(IHandle.stringToLong(downloads));
			app_Info.setFlatformid(IHandle.stringToLong(ftfmid));
			app_Info.setCategorylevel1(IHandle.stringToLong(lv1));
			app_Info.setCategorylevel2(IHandle.stringToLong(lv2));
			app_Info.setCategorylevel3(IHandle.stringToLong(lv3));
			app_Info.setAppinfo(appinfo);
			// 设置修改日期
			Date date = IHandle.curDate();
			app_Info.setModifydate(date);
			// 设置图片的服务器端路径
			String orifilename = file.getOriginalFilename();
			String suffix = orifilename.substring(orifilename.lastIndexOf(".") + 1).toLowerCase();
			app_Info.setLogopicpath(appService.setDownLoadLinkPrefix(request) + app_Info.getApkname() + "." + suffix);
			// 保存图片
			IHandle.upLoad(file, appService.setLocalPathPrefix(request), app_Info.getApkname());
			// 设置图片的本地路径
			app_Info.setLogolocpath(appService.setDownLoadLinkPrefix(request) + app_Info.getApkname() + "." + suffix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		devService.modifyAppInfo(app_Info);
		return "forward:/dev/flatform/app/list";
	}

	// 跳转至app版本信息添加页面
	@RequestMapping(value = "/appversionadd", method = RequestMethod.GET)
	public String AppVersionAdd(Model model, @RequestParam(value = "id") String aid) {

		logger.info("DevController--appVersionAdd--(id):" + aid);

		// 填充实体数据集
		List<App_Version> app_Versions = appService.getAppVersionByAID(IHandle.stringToLong(aid));
		for (App_Version app_Version : app_Versions) {
			dataService.setAppVersionLogoName(app_Version);
		}
		// 写入model
		model.addAttribute("appVersionList", app_Versions);
		model.addAttribute("appid", aid);
		return "developer/appversionadd";
	}

	// 保存appversion
	@RequestMapping(value = "/addversionsave", method = RequestMethod.POST)
	public String appVersionSave(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(value = "aId") String said, @RequestParam(value = "versionNo") String vno,
			@RequestParam(value = "versionSize") String vsize, @RequestParam(value = "versionInfo") String vinfo,
			@RequestParam(value = "publishStatus") String psts,
			@RequestParam(value = "a_downloadLink", required = false) MultipartFile file) {

		logger.info("DevController--appVersionSave--(said):" + said);
		logger.info("DevController--appVersionSave--(vno):" + vno);
		logger.info("DevController--appVersionSave--(vsize):" + vsize);
		logger.info("DevController--appVersionSave--(vinfo):" + vinfo);
		logger.info("DevController--appVersionSave--(psts):" + psts);

		App_Version app_Version = new App_Version();
		// 填充实体数据
		try {
			Long aid = IHandle.stringToLong(said);
			// 填入参数信息
			app_Version.setAppid(aid);
			app_Version.setVersionno(vno);
			app_Version.setVersioninfo(vinfo);
			app_Version.setPublishstatus(IHandle.stringToLong(psts));
			app_Version.setVersionsize(BigDecimal.valueOf(Double.parseDouble(vsize)));
			// 设置创建日期
			Date date = IHandle.curDate();
			app_Version.setCreationdate(date);
			// 设置创建者
			Long devid = ((Dev_User) session.getAttribute(Constants.DEV_USER_SESSION)).getId();
			app_Version.setCreatedby(devid);
			// 设置apkfile名称
			String apkfilename = appService.setAPKFileName(app_Version);
			app_Version.setApkfilename(apkfilename);
			// 上传文件
			IHandle.upLoad(file, appService.setLocalPathPrefix(request), apkfilename);
			// 设置下载地址
			app_Version.setDownloadlink(appService.setDownLoadLinkPrefix(request) + apkfilename);
			// 设置本地路径
			app_Version.setApklocpath(appService.setLocalPathPrefix(request) + apkfilename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 添加版本信息
		devService.addAppVersion(app_Version);
		return "forward:/dev/flatform/app/list";
	}

	// 跳转至appversion修改界面
	@RequestMapping(value = "/appversionmodify", method = RequestMethod.GET)
	public String appVersionModify(Model model, @RequestParam(value = "aid") String aid,
			@RequestParam(value = "vid") String vid) {

		logger.info("DevController--appVersionModify--(aid):" + aid);
		logger.info("DevController--appVersionModify--(vid):" + vid);

		// 填充实体数据
		List<App_Version> app_Versions = appService.getAppVersionByAID(IHandle.stringToLong(aid));
		for (App_Version app_Version : app_Versions) {
			if (app_Version.getId() == IHandle.stringToLong(vid))
				model.addAttribute("appVersion", app_Version);
			dataService.setAppVersionLogoName(app_Version);
		}
		// 写入model
		model.addAttribute("appVersionList", app_Versions);
		model.addAttribute("appid", aid);
		return "developer/appversionmodify";
	}

	// 保存appversion修改
	@RequestMapping(value = "/appversionmodifysave", method = RequestMethod.POST)
	public String appModifySave(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(value = "id") String id, @RequestParam(value = "appId") String aid,
			@RequestParam(value = "versionInfo") String vInfo, @RequestParam(value = "versionSize") String vsize) {

		logger.info("DevController--appModifySave--(id):" + id);
		logger.info("DevController--appModifySave--(aid):" + aid);
		logger.info("DevController--appVersionModify--(vInfo):" + vInfo);
		logger.info("DevController--appVersionSave--(vsize):" + vsize);

		Long devid = ((Dev_User) session.getAttribute(Constants.DEV_USER_SESSION)).getId();
		// 填充实体数据
		List<App_Version> app_Versions = appService.getAppVersionByAID(IHandle.stringToLong(aid));
		for (App_Version app_Version : app_Versions) {
			if (app_Version.getId() == IHandle.stringToLong(id)) {
				app_Version.setVersionsize(BigDecimal.valueOf(Double.parseDouble(vsize)));
				app_Version.setVersioninfo(vInfo);
				app_Version.setModifyby(devid);
				app_Version.setModifydate(IHandle.curDate());
				devService.modifyAppVersion(app_Version);
			}
		}
		return "forward:/dev/flatform/app/list";
	}

	// 传入delfile的结果
	@ResponseBody
	@RequestMapping(value = "/delfile.json", method = RequestMethod.GET)
	public Map<String, String> delFile(Long id, String flag) {

		logger.info("DevController--delFile--(id):" + id);
		logger.info("DevController--delFile--(flag):" + flag);

		// 逻辑判断
		if (flag.equals("apk")) {
			App_Version app_Version = appService.getAppVersion(id);
			IHandle.delfile(app_Version.getApklocpath());
			app_Version.setApkfilename(null);
			app_Version.setApklocpath(null);
			Map<String, String> map = new HashMap<>();
			if (devService.modifyAppVersion(app_Version) > 0) {
				map.put("result", "success");

			} else {
				map.put("result", "failed");
			}
			return map;
		} else {
			App_Info app_Info = appService.getAppInfo(id);
			IHandle.delfile(app_Info.getLogolocpath());
			app_Info.setLogolocpath(null);
			app_Info.setLogopicpath(null);
			Map<String, String> map = new HashMap<>();
			if (devService.modifyAppInfo(app_Info) > 0) {
				map.put("result", "success");

			} else {
				map.put("result", "failed");
			}
			return map;
		}
	}

	// 传入删除app结果
	@ResponseBody
	@RequestMapping(value = "/delapp.json", method = RequestMethod.GET)
	public Map<String, String> delApp(Long id) {

		logger.info("DevController--delApp--(id):" + id);

		// 逻辑判断
		Map<String, String> map = new HashMap<>();
		if (devService.deleteApp(id) > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "failed");
		}
		return map;
	}
}
