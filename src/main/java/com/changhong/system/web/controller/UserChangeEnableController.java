package com.changhong.system.web.controller;

import com.changhong.system.service.UserService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 上午9:01
 */
public class UserChangeEnableController extends AbstractController {

    private UserService userService;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = ServletRequestUtils.getIntParameter(request, "userId", -1);
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filername = ServletRequestUtils.getStringParameter(request, "filername", "");

        userService.changeStatusForUser(userId);

        return new ModelAndView(new RedirectView("useroverview.html?current="+current+"&filername="+filername));
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
