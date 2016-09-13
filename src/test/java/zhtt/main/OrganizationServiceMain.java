package zhtt.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zhtt.service.user.OrganizationService;

/**
 * Created by zhtt on 2016/8/20.
 */
public class OrganizationServiceMain {

    private static OrganizationService organizationService=null;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                new String[] { "applicationContext-test.xml", "applicationContext-mongo-config-test.xml", "applicationContext-servlet-test.xml"});
        organizationService = (zhtt.service.user.OrganizationService) ac.getBean("organizationService");
        getCurrentRootOrg();
    }

    public static void getCurrentRootOrg(){
        organizationService.getCurrentRootOrg("00");
    }

    public static void buildOrgCode(){
        String code="000101";
        String parentId="f3af7031-60d0-49a7-aa83-a784a12e8d13";
        organizationService.buildOrgCode(code,parentId);
    }

    public static void regTest(){
        organizationService.regTest();
    }
}
