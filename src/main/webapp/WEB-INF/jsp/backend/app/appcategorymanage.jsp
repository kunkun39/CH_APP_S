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
    <div id="breadcrumb"> <a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> 首页</a> <a href="#" class="current">应用类别管理</a> </div>
  </div>



  <div class="container-fluid">
    <h4></h4>
    <div class="alert alert-info alert-block">
        <h4 class="alert-heading">提示信息</h4><br/>
        <li>我们根据现有应用初始化部分分类信息【娱乐->影视、音乐、健康、亲子】【游戏->休闲、棋牌、动作、其他】【生活->工具、教育、咨询、新闻】【其他->】</li>
        <li>现在系统只接受4个一级分类，二级分类个数无限制</li>
        <li>我们给了一个样子，看点击图片查看</li>
        <br/>
        <a class="thumbnail lightbox_trigger" href="${pageContext.request.contextPath}/images/category/categoryexample.png">
            <img width="100px" height="50px" alt="" src="${pageContext.request.contextPath}/images/category/categoryexample.png"/>
        </a>
    </div>

    <div class="row-fluid">
      <div class="span12">

      <div class="widget-box">
        <div class="widget-title">
            <a href="#" onclick="openCategoryDialog('-1', 'add');">
            <span class="icon">
                <i class="icon-plus"></i>
            </span>
            </a>
            <h5>应用类别管理</h5>
        </div>

        <div class="widget-content">

            <c:forEach items="${categories}" var="category">
            <ul class="thumbnails">

                <li class="span2">
                    <a class="thumbnail lightbox_trigger" href="#">
                        <img style="width: 60px; height: 60px;" src="${pageContext.request.contextPath}/images/backgrounds/category2.jpg" alt="" >
                    </a>
                    <div class="actions">
                        <a title="" href="#" onclick="openCategoryDialog('${category.id}', 'edit');"><i class="icon-pencil icon-white"></i></a>
                        <c:if test="${category.children == null || empty category.children}">
                            <a title="" href="#" onclick="categoryDeleteConfirm('${category.id}');"><i class="icon-remove icon-white"></i></a>
                        </c:if>
                    </div>
                    <div style="text-align: center;">
                        ${category.categoryName}
                    </div>
                </li>

                <c:forEach items="${category.children}" var="child">
                    <li class="span1">
                        <a class="thumbnail lightbox_trigger" href="#">
                            <img style="width: 60px; height: 60px;" src="${fileRequestHost}category/${child.categoryIconName}" alt="" >
                        </a>
                        <div class="actions">
                            <a title="" href="#" onclick="openCategoryDialog('${child.id}', 'edit');"><i class="icon-pencil icon-white"></i></a>
                            <a title="" href="#" onclick="categoryDeleteConfirm('${child.id}');"><i class="icon-remove icon-white"></i></a>
                        </div>
                        <div style="text-align: center;">
                            ${child.categoryName}
                    </div>
                    </li>
                </c:forEach>

            </ul>

            </c:forEach>
        </div>
      </div>

      </div>
    </div>
  </div>

</div>

<div id="category_delete_dialog" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        请确认你是否要删除该应用类别？
    </p>
</div>

<div id="category_delete_refuse" title="确认对话框?" style="visibility: hidden;">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
        该类别下已经存在应用信息，请转移后再删除。
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
	function openCategoryDialog(id, method) {
		settings.source = '${pageContext.request.contextPath}/backend/appcategoryform.html?categoryId=' + id + '&method=' + method;
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

    function saveAppCategory(form) {
        var parentId = jQuery("#parentId").val();
        var categoryId = jQuery("#categoryId").val();
        var categoryName = jQuery("#categoryName").val();
        var oldParentId = jQuery("#oldParentId").val();
        if(categoryName == null || categoryName == '') {
            jQuery("#categoryName_help").css("display", "block");
            return;
        } else {
            jQuery("#categoryName_help").css("display", "none");
        }

        if(categoryId <= 0 && parentId > 0) {
            var categoryIconName = jQuery("#categoryIconName").val();
            if(categoryIconName == null || categoryIconName == '') {
                jQuery("#categoryIcon_help").css("display", "block");
                return;
            } else {
                if(!isImage(categoryIconName)) {
                    jQuery("#categoryIcon_format_help").css("display", "block");
                    return;
                }
            }
        }  else if(categoryId > 0 && parentId >=0 && oldParentId <= 0) {
            var categoryIconName = jQuery("#categoryIconName").val();
            if(categoryIconName == null || categoryIconName == '') {
                jQuery("#categoryIcon_help").css("display", "block");
                return;
            } else {
                if(!isImage(categoryIconName)) {
                    jQuery("#categoryIcon_format_help").css("display", "block");
                    return;
                }
            }
        } else {
            var categoryIconName = jQuery("#categoryIconName").val();
            if(categoryIconName != null && categoryIconName != '') {
                if(!isImage(categoryIconName)) {
                    jQuery("#categoryIcon_format_help").css("display", "block");
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

    function categoryDeleteConfirm(categoryId) {
        jQuery("#category_delete_dialog").css("visibility", "visible");
        jQuery("#category_delete_dialog").dialog({
                resizable: false,
                height:160,
                width:300,
                modal: true,
                buttons: {
                    "确  认": function() {
                        jQuery("#category_delete_dialog").css("visibility", "hidden");
                        jQuery("#category_delete_dialog").dialog("close");
                        SystemDWRHandler.obtainCategoryHasApps(categoryId, function(result) {
                            if(result) {
                                jQuery("#category_delete_refuse").css("visibility", "visible");
                                jQuery("#category_delete_refuse").dialog({
                                        resizable: false,
                                        height:160,
                                        width:300,
                                        modal: true,
                                        buttons: {
                                            "取  消": function() {
                                                jQuery("#category_delete_refuse").css("visibility", "hidden");
                                                jQuery(this).dialog("close");
                                            }
                                        }
                                    });
                            } else {
                                jQuery("#category_delete_refuse").dialog("close");
                                window.location.href = '${pageContext.request.contextPath}/backend/categorydelete.html?categoryId=' + categoryId;
                            }
                        });

                    },
                    "取  消": function() {
                        jQuery("#category_delete_dialog").css("visibility", "hidden");
                        jQuery(this).dialog("close");
                    }
                }
            });
    }

</script>

</body>
</html>