package zhtt.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhtt.entity.user.User;
import zhtt.service.user.UserService;
import zhtt.util.JsonResponse;
import zhtt.util.JsonResponseStatusEnum;

/**
 * Created by zhtt on 2016/8/6.
 */
@Controller
@RequestMapping("/user")
public class CreateUpdateUserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/create")
    public String toCreateJsp(){
        return "/user/create";
    }

    @RequestMapping("/create-form")
    @ResponseBody
    public JsonResponse save(User user){
        try{
            userService.saveUser(user);
            return new JsonResponse();
        }catch (Exception e){
            return new JsonResponse(JsonResponseStatusEnum.ERROR,e.getMessage());
        }
    }
}
