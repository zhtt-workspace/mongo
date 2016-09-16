package zhtt.main;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zhtt.service.template.DataStatisticsTemplateFormService;
import zhtt.service.template.DataStatisticsTemplateService;
import zhtt.service.user.OrganizationService;

/**
 * Created by zhtt on 2016/9/12.
 */
public class DataStatisticsTemplateServiceMain {
    private static DataStatisticsTemplateService dataStatisticsTemplateService = null;
    private static DataStatisticsTemplateFormService dataStatisticsTemplateFormService = null;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                new String[]{"applicationContext-test.xml", "applicationContext-mongo-config-test.xml", "applicationContext-servlet-test.xml"});
        dataStatisticsTemplateService = (DataStatisticsTemplateService) ac.getBean("dataStatisticsTemplateService");
        dataStatisticsTemplateFormService = (DataStatisticsTemplateFormService) ac.getBean("dataStatisticsTemplateFormService");
        upOrDown();
    }

    public static void upOrDown(){
        String orgId="74fade7353954b899a37ba137df227d2";
        String uuid="ad3d2c8c207249209939218513b03e8a";
        dataStatisticsTemplateFormService.upOrDown(orgId,uuid,1);
    }

    public static void update(){
        BasicDBObject query=new BasicDBObject();
        query.put("uuid","bfc8eee75eb14315a9c5e09ba4ddc291");
        BasicDBObject update=new BasicDBObject();
        update.put("uuid","bfc8eee75eb14315a9c5e09ba4ddc291");
        update.put("name","英国蔬菜");
        update.put("type","group");
        update.put("parentId","68f44383cc9a4cb0932fad770592b4b0");
        WriteResult wr=dataStatisticsTemplateFormService.update(query,new BasicDBObject("$set",update),false,true);
        /**
         * insert:false
         { "serverUsed" : "/10.0.0.196:27017" , "updatedExisting" : true , "n" : 1 , "connectionId" : 989 , "err" :  null  , "ok" : 1.0}
         { "serverUsed" : "/10.0.0.196:27017" , "updatedExisting" : true , "n" : 1 , "connectionId" : 989 , "err" :  null  , "ok" : 1.0}
         { "serverUsed" : "/10.0.0.196:27017" , "updatedExisting" : true , "n" : 1 , "connectionId" : 989 , "err" :  null  , "ok" : 1.0}
         insert :true
         { "serverUsed" : "/10.0.0.196:27017" , "updatedExisting" : true , "n" : 1 , "connectionId" : 990 , "err" :  null  , "ok" : 1.0}
         { "serverUsed" : "/10.0.0.196:27017" , "updatedExisting" : true , "n" : 1 , "connectionId" : 990 , "err" :  null  , "ok" : 1.0}
         { "serverUsed" : "/10.0.0.196:27017" , "updatedExisting" : true , "n" : 1 , "connectionId" : 990 , "err" :  null  , "ok" : 1.0}
         */
        System.out.println(wr);
        System.out.println(wr);
        System.out.println(wr);
    }
}