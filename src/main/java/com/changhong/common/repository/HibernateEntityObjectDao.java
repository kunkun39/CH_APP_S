package com.changhong.common.repository;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.utils.CHStringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:24
 */
public class HibernateEntityObjectDao extends HibernateDaoSupport implements EntityObjectDao {

    @Autowired
    public final void setEntityManagerFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public void saveOrUpdate(EntityBase entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void persist(EntityBase entity) {
        getHibernateTemplate().persist(entity);
        getHibernateTemplate().flush();
    }

    public void delete(EntityBase entity) {
        getHibernateTemplate().delete(entity);
        getHibernateTemplate().flush();
    }

    @SuppressWarnings("unchecked")
	public EntityBase findById(int id, Class clazz) {
        return (EntityBase)getHibernateTemplate().get(clazz.getName(), id);
    }

    public <T extends EntityBase> List<T> findByIds(String[] ids, Class<T> clazz) {
        if (ids.length > 0) {
            return getHibernateTemplate().find("from " + clazz.getName() + " do where do.id in (" + CHStringUtils.convertListToSQLIn(ids) + ")");
        }
        return new ArrayList<T>();
    }

    public void saveAll(List list) {
        getHibernateTemplate().saveOrUpdateAll(list);
    }
}
