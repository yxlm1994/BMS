package com.cloud.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.cloud.pojo.Backend_User;
import com.cloud.pojo.Dev_User;
import com.cloud.service.BkdService;
import com.cloud.service.DevService;
import com.cloud.service.DataService;
import com.cloud.util.Constants;

/**
 * 
 * @ClassName: LoginController
 * @Description: TODO(������е�¼����)
 * @author zhouwei
 * @date 2017��9��29�� ����2:28:34
 */
@Controller
public class LoginController {

	static Logger logger = Logger.getLogger(BkdController.class);

	@Autowired
	public DevService devService;
	@Autowired
	public BkdService bkdService;
	@Autowired
	public DataService rService;

	// ��ת����¼ҳ��
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	// ��¼��֤
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String doLogin(HttpSession httpSession, String code, String password) {

		logger.info("LoginController--doLogin--(code):" + JSONArray.toJSONString(code));
		logger.info("LoginController--doLogin--(password):" + JSONArray.toJSONString(password));

		// ͬʱ�����ű����ʵ������
		Dev_User dev_User = devService.login(code, password);
		Backend_User backend_User = bkdService.login(code, password);
		// �߼��жϲ�д��session
		if (dev_User == null && backend_User == null) {
			return "redirect:login";
		} else if (dev_User != null) {
			httpSession.setAttribute(Constants.DEV_USER_SESSION, dev_User);
			return "developer/main";
		} else {
			httpSession.setAttribute(Constants.BACKEND_USER_SESSION,
					backend_User = rService.setBkdUserTypeName(backend_User));
			return "backend/main";
		}
	}

	// ע��
	@RequestMapping(value = "/logout")
	public String outLogin(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:login";
	}
}
