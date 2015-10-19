package com.changhong.client.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * User: Jack Wang
 * Date: 15-3-1
 * Time: 下午3:04
 */
public class IbatisEntityObjectDao extends SqlMapClientDaoSupport {

    @Resource(name = "sqlMapClient")
    protected SqlMapClient sqlMapClient;

    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }


}
