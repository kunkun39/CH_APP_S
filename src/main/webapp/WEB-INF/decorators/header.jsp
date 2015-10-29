<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>

<!--Header-part-->
<div id="header">
  <h1><a href="javascript:void(0);">广电应用市场后台管理系统</a></h1>
</div>
<!--close-Header-part-->

<!--top-Header-messaages-->
<div class="btn-group rightzero">
    <a class="top_message tip-left" title="Manage Files"><i class="icon-file"></i></a>
    <a class="top_message tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
    <a class="top_message tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
    <a class="top_message tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
</div>
<!--close-top-Header-messaages-->

<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li class="" ><a title="" href="javascript:void(0);"><i class="icon icon-user"></i> <span class="text"><ch:user/></span></a></li>
    <li class=""><a title="" href="${pageContext.request.contextPath}/backend/userchangepassword.html"><i class="icon icon-cog"></i> <span class="text">修改密码</span></a></li>
    <li class=""><a title="" href="${pageContext.request.contextPath}/j_spring_security_logout"><i class="icon icon-share-alt"></i> <span class="text">退出</span></a></li>
  </ul>
</div>
<!--close-top-Header-menu-->

<div id="sidebar"><a href="javascript:void(0);" class="visible-phone"><i class="icon icon-home"></i> 首页</a>
  <ul>
    <li class="active"><a href="${pageContext.request.contextPath}/backend/dashboard.html"><i class="icon icon-home"></i> <span>首页</span></a></li>

    <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_APP_INFO,ROLE_APP_STATUS">
        <li class="submenu"> <a href="javascript:void(0);"><i class="icon icon-th"></i> <span>应用管理</span> <span class="label">5</span></a>
          <ul>
            <li><a href="${pageContext.request.contextPath}/backend/appcategoryoverview.html">应用类别管理</a></li>
            <li><a href="${pageContext.request.contextPath}/backend/marketappoverview.html">应用信息管理</a></li>
            <li><a href="${pageContext.request.contextPath}/backend/appmust.html?install=true">应用强制安装管理</a></li>
              <li><a href="${pageContext.request.contextPath}/backend/appmust.html?install=false">应用强制卸载管理</a></li>
              <li><a href="${pageContext.request.contextPath}/backend/luncherrecommend.html">Luncher应用推荐管理</a></li>
          </ul>
        </li>
    </security:authorize>

    <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_APP_STATISTIC">
    <li class="submenu"> <a href="javascript:void(0);"><i class="icon icon-shopping-cart"></i> <span>统计管理</span> <span class="label">2</span></a>
      <ul>
        <li><a href="${pageContext.request.contextPath}/backend/marketappranklist.html">应用下载排行榜</a></li>
        <li><a href="${pageContext.request.contextPath}/backend/appstatistic.html">应用下载统计</a></li>
      </ul>
    </li>
    </security:authorize>

    <security:authorize ifAnyGranted="ROLE_ADMIN">
    <li class="submenu"> <a href="javascript:void(0);"><i class="icon icon-user"></i> <span>系统设置</span> <span class="label">4</span></a>
      <ul>
        <li><a href="${pageContext.request.contextPath}/backend/useroverview.html">系统用户管理</a></li>
        <li><a href="${pageContext.request.contextPath}/backend/clientoverview.html">客户端用户管理</a></li>
        <li><a href="${pageContext.request.contextPath}/backend/clientversionshow.html?method=load">客户端版本管理</a></li>
         <li><a href="${pageContext.request.contextPath}/backend/clientbootimageshow.html?method=load">开机广告管理</a></li>
      </ul>
    </li>
    </security:authorize>

  </ul>
</div>