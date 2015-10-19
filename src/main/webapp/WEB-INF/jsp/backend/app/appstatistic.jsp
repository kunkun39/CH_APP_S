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
    <script src="${pageContext.request.contextPath}/js/highslide/highcharts.js"></script>
    <script src="${pageContext.request.contextPath}/js/highslide/exporting.js"></script>
    <script src="${pageContext.request.contextPath}/js/highslide/dark-blue.js"></script>
    <script src="${pageContext.request.contextPath}/js/highslide/report/app_download_statistic.js"></script>
    <script src="${pageContext.request.contextPath}/dwr/engine.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/util.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/dwr/interface/SystemDWRHandler.js" type="text/javascript"></script>
</head>
<body>

<%--开头菜单部分***********************************************************--%>

<jsp:include page="/WEB-INF/decorators/header.jsp"/>

<%--内容部分***********************************************************--%>

<div id="commit_form_mask">

<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> 首页</a> <a href="#" class="current">应用下载排行榜</a> </div>
  </div>

<div class="container-fluid">
    <div class="row-fluid">
    <div class="span12">

      <div class="widget-box">
          <div class="widget-title"><a href="#"><span class="icon"> <i class="icon-signal"></i> </span></a>
            <h5>应用统计查询</h5>
          </div>

          <div class="widget-content">
            <form id="app_statistic_form" action="${pageContext.request.contextPath}/backend/marketappoverview.html" method="post">
            <input type="hidden" id="appStatus" name="appStatus" value="${paging.appStatus}">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>统计条件</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                      应用类别：
                      <select id="categoryId" name="categoryId" style="height: 30px;">
                          <option value="-1">全 部</option>
                          <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.categoryName}</option>
                            <c:forEach items="${category.children}" var="child">
                                <option value="${child.id}">${category.categoryName} -> ${child.categoryName}</option>
                            </c:forEach>
                          </c:forEach>
                      </select>
                      &nbsp;&nbsp;&nbsp;&nbsp;
                      统计时间：
                      <select id="sta_year" name="sta_year" style="height: 30px;">
                          <c:forEach begin="2015" end="2020" step="1" var="sta_year">
                              <option value="${sta_year}" <c:if test="${currentYear==sta_year}">selected="true"</c:if>>${sta_year}年</option>
                          </c:forEach>
                      </select>
                      <select id="sta_month" name="sta_month" style="height: 30px;">
                          <option value="0">全部</option>
                          <c:forEach begin="1" end="12" step="1" var="sta_month">
                              <option value="${sta_month}">${sta_month}月</option>
                          </c:forEach>
                      </select>
                      &nbsp;
                     <i class="icon icon-search" onclick="generateReport();"></i>
                  </td>
                </tr>
              </tbody>
            </table>
            </form>

            <div class="row-fluid">
                <div class="span12">
                    <div class="widget-box">
                        <div class="widget-title">
                            <span class="icon">
                                <i class="icon-signal"></i>
                            </span>
                            <h5>应用下载量统计表-按时间</h5>
                        </div>
                        <div class="widget-content">
                            <div id="app_column_sta" class="bars"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row-fluid">
                <div class="span6">
                    <div class="widget-box">
                        <div class="widget-title">
                            <span class="icon">
                                <i class="icon-signal"></i>
                            </span>
                            <h5>应用下载量比例表-按分类</h5>
                        </div>
                        <div class="widget-content">
                            <div id="app_pie_sta" class="pie"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      </div>
    </div>
    </div>
</div>
</div>
</div>


<script type="text/javascript">

    jQuery(document).ready(function(){
        generateReport();
    });

        function generateReport() {
        var categoryId = jQuery("#categoryId").val();
        var sta_year = jQuery("#sta_year").val();
        var sta_month = jQuery("#sta_month").val();
        renew_column_container(categoryId, sta_year, sta_month);
        renew_pie_container(categoryId, sta_year, sta_month);
    }

    function renew_column_container(categoryId, year, month) {
        if("0" == month) {
            //全年的报表
            //一个月的报表
            SystemDWRHandler.obtainAppDownloadColumnData(categoryId, year, month, function(result) {
                var statisticData = JSON.parse(result);
                var total = statisticData[0].total.split(",");
                var days = statisticData[0].days;
                app_column_sta_container.xAxis.categories = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];

                var newData = new Array();
                var totalUpdateTimes = 0;
                for(var i=0; i<total.length; i++) {
                    newData[i] = parseInt(total[i]);
                    totalUpdateTimes = totalUpdateTimes + parseInt(total[i]);
                }
                app_column_sta_container.series[0].data = newData;

                app_column_sta_container.title.text = "用户下载应用下载统计表";

                new Highcharts.Chart(app_column_sta_container);
            });
        } else {
            //一个月的报表
            SystemDWRHandler.obtainAppDownloadColumnData(categoryId, year, month, function(result) {
                var statisticData = JSON.parse(result);
                var total = statisticData[0].total.split(",");

                var days = statisticData[0].days;
                if(parseInt(days) == 28) {
                    app_column_sta_container.xAxis.categories = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28'];
                } else if (parseInt(days) == 29) {
                    app_column_sta_container.xAxis.categories = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29'];
                } else if (parseInt(days) == 30) {
                    app_column_sta_container.xAxis.categories = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'];
                } else {
                    app_column_sta_container.xAxis.categories = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'];
                }

                var newData = new Array();
                var totalUpdateTimes = 0;
                for(var i=0; i<total.length; i++) {
                    newData[i] = parseInt(total[i]);
                    totalUpdateTimes = totalUpdateTimes + parseInt(total[i]);
                }
                app_column_sta_container.series[0].data = newData;
                app_column_sta_container.title.text = "用户下载应用下载统计表";
                new Highcharts.Chart(app_column_sta_container);
            });
        }
    }

    function renew_pie_container(categoryId, year, month) {
        SystemDWRHandler.obtainAppDownloadPienData(categoryId, year, month, function(result) {
            var statisticData = JSON.parse(result);
            if(statisticData.length <= 0) {
                return;
            }

            var version = statisticData[0].categoryGroup.split(",");
            var total=statisticData[0].total.split(",");

            var newData = new Array();
            var  temp=0;
            for(var i=0; i<total.length; i++) {
                var inner = new Array();
                inner[0]=version[i];
                inner[1]=parseInt(total[i]);
                temp+=inner[1];
                newData[i]=inner;
            }

            var extraData=new Array();
            if(temp<=0){
                for(var i=0; i<total.length; i++) {
                    var inner = new Array();
                    inner[0]=version[i];
                    inner[1]=parseInt(total[i])+1;
                    extraData[i]=inner;
                }
                app_pie_sta_container.series[0].data = extraData;
            }else{
                app_pie_sta_container.series[0].data = newData;
            }

            app_pie_sta_container.title.text = "用户下载应用下载统计表";
            new Highcharts.Chart(app_pie_sta_container);
        });
    }

</script>
</body>
</html>