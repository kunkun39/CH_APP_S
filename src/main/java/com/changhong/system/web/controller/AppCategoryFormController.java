package com.changhong.system.web.controller;

import com.changhong.system.service.AppService;
import com.changhong.system.web.facade.dto.AppCategoryDTO;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午11:10
 */
public class AppCategoryFormController extends AbstractController {

    private AppService appService;

    private String fileRequestHost;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method");
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);
        int parentId = ServletRequestUtils.getIntParameter(request, "parentId", -1);
        String categoryName = ServletRequestUtils.getStringParameter(request, "categoryName", "");

        if ("save".equals(method)) {
            DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            MultipartFile iconFile = multipartRequest.getFile("categoryIconName");

            appService.saveOrUpdateAppCategory(categoryId, categoryName, parentId, iconFile);
            return new ModelAndView(new RedirectView("appcategoryoverview.html"));
        } else {
            Map<String, Object> model = new HashMap<String, Object>();
            AppCategoryDTO dto = null;
            if ("add".equals(method)) {
                dto = new AppCategoryDTO();
            } else {
                dto = appService.obtainAppCategoryById(categoryId);
            }
            model.put("category", dto);

            List<AppCategoryDTO> parents = appService.obtainAllFirstLevelCategory(false);
            model.put("parents", parents);
            model.put("fileRequestHost", fileRequestHost);
            return new ModelAndView("backend/app/appcategoryform", model);
        }

    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setFileRequestHost(String fileRequestHost) {
        this.fileRequestHost = fileRequestHost;
    }
}
