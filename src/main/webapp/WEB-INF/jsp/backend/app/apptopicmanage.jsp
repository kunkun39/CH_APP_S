<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
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
    <script src="${pageContext.request.contextPath}/dwr/engine.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/util.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/interface/SystemDWRHandler.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.22.custom.css" type="text/css"/>
</head>
<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/header.jsp"/>

<%--内容部分***********************************************************--%>

<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="javascript:void(0);" class="tip-bottom"><i class="icon-home"></i> 首页</a> <a href="javascript:void(0);" class="current">应用专题管理</a> </div>
  </div>

  <div class="container-fluid">
    <h4></h4>

    <div class="row-fluid">
      <div class="span12">

      <div class="widget-box">
        <div class="widget-title">
            <c:if test="${topicSize < 20}">
                <a href="javascript:void(0);" onclick="openTopicDialog('-1', 'add');">
                    <span class="icon">
                        <i class="icon-plus"></i>
                    </span>
                </a>
            </c:if>
            <h5>应用专题管理【友情提示：为了增强用户使用体验，专题数量20以内为最佳】</h5>
        </div>

          <div class="widget-content">
              <ul class="thumbnails">
                <c:forEach items="${topics}" var="topic">
                    <div id="menu">
                        <li class="span2">
                             <a class="thumbnail lightbox_trigger" href="${fileRequestHost}topic/${topic.topicIconName}">
                                <img style="width: 160px; height: 100px;" src="${fileRequestHost}topic/${topic.topicIconName}" alt="" >
                            </a>

                            <div class="actions">
                                <a title="" href="javascript:void(0);" onclick="openTopicDialog('${topic.id}', 'edit');"><i class="icon-pencil icon-white"></i></a>
                                <a title="" href="javascript:void(0);" onclick="topicDeleteConfirm('${topic.id}');"><i class="icon-remove icon-white"></i></a>
                            </div>
                            <div style="text-align: center;">
                                ${topic.topicName}
                            </div>
                        </li>
                    </div>
                </c:forEach>
              </ul>
        </div>
      </div>

      </div>
    </div>
  </div>

</div>

<div id="topic_delete_dialog" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要删除该应用专题？
    </p>
</div>

<script>
    jQuery(function() {
		settings = {
			align : 'center',									//Valid values, left, right, center
			top : 50, 											//Use an integer (in pixels)
			width : 600, 										//Use an integer (in pixels)
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
	function openTopicDialog(id, method) {
		settings.source = '${pageContext.request.contextPath}/backend/apptopicform.html?topicId=' + id + '&method=' + method;
		openModalPopup(settings);
	}

	function openModalPopup(obj) {
		modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
	}

    function showIconPlace() {
        var parentId = jQuery("#parentId").val();
        if(parentId > 0) {
            jQuery("#icon_place").css("display", "block");
        } else {
            jQuery("#icon_place").css("display", "none");
        }
    }

    function saveAppTopic(form) {
        var topicId = jQuery("#topicId").val();
        var topicName = jQuery("#topicName").val();
        if(topicName == null || topicName == '') {
            jQuery("#topicName_help").css("display", "block");
            return;
        } else {
            jQuery("#topicName_help").css("display", "none");
        }

        var topicIconName = jQuery("#topicIconName").val();
        if(topicId <= 0) {
            if(topicIconName == null || topicIconName == '') {
                jQuery("#topicIcon_help").css("display", "block");
                return;
            } else {
                if(!isImage(topicIconName)) {
                    jQuery("#topicIcon_format_help").css("display", "block");
                    return;
                }
            }
        }  else {
            if(topicIconName != null && topicIconName != '') {
                if(!isImage(topicIconName)) {
                    jQuery("#topicIcon_format_help").css("display", "block");
                    return;
                }
            }
        }

        form.submit();
    }

    function isImage(url) {
        var filename = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
        if (filename != "jpg" && filename != "gif" && filename != "png" && filename != "bmp" && filename != "jpeg") {
             return false;
        }
        return true;
    }

    function topicDeleteConfirm(topicId) {
        jQuery("#topic_delete_dialog").css("visibility", "visible");
        jQuery("#topic_delete_dialog").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确  认": function() {
                        jQuery("#topic_delete_dialog").css("visibility", "hidden");
                        jQuery(this).dialog("close")
                        window.location.href = '${pageContext.request.contextPath}/backend/topicdelete.html?topicId=' + topicId;
                    },
                    "取  消": function() {
                        jQuery("#topic_delete_dialog").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    }

</script>

</body>
</html>