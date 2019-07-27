package cn.appsys.controller.backend;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import cn.appsys.tools.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/manager")
public class UserLoginController {
    private Logger logger = Logger.getLogger(UserLoginController.class);

    @Resource
    private BackendUserService backendUserService;

    @RequestMapping(value = "/login")
    public String login() {
        return "backendlogin";
    }

    //后台系统管理者登录
    @RequestMapping("/dologin")
    public String backendLogin(String userCode, String userPassword, HttpSession session) throws Exception {
        BackendUser backend_user = backendUserService.login(userCode, userPassword);
        if (backend_user != null) {
//            登录成功
//             放入session
            session.setAttribute(Constants.USER_SESSION, backend_user);
//            页面跳转
            return "redirect:/manager/backend/main";
        } else {
            session.setAttribute("error", "用户名或密码不正确");
            return "backendlogin";
        }
    }


    @RequestMapping(value = "/backend/main")
    public String main(HttpSession session) {
        if (session.getAttribute(Constants.USER_SESSION) == null) {
            return "redirect:/manager/login";
        }
        return "backend/main";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        //清除session
        session.removeAttribute(Constants.USER_SESSION);
        return "backendlogin";
    }
}
