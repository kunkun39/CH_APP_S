package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.ClientVersion;
import com.changhong.system.domain.MultipHost;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-11
 * Time: 下午1:39
 */
@Repository("systemDao")
public class SystemDaoImpl extends HibernateEntityObjectDao implements SystemDao {

    public ClientVersion findClientVersion() {
        return (ClientVersion) getHibernateTemplate().find("from ClientVersion c where c.id = 1").get(0);
    }

    public void saveClientVersion(ClientVersion version) {
        getHibernateTemplate().saveOrUpdate(version);
    }

    public List<MultipHost> loadAllMultipHosts() {
        return getHibernateTemplate().find("from MultipHost");
    }
}
