package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cloud.pojo.App_Info;
import com.cloud.service.BkdService;
import com.cloud.service.DataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class test_sql_dao {

	@Autowired
	BkdService bkdService;
	@Autowired
	DataService parseService;

	@Test
	public void test() {
		// System.out.println("-----------------------");
		App_Info app_Info = new App_Info();
		// for (App_Info app : bkdService.getSectionMeetingPendingApp(app_Info, 0)) {
		// System.out.println(app.getSoftwarename());
		// }
		// System.out.println("-----------------------");
		// System.out.println(bkdService.getAllPendingApp().size());
		// System.out.println("-----------------------");
		// System.out.println(bkdService.getMeetingCountPendingApp(app_Info));
		// System.out.println("-----------------------");
		// 3 2 19 38
		app_Info.setFlatformid(3L);
		app_Info.setCategorylevel1(2L);
		app_Info.setCategorylevel2(19L);
		app_Info.setCategorylevel3(38L);
		System.out.println(parseService.setAppInfoLogoName(app_Info).getFlatformname());
		System.out.println(parseService.setAppInfoLogoName(app_Info).getStatusname());
		System.out.println(parseService.setAppInfoLogoName(app_Info).getCategorylevel1name());
		System.out.println(parseService.setAppInfoLogoName(app_Info).getCategorylevel2name());
		System.out.println(parseService.setAppInfoLogoName(app_Info).getCategorylevel3name());
	}
}
