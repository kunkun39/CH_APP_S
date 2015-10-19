package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.Client;
import com.changhong.system.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:26
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateEntityObjectDao implements UserDao {

    public UserDetails findUserByName(String username) {
        List<User> users = getHibernateTemplate().find("from User u where u.username = ? and u.enabled = true", username);
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> loadUsers(String name, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("from User u");
        if (StringUtils.hasText(name)) {
            builder.append(" where u.name like '%" + name + "%' or u.username like '%" + name + "%'");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<User> users = query.list();
        return users;
    }

    public int loadUserSize(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(u.id) from User u");
        if (StringUtils.hasText(name)) {
            builder.append(" where u.name like '%" + name + "%' or u.username like '%" + name + "%'");
        }
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }

    public boolean loadUserExist(int userId, String username) {
        if (userId < 0) {
            List list = getHibernateTemplate().find("select count(u.id) from User u where u.username = ?", username);
            return ((Long)list.get(0)).intValue() > 0 ? true : false;
        } else {
            List list = getHibernateTemplate().find("select count(u.id) from User u where u.username = ? and u.id <> ?",
                    new Object[]{username, userId});
            return ((Long)list.get(0)).intValue() > 0 ? true : false;
        }
    }

     /***********************************************客户部分**********************************************************/

    public List<Client> loadClients(String contactName, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("from Client c");
        if (StringUtils.hasText(contactName)) {
            builder.append(" where c.macFrom like '" + contactName + "%' or c.macTo like '" + contactName + "%'");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<Client> clients = query.list();
        return clients;
    }

    public int loadClientSize(String contactName) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(c.id) from Client c");
        if (StringUtils.hasText(contactName)) {
            builder.append(" where c.macFrom like '" + contactName + "%' or c.macTo like '" + contactName + "%'");
        }
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }
}
