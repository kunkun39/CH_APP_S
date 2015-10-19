package com.changhong.system.repository;

import com.changhong.system.domain.User;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * User: Jack Wang
 * Date: 14-11-20
 * Time: 上午8:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class UserDaoImplTest extends TestCase {

    @Resource
    SessionFactory sessionFactory;

    @Resource
    UserDaoImpl userDao;

    HibernateTemplate hibernateTemplate;

    @Before
    public void setUp() {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @After
    public void tearDown() {
        hibernateTemplate = null;
    }




    @Test
    public void testFindUserByName() {
        UserDetails user = userDao.findUserByName("jack");
        assertNull(user);

        User saveUser = new User("jack", "128", "jackwang", "password");
        hibernateTemplate.saveOrUpdate(saveUser);

        user = userDao.findUserByName("jackwang");
        assertNotNull(user);
        assertEquals(user.getUsername(), "jackwang");
        assertEquals(user.getPassword(), "password");

        saveUser.setEnabled(false);
        hibernateTemplate.saveOrUpdate(saveUser);
        user = userDao.findUserByName("jackwang");
        assertNull(user);

    }


    @Test
    public void testSaveTestUser() {
        //1 - get json string by web utils

        //2 - parse json to domain

        //3 - create table, create hbm file

        //4 - save test user
    }
}
