<%--
  User: dangwei pan
  Date: 15-11-11
  Time: 上午11:33
  To change this template use File | Settings | File Templates.
--%>
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
    <style type="text/css">
        p{
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
            <a style="font-size: 13px" href="javascript:void(0);" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> 首页</a>
            <a style="font-size: 13px" href="javascript:void(0);" class="current">apk分析器管理</a>
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
                        <h5>apk分析器管理</h5>
                    </div>

                   <div class="widget-content nopadding">
                       <form action="${pageContext.request.contextPath}/backend/clientapkparsershow.html" class="form-horizontal" method="post" name="basic_validate" id="basic_validate" novalidate="novalidate" enctype="multipart/form-data">
                            <input type="hidden" name="method" value="save"/>
                           <div style="border:2px solid #d3d3d3" class="control-group">
                                <label class="control-label">待解析APK文件[必选]</label>
                                <div class="controls">
                                    <input type="file" id="clientApkParserFlie" name="clientApkParserFlie"/>
                                </div>
                           </div>
                           <div style="border-:2px solid #d3d3d3" class="control-group">
                               <br/>
                               <ul style="font-size: 14px;font-weight: bold;">文件大小:${fileSize}M</ul>
                               <ul style="font-size: 14px;font-weight: bold;">包名:${packageName}</ul>
                               <ul style="font-size: 14px;font-weight: bold;">版本名:${versionName}</ul>
                               <ul style="font-size: 14px;font-weight: bold;">版本号:${versionCode}</ul>
                           </div>
                           <div class="form-actions">
                                  <input type="button" value="取 消" class="btn btn-success"
                                       onclick="window.location.href='${pageContext.request.contextPath}/backend/clientapkparsershow.html?method=load'">
                                        &nbsp;
                                  <input type="button" value="确 定" class="btn btn-success"
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
    function saveApkParser(form){
        form.submit();
    }
</script>
</body>
</html>