package com.changhong.common.web.tag;

import com.changhong.common.utils.JodaUtils;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午5:34
 */
public class TimeFormatTag extends TagSupport {
    private Date value;
    private String pattern;

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
        if (StringUtils.hasText(pattern)) {
            return JodaUtils.toString(value, pattern);
        }
        return JodaUtils.toString(value);
    }

    protected void writeMessage(String urlInfo) throws IOException {
        pageContext.getOut().write(urlInfo);
    }

    //************ GETTERS / SETTERS *************//


    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
