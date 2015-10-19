package com.changhong.system.service;

import com.changhong.system.domain.Client;
import com.changhong.system.domain.User;
import com.changhong.system.repository.UserDao;
import com.changhong.system.web.facade.dto.ClientDTO;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.facade.assember.*;
import com.changhong.system.web.facade.dto.UserPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:20
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return userDao.findUserByName(username);
    }

    public UserDTO obtainUserById(int userId) {
        User user = (User) userDao.findById(userId, User.class);
        return UserWebAssember.toUserDTO(user);
    }

    public List<UserDTO> obtainUsers(String name, int startPosition, int pageSize) {
        List<User> users = userDao.loadUsers(name, startPosition, pageSize);
        return UserWebAssember.toUserDTOList(users);
    }

    public int obtainUserSize(String name) {
        return userDao.loadUserSize(name);
    }

    public boolean obtainUserExist(int id, String username) {
        return userDao.loadUserExist(id, username);
    }

    public void changeUserDetails(UserDTO userDTO) {
        User user = UserWebAssember.toUserDomain(userDTO);
        userDao.persist(user);
    }

    public void changeStatusForUser(int userId) {
        User user = (User) userDao.findById(userId, User.class);
        if (user.isEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
    }

    public UserPasswordDTO obtainUserPassword(int userId) {
        User user = (User) userDao.findById(userId, User.class);
        return UserWebAssember.toPasswordDTO(user);
    }

    public boolean obtainOldPasswordRight(int userId, String oldPassword) {
        User user = (User) userDao.findById(userId, User.class);
        return user.getPassword().equals(oldPassword) ? true : false;
    }

    public void changeUserPassword(int userId, String newPassword) {
        User user = (User) userDao.findById(userId, User.class);
        user.setPassword(newPassword);
    }

    /***********************************************客户部分**********************************************************/

    public List<ClientDTO> obtainClients(String contactName, int startPosition, int pageSize) {
        List<Client> clients = userDao.loadClients(contactName, startPosition, pageSize);
        return ClientWebAssember.toClientDTOList(clients);
    }

    public int obtainClientSize(String contactName) {
        return userDao.loadClientSize(contactName);
    }

    public ClientDTO obtainClientById(int clientId) {
        Client client = (Client) userDao.findById(clientId, Client.class);
        return ClientWebAssember.toClientDTO(client);
    }

    public void changeClientDetails(ClientDTO clientDTO) {
        Client client = ClientWebAssember.toClientDomain(clientDTO);
        userDao.saveOrUpdate(client);
    }

    public void saveImportClients(String clientFrom, String clientTo) {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
