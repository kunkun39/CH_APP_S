package com.changhong.system.web.paging;

import com.changhong.system.service.UserService;
import com.changhong.system.web.facade.dto.ClientDTO;
import com.changhong.system.web.facade.dto.UserDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:34
 */
public class ClientOverviewPaging extends AbstractPaging<ClientDTO> {

    private UserService userService;

    private String contactName;

    public ClientOverviewPaging(UserService userService) {
        this.userService = userService;
    }

    public List<ClientDTO> getItems() {
        return userService.obtainClients(contactName, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = userService.obtainClientSize(contactName);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&filername=" + getContactName();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}

