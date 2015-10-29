<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>长虹机顶盒后台管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uniform.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select2.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-media.css" class="skin-color" />
    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/maruti.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery-loadmask/jquery.loadmask.min.js'></script>
    <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/js/jquery-loadmask/jquery.loadmask.css'/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-raty/jquery.raty.min.js"></script>

    <script type="text/javascript">
        jQuery(function() {
            settings = {
                align : 'center',									//Valid values, left, right, center
                top : 50, 											//Use an integer (in pixels)
                width : 600, 										//Use an integer (in pixels)
                height : 500, 										//Use an integer (in pixels)
                padding : 10,										//Use an integer (in pixels)
                backgroundColor : 'white', 						    //Use any hex code
                source : '', 				    					//Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
                borderColor : '#333333', 							//Use any hex code
                borderWeight : 4,									//Use an integer (in pixels)
                borderRadius : 5, 									//Use an integer (in pixels)
                fadeOutTime : 300, 									//Use any integer, 0 : no fade
                disableColor : '#666666', 							//Use any hex code
                disableOpacity : 40, 								//Valid range 0-100
                loadingImage : '${pageContext.request.contextPath}/js/popup/loading.gif'
            };
            jQuery(document).keyup(function(event) {
                if (event.keyCode == 27) {
                    closePopup(settings.fadeOutTime);
                }
            });

        });
        function openHistoryModel(id) {
            settings.source = '${pageContext.request.contextPath}/backend/apphistory.html?appId=' + id;
            openModalPopup(settings);
        }

        function openModalPopup(obj) {
            modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
        }

        $(function() {
          jQuery.fn.raty.defaults.path = '${pageContext.request.contextPath}/js/jquery-raty/img';
          jQuery('#function-score').raty({
                number: 10,//多少个星星设置
                score: ${app.appScores},//初始值是设置
                targetType: 'number',//类型选择，number是数字值，hint，是设置的数组值
                path      : '${pageContext.request.contextPath}/js/jquery-raty/img',
                cancelOff : 'cancel-off-big.png',
                cancelOn  : 'cancel-on-big.png',
                size      : 30,
                starHalf  : 'star-half-big.png',
                starOff   : 'star-off-big.png',
                starOn    : 'star-on-big.png',
                target    : '#function-hint',
                cancel    : false,
                targetKeep: true,
                precision : false,//是否包含小数
                click: function(score, evt) {
                    jQuery("#appScore").val(score);
                }
          });
        });
    </script>
</head>
<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/header.jsp"/>

<%--内容部分***********************************************************--%>

