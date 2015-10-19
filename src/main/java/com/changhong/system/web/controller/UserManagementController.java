package com.changhong.system.web.controller;

import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.paging.UserOverviewPaging;
import com.changhong.common.utils.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:30
 */
public class UserManagementController extends AbstractController {

    private UserService userService;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filername = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "filername", ""));
        request.setAttribute("current", current);
        request.setAttribute("filername", filername);

        UserOverviewPaging paging = new UserOverviewPaging(userService);
        constructPaging(paging, current, filername);
        List<UserDTO> users = paging.getItems();
        model.put("users", users);
        model.put("paging", paging);
        model.put("currentUser", SecurityUtils.currentUser());

        return new ModelAndView("backend/system/useroverview", model);
    }

    private void constructPaging(UserOverviewPaging paging, int current, String name) {
        paging.setCurrentPageNumber(current);
        paging.setName(name);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
