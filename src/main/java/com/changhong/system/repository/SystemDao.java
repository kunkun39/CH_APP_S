package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.ClientVersion;

/**
 * User: Jack Wang
 * Date: 15-9-11
 * Time: 下午1:39
 */
public interface SystemDao extends EntityObjectDao {

    ClientVersion findClientVersion();

    void saveClientVersion(ClientVersion version);
}
