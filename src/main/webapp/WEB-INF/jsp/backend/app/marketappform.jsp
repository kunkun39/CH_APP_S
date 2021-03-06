<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>长虹机顶盒后台管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uniform.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select2.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-style.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-media.css" class="skin-color"/>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/maruti.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script type='text/javascript'
            src='${pageContext.request.contextPath}/js/jquery-loadmask/jquery.loadmask.min.js'></script>
    <link rel='stylesheet' type='text/css'
          href='${pageContext.request.contextPath}/js/jquery-loadmask/jquery.loadmask.css'/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-raty/jquery.raty.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.16.custom.min.js"
            type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.22.custom.css"
          type="text/css"/>

    <script type="text/javascript">
        jQuery(function() {
            settings = {
                align : 'center',                                    //Valid values, left, right, center
                top : 50,                                             //Use an integer (in pixels)
                width : 600,                                         //Use an integer (in pixels)
                height : 500,                                         //Use an integer (in pixels)
                padding : 10,                                        //Use an integer (in pixels)
                backgroundColor : 'white',                             //Use any hex code
                source : '',                                         //Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
                borderColor : '#333333',                             //Use any hex code
                borderWeight : 4,                                    //Use an integer (in pixels)
                borderRadius : 5,                                     //Use an integer (in pixels)
                fadeOutTime : 300,                                     //Use any integer, 0 : no fade
                disableColor : '#666666',                             //Use any hex code
                disableOpacity : 40,                                 //Valid range 0-100
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
        <a href="javascript:void(0);" class="tip-bottom"><i class="icon-home"></i> 首页</a>
        <a href="javascript:void(0);" class="current">添加/编辑应用信息</a>
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
                <input type="hidden" name="current" value="${current}"/>
                <input type="hidden" name="categoryId" value="${categoryId}"/>
                <input type="hidden" name="topicId" value="${topicId}"/>
                <input type="hidden" name="marketAppId" value="${app.id}"/>
                <input type="hidden" name="appVersionOldInt" value="${appVersionOldInt}"/>
                <input type="hidden" id="appScore" name="appScores" value="${app.appScores}"/>

                <c:if test="${alertInfoShow}">
                    <div class="alert alert-success alert-block">
                        <a class="close" data-dismiss="alert" href="javascript:void(0);">×</a>
                        <h4 class="alert-heading">提示信息</h4>
                        已经成功保存该应用信息!
                    </div>
                </c:if>
                <c:if test="${errorInfoShow}">
                    <div class="alert alert-danger alert-block">
                        <a class="close" data-dismiss="alert" href="javascript:void(0);">×</a>
                        <h4 class="alert-heading">提示信息</h4>
                        应用文件对应包名冲突，请确认后再上传应用文件!
                    </div>
                </c:if>

                <div id="app_error_information" class="alert alert-success alert-block" style="display: none;">
                    <a class="close" data-dismiss="alert" href="javascript:void(0);">×</a>
                    <h4 class="alert-heading">提示信息</h4>
                </div>

                <div class="control-group">
                    <label class="control-label">应用名称 [必填]</label>

                    <div class="controls">
                        <spring-form:input path="appFullName" maxlength="40" cssStyle="height: 30px;"/>&nbsp;
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
                <%--<div class="control-group">--%>
                    <%--<label class="control-label">应用版本(数字) [必填]</label>--%>

                    <%--<div class="controls">--%>
                        <%--<spring-form:input path="appVersionInt" maxlength="30" cssStyle="height:30px;"/>&nbsp;--%>
                        <%--<spring-form:errors path="appVersionInt" cssClass="help-inline"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="control-group">--%>
                    <%--<label class="control-label">应用版本（字符） [必填]</label>--%>

                    <%--<div class="controls">--%>
                        <%--<spring-form:input path="appVersion" maxlength="30" cssStyle="height:30px;"/>&nbsp;--%>
                        <%--<spring-form:errors path="appVersion" cssClass="help-inline"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="control-group">--%>
                    <%--<label class="control-label">应用包名 [必填]</label>--%>

                    <%--<div class="controls">--%>
                        <%--<c:set var="packageChange" value="${app.id > 0 && app.status == 'PASSED'}"/>--%>
                        <%--<spring-form:input path="appPackage" maxlength="80" cssStyle="height:30px;"--%>
                                           <%--readonly="${packageChange}"/>&nbsp;--%>
                        <%--<spring-form:errors path="appPackage" cssClass="help-inline"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="control-group">
                    <label class="control-label">是否推荐</label>

                    <div class="controls">
                        <spring-form:checkbox path="recommend" cssStyle="height:30px;"/>&nbsp;
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">应用类别 [必填]</label>

                    <div class="controls">
                        <select id="selectCategoryId" name="selectCategoryId" style="height: 30px;">
                            <c:forEach items="${categories}" var="category">
                                <option value="${category.id}" disabled="">${category.categoryName}</option>
                                <c:forEach items="${category.children}" var="child" varStatus="counter">
                                    <option value="${child.id}"
                                            <c:if test="${child.id==app.categoryId}">selected="true"</c:if>>
                                        [${counter.count}] ${category.categoryName}
                                        -> ${child.categoryName}</option>
                                </c:forEach>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">应用专题</label>
                    <input type="hidden" id="add_topics" name="addTopics" value="${app.topicIds}"/>
                    <input type="hidden" id="delete_topics" name="deleteTopics" value=""/>

                    <div class="controls">
                        <select id="selectTopicId" name="selectTopicId" style="height: 30px;">
                            <c:forEach items="${topics}" var="topic">
                                <option value="${topic.id}">${topic.topicName}</option>
                            </c:forEach>
                        </select>
                        <a href="javascript:void(0);" onclick="appendAppTopic()"
                           class="btn btn-warning btn-mini">添加专题</a>
                        <br/>
                        <br/>

                        <div id="exist_topics">
                            <c:forEach items="${app.topics}" var="topic">
                                <div id="topic_exist_${topic.id}">
                                        ${topic.topicName} <a href="#" onclick="deleteAppTopic('${topic.id}')"
                                                              class="btn btn-danger btn-mini">删除专题</a> <br/>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">应用图标(1:1) [必填]</label>

                    <div class="controls">
                        <input type="file" id="appIconUploadFile" name="appIconUploadFile"/>&nbsp;
                        <spring-form:errors path="appIconId" cssClass="help-inline"/>
                        <c:if test="${app.appIconId > 0}">
                            <br/>
                            <br/>
                            <img width="50" height="50" alt=""
                                 src="${fileRequestHost}${app.appKey}/${app.iconActualFileName}"/>
                        </c:if>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">应用海报(16:9) [必填]</label>

                    <div class="controls">
                        <input type="file" id="appPosterUploadFile" name="appPosterUploadFile"/>&nbsp;
                        <spring-form:errors path="appPosterId" cssClass="help-inline"/>
                        <c:if test="${app.appPosterId > 0}">
                            <br/>
                            <br/>
                            <ul class="thumbnails">
                                <li class="span3">
                                    <a class="thumbnail lightbox_trigger"
                                       href="${fileRequestHost}${app.appKey}/${app.posterActualFileName}">
                                        <img width="400" height="200" alt=""
                                             src="${fileRequestHost}${app.appKey}/${app.posterActualFileName}"/>
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
                            ${app.apkFakeFileName}  <a href="${fileRequestHost}${app.appKey}/${app.apkActualFileName}"
                                                       target="_blank" class="btn btn-warning btn-mini">下载应用</a>
                        </c:if>
                    </div>
                    <label id = "processBarTextControl" class="control-label" style="display: none">文件上传进度</label>

                    <div id = "processBarValControl" class="controls" style="display: none">
                         <div class="progress progress-success progress-striped" style="width:30%">
                             <div id='proBar' class="bar" style="width: 0%"><span id="proVal"></span></div>
                         </div>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">应用简介 [必填]</label>

                    <div class="controls">
                        <spring-form:textarea path="appDescription" cssClass="span20" rows="4"/>
                        <spring-form:errors path="appDescription" cssClass="help-inline"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">应用备注</label>

                    <div class="controls">
                        <spring-form:textarea path="appNote" cssClass="span20" rows="4"/>
                    </div>
                </div>
                <c:if test="${app.id > 0}">
                    <div class="control-group">
                        <label class="control-label">基本信息</label>

                        <div class="controls">
                            <input class="span3" placeholder="包名：${app.appPackage}" readonly="true"/>&nbsp;
                            <input class="span3" placeholder="版本：${app.appVersionInt}" readonly="true"/>&nbsp;
                            <input class="span3" placeholder="版本名：${app.appVersion}" readonly="true"/>&nbsp;
                            <input class="span3" placeholder="大小：${app.appSize}M" readonly="true"/>&nbsp;
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">&nbsp;</label>

                        <div class="controls">
                            <input class="span3" placeholder="下载：${app.downloadTimes}次" readonly="true"/>&nbsp;
                            <input class="span3" placeholder="状态：${app.statusName}" readonly="true"/>&nbsp;
                            <input class="span3" placeholder="更新时间：${app.updateDate}" readonly="true"/>&nbsp;
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">&nbsp;</label>

                        <div class="controls">
                            <input class="span6" placeholder="查询关键字：'${app.pinYingShort}' 或 '${app.pinYingFull}'"
                                   readonly="true"/>&nbsp;
                        </div>
                    </div>
                </c:if>
                <div class="form-actions">
                    <input type="button" value="查看历史" class="btn btn-success" onclick="openHistoryModel('${app.id}')">
                    &nbsp;
                    <input type="button" value="返 回" class="btn btn-success"
                           onclick="window.location.href='${pageContext.request.contextPath}/backend/marketappoverview.html?appName=${appName}&current=${current}&appStatus=${appStatus}&categoryId=${categoryId}&topicId=${topicId}'">
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

<div id="marketapp-dialog-confirm" title="信息提示?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        该类别已经存在于在应用下？
    </p>
</div>

<script type="text/javascript">

    function appendAppTopic() {
        var topicId = jQuery("#selectTopicId").val();
        var topicLable = jQuery("#selectTopicId").find("option:selected").text();
        var alreadyAdd = jQuery("#add_topics").val();

        if (alreadyAdd.indexOf(topicId + ",") >= 0) {
            jQuery("#marketapp-dialog-confirm").css("visibility", "visible");
            jQuery("#marketapp-dialog-confirm").dialog({
                        resizable: false,
                        height:160,
                        width:300,
                        modal: true,
                        buttons: {
                            "确  认": function() {
                                jQuery("#marketapp-dialog-confirm").css("visibility", "hidden");
                                jQuery(this).dialog("close");
                            }
                        }
                    });

        } else {
            jQuery("#add_topics").val(alreadyAdd + topicId + ",");

            var newHtml = "<div id=\"topic_exist_" + topicId + "\">" + topicLable + " <a href=\"javascript:void(0);\" onclick=\"deleteAppTopic('" + topicId + "')\" class=\"btn btn-danger btn-mini\">删除专题</a><br/><div>";
            var oldHtml = jQuery("#exist_topics").html();
            jQuery("#exist_topics").html(oldHtml + newHtml);
        }
    }

    function deleteAppTopic(topicId) {
        var alreadyAdd = jQuery("#add_topics").val();
        var alreadyDelete = jQuery("#delete_topics").val();

        if (alreadyAdd.indexOf(topicId + ",") >= 0) {
            jQuery("#add_topics").val(alreadyAdd.replace(topicId + ",", ""))
        } else {
            jQuery("#delete_topics").val(alreadyDelete + topicId + ",");
        }

        jQuery("#topic_exist_" + topicId).remove();
    }

    function saveMarketApp(form) {
        form.submit();
        beginUploadProcess();
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