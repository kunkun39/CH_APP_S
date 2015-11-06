<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
    <script src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.22.custom.css" type="text/css"/>
</head>
<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/header.jsp"/>

<%--内容部分***********************************************************--%>

<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a style="font-size:13px" href="javascript:void(0);" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> 首页</a> <a style="font-size:13px" href="javascript:void(0);" class="current">应用信息管理</a> </div>
  </div>

  <%--<div  class="quick-actions_homepage">--%>
    <%--<ul class="quick-actions">--%>
      <%--<li> <a href="${pageContext.request.contextPath}/backend/userform.html"> <i class="icon-wallet"></i> 添加应用 </a> </li>--%>
    <%--</ul>--%>
  <%--</div>--%>

  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span12">

          <div class="widget-box">
          <div class="widget-title"><a href="${pageContext.request.contextPath}/backend/marketappform.html?appName=${paging.appName}&categoryId=${paging.categoryId}&appStatus=${paging.appStatus}"><span class="icon"> <i class="icon-plus"></i> </span></a>
            <h5>应用信息查询</h5>
          </div>

          <div class="widget-content">
            <form id="app_search_form" action="${pageContext.request.contextPath}/backend/marketappoverview.html" method="post">
            <input type="hidden" id="appStatus" name="appStatus" value="${paging.appStatus}">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th style="font-size: 12px">查询条件</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                      应用类别：<select name="categoryId" style="height: 30px;">
                          <option value="-1" <c:if test="${-1==paging.categoryId}">selected="true"</c:if>>全 部</option>
                          <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" <c:if test="${category.id==paging.categoryId}">selected="true"</c:if>>${category.categoryName}</option>
                            <c:forEach items="${category.children}" var="child">
                                <option value="${child.id}" <c:if test="${child.id==paging.categoryId}">selected="true"</c:if>>${category.categoryName} -> ${child.categoryName}</option>
                            </c:forEach>
                          </c:forEach>
                      </select>
                      &nbsp;
                      应用专题：<select name="topicId" style="height: 30px;">
                          <option value="-1" <c:if test="${-1==paging.topicId}">selected="true"</c:if>>全 部</option>
                          <c:forEach items="${topics}" var="topic">
                            <option value="${topic.id}" <c:if test="${topic.id==paging.topicId}">selected="true"</c:if>>${topic.topicName}</option>
                          </c:forEach>
                      </select>
                      &nbsp;
                      应用名称：<input type="text" name="appName" class="text" value="${paging.appName}" style="height: 25px;"/>
                      &nbsp;
                     <i id="app_search_button" class="icon icon-search" style="cursor: pointer" onclick="jQuery('#app_search_form').submit();"></i>
                  </td>
                </tr>
                <tr>
                  <td>
                      应用状态：
                      <span onclick="searchApp('ALL');" style="cursor: pointer" class="badge <c:if test="${'ALL'==paging.appStatus}">badge-warning</c:if>">全  部</span>&nbsp;&nbsp;
                      <span onclick="searchApp('CREAETED');" style="cursor: pointer" class="badge <c:if test="${'CREAETED'==paging.appStatus}">badge-warning</c:if>">待审核</span>&nbsp;&nbsp;
                      <span onclick="searchApp('PASSED');" style="cursor: pointer" class="badge <c:if test="${'PASSED'==paging.appStatus}">badge-warning</c:if>">已上架</span>&nbsp;&nbsp;
                      <span onclick="searchApp('OFFSHELVES');" style="cursor: pointer" class="badge <c:if test="${'OFFSHELVES'==paging.appStatus}">badge-warning</c:if>">已下架</span>&nbsp;&nbsp;
                      <span onclick="searchApp('REJECTED');" style="cursor: pointer" class="badge <c:if test="${'REJECTED'==paging.appStatus}">badge-warning</c:if>">已拒绝</span>
                  </td>
                </tr>
              </tbody>
            </table>
            </form>

            <div class="widget-box">
                <div class="widget-title">
                        <span class="icon">
                            <i class="icon-file"></i>
                        </span>
                    <h5>结果</h5>
                </div>
                <div class="widget-content nopadding">
                    <ul class="recent-posts">
                        <c:forEach items="${apps}" var="app">
                        <li>
                            <div class="user-thumb">
                                <img style="width: 50px; height: 50px;" src="${fileRequestHost}${app.appKey}/${app.iconActualFileName}"/>
                                <c:if test="${app.recommend}">
                                    <span class="recommend">推荐</span>
                                </c:if>
                            </div>
                            <div class="article-post">
                                <div class="fr">
                                    <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_APP_STATUS">
                                    <c:choose>
                                        <c:when test="${app.status == 'CREAETED'}">
                                            <a href="javascript:void(0);" onclick="appStatusChangeConfirm('${app.id}', 'PASSED')" class="btn btn-success btn-mini">通过审核</a>
                                            <a href="javascript:void(0);" onclick="appStatusChangeConfirm('${app.id}', 'REJECTED')" class="btn btn-info btn-mini">拒绝通过</a>
                                        </c:when>
                                        <c:when test="${app.status == 'PASSED'}">
                                            <a href="javascript:void(0);" onclick="appStatusChangeConfirm('${app.id}', 'OFFSHELVES')" class="btn btn-danger btn-mini">下架应用</a>
                                        </c:when>
                                        <c:when test="${app.status == 'OFFSHELVES'}">
                                            <a href="javascript:void(0);" onclick="appStatusChangeConfirm('${app.id}', 'PASSED')" class="btn btn-inverse btn-mini">重新上架</a>
                                        </c:when>
                                        <c:when test="${app.status == 'REJECTED'}">
                                            <a href="javascript:void(0);" onclick="appStatusChangeConfirm('${app.id}', 'CREAETED')" class="btn btn-inverse btn-mini">重新审核</a>
                                        </c:when>
                                    </c:choose>
                                    </security:authorize>

                                    <a href="${fileRequestHost}${app.appKey}/${app.apkActualFileName}" target="_blank" class="btn btn-warning btn-mini">下载应用</a>
                                    <a href="${pageContext.request.contextPath}/backend/marketappform.html?marketAppId=${app.id}&appName=${paging.appName}&categoryId=${paging.categoryId}&appStatus=${paging.appStatus}" class="btn btn-primary btn-mini">编辑信息</a>
                                    <input type="button" style="width:54px;height:22px" value="查看历史" class="btn btn-success btn-mini"  onclick="openHistoryModel('${app.id}')">

                                </div>
                                <span class="user-info">
                                    <span style=font-weight:bold>名称</span>: ${app.appFullName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span style=font-weight:bold>版本</span>：${app.appVersion}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span style=font-weight:bold>类别</span>：${app.fullCategoryName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span style=font-weight:bold>下载</span>：${app.downloadTimes}次&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span style=font-weight:bold>大小</span>：${app.appSize}M&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
                                    <span style=font-weight:bold>状态</span>：${app.statusName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span style=font-weight:bold>更新时间</span>：${app.updateDate} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span style=font-weight:bold>评分</span>：${app.appScores}分 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <br/>
                                    <span style=font-weight:bold>简介</span>：${app.appDescription}
                                    <br/>
                                    <br/>
                                </span>

                            </div>
                        </li>
                        </c:forEach>

                        <li>
                            <ch:paging urlMapping="${pageContext.request.contextPath}/backend/marketappoverview.html" showGoTo="false" paging="${paging}"/>
                        </li>
                    </ul>


                </div>
            </div>
          </div>
        </div>
        </div>
      </div>
    </div>

</div>

<div id="marketapp-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要改变该应用的状态？
    </p>
</div>

<script type="text/javascript">

//     //让指定的DIV始终显示在屏幕正中间
//    function setDivCenter(divName){
//        var top = ($(window).height() - $(divName).height())/2;
//        var left = ($(window).width() - $(divName).width())/2;
//        var scrollTop = $(document).scrollTop();
//        var scrollLeft = $(document).scrollLeft();
//        $(divName).css( { position : 'absolute', 'top' : top + scrollTop, left : left + scrollLeft } ).show();
//    }

    document.onkeydown = function(e){
        if(!e) e = window.event;//火狐中是 window.event
        if((e.keyCode || e.which) == 13){
            jQuery("#app_search_button").click();
        }
    }

    function appStatusChangeConfirm(marketAppId, resetStatus) {
        jQuery("#marketapp-dialog-confirm").css("visibility", "visible");
//        setDivCenter(marketAppId);
        jQuery("#marketapp-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确  认": function() {
                        jQuery("#marketapp-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                        window.location.href = '${pageContext.request.contextPath}/backend/marketappstatus.html?marketAppId=' + marketAppId + '&current=${paging.currentPageNumber}&appName=${paging.appName}&categoryId=${paging.categoryId}&appStatus=${paging.appStatus}&resetStatus=' + resetStatus;
                    },
                    "取  消": function() {
                        jQuery("#marketapp-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    }

    function searchApp(appStatus) {
        jQuery("#appStatus").val(appStatus);
        jQuery('#app_search_form').submit();
    }

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
</script>

</body>
</html>