package zhtt.service;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhtt.entity.user.Organization;
import zhtt.entity.user.User;
import zhtt.service.user.OrganizationService;
import zhtt.service.user.UserService;
import zhtt.util.FileUtil;

import java.util.List;

/**
 * Created by zhtt on 2016/8/29.
 */
@Service(value = "initService")
public class InitService {

    @Autowired private OrganizationService organizationService;

    @Autowired private UserService userService;

    public void init(){
        organization();
    }

    private void organization(){
        List<BasicDBObject> org=FileUtil.readBasicDBObjectsFrom("/init-data/organization.json");
        if(organizationService.count(".","",".")==0){
            Organization organization=new Organization();
            organization.setName(org.get(0).getString("orgName"));
            organization.setFullName(org.get(0).getString("orgName"));
            organization.setCode("00");
            organization.setOrgType("root");
            organization.setParentId("");
            organizationService.save(organization);
            user(organization.getUuid(),org.get(0));
        }
    }

    private void user(String orgId,BasicDBObject obj){
        User user=new User();
        user.setName(obj.getString("username"));
        user.setUsername(obj.getString("username"));
        user.setPassword(obj.getString("password"));
        user.setOrgId(orgId);
        userService.saveUser(user);
    }
}
