package com.cloud.service;

import java.util.List;
import com.cloud.pojo.App_Info;
import com.cloud.pojo.App_Version;
import com.cloud.pojo.Backend_User;
import com.cloud.pojo.Data_Dictionary;

/**
 * 
 * @author ninth-group
 * @function describe
 */
// �����ֵ�
public interface DataService {
	// ���ú�̨�û���ɫ����������
	public Backend_User setBkdUserTypeName(Backend_User backend_User);

	// ����app_info���б�ʶ��Ӧ������
	public App_Info setAppInfoLogoName(App_Info app_Info);

	// ����app_version���б�ʶ��Ӧ������
	public App_Version setAppVersionLogoName(App_Version app_Version);

	// ��ȡ����appƽ̨
	public List<Data_Dictionary> getFlatForm();

	// ��ȡ����app״̬
	public List<Data_Dictionary> getAppStatus();

	// ��ȡ�����ֵ�ͨ��type_code
	public List<Data_Dictionary> getByTypeCode(String typecode);

}
