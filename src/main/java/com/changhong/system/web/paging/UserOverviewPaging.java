package com.changhong.system.web.paging;

import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.service.UserService;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:34
 */
public class UserOverviewPaging extends AbstractPaging<UserDTO> {

    private UserService userService;

    private String name;

    public UserOverviewPaging(UserService userService) {
        this.userService = userService;
    }

    public List<UserDTO> getItems() {
        return userService.obtainUsers(name, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = userService.obtainUserSize(name);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&filername=" + getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

