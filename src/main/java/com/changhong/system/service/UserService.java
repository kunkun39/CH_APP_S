package com.changhong.system.service;

import com.changhong.system.web.facade.dto.ClientDTO;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.facade.dto.UserPasswordDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:20
 */
public interface UserService extends UserDetailsService {

    UserDTO obtainUserById(int userId);

    List<UserDTO> obtainUsers(String name, int startPosition, int pageSize);

    int obtainUserSize(String name);

    boolean obtainUserExist(int userId, String username);

    void changeUserDetails(UserDTO userDTO);

    void changeStatusForUser(int userId);

    UserPasswordDTO obtainUserPassword(int userId);

    boolean obtainOldPasswordRight(int userId, String oldPassword);

    void changeUserPassword(int userId, String newPassword);

    /***********************************************客户部分**********************************************************/

    int obtainClientSize(String contactName);

    List<ClientDTO> obtainClients(String contactName, int startPosition, int pageSize);

    ClientDTO obtainClientById(int clientId);

    void changeClientDetails(ClientDTO clientDTO);

    void saveImportClients(String clientFrom, String clientTo);
}
