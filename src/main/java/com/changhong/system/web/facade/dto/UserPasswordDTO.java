package com.changhong.system.web.facade.dto;

import java.io.Serializable;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 上午9:12
 */
public class UserPasswordDTO implements Serializable {
    private int userId;
    private String name;
    private String username;
    private String oldPassword;
    private String newPassword;
    private String newPasswordAgain;

    public UserPasswordDTO() {
    }

    public UserPasswordDTO(int userId, String name, String username) {
        this.userId = userId;
        this.name = name;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }
}

