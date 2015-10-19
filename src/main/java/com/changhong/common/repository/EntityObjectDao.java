package com.changhong.common.repository;

import com.changhong.common.domain.EntityBase;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:23
 */
public interface EntityObjectDao {

    void saveOrUpdate(EntityBase entity);

    void persist(EntityBase entity);

    void delete(EntityBase entity);

    EntityBase findById(int id, Class clazz);

    <T extends EntityBase> List<T> findByIds(String[] ids, Class<T> clazz);

    void saveAll(List list);
}
