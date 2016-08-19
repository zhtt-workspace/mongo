package zhtt.controller.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import zhtt.entity.user.Organization;
import zhtt.service.user.OrganizationService;
import zhtt.util.JsonResponse;

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
}
