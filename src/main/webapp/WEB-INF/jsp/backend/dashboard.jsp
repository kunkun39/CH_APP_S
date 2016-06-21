<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>应用市场后台管理系统</title><meta charset="UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/uniform.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select2.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-media.css" class="skin-color" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/module.css" />

    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/maruti.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/engine.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/util.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/interface/SystemDWRHandler.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.22.custom.css" type="text/css"/>
</head>

<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/header.jsp"/>

<%--内容部分***********************************************************--%>



<div id="content">
    <div id="content-header">
        <div id="breadcrumb">
            <a style="font-size:13px" href="javascript:void(0);" class="tip-bottom"><i class="icon-home"></i> 首页</a>
            <a style="font-size:13px" href="javascript:void(0);" class="current">前台效果展示</a>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">

                <%--效果部分--%>
                <div class="widget-box">
                    <div class="widget-title">
                        <span class="icon">
                            <i class="icon-play-circle"></i>
                        </span>
                        <h5>应用展示效果图</h5>
                        <c:set var="buttons_show" value="hidden"/>
                        <c:set var="info_show" value="hidden"/>
                        <c:choose>
                            <c:when test="${record.changeUserId == currentUserId && !record.commit}">
                                <c:set var="buttons_show" value="visible"/>
                            </c:when>
                            <c:when test="${record.changeUserId != currentUserId && !record.commit}">
                                <c:set var="info_show" value="visible"/>
                            </c:when>
                        </c:choose>
                        <div id="commit_buttons" class="buttons" style="visibility: ${buttons_show};">
                            <button onclick="changeCommit(true);" class="btn btn-mini"><i class="icon-refresh"></i>  确认更新</button>
                            <button onclick="changeCommit(false);" class="btn btn-mini"><i class="icon-fullscreen"></i>  取消更新</button>
                        </div>
                        <div id="commit_info" class="buttons"  style="visibility: ${info_show};">
                            用户${record.changeUsername}正在修改推荐页面...
                        </div>
                    </div>

                    <div class="widget-content">
                        <%--推荐部分--%>
                        <div class="row-fluid">
                            <div class="span12 btn-icon-pg">
                                <ul>
                                    <li onclick="resetPageNumber(1);"><i class="icon-star" ></i> 首页</li>
                                    <li onclick="resetPageNumber(2);"><i class="icon-th-list"></i> ${category_1.categoryName}</li>
                                    <li onclick="resetPageNumber(3);"><i class="icon-gift"></i> ${category_2.categoryName}</li>
                                    <li onclick="resetPageNumber(4);"><i class="icon-briefcase"></i> ${category_3.categoryName}</li>
                                    <li onclick="resetPageNumber(5);"><i class="icon-adjust"></i> 专题</li>
                                </ul>
                            </div>

                            <jsp:include page="recommend/recommendpage1.jsp"/>
                            <jsp:include page="recommend/recommendpage2.jsp"/>
                            <jsp:include page="recommend/recommendpage3.jsp"/>
                            <jsp:include page="recommend/recommendpage4.jsp"/>
                            <jsp:include page="recommend/recommendpage5.jsp"/>
                        </div>

                        <%--推荐操作部分--%>
                        <br/>
                        <br/>

                        <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_APP_RECOMMEND">
                            <table class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th style="font-size:13px">查询条件</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                    点击查看文件位置:
                                    <a class="lightbox_trigger" href="${pageContext.request.contextPath}/images/examlpe.jpg">
                                        <img width="100px" height="50px" alt="" src="${pageContext.request.contextPath}/images/examlpe.jpg"/>
                                    </a>
                                    应用类别：<select id="categoryId" name="categoryId" style="height: 30px;" >
                                        <option value="-1">全 部
                                        </option>
                                        <c:forEach items="${categories}" var="category">
                                            <option value="${category.id}" >${category.categoryName}</option>
                                            <c:forEach items="${category.children}" var="child" varStatus="counter">
                                                <option value="${child.id}">[${counter.count}] ${category.categoryName} -> ${child.categoryName}</option>
                                            </c:forEach>
                                        </c:forEach>
                                    </select>
                                     &nbsp;
                                     应用名称：<input type="text" id="appName" name="appName" class="text" style="height: 25px;"/>
                                        &nbsp;
                                        <i id="app_search_button" class="icon icon-search" style="cursor: pointer" onclick="searhRecommendApps();"></i>

                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="control-group">
                                            <label class="control-label">推荐到</label>
                                            <div id="recommend_options" class="controls">
                                                <input type="radio" name="rem_radios" value="1"/> 位置一&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="2"/> 位置二&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="3"/> 位置三&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="4"/> 位置四&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="5"/> 位置五&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="6"/> 位置六&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="7"/> 位置七&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="8"/> 位置八&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="9"/> 位置九&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="10"/> 位置十&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="11"/> 位置十一&nbsp;&nbsp;
                                                <input type="radio" name="rem_radios" value="12"/> 位置十二&nbsp;&nbsp;
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                            <%--！搜索结果部分--%>
                            <div class="widget-content nopadding">
                                <ul id="search_list_content" class="recent-posts">

                                </ul>
                            </div>
                        </security:authorize>

                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<div id="notselected-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请选择你需要推荐的位置？
    </p>
