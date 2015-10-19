package com.changhong.common.repository;

import com.changhong.system.repository.UserDao;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午1:21
 */
public class EntityLoadHolder {

    private static UserDao userDao;

    public static UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
    	EntityLoadHolder.userDao = userDao;
    }
}
