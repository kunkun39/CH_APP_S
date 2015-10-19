package com.changhong.system.web.controller;

import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.ClientDTO;
import com.changhong.system.web.paging.ClientOverviewPaging;
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
public class ClientManagementController extends AbstractController {

    private UserService userService;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String filername = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "filername", ""));
        request.setAttribute("current", current);
        request.setAttribute("filername", filername);

        ClientOverviewPaging paging = new ClientOverviewPaging(userService);
        constructPaging(paging, current, filername);
        List<ClientDTO> clients = paging.getItems();
        model.put("clients", clients);
        model.put("paging", paging);

        return new ModelAndView("backend/system/clientoverview", model);
    }

    private void constructPaging(ClientOverviewPaging paging, int current, String contactName) {
        paging.setCurrentPageNumber(current);
        paging.setContactName(contactName);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
