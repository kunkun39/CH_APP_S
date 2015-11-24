package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.ClientVersion;
import com.changhong.system.domain.MultipHost;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-11
 * Time: 下午1:39
 */
public interface SystemDao extends EntityObjectDao {

    ClientVersion findClientVersion();

    void saveClientVersion(ClientVersion version);

    List<MultipHost> loadAllMultipHosts();
}
