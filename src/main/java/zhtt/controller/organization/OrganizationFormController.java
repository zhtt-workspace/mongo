package zhtt.controller.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhtt.entity.user.Organization;
import zhtt.service.user.OrganizationService;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

/**
 * Created by zhtt on 2016/8/13.
 */
@Controller
@RequestMapping("/organization")
public class OrganizationFormController {

    @Autowired
    private OrganizationService service;

    @RequestMapping("/create-form")
    @ResponseBody
    public JsonResponse create(Organization organization){
        try{
            return service.save(organization);
        }catch (Exception e){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }

    @RequestMapping("/update-form")
    @ResponseBody
    public JsonResponse update(Organization organization){
        try{
            return service.save(organization);
        }catch (Exception e){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }
}
