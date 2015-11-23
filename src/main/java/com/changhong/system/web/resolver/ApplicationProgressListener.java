package com.changhong.system.web.resolver;

import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午9:15
 */
public class ApplicationProgressListener implements ProgressListener {

    private HttpServletRequest request;

    public ApplicationProgressListener(HttpServletRequest request) {
        this.request = request;
    }

    public void update(long byteRead, long contentLength, int itemSequence) {
        if (request != null) {
            int processRate = ((Long) ((byteRead * 100) / contentLength)).intValue();
            request.getSession().setAttribute("UPLAOD_FILE_PROCESS", processRate);
        }
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
