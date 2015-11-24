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
                    <h5>文件服务器添加</h5>
                </div>

                <div class="widget-content nopadding">
                    <form action="${pageContext.request.contextPath}/backend/hostform.html" method="post" class="form-horizontal" enctype="multipart/form-data">
                        <input type="hidden" id="hostId" name="hostId" value="${host.id}"/>
                        <input type="hidden" name="method" value="save"/>
                        <div class="control-group">
                            <label class="control-label">服务器地址 [必填]</label>
                            <div class="controls">
                                <input type="text" id="hostName" name="hostName" class="span20" value="${host.hostName}"/>
                                <span id="hostName_help" class="help-block" style="display: none;">服务器地址不能为空</span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"></label>
                            <div class="controls">
                                如:http://127.0.0.1/appmarket/uplaod/
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn btn-success" onclick="closePopup(settings.fadeOutTime);">取 消</button>
                            <button type="button" class="btn btn-success" onclick="saveMultipHost(this.form);">保 存</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>