package com.changhong.system.web.paging;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:35
 */
public abstract class AbstractPaging<T> implements Paging<T> {

    private int currentPageNumber;

    protected int totalItemSize = -1;

    protected AbstractPaging() {
    }

    public AbstractPaging(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getPageSize() {
        return 20;
    }

    public abstract List<T> getItems();

    public abstract long getTotalItemSize();

    public int getStartPosition() {
        int totalPages = getTotalPages();
        int currentPageNumberCorrection = currentPageNumber;
        if (currentPageNumberCorrection <= 0) {
            currentPageNumberCorrection = 1;
        }
        if (currentPageNumberCorrection > totalPages) {
            currentPageNumberCorrection = totalPages;
            currentPageNumber = totalPages;
        }
        currentPageNumber = currentPageNumberCorrection;
        int pageSize = getPageSize() * (currentPageNumberCorrection - 1);
        return pageSize >= 0 ? pageSize : 0;
    }

    public int getTotalPages() {
        long itemSize = getTotalItemSize();
        int pageSize = getPageSize();
        long totalPages = itemSize / pageSize;
        if (itemSize % pageSize != 0) {
            totalPages++;
        }
        return (int) totalPages;
    }

    public boolean hasNextPage() {
        return currentPageNumber != getTotalPages() && getTotalPages() != 0;
    }

    public boolean hasPreviousPage() {
        return currentPageNumber > 1;
    }

    public int getNextPageNumber() {
        if (hasNextPage()) {
            return currentPageNumber + 1;
        } else {
            return currentPageNumber;
        }
    }

    public int getPreviousPageNumber() {
        if (hasPreviousPage()) {
            return currentPageNumber - 1;
        } else {
            return currentPageNumber;
        }
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }
}
