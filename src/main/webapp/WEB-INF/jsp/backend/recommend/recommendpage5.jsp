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

                        <c:if test="${counter.count == 6}">
                            <br/>
                            <br/>
                        </c:if>

                        <li class="c" style="background-color: ${linecolor}">
                            <p class="icon">
                                <img style="width: 100%; height: 80%" src="${fileRequestHost}/topic/${topic.topicIconName}" alt="">
                            </p>
                            <a class="flag">&nbsp;${topic.topicName}</a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>

        </div>
    </div>
</div>