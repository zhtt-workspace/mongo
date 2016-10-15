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
public class UserFormController {

    @Autowired
    private UserService userService;

    @RequestMapping("/create-form")
    @ResponseBody
    public JsonResponse save(User user){
        try{
            userService.saveUser(user);
            return JsonResponse.success(user);
        }catch (Exception e){
            return  JsonResponse.error(e.getMessage());
        }
    }

    @RequestMapping("/update-form")
    @ResponseBody
    public JsonResponse update(User user){
        try{
            userService.update(user);
            return  JsonResponse.success(user);
        }catch (Exception e){
            return JsonResponse.error(e.getMessage());
        }
    }
}
