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
</head>

<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/header.jsp"/>

<%--内容部分***********************************************************--%>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a style="font-size: 13px" href="javascript:void(0);" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> 首页</a>
            <a style="font-size: 13px" href="javascript:void(0);" class="current">开机广告管理</a>
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
                        <h5>开机广告管理</h5>
                    </div>

                    <div class="widget-content nopadding">
                        <form action="${pageContext.request.contextPath}/backend/clientbootimageshow.html"
                              class="form-horizontal" method="post" name="basic_validate" id="basic_validate"
                              novalidate="novalidate" enctype="multipart/form-data">
                            <%--转换到后台method模式--%>
                            <input type="hidden" name="method" value="save"/>
                            <div class="control-group">
                                <label class="control-label">开机广告 [必添]<br/> 比例(16:9)、大小(<512K)</label>
                                <div class="controls">
                                    <%--<input id="clientImageUploadFile" type="file" name="clientImageUploadFile"/>&nbsp;--%>
                                    <%--<c:if test="${error}">--%>
                                        <%--<span id="clientImg_bigger" class="help-block" for="required" style="display:block;">选择的图片大小超过限制</span>--%>
                                    <%--</c:if>--%>
                                    <%--<span id="clientImg_help" class="help-block" for="required" style="display:none">选择文件不能为空</span>--%>
                                    <%--<span id="clientImg_format_help" class="help-block" style="display: none;">选择文件不是图片格式</span>--%>

                                    <%--&nbsp;--%>
                                    <%--<img width="540px" height="360px" alt="" src="${clientImageRequestHost}${clientbootimage.actualFileName}"/>--%>

                                    <input id="clientImageUploadFile" type="file" name="clientImageUploadFile"/>&nbsp;
                                    <c:if test="${error}">
                                        <span id="clientImg_bigger" class="help-block" for="required" style="display:block;">选择的图片大小超过限制</span>
                                    </c:if>
                                    <span id="clientImg_help" class="help-block" for="required" style="display:none">选择文件不能为空</span>
                                    <span id="clientImg_format_help" class="help-block" style="display: none;">选择文件不是图片格式</span>

                                    <br/>
                                    <br/>
                                    <img width="540px" height="360px" alt="" src="${clientImageRequestHost}${clientbootimage.actualFileName}"/>
                                </div>
                            </div>

                            <div class="form-actions">
                                <input type="button" value="返 回" class="btn btn-success"
                                       onclick="window.location.href='${pageContext.request.contextPath}/backend/clientbootimageshow.html?method=load'">
                                &nbsp;
                                <input type="button" value="保 存" class="btn btn-success"
                                       onclick="saveBootImage(this.form);">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function saveBootImage(form) {
        jQuery("#clientImg_bigger").css("display", "block");
        var canSubmit = true;
        //文件是否为空的检查
        var clientImageUploadFile = jQuery("#clientImageUploadFile").val();
        if (clientImageUploadFile == null || clientImageUploadFile == '') {
            jQuery("#clientImg_help").css("display", "block");
            canSubmit = false;
        } else {
            jQuery("#clientImg_help").css("display", "none");
            //文件是否符合图片格式检查
            if (isImage(clientImageUploadFile)) {
                jQuery("#clientImg_format_help").css("display", "none");
                canSubmit = true;
            } else {
                jQuery("#clientImg_format_help").css("display", "block");
                canSubmit = false;
            }
        }
        if (canSubmit) {
            form.submit();
        }
    }

    function isImage(url) {
        var filename = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
        if (filename != "jpg" && filename != "gif" && filename != "png" && filename != "bmp" && filename != "jpeg") {
            return false;
        } else {
            return true;
        }
    }

</script>

</body>
</html>