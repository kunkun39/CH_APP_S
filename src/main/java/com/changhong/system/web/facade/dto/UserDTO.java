package com.changhong.system.web.facade.dto;

import com.changhong.common.utils.CHStringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:37
 */
public class UserDTO implements Serializable {
    private int id = -1;
    private String name;
    private String contactWay;

    private String username;
    private String password;
    private boolean enabled;

    private List<String> roles;

    public UserDTO() {
        this.password = CHStringUtils.getRandomString(10);
    }

    public UserDTO(int id, String name, String contactWay, String username, String password, boolean enabled) {
        this.id = id;
        this.name = name;
        this.contactWay = contactWay;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public void grantRole(String role) {
        if (roles == null) {
            roles = new ArrayList<String>();
        }
        roles.add(role);
    }

    public boolean getHasRoleAdmin() {
        return hasSpecicalRole("ROLE_ADMIN");
    }

    public boolean getHasRoleAppInfo() {
        return hasSpecicalRole("ROLE_APP_INFO");
    }

    public boolean getHasRoleAppStatistic() {
        return hasSpecicalRole("ROLE_APP_STATISTIC");
    }

    public boolean getHasRoleAppStatus() {
        return hasSpecicalRole("ROLE_APP_STATUS");
    }

    public boolean getHasRoleAppRecommend() {
        return hasSpecicalRole("ROLE_APP_RECOMMEND");
    }

    public boolean hasSpecicalRole(String specicalRole) {
        if (roles == null) {
            return false;
        }
        for (String role : roles) {
            if (role.equals(specicalRole)) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
