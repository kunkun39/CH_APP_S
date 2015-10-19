<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>

<div class="container-fluid" style="height: 500px; overflow-y: auto;">
    <div class="row-fluid">
      <div class="span12">

        <c:forEach items="${histories}" var="history">
        <div class="widget-box">
          <div class="widget-title" style="padding-top: 2px;">
            <h5 style="color: navy;">${history.changePerson} 修改于 ${history.changeDate}</h5>
          </div>

          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th width="30%">修改字段</th>
                  <th width="70%">修改内容</th>
                </tr>
              </thead>

              <tbody>
                <c:forEach items="${history.fieldDetails}" var="details">
                    <tr class="gradeX">
                        <c:if test="${details.field == 'NEW'}">
                            <td>创建新应用</td>
                        </c:if>
                        <c:if test="${details.field != 'NEW'}">
                            <td>${details.field}</td>
                        </c:if>
                        <td>${details.description}</td>
                    </tr>
                </c:forEach>
              </tbody>

            </table>
            </div>
          </div>
          </c:forEach>

      </div>
    </div>
</div>

</body>
</html>