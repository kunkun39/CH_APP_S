package com.changhong.system.web.controller;

import com.changhong.common.utils.SecurityUtils;
import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.UserPasswordDTO;
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
 * Date: 14-4-10
 * Time: 上午9:12
 */
public class UserChangePasswordController extends SimpleFormController {

    private UserService userService;

    public UserChangePasswordController() {
        setCommandClass(UserPasswordDTO.class);
        setCommandName("userPassword");
        setFormView("backend/system/userchangepasswordform");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String changed = ServletRequestUtils.getStringParameter(request, "changed", "no");
        request.setAttribute("changed", changed);

        int currentUserId = SecurityUtils.currectUserId();
        return userService.obtainUserPassword(currentUserId);
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        int userId = ServletRequestUtils.getIntParameter(request, "userId", -1);
        String oldPassword = ServletRequestUtils.getStringParameter(request, "oldPassword", "");
        if (!StringUtils.hasText(oldPassword)) {
            errors.rejectValue("oldPassword", "user.oldpassword.empty");
        } else {
            boolean oldPasswordRight = userService.obtainOldPasswordRight(userId, oldPassword);
            if (!oldPasswordRight) {
                errors.rejectValue("oldPassword", "user.oldpassword.notright");
            }
        }

        if (errors.getFieldError("newPasswordAgain") == null) {
            String newPassword = ServletRequestUtils.getStringParameter(request, "newPassword", "");
            String newPasswordAgain = ServletRequestUtils.getStringParameter(request, "newPasswordAgain", "");
            if (!StringUtils.hasText(newPassword) || !StringUtils.hasText(newPasswordAgain)) {
                errors.rejectValue("newPasswordAgain", "user.password.empty");
            } else {
                if (!newPassword.equals(newPasswordAgain)) {
                    errors.rejectValue("newPasswordAgain", "user.password.notsame");
                }
            }
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        UserPasswordDTO userPasswordDTO = (UserPasswordDTO) command;
        userService.changeUserPassword(userPasswordDTO.getUserId(), userPasswordDTO.getNewPassword());

        return new ModelAndView(new RedirectView("dashboard.html"));
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
