package com.changhong.common.web.tag;

import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午11:34
 */
public class SubStringTag extends TagSupport {
    private String value;
    private int length = 10;
    private String suffix = "...";

    @Override
    public int doStartTag() throws JspException {
        String subString = generateSubString();
        try {
            writeMessage(subString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    protected String generateSubString() {
        boolean needSuffixAsBoolean = needSuffix();
        String suffix = needSuffixAsBoolean ? this.suffix : "";
        String value = needSuffixAsBoolean ? this.value.substring(0, length) : this.value;
        return generateSimpleTag(value, suffix);
    }

    private boolean needSuffix() {
        return value.length() > length;
    }

    private String generateSimpleTag(String value, String suffix) {
        if (!StringUtils.hasText(value)) {
            return "";
        }
        return value + suffix;
    }

    protected void writeMessage(String urlInfo) throws IOException {
        pageContext.getOut().write(urlInfo);
    }

    //************ GETTERS / SETTERS *************//

    public void setLength(int length) {
        this.length = length;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


