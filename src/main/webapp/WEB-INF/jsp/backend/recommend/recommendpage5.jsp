<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div id="part_5" class="tv_main_bg" style="display: none;">
    <div class="tv_main">
        <div class="linec">
            <ul class="list">
                <c:forEach items="${topics}" var="topic" varStatus="counter">
                    <c:if test="${counter.count <= 10}">
                        <c:set var="linecolor" value="#660066"/>
                        <c:choose>
                            <c:when test="${counter.count == 1 || counter.count == 9}">
                                <c:set var="linecolor" value="#660066"/>
                            </c:when>
                            <c:when test="${counter.count == 2 || counter.count == 5}">
                                <c:set var="linecolor" value="#80db26"/>
                            </c:when>
                            <c:when test="${counter.count == 3 || counter.count == 10}">
                                <c:set var="linecolor" value="#ff6631"/>
                            </c:when>
                            <c:when test="${counter.count == 4 || counter.count == 7}">
                                <c:set var="linecolor" value="#cc3366"/>
                            </c:when>
                            <c:when test="${counter.count == 6}">
                                <c:set var="linecolor" value="#ff8025"/>
                            </c:when>
                            <c:when test="${counter.count == 8}">
                                <c:set var="linecolor" value="#6699ff"/>
                            </c:when>
                        </c:choose>

                        <c:set var="lineclass" value="c"/>
                        <c:set var="lineicon" value="icon"/>
                        <c:set var="lineflag" value="flag"/>
                        <%--<c:if test="${counter.count == 3 || counter.count == 5 || counter.count == 7 || counter.count == 9}">--%>
                            <%--<c:set var="lineclass" value="c1"/>--%>
                            <%--<c:set var="lineicon" value="icon1"/>--%>
                            <%--<c:set var="lineflag" value="flag1"/>--%>
                        <%--</c:if>--%>

                        <c:if test="${counter.count == 6}">
                            <br/>
                            <br/>
                        </c:if>

                        <li class="${lineclass}" style="background-color: ${linecolor}">
                            <p class="${lineicon}">
                                <img style="width: 100%; height: 80%" src="${fileRequestHost}/topic/${topic.topicIconName}" alt="">
                            </p>
                            <a class="${lineflag}">&nbsp;${topic.topicName}</a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>