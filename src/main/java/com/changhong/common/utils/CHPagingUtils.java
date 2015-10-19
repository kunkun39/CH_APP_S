/*
 * This class is used for make a page in the old code for 3i such as retailer overview and retailerIdentification
 * and invoice overview.
 * <p>
 *
 * let me take a list as an example.
 * conditions:
 * 1 - every page has 25 max item [DEFAULT_OVERVIEW_MAX_ITEMS]
 * 2 - the total number is 101 items [List<Invoice> invoices = invoiceService.getAllInvoices()]
 * 3 - current page is 0 [request.getparameter("page)]
 *
 * PagingTool pagingTool = new PagingTool(invoices.size(), request.getparameter("page), DEFAULT_OVERVIEW_MAX_ITEMS);
 *
 * in this way we make a pagingTool and how invoices.subList(paging.getBegin(), paging.getEnd()) on the page
 *
 */
package com.changhong.common.utils;

public class CHPagingUtils {

    /**
     * this parameter is used for check current is in which page,
     * the first page's index equal to 0 not 1
     */
    private int currentPage  = 1;
    /**
     * This parameter is used for check every page has how many items
     */
    private int maxItems = 100;
    /**
     * This parameter is used for check how many items in total list
     */
    private int numItems = 1;


    public int getMaxItems(){
        return maxItems;
    }
    public int getStartPosition(){
        return (currentPage - 1) * maxItems;
    }
    public CHPagingUtils(int numItems) {
		setNumItems(numItems);
	}

	public CHPagingUtils(int numItems, int currentPage) {
		setNumItems(numItems);
		setCurrentPage(currentPage);
	}

	public CHPagingUtils(int numItems, int currentPage, int maxItems) {
		setNumItems(numItems);
		setMaxItems(maxItems);
		setCurrentPage(currentPage);
	}

	public CHPagingUtils(int numItems, String currentPage, int maxItems) {
		setNumItems(numItems);
		setMaxItems(maxItems);
		setCurrentPage(currentPage);
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
		if (maxItems > 0) this.maxItems = maxItems;
	}

	/**
	 * @return index of the first item
	 */
	public int getBegin() {
		return (currentPage - 1) * maxItems;
	}

	/**
	 * @return index of the last item + 1, or 0 if numItems = 0
	 */
	public int getEnd() {
		int end = currentPage * maxItems;
		return end > numItems ? numItems : end;
	}

	public int getNumItems() {
		return numItems;
	}
	
	public int getNumPages() {
		int num = numItems / maxItems;
		if (numItems % maxItems > 0) num++;
		return num;
	}
	
	/**
	 * @return number of the current page (first page = 1)
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * @return number of the next page, or -1 if no next page available (first page = 1)
	 */
	public int getNextPage() {
		int next = currentPage + 1;
		if (next > getNumPages()) return -1;
		return currentPage + 1;
	}
	
	/**
	 * @return number of the previous page, or -1 if the current page is the first page (first page = 1)
	 */
	public int getPrevPage() {
		final int prev = currentPage - 1;
		if (prev < 1) return -1;
		return prev;
	}
}
