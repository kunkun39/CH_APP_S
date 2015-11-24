<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>应用市场后台管理系统</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uniform.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select2.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-media.css" class="skin-color"/>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
    <script type='text/javascript'
            src='${pageContext.request.contextPath}/js/jquery-loadmask/jquery.loadmask.min.js'></script>
    <link rel='stylesheet' type='text/css'
          href='${pageContext.request.contextPath}/js/jquery-loadmask/jquery.loadmask.css'/>
    <style type="text/css">
        p {
            font-size: 20px;
            text-index: 2em;
        }
    </style>
</head>

<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/header.jsp"/>

<%--内容部分***********************************************************--%>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a style="font-size: 13px" href="javascript:void(0);" title="Go to Home" class="tip-bottom"><i
                    class="icon-home"></i> 首页</a>
            <a style="font-size: 13px" href="javascript:void(0);" class="current">APK分析工具</a>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon">
                            <i class="icon-info-sign"></i>
                        </span>
                        <h5>APK分析工具</h5>
                    </div>

                    <div class="widget-content nopadding">
                        <form action="${pageContext.request.contextPath}/backend/apkparsershow.html"
                              class="form-horizontal" method="post" name="basic_validate" id="basic_validate"
                              novalidate="novalidate" enctype="multipart/form-data">
                            <input type="hidden" name="method" value="save"/>

                            <div class="control-group">
                                <label class="control-label">APK分析文件 [必选]</label>

                                <div class="controls">
                                    <input type="file" id="clientApkParserFlie" name="clientApkParserFlie"/>
                                    <span id="clientApk_help" class="help-block" for="required" style="display:none">选择文件不能为空</span>
                                    <span id="clientApk_format_help" class="help-block" style="display: none;">选择文件不是apk格式,请重新选择</span>
                                </div>

                                <label id = "processBarTextControl" class="control-label" style="display: none">分析进度</label>

                                <div id = "processBarValControl" class="controls" style="display: none">
                                    <div class="progress progress-success progress-striped" style="width:30%">
                                        <div id='proBar' class="bar" style="width: 0%"><span id="proVal"></span></div>
                                    </div>
                                </div>

                            </div>
                            <div class="control-group">
                                <label class="control-label">文件名</label>

                                <div class="controls">
                                    <input value="${fileName}" style="height:30px;width: 200px" readonly="true"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">大小</label>

                                <div class="controls">
                                    <input value="${fileSize}" style="height:30px;width: 200px" readonly="true"/>M
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">包名</label>

                                <div class="controls">
                                    <input value="${packageName}" style="height:30px;width: 200px" readonly="true"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">版本号[数字]</label>

                                <div class="controls">
                                    <input value="${versionCode}" style="height:30px;width: 200px" readonly="true"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">版本号[字符]</label>

                                <div class="controls">
                                    <input value="${versionName}" style="height:30px;width: 200px" readonly="true"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">最小机顶盒SDK版本</label>

                                <div class="controls">
                                    <input value="${minSdkVersion}" style="height:30px;width: 200px" readonly="true"/>
                                </div>
                            </div>
                            <div class="form-actions">
                                <input type="button" value="取 消" class="btn btn-success"
                                       onclick="window.location.href='${pageContext.request.contextPath}/backend/apkparsershow.html?method=load'">
                                &nbsp;
                                <input id="subbut" type="button" value="确 定" class="btn btn-success"
                                       onclick="saveApkParser(this.form);">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    function saveApkParser(form) {
        // jQuery("#clientImg_bigger").css("display", "block");
        var canSubmit = true;
        //文件是否为空的检查
        var clientApkParserFlie = jQuery("#clientApkParserFlie").val();
        if (clientApkParserFlie == null || clientApkParserFlie == '') {
            jQuery("#clientApk_help").css("display", "block");
            canSubmit = false;
        } else {
            jQuery("#clientApk_help").css("display", "none");
            //文件是否符合apk格式检查
            if (isImage(clientApkParserFlie)) {
                jQuery("#clientApk_format_help").css("display", "none");
                canSubmit = true;
            } else {
                jQuery("#clientApk_format_help").css("display", "block");
                canSubmit = false;
            }
        }
        if (canSubmit) {
            form.submit();
            beginUploadProcess();
        }
    }

    function isImage(url) {
        var filename = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
        if (filename != "apk") {
            return false;
        } else {
            return true;
        }
    }

    function beginUploadProcess() {
        jQuery("#processBarTextControl").css("display", "block");
        jQuery("#processBarValControl").css("display", "block");
        var intId = setInterval(getUploadMeter, 1000);

        function getUploadMeter() {
            jQuery.getJSON("${pageContext.request.contextPath}/backend/apkuploadprocess.html", function(data) {
                jQuery("#proBar").css("width", data.percentage + '%');
                jQuery("#proVal").html(data.percentage + '%');

                if (data.percentage == 100) {
                    //stop requirement
                    window.clearInterval(intId);
                }
            });
        }
    }

</script>
</body>
</html>