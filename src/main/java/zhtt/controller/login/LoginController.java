package zhtt.controller.login;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import zhtt.entity.user.Organization;
import zhtt.entity.user.User;
import zhtt.service.user.OrganizationService;
import zhtt.service.user.UserService;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhtt on 2016/8/9.
 */
@Controller
public class LoginController {

    @Autowired private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView("/login");
        return mav;
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("/index");
        return mav;
    }

    @RequestMapping("/login/{username}/{password}")
    @ResponseBody
    private JsonResponse loginCheck(@PathVariable("username")String username,@PathVariable("password")String password,HttpServletRequest request){
        try{
            User user=userService.login(username,password);


            String orgId=user.getOrgId();
            Organization organization=organizationService.getByUuid(orgId);
            request.getSession().setAttribute("loginUser",user);
            request.getSession().setAttribute("loginRootOrganization",getloginRootOrganization(organization));
            request.getSession().setAttribute("loginOrganization",organization);
            return JsonResponse.success(user);
        }catch (Exception e){
            return JsonResponse.error(e.getMessage());
        }
    }

    /**
     * 获取代表当前机构的最高机构
     * @param organization
     * @return
     */
    private Organization getloginRootOrganization(Organization organization){
        Organization loginRootOrganization=null;
        if(organization.getOrgType().equals(Organization.ROOT)||organization.getOrgType().equals(Organization.ORG)){
            loginRootOrganization=organization;
        }else if(organization.getOrgType().equals(Organization.HEADQUARTERS)){
            loginRootOrganization=organizationService.getByUuid(organization.getParentId());
        }else if(organization.getOrgType().equals(Organization.DEPT)){
            BasicDBObject orgDBObj =organizationService.getCurrentRootOrg(organization.getCode());
            if(orgDBObj==null){
                return null;
            }
            loginRootOrganization=organizationService.getByUuid(orgDBObj.getString("rootParentUuid"));
        }
        return loginRootOrganization;
    }
}
