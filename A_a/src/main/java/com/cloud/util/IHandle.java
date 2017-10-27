package com.cloud.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.controller.BkdController;

public class IHandle {
	static Logger logger = Logger.getLogger(BkdController.class);

	// stringת��Ϊlong��
	public static Long stringToLong(String num) {
		if ((num != null) && !(num.equals(""))) {
			return Long.parseLong(num);
		} else {
			return null;
		}
	}

	// �ϴ��ļ�
	public static boolean upLoad(MultipartFile file, String path, String prefix)
			throws IllegalStateException, IOException {
		String orifilename = file.getOriginalFilename();
		String suffix = orifilename.substring(orifilename.lastIndexOf(".") + 1).toLowerCase();
		boolean flag = true;
		String newfilename = prefix + "." + suffix;
		File targetFile = new File(path, newfilename);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		} else {
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	// ɾ���ļ�
	public static void delfile(String locpath) {
		File file = new File(locpath);
		if (file.exists()) {
			logger.info("----------file-delete");
			file.delete();
		}
	}

	// ��ȡ��ǰʱ��[yyyy-mm-dd hh:mm:ss]
	public static Date curDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
