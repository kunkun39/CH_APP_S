<!DOCTYPE html>
<html lang="en">

<head>
<title>Maruti Admin</title><meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fullcalendar.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-style.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/maruti-media.css" class="skin-color" />
</head>
<body>

<!--Header-part-->
<div id="header">
  <h1><a href="http://www.mafiashare.net">Shared on www.MafiaShare.net</a></h1>
</div>
<!--close-Header-part-->

<!--top-Header-messaages-->
<div class="btn-group rightzero"> <a class="top_message tip-left" title="Manage Files"><i class="icon-file"></i></a> <a class="top_message tip-bottom" title="Manage Users"><i class="icon-user"></i></a> <a class="top_message tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a> <a class="top_message tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a> </div>
<!--close-top-Header-messaages-->

<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li class="" ><a title="" href="#"><i class="icon icon-user"></i> <span class="text">Profile</span></a></li>
    <li class=" dropdown" id="menu-messages"><a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text">Messages</span> <span class="label label-important">5</span> <b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a class="sAdd" title="" href="#">new message</a></li>
        <li><a class="sInbox" title="" href="#">inbox</a></li>
        <li><a class="sOutbox" title="" href="#">outbox</a></li>
        <li><a class="sTrash" title="" href="#">trash</a></li>
      </ul>
    </li>
    <li class=""><a title="" href="#"><i class="icon icon-cog"></i> <span class="text">Settings</span></a></li>
    <li class=""><a title="" href="login.html"><i class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
  </ul>
</div>

<%--<script src="${pageContext.request.contextPath}/js/excanvas.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/jquery.ui.custom.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/jquery.flot.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/jquery.flot.resize.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/jquery.peity.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/fullcalendar.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/maruti.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/maruti.dashboard.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/maruti.chat.js"></script>--%>
</script>
</body>

</html>
