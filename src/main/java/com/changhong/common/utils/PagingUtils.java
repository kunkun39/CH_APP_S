package com.changhong.common.utils;

/**
 * User: Jack Wang
 * Date: 14-11-20
 * Time: 下午5:01
 */
public class PagingUtils {

    private int currentPage  = 1;
    /**
     * 分页每一页的大小
     */
    public static int pageItems = 10;
    /**
     * 总共的数据
     */
    private int numItems = 1;

    public PagingUtils(int numItems) {
		setNumItems(numItems);
	}

    public int getPageItems(){
        return pageItems;
    }
    public int getStartPosition(){
        return (currentPage - 1) * pageItems;
    }

	public void setNumItems(int numItems) {
		if (numItems >= 0) this.numItems = numItems;
	}

	private void setCurrentPage(int currentPage) {
		final int numPages = getNumPages();
		if (currentPage > numPages) currentPage = numPages;
		if (currentPage < 1) currentPage = 1;
	  this.currentPage = currentPage;
  }

	public void setCurrentPage(String currentPage) {
		try {
			setCurrentPage(Integer.parseInt(currentPage));
		} catch (NumberFormatException e) {
			setCurrentPage(0);
		}
	}

	private void setMaxItems(int maxItems) {
		if (maxItems > 0) this.pageItems = maxItems;
	}

	public int getBegin() {
		return (currentPage - 1) * pageItems;
	}

	public int getEnd() {
		int end = currentPage * pageItems;
		return end > numItems ? numItems : end;
	}

	public int getNumItems() {
		return numItems;
	}

	public int getNumPages() {
		int num = numItems / pageItems;
		if (numItems % pageItems > 0) num++;
		return num;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getNextPage() {
		int next = currentPage + 1;
		if (next > getNumPages()) return -1;
		return currentPage + 1;
	}

	public int getPrevPage() {
		final int prev = currentPage - 1;
		if (prev < 1) return -1;
		return prev;
	}
}
