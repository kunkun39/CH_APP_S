package com.changhong.client.dao;

/**
 * User: Jack Wang
 * Date: 15-3-1
 * Time: 下午2:54
 */
public interface IbatisMovieDao {

    /**
     * 获得所有的栏目,可能这个接口会改成推荐栏目,就是根据不同的页面，推荐不同的栏目
     */
    String obtainColumns();
}
