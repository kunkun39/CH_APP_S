<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>应用市场后台管理系统</title><meta charset="UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uniform.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select2.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-media.css" class="skin-color" />
</head>

<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/header.jsp"/>

<%--内容部分***********************************************************--%>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
        <a href="javascript:void(0);" class="tip-bottom"><i class="icon-home"></i> 首页</a>
        <a href="javascript:void(0);" class="current">修改密码</a>
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
                        <h5>用户修改密码</h5>
                    </div>
                    <div class="widget-content nopadding">
                        <spring-form:form commandName="userPassword" class="form-horizontal" method="post" name="basic_validate" id="basic_validate" novalidate="novalidate">
                            <input type="hidden" name="userId" value="${userPassword.userId}"/>
                            <div class="control-group">
                                <label class="control-label">姓名</label>
                                <div class="controls">
                                    <input type="text" name="required" id="required" style="height: 30px;" value="${userPassword.name}" readonly="true"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">旧密码 [必填]</label>
                                <div class="controls">
                                    <input type="password" id="oldPassword" name="oldPassword" value="" maxlength="30" style="height: 30px;"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">新密码 [必填]</label>
                                <div class="controls">
                                    <input type="password" id="newPassword" name="newPassword" value="" maxlength="30" style="height: 30px;"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">再次输入新密码 [必填]</label>
                                <div class="controls">
                                    <input type="password" id="newPasswordAgain" name="newPasswordAgain" value="" maxlength="30" style="height: 30px;"/>
                                    <span class="help-inline" for="required" generated="true"><spring-form:errors path="newPasswordAgain"/></span>
                                </div>
                            </div>
                            <div class="form-actions">
                                <input type="button" value="返 回" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/backend/dashboard.html'">
                                &nbsp;
                                <input type="submit" value="保 存" class="btn btn-success">
                            </div>
                        </spring-form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>