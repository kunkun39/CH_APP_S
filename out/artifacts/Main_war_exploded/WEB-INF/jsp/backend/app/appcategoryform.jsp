<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="widget-box">

                <div class="widget-title">
                    <span class="icon">
                        <i class="icon-align-justify"></i>
                    </span>
                    <h5>应用类别编辑</h5>
                </div>

                <div class="widget-content nopadding">
                    <form action="${pageContext.request.contextPath}/backend/appcategoryform.html" method="post" class="form-horizontal" enctype="multipart/form-data">
                        <input type="hidden" id="categoryId" name="categoryId" value="${category.id}"/>
                        <input type="hidden" id="oldParentId" name="oldParentId" value="${category.parentId}"/>
                        <input type="hidden" name="method" value="save"/>
                        <div class="control-group">
                            <label class="control-label">应用类别名称 [必填]</label>
                            <div class="controls">
                                <input type="text" id="categoryName" name="categoryName" class="span20" value="${category.categoryName}"/>
                                <span id="categoryName_help" class="help-block" style="display: none;">应用类别不能为空</span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">应用类别层次 [必填]</label>
                            <div class="controls">
                                <select id="parentId" name="parentId" onchange="showIconPlace();" <c:if test="${category.includeChild && category.id > 0}">disabled="true"</c:if></select>
                                    <option value="-1" <c:if test="${category.parentId==-1}">selected="true"</c:if>>一级类别</option>
                                    <c:forEach items="${parents}" var="parent">
                                        <option value="${parent.id}" <c:if test="${category.parentId==parent.id}">selected="true"</c:if>>${parent.categoryName}类别下</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <c:set var="iconShow" value="${category.categoryIconId > 0 ? 1:0}"/>
                        <div id="icon_place" class="control-group" <c:if test="${category.includeChild}">style="display: none;"</c:if>>
                            <label class="control-label">应用类别ICON [必填]</label>
                            <div class="controls">
                                <input type="file" id="categoryIconName" name="categoryIconName" class="span20"/>
                                <span id="categoryIcon_help" class="help-block" style="display: none;">应用类别ICON不能为空</span>
                                <span id="categoryIcon_format_help" class="help-block" style="display: none;">应用类别ICON必须为图片格式</span>
                            </div>
                        </div>
                        <c:if test="${category.categoryIconId > 0}">
                            <div>
                                <div class="controls">
                                    <img style="width: 60px; height: 60px;" src="${fileRequestHost}category/${category.categoryIconName}" alt="" >
                                </div>
                            </div>
                        </c:if>
                        <div class="form-actions">
                            <button type="button" class="btn btn-success" onclick="closePopup(settings.fadeOutTime);">取 消</button>
                            <button type="button" class="btn btn-success" onclick="saveAppCategory(this.form);">保 存</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>