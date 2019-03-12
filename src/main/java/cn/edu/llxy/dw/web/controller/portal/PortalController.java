package cn.edu.llxy.dw.web.controller.portal;

import cn.edu.llxy.dw.core.bean.User;
import cn.edu.llxy.dw.service.portal.UserService;
import cn.edu.llxy.dw.service.portal.impl.UserServiceImpl;
import cn.edu.llxy.dw.web.base.mvc.BaseContorller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class PortalController extends BaseContorller {

    @Autowired
    private UserService userService;

    /**
     * 系统入口，跳转到登录页
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        ModelAndView model = new ModelAndView("portal/login");
        ModelAndView model = new ModelAndView("dw/home/index");
        model.addObject("message", "login");
        return model;
    }

    /**
     * 元数据管理
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView meta(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView("dw/home/manager");
        return model;
    }

    /**
     * 系统管理
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView system(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView("dw/home/system");
        return model;
    }

    /**
     * etl
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView etl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView("dw/home/etl");
        return model;
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        User user = userService.userLogin(username, password);
        ModelAndView model = new ModelAndView();
        if(user!=null){
            model.setViewName("dw/home/index");
            model.addObject("msg", "登录成功");
        }else{
            model.setViewName("portal/login");
            model.addObject("msg", "登录失败");
        }
        return model;
    }
}
