package com.changhong.common.web.tag;

import com.changhong.system.web.paging.Paging;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午11:35
 */
public class PagingTag extends TagSupport {

    private Paging paging;
    private String urlMapping;
    private String first = "第一页";
    private String last = "后一页";
    private String previous = "前一页";
    private String next = "下一页";
    private String goToSpecificPage = "OK";
    private String currentPageNumberParameter = "current";
    private String delimiter = "&nbsp;&nbsp;&nbsp;";
    private boolean showPageInformation = Boolean.TRUE;
    private boolean showFirstPageLink = Boolean.TRUE;
    private boolean showLastPageLink = Boolean.TRUE;
    private boolean showGoTo = Boolean.TRUE;
    private String customInputBoxClass = "goto";
    private String function = "";

    @Override
    public int doStartTag() throws JspException {
        String pagingLinks;
        if (paging == null || paging.getTotalItemSize() <= 0) {
            pagingLinks = "";
        } else {
            pagingLinks = createPaingLinks();
        }
        try {
            writeMessage(pagingLinks);
        } catch (IOException e) {

        }
        return EVAL_BODY_INCLUDE;
    }

    private String createPaingLinks() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(showFirstPageLink ? createFirstPageLink() : "");
        buffer.append(createPreviousPageLink());
        buffer.append(createNextPageLink());
        buffer.append(showLastPageLink ? createLastPageLink() : "");
        buffer.append(showPageInformation ? createPageInformation() : "");
        buffer.append(showGoTo ? createGoToSpecificPageLink() : "");

        return buffer.toString();
    }

    private String createGoToSpecificPageLink() {
        String goToSpecificPageLink = "<input id=\"specificPageNumber\" type=\"text\" value=\"" + paging.getCurrentPageNumber() + "\"/>" + delimiter + "<a href=\"javascript:void(0);\" onclick=\"" + createGotoClick() + "\" >" + goToSpecificPage + "</a>";
        return goToSpecificPageLink;
    }

    private String createLastPageLink() {
        String lastLink = "<a style=\"color:red;\" href=\"" + createHref(paging.getTotalPages()) + "\"" + createLinkClick(paging.getTotalPages()) + " >" + last + "</a>";
        return lastLink + delimiter;
    }

    private String createFirstPageLink() {
        String firstLink = "<a style=\"color:red;\" href=\"" + createHref(1) + "\"" + createLinkClick(1) + " >" + first + "</a>";
        return firstLink + delimiter;
    }

    private String createPageInformation() {
        return delimiter + "(" + paging.getCurrentPageNumber() + " / " + paging.getTotalPages() + ")" + delimiter;
    }

    private String createNextPageLink() {
        if (paging.hasNextPage()) {
            return "<a style=\"color:red;\" href=\"" + createHref(paging.getNextPageNumber()) + "\"" + createLinkClick(paging.getNextPageNumber()) + " >" + next + "</a>" + delimiter;
        } else {
            return next + delimiter;
        }
    }

    private String createPreviousPageLink() {
        String prevLink = previous;
        if (paging.hasPreviousPage()) {
            prevLink = "<a style=\"color:red;\" href=\"" + createHref(paging.getPreviousPageNumber()) + "\"" + createLinkClick(paging.getPreviousPageNumber()) + " >" + previous + "</a>";
        }
        return prevLink + delimiter;
    }

    private String createGotoClick() {
        if (StringUtils.hasText(function)) {
            return function + "('" + urlMapping + getCurrentPageNumberParameter() + "'+document.getElementById('specificPageNumber').value);";
        } else {
            String defaultAction = "this.href='" + urlMapping + getCurrentPageNumberParameter() + "'+document.getElementById('specificPageNumber').value;";
            String errorhandleAction = "this.href='" + urlMapping + getCurrentPageNumberParameter() + 1 + "'";
            return "if(document.getElementById('specificPageNumber').value.match(/\\D+/) != null || " +
                    " document.getElementById('specificPageNumber').value == '' || " +
                    "document.getElementById('specificPageNumber').value == 0 || " +
                    "document.getElementById('specificPageNumber').value > " + paging.getTotalPages() + "){" + errorhandleAction + " ;return ;}"
                    + defaultAction;
        }
    }

    private String createUrl(int pageNumber) {
        return urlMapping + getCurrentPageNumberParameter() + pageNumber + getParameterValues();
    }

    private String createHref(int pageNumber) {
        return StringUtils.hasText(function) ? "javascript:void(0);" : createUrl(pageNumber);
    }

    private String createLinkClick(int pageNumber) {
        return StringUtils.hasText(function) ? " onclick=\"" + function + "('" + createUrl(pageNumber) + "');\"" : "";
    }

    private String getCurrentPageNumberParameter() {
        if (urlMapping.contains("?")) {
            return "&" + currentPageNumberParameter + "=";
        } else {
            return "?" + currentPageNumberParameter + "=";
        }
    }

    public String getParameterValues() {
        return  paging.getParameterValues();
    }

    protected void writeMessage(String urlInfo) throws IOException {
        pageContext.getOut().write(urlInfo);
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public void setUrlMapping(String urlMapping) {
        this.urlMapping = urlMapping;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setCurrentPageNumberParameter(String currentPageNumberParameter) {
        this.currentPageNumberParameter = currentPageNumberParameter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }


    public void setShowPageInformation(boolean showPageInformation) {
        this.showPageInformation = showPageInformation;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setShowFirstPageLink(boolean showFirstPageLink) {
        this.showFirstPageLink = showFirstPageLink;
    }

    public void setShowLastPageLink(boolean showLastPageLink) {
        this.showLastPageLink = showLastPageLink;
    }

    public void setShowGoTo(boolean showGoTo) {
        this.showGoTo = showGoTo;
    }

    public void setGoToSpecificPage(String goToSpecificPage) {
        this.goToSpecificPage = goToSpecificPage;
    }

    public void setCustomInputBoxClass(String customInputBoxClass) {
        this.customInputBoxClass = customInputBoxClass;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}