<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
        <a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> 首页</a>
        <a href="#" class="current">添加/编辑应用信息</a>
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
                        <h5>添加/编辑应用信息</h5>
                    </div>

                    <div class="widget-content nopadding">
                        <spring-form:form commandName="app" class="form-horizontal" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="marketAppId" value="${app.id}"/>
                            <input type="hidden" name="appVersionOldInt" value="${appVersionOldInt}"/>
                            <input type="hidden" id="appScore" name="appScores" value="${app.appScores}"/>

                            <c:if test="${alertInfoShow}">
                                <div class="alert alert-success alert-block">
                                    <a class="close" data-dismiss="alert" href="#">×</a>
                                    <h4 class="alert-heading">提示信息</h4>
                                    已经成功保存该应用信息!
                                </div>
                            </c:if>

                            <div id="app_error_information" class="alert alert-success alert-block" style="display: none;">
                                <a class="close" data-dismiss="alert" href="#">×</a>
                                <h4 class="alert-heading">提示信息</h4>
                            </div>

                            <div class="control-group">
                                <label class="control-label">应用名称 [必填]</label>
                                <div class="controls">
                                    <spring-form:input path="appFullName" maxlength="50" cssStyle="height: 30px;"/>&nbsp;
                                    <spring-form:errors path="appFullName" cssClass="help-inline"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">应用评分 [必选]</label>
                                <div class="controls">
                                    <div id="function-score" style="float: left"></div>
                                    <div id="function-hint" style="float: left"></div>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">应用版本(数字) [必填]</label>
                                <div class="controls">
                                    <spring-form:input path="appVersionInt" maxlength="30" cssStyle="height:30px;"/>&nbsp;
                                    <spring-form:errors path="appVersionInt" cssClass="help-inline"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">应用版本（字符） [必填]</label>
                                <div class="controls">
                                    <spring-form:input path="appVersion" maxlength="30" cssStyle="height:30px;"/>&nbsp;
                                    <spring-form:errors path="appVersion" cssClass="help-inline"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">应用包名 [必填]</label>
                                <div class="controls">
                                    <c:set var="packageChange" value="${app.id > 0 && app.status == 'PASSED'}"/>
                                    <spring-form:input path="appPackage" maxlength="60" cssStyle="height:30px;" readonly="${packageChange}"/>&nbsp;
                                    <spring-form:errors path="appPackage" cssClass="help-inline"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">是否推荐</label>
                                <div class="controls">
                                    <spring-form:checkbox path="recommend" cssStyle="height:30px;"/>&nbsp;
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">应用类别 [必填]</label>
                                <div class="controls">
                                    <select name="selectCategoryId" style="height: 30px;">
                                      <c:forEach items="${categories}" var="category">
                                        <c:forEach items="${category.children}" var="child">
                                            <option value="${child.id}" <c:if test="${child.id==app.categoryId}">selected="true"</c:if>>${category.categoryName} -> ${child.categoryName}</option>
                                        </c:forEach>
                                      </c:forEach>
                                  </select>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label">应用图标(96x96) [必填]</label>
                                <div class="controls">
                                    <input type="file" id="appIconUploadFile" name="appIconUploadFile"/>&nbsp;
                                    <spring-form:errors path="appIconId" cssClass="help-inline"/>
                                    <c:if test="${app.appIconId > 0}">
                                        <br/>
                                        <br/>
                                        <img width="50" height="50" alt="" src="${fileRequestHost}${app.appKey}/${app.iconActualFileName}"/>
                                    </c:if>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label">应用海报(200x300) [必填]</label>
                                <div class="controls">
                                    <input type="file" id="appPosterUploadFile" name="appPosterUploadFile"/>&nbsp;
                                    <spring-form:errors path="appPosterId" cssClass="help-inline"/>
                                    <c:if test="${app.appPosterId > 0}">
                                        <br/>
                                        <br/>
                                        <ul class="thumbnails">
                                        <li class="span3">
                                            <a class="thumbnail lightbox_trigger" href="${fileRequestHost}${app.appKey}/${app.posterActualFileName}">
                                                <%--<img src="images/gallery/imgbox3.jpg" alt="" >--%>
                                                <img width="400" height="200" alt="" src="${fileRequestHost}${app.appKey}/${app.posterActualFileName}"/>
                                            </a>
                                        </li>
                                        </ul>
                                    </c:if>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label">应用下载文件(APK) [必填]</label>
                                <div class="controls">
                                    <input type="file" id="appApkUploadFile" name="appApkUploadFile"/>&nbsp;
                                    <spring-form:errors path="appFileId" cssClass="help-inline"/>
                                    <c:if test="${app.appFileId > 0}">
                                        <br/>
                                        <br/>
                                        ${app.apkFakeFileName}  <a href="${fileRequestHost}${app.appKey}/${app.apkActualFileName}" target="_blank" class="btn btn-warning btn-mini">下载应用</a>
                                    </c:if>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label">应用简介 [必填]</label>
                                <div class="controls">
                                    <spring-form:textarea path="appDescription" cssClass="span20"/>
                                    <spring-form:errors path="appDescription" cssClass="help-inline"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">应用备注</label>
                                <div class="controls">
                                    <spring-form:textarea path="appNote" cssClass="span20"/>
                                </div>
                            </div>
                            <c:if test="${app.id > 0}">
                                <div class="control-group">
                                    <label class="control-label">其他信息</label>
                                    <div class="controls">
                                        <input class="span3" placeholder="下载：${app.downloadTimes}次" readonly="true"/>&nbsp;
                                        <input class="span3" placeholder="大小：${app.appSize}M" readonly="true"/>&nbsp;
                                        <input class="span3" placeholder="状态：${app.statusName}" readonly="true"/>&nbsp;
                                        <input class="span3" placeholder="更新时间：${app.updateDate}" readonly="true"/>&nbsp;
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">&nbsp;</label>
                                    <div class="controls">
                                        <input class="span6" placeholder="查询关键字：'${app.pinYingShort}' 或 '${app.pinYingFull}'" readonly="true"/>&nbsp;
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-actions">
                                <input type="button" value="查看历史" class="btn btn-success" onclick="openHistoryModel('${app.id}')">
                                &nbsp;
                                <input type="button" value="返 回" class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/backend/marketappoverview.html?appName=${appName}&appStatus=${appStatus}&categoryId=${categoryId}'">
                                &nbsp;
                                <input type="button" value="保 存" class="btn btn-success" onclick="saveMarketApp(this.form);">
                            </div>
                        </spring-form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function saveMarketApp(form) {
        jQuery('#content').mask("正在上传数据文件，请耐心等待!");
        form.submit();
    }

</script>

</body>
</html>