package zhtt.controller.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import zhtt.entity.user.Organization;
import zhtt.service.user.OrganizationService;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;
import zhtt.util.JsonTableResponse;

import java.util.List;

/**
 * Created by zhtt on 2016/8/11.
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @RequestMapping("/tree")
    @ResponseBody
    public List<Organization> root(@RequestParam(value = "parentId",required = false)String parentId){
        return service.getByParentId(parentId);
    }

    @RequestMapping("/query")
    @ResponseBody
    public JsonTableResponse query(String code,String parentId,String name,Integer limit,Integer offset) {
        limit = limit == null ? 2 : limit;
        offset = offset == null ? 0 : offset;
        List<Organization> orgList=service.query(code,parentId,name,limit,offset);
        long total=service.count(code,parentId,name);
        return new JsonTableResponse(total,orgList);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResponse delete(@RequestBody List<String> uuid){
        try{
            service.delete(uuid);
            return new JsonResponse(uuid);
        }catch (Exception e){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }
}
