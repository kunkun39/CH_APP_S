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
    <div id="breadcrumb"> <a style="font-size: 13px" href="javascript:void(0);" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> 首页</a> <a style="font-size: 13px" href="javascript:void(0);" class="current">文件访问服务器管理</a> </div>
  </div>

  <div class="container-fluid">
    <div class="row-fluid">
      <div class="span12">

        <div class="widget-box">
          <div class="widget-title" style="padding-top: 2px;">
            <a href="javascript:void(0);" onclick="openMultipHostDialog('-1', 'add');">
              <span class="icon">
                <i class="icon-plus"></i>
              </span>
            </a>
          </div>

          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th width="70%">服务器访问地址</th>
                  <th width="15%">状态</th>
                  <th>操作</th>
                </tr>
              </thead>

              <tbody>
                <c:forEach items="${hosts}" var="host">
                    <tr class="gradeX">
                      <td>${host.hostName}</td>
                      <td>
                        <c:if test="${host.hostEnabled}">
                            在用
                        </c:if>
                        <c:if test="${!host.hostEnabled}">
                            停止使用
                        </c:if>
                      </td>
                      <td class="center">
                        <a href="javascript:void(0);" onclick="openMultipHostDialog('${host.id}', 'edit');" class="btn btn-primary btn-mini">编辑</a>

                        <c:if test="${host.hostEnabled}">
                            <a href="javascript:void(0);" onclick="return hostDisableConfirm('${host.id}');" class="btn btn-info btn-mini">停止使用</a>
                        </c:if>
                        <c:if test="${!host.hostEnabled}">
                            <a href="javascript:void(0);" onclick="return hostEnableConfirm('${host.id}');" class="btn btn-info btn-mini">开始使用</a>
                        </c:if>

                        <a ref="javascript:void(0);" onclick="return hostDeleteConfirm('${host.id}');" class="btn btn-danger btn-mini">删除</a>
                      </td>
                    </tr>
                </c:forEach>
              </tbody>

            </table>
          </div>
        </div>

      </div>
    </div>
  </div>

</div>

<div id="hostenable-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要停止使用该服务器？
    </p>
</div>

<div id="hostdisable-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要使用该服务器？
    </p>
</div>

<div id="hostdelete-dialog-confirm" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要删除该服务器？
    </p>
</div>

<script type="text/javascript">

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
	function openMultipHostDialog(id, method) {
		settings.source = '${pageContext.request.contextPath}/backend/hostform.html?hostId=' + id + '&method=' + method;
		openModalPopup(settings);
	}

	function openModalPopup(obj) {
		modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
	}

    function saveMultipHost(form) {
        var hostId = jQuery("#hostId").val();
        var hostName = jQuery("#hostName").val();
        if(hostName == null || hostName == '') {
            jQuery("#hostName_help").css("display", "block");
            return;
        } else {
            jQuery("#hostName_help").css("display", "none");
        }

        form.submit();
    }

    function hostDisableConfirm(hostId) {
        jQuery("#hostenable-dialog-confirm").css("visibility", "visible");
        jQuery("#hostenable-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确  认": function() {
                        jQuery("#hostenable-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                        window.location.href = '${pageContext.request.contextPath}/backend/hostenable.html?hostId=' + hostId;
                    },
                    "取  消": function() {
                        jQuery("#hostenable-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    }

    function hostEnableConfirm(hostId) {
        jQuery("#hostdisable-dialog-confirm").css("visibility", "visible");
        jQuery("#hostdisable-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确  认": function() {
                        jQuery("#hostdisable-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                        window.location.href = '${pageContext.request.contextPath}/backend/hostenable.html?hostId=' + hostId;
                    },
                    "取  消": function() {
                        jQuery("#hostdisable-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    }

    function hostDeleteConfirm(hostId) {
        jQuery("#hostdelete-dialog-confirm").css("visibility", "visible");
        jQuery("#hostdelete-dialog-confirm").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确  认": function() {
                        jQuery("#hostdelete-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                        window.location.href = '${pageContext.request.contextPath}/backend/hostdelete.html?hostId=' + hostId;
                    },
                    "取  消": function() {
                        jQuery("#hostdelete-dialog-confirm").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    }
</script>

</body>
</html>