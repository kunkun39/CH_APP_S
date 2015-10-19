package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.Role;
import com.changhong.system.domain.User;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.facade.dto.UserPasswordDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:58
 */
public class UserWebAssember {

    public static User toUserDomain(UserDTO userDTO) {
        User user = null;
        if(userDTO == null) return null;

        if (userDTO.getId() > 0) {
            user = (User) EntityLoadHolder.getUserDao().findById(userDTO.getId(), User.class);
            user.setId(userDTO.getId());
            user.setName(userDTO.getName());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setContactWay(userDTO.getContactWay());
        } else {
            user = new User(userDTO.getName(), userDTO.getContactWay(), userDTO.getUsername(), userDTO.getPassword());
        }

        List<String> roles = userDTO.getRoles();
        if (roles != null) {
            if (user.getRoles() != null) {
                user.getRoles().clear();
            }
            for (String role : roles) {
                user.grantRole(role);
            }
        }

        return user;
    }

    public static UserDTO toUserDTO(User user) {
        final int id = user.getId();
        final String name = user.getName();
        final String contactWay = user.getContactWay();
        final String username = user.getUsername();
        final String password = user.getPassword();
        final boolean enabled = user.isEnabled();

        UserDTO dto =  new UserDTO(id, name, contactWay, username, password, enabled);
        List<String> roles = new ArrayList<String>();
        if(user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                roles.add(role.getAuthority());
            }
        }
        dto.setRoles(roles);

        return dto;
    }

    public static List<UserDTO> toUserDTOList(List<User> users) {
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        if (users != null) {
            for (User user : users) {
                dtos.add(toUserDTO(user));
            }
        }
        return dtos;
    }

    public static UserPasswordDTO toPasswordDTO(User user) {
        final int userId = user.getId();
        final String name = user.getName();
        final String userName = user.getName();
        return new UserPasswordDTO(userId, name, userName);
    }
}
