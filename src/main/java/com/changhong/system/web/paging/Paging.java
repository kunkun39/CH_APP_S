package com.changhong.system.web.paging;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:35
 */
public interface Paging<T> {

    int getCurrentPageNumber();

    int getPageSize();

    List<T> getItems();

    long getTotalItemSize();

    boolean hasNextPage();

    boolean hasPreviousPage();

    int getNextPageNumber();

    int getPreviousPageNumber();

    int getTotalPages();

    String getParameterValues();
}