</div>

<div id="recommend-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你要推荐该应用到该位置？
    </p>
</div>

<div id="commit-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        当前正有人正在修改推荐页面，请稍后修改。
    </p>
</div>

<div id="update-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        确认后，用户将会看见现在推荐内容，确认是否要更新现在的推荐？
    </p>
</div>

<div id="cancel-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        确认是否要取消现在的推荐？
    </p>
</div>

<div id="duplicate-dialog-confirm" title="消息对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        该应用已经推荐到其他页面，是否继续？
    </p>
</div>

<script type="text/javascript">
    var fileRequestHost = '${fileRequestHost}';
    var currentPage = 1;

    // enter key can search app directly
    document.onkeydown = function(e){
        if(!e) e = window.event;//火狐中是 window.event
        if((e.keyCode || e.which) == 13){
            jQuery("#app_search_button").click();
        }
    }

    // reset the page number currently
    function resetPageNumber(pageNumber) {
        currentPage = pageNumber;
        for(var i=1; i<=5; i++) {
            if(i == currentPage) {
                 jQuery("#part_" + i).css("display", "block");
            } else {
                jQuery("#part_" + i).css("display", "none");
            }
        }
    }

    // search app
    function searhRecommendApps() {
        var contentContainer = jQuery("#search_list_content");
        contentContainer.html("");

        var categoryId = jQuery("#categoryId").val();
        var appName = jQuery("#appName").val();

        SystemDWRHandler.obtainRecommendApps(-1, categoryId, appName, function(result) {
            var statisticData = JSON.parse(result);
            var newContent = "<li><div class=\"article-post\" style=\"text-align: center;\"><span class=\"user-info\"> 最多显示20条查询的数据</span></div></li>";
            for(var i=0; i<statisticData.length; i++) {
                var appValues = statisticData[i];
                newContent = newContent + "<li><div class=\"fr\"><a class=\"btn btn-primary btn-mini\" onclick=\"recommendAppTo('" + appValues.appId + "')\">推荐</a></div>" +
                        "<div class=\"user-thumb\"><img width=\"50\" height=\"50\" src=\"" + fileRequestHost + appValues.appKey + "/" + appValues.iconActualFileName + "\"></div>" +
                        "<div class=\"article-post\"><span class=\"user-info\"> 名称:</span>" + appValues.appFullName + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"user-info\"> 版本:</span>" + appValues.appVersion + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"user-info\"> 类别:</span>" + appValues.appCategory +
                        "</span><p><span class=\"user-info\"> 描述:</span>" + appValues.appDescription + "<br/><br/></p></div></li>";
                }
            contentContainer.html(newContent);
        });
    }

    // recommend app
    function recommendAppTo(appId) {
        //1 - check is position is selected or not
        var itemPostion = $("input[name='rem_radios']:checked").val();
        if(itemPostion == null || itemPostion == undefined || itemPostion == "") {
            jQuery("#notselected-dialog-confirm").css("visibility", "visible");
            jQuery("#notselected-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "取  消": function() {
                        jQuery("#notselected-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
            return;
        }

        //2 - recommend app to which page and position, first check is any other position recommend
        SystemDWRHandler.obtainCheckIsAppRecommend(appId, currentPage, itemPostion, function(result) {
            var boxRecommend = JSON.parse(result);
            var pageIndex = boxRecommend.page_index;
            var positionIndex = boxRecommend.position_index;

            //2.1 - if recommend, comfirm with user
            if(pageIndex > 0) {
                jQuery("#duplicate-dialog-confirm").css("visibility", "visible");
                jQuery("#duplicate-dialog-confirm").dialog({
                    resizable: false,
                    height:160,
                    width:300,
                    modal: true,
                    buttons: {
                        "确  认": function() {
                            jQuery("#duplicate-dialog-confirm").css("visibility", "hidden");
                            jQuery(this).dialog("close");
                            confirmRecommendApp(itemPostion, appId);
                        },
                        "取  消": function() {
                            jQuery("#duplicate-dialog-confirm").css("visibility", "hidden");
                            jQuery(this).dialog("close");
                        }
                    }
                });
            } else {
                //2.2 - not recommend, update the db
                confirmRecommendApp(itemPostion, appId);
            }
        });
    }

    function confirmRecommendApp(itemPosition, appId) {
        jQuery("#recommend-dialog-confirm").css("visibility", "visible");
        jQuery("#recommend-dialog-confirm").dialog({
            resizable: false,
            height:160,
            width:300,
            modal: true,
            buttons: {
                "确  认": function() {
                        jQuery("#recommend-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");

                        SystemDWRHandler.updateBoxRecommendPosition(currentPage, itemPosition, appId, ${currentUserId}, function(result) {
                            if(result) {
                                jQuery("#commit_buttons").css("visibility", "visible");
                                jQuery("#commit_info").css("visibility", "hidden");

                                SystemDWRHandler.obtainRecommendApps(appId, -1, "", function(result) {
                                    var appValues = JSON.parse(result)[0];
                                    var path = "";
                                    if(itemPosition <= 6) {
                                        path = fileRequestHost + appValues.appKey + "/" + appValues.posterActualFileName;
                                    } else {
                                        path = fileRequestHost + appValues.appKey + "/" + appValues.iconActualFileName;
                                    }
                                    jQuery("#" + currentPage + "_pos_img_" + itemPosition).attr("src", path);
                                    jQuery("#" + currentPage + "_pos_name_" + itemPosition).html(appValues.appFullName);
                                });
                            } else {
                                jQuery("#commit_buttons").css("visibility", "hidden");
                                jQuery("#commit_info").css("visibility", "visible");

                                jQuery("#commit-dialog-confirm").css("visibility", "visible");
                                jQuery("#commit-dialog-confirm").dialog({
                                    resizable: false,
                                    height:160,
                                    width:300,
                                    modal: true,
                                    buttons: {
                                        "取  消": function() {
                                            jQuery("#commit-dialog-confirm").css("visibility", "hidden");
                                            jQuery(this).dialog("close");
                                        }
                                    }
                                });
                            }
                        });
                    },
                "取  消": function() {
                    jQuery("#notselected-dialog-confirm").css("visibility", "hidden");
                    jQuery(this).dialog("close");
                }
            }
        });
    }

    function changeCommit(update) {
        if(update) {
            jQuery("#update-dialog-confirm").css("visibility", "visible");
            jQuery("#update-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确  认": function() {
                        window.location.href = "${pageContext.request.contextPath}/backend/updateboxrecommend.html";
                    },
                    "取  消": function() {
                        jQuery("#update-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
        } else {
            jQuery("#cancel-dialog-confirm").css("visibility", "visible");
            jQuery("#cancel-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确  认": function() {
                        window.location.href = "${pageContext.request.contextPath}/backend/cancelboxrecommend.html";
                    },
                    "取  消": function() {
                        jQuery("#cancel-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
        }
    }


</script>
</body>
</html>