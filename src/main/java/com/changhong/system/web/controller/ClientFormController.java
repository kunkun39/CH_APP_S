package com.changhong.system.web.controller;

import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.ClientDTO;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午11:42
 */
public class ClientFormController extends SimpleFormController {

    private UserService userService;

    public ClientFormController() {
        setCommandClass(ClientDTO.class);
        setCommandName("client");
        setFormView("/backend/system/clientform");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        int clientId = ServletRequestUtils.getIntParameter(request, "clientId", -1);
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filername = ServletRequestUtils.getStringParameter(request, "filername", "");
        request.setAttribute("current", current);
        request.setAttribute("filername", filername);

        if (clientId > 0) {
            return userService.obtainClientById(clientId);
        }
        return new ClientDTO();
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        String macFrom = ServletRequestUtils.getStringParameter(request, "macFrom", "");
        if (!StringUtils.hasText(macFrom)) {
            errors.rejectValue("macFrom", "client.mac.empty");
        } else {
            String[] tokens = StringUtils.delimitedListToStringArray(macFrom, ":");
            if (tokens.length != 6) {
                errors.rejectValue("macFrom", "client.mac.notright");
            }
        }

        String macTo = ServletRequestUtils.getStringParameter(request, "macTo", "");
        if (!StringUtils.hasText(macFrom)) {
            errors.rejectValue("macTo", "client.mac.empty");
        } else {
            String[] tokens = StringUtils.delimitedListToStringArray(macTo, ":");
            if (tokens.length != 6) {
                errors.rejectValue("macTo", "client.mac.notright");
            }
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String filername = ServletRequestUtils.getStringParameter(request, "filername", "");

        ClientDTO clientDTO = (ClientDTO) command;
        userService.changeClientDetails(clientDTO);

        return new ModelAndView(new RedirectView("clientoverview.html?current="+current+"&filername="+filername));
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
