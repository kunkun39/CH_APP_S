<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="widget-box">

                <div class="widget-title">
                    <span class="icon">
                        <i class="icon-align-justify"></i>
                    </span>
                    <h5>应用类别编辑</h5>
                </div>

                <div class="widget-content nopadding">
                    <form action="${pageContext.request.contextPath}/backend/clientimport.html" method="post" class="form-horizontal">
                        <input type="hidden" name="method" value="save"/>
                        <div class="control-group">
                            <label class="control-label">MAC地址范围 [必填]</label>
                            <div class="controls">
                                <input type="text" id="clientFrom" name="clientFrom" class="span20" maxlength="17"/>
                                <input type="text" id="clientTo" name="clientTo" class="span20" maxlength="17" onblur="caculateClientNumber();"/>
                                <span id="client_help" class="help-block" style="visibility: hidden;">MAC地址范围填写不正确</span>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn btn-success" onclick="closePopup(settings.fadeOutTime);">取 消</button>
                            <button type="button" class="btn btn-success" onclick="saveClients(this.form);">保 存</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>