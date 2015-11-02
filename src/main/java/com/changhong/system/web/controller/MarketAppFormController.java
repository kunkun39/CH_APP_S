package com.changhong.system.web.controller;

import com.changhong.common.utils.CHFileUtils;
import com.changhong.system.service.AppService;
import com.changhong.system.web.facade.dto.AppCategoryDTO;
import com.changhong.system.web.facade.dto.MarketAppDTO;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午11:42
 */
public class MarketAppFormController extends SimpleFormController {

    private final static int MAX_ICON_IMAGE_SIZE = 1024 * 50;
    private final static int MAX_POSTER_IMAGE_SIZE = 1024 * 512;

    private AppService appService;

    private String fileRequestHost;

    public MarketAppFormController() {
        setCommandClass(MarketAppDTO.class);
        setCommandName("app");
        setFormView("/backend/app/marketappform");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        request.setAttribute("fileRequestHost", fileRequestHost);
        String alertInfoShow = ServletRequestUtils.getStringParameter(request, "alertInfoShow", "false");
        request.setAttribute("alertInfoShow", alertInfoShow);

        int marketAppId = ServletRequestUtils.getIntParameter(request, "marketAppId", -1);
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String appName = ServletRequestUtils.getStringParameter(request, "appName", "");
        String appStatus = ServletRequestUtils.getStringParameter(request, "appStatus", "ALL");
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);
        request.setAttribute("current", current);
        request.setAttribute("appName", appName);
        request.setAttribute("appStatus", appStatus);
        request.setAttribute("categoryId", categoryId);

        List<AppCategoryDTO> categories = appService.obtainAllFirstLevelCategory(true);
        request.setAttribute("categories", categories);

        if (marketAppId > 0) {
            MarketAppDTO dto = appService.obtainMarketAppById(marketAppId);
            request.setAttribute("appVersionOldInt", dto.getAppVersionInt());
            return dto;
        }
        request.setAttribute("appVersionOldInt", "0");
        return new MarketAppDTO();
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        int marketAppId = ServletRequestUtils.getIntParameter(request, "marketAppId", -1);
        String appFullName = ServletRequestUtils.getStringParameter(request, "appFullName", "");
        if (!StringUtils.hasText(appFullName)) {
            errors.rejectValue("appFullName", "app.appName.empty");
        }

        String appVersionInt = ServletRequestUtils.getStringParameter(request, "appVersionInt", "");
        if (!StringUtils.hasText(appVersionInt)) {
            errors.rejectValue("appVersionInt", "app.version.empty");
        } else {
            boolean formatRight = true;
            try {
                Integer.valueOf(appVersionInt);
            } catch (NumberFormatException e) {
                errors.rejectValue("appVersionInt", "app.version.notNumber");
                formatRight = false;
            }

            /**
            if (formatRight) {
                String appOldVersionInt = ServletRequestUtils.getStringParameter(request, "appVersionOldInt", "0");
                try {
                    if(Integer.valueOf(appOldVersionInt) > Integer.valueOf(appVersionInt)) {
                        errors.rejectValue("appVersionInt", "app.version.small.newversion");
                    }
                } catch (Exception e) {
                    errors.rejectValue("appVersionInt", "app.version.notNumber");
                }
            }
            */
        }

        String appVersion = ServletRequestUtils.getStringParameter(request, "appVersion", "");
        if (!StringUtils.hasText(appVersion)) {
            errors.rejectValue("appVersion", "app.version.empty");
        }

        String appPackage = ServletRequestUtils.getStringParameter(request, "appPackage", "");
        if (!StringUtils.hasText(appPackage)) {
            errors.rejectValue("appPackage", "app.package.empty");
        } else {
            boolean duplicate = appService.obtainAppPackageDuplicate(marketAppId, appPackage);
            if (duplicate) {
                errors.rejectValue("appPackage", "app.package.duplicate");
            }
        }

        String appDescription = ServletRequestUtils.getStringParameter(request, "appDescription", "");
        if (!StringUtils.hasText(appDescription)) {
            errors.rejectValue("appDescription", "app.description.empty");
        }

        //check file is empty
        DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
        MultipartFile appIconUploadFile = multipartRequest.getFile("appIconUploadFile");
        MultipartFile appApkUploadFile = multipartRequest.getFile("appApkUploadFile");
        MultipartFile appPosterUploadFile = multipartRequest.getFile("appPosterUploadFile");

        if (marketAppId <= 0) {
            if (appIconUploadFile == null || appIconUploadFile.getSize() <= 0) {
                errors.rejectValue("appIconId", "app.icon.empty");
            }

            if (appApkUploadFile == null || appApkUploadFile.getSize() <= 0) {
                errors.rejectValue("appFileId", "app.apk.empty");
            }

            if (appPosterUploadFile == null || appPosterUploadFile.getSize() <= 0) {
                errors.rejectValue("appPosterId", "app.poster.empty");
            }
        }

        //check file format is right
        if (appIconUploadFile != null && appIconUploadFile.getSize() > 0) {
            if (!CHFileUtils.isImageFile(appIconUploadFile.getOriginalFilename()) && !errors.hasFieldErrors("appIconId")) {
                errors.rejectValue("appIconId", "app.not.image");
            } else {
                if (appIconUploadFile.getSize() > MAX_ICON_IMAGE_SIZE) {
                    errors.rejectValue("appIconId", "app.image.bigger");
                }
            }
        }

        if (appApkUploadFile != null && appApkUploadFile.getSize() > 0) {
            if (!CHFileUtils.isAndroidAppFile(appApkUploadFile.getOriginalFilename()) && !errors.hasFieldErrors("appFileId")) {
                errors.rejectValue("appFileId", "app.not.apk");
            }
        }

        if (appPosterUploadFile != null && appPosterUploadFile.getSize() > 0) {
            if (!CHFileUtils.isImageFile(appPosterUploadFile.getOriginalFilename()) && !errors.hasFieldErrors("appPosterId")) {
                errors.rejectValue("appPosterId", "app.not.image");
            } else {
                if (appIconUploadFile.getSize() > MAX_POSTER_IMAGE_SIZE) {
                    errors.rejectValue("appPosterId", "app.image.bigger");
                }
            }
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String appName = ServletRequestUtils.getStringParameter(request, "appName", "");
        String appStatus = ServletRequestUtils.getStringParameter(request, "appStatus", "ALL");
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);


        MarketAppDTO app = (MarketAppDTO) command;
        int selectCategoryId = ServletRequestUtils.getIntParameter(request, "selectCategoryId", -1);
        app.setCategoryId(selectCategoryId);
        int appScores = ServletRequestUtils.getIntParameter(request, "appScores", 1);
        app.setAppScores(appScores);

        DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
        MultipartFile apkFile = multipartRequest.getFile("appApkUploadFile");
        if (apkFile != null && apkFile.getSize() > 0) {
            app.setApkFile(apkFile);
        }
        MultipartFile iconFile = multipartRequest.getFile("appIconUploadFile");
        if (iconFile != null && iconFile.getSize() > 0) {
            app.setIconFile(iconFile);
        }
        MultipartFile posterFile = multipartRequest.getFile("appPosterUploadFile");
        if (posterFile != null && posterFile.getSize() > 0) {
            app.setPosterFile(posterFile);
        }

        int marketAppId = appService.saveOrUpdateMarketApp(app);

        return new ModelAndView(new RedirectView("marketappform.html?marketAppId=" + marketAppId + "&current=" + current + "&appName=" + appName + "&appStatus=" + appStatus + "&categoryId=" + categoryId + "&alertInfoShow=true"));
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setFileRequestHost(String fileRequestHost) {
        this.fileRequestHost = fileRequestHost;
    }
}
