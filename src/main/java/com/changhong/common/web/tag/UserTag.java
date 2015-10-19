package com.changhong.common.web.tag;

import com.changhong.common.utils.SecurityUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午11:35
 */
public class UserTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        String user = generateUserInfo();
        try {
            writeMessage(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    protected String generateUserInfo() {
        return SecurityUtils.currentUser().getName();
    }

    protected void writeMessage(String urlInfo) throws IOException {
        pageContext.getOut().write(urlInfo);
    }
}
