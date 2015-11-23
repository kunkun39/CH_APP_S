package com.changhong.system.web.resolver;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
//import org.apache.commons.fileupload.ProgressListener;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午9:14
 */
public class ApplicationMultipartResolver  extends CommonsMultipartResolver {

    private HttpServletRequest request;

    private ApplicationProgressListener progressListener;

    //判断request是否有文件上传,即多部分请求...
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        this.request = request;
        return super.isMultipart(request);
    }

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        FileUpload fileUpload = super.newFileUpload(fileItemFactory);

        if (progressListener == null) {
            progressListener = new ApplicationProgressListener(request);
        } else {
            progressListener.setRequest(request);
        }

        fileUpload.setProgressListener(progressListener);

        return fileUpload;
    }
}
