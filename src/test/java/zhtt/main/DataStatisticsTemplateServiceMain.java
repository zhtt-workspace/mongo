package zhtt.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zhtt.service.template.DataStatisticsTemplateService;
import zhtt.service.user.OrganizationService;

/**
 * Created by zhtt on 2016/9/12.
 */
public class DataStatisticsTemplateServiceMain {
    private static DataStatisticsTemplateService dataStatisticsTemplateService=null;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                new String[] { "applicationContext-test.xml", "applicationContext-mongo-config-test.xml", "applicationContext-servlet-test.xml"});
        dataStatisticsTemplateService = (DataStatisticsTemplateService) ac.getBean("dataStatisticsTemplateService");

    }


}
