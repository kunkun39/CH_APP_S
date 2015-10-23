<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div id="part_2" class="tv_main_bg" style="display: none;">
    <div class="tv_main">
        <div class="leftside">
            <ul style="list-style:none" class="list">
                <c:forEach items="${category_1.children}" var="child" varStatus="counter">
                    <c:if test="${counter.count <= 4}">
                        <li class="c${counter.count}">
                            <p class="icon">
                                <img src="${fileRequestHost}/category/${child.categoryIconName}" alt="">
                            </p>
                            <br/>
                            <%--><br/><--%>
                            <a class="flag">&nbsp;${child.categoryName}</a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>

        <div class="centerside">
            <div class="preview">
                <div class="wrap">
                    <ch:recommend pageNumber="2" recommendPosition="1" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                </div>
            </div>

            <div class="preview1">
                <div class="wrap1">
                    <ch:recommend pageNumber="2" recommendPosition="2" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                </div>
                <div class="wrap1">
                    <ch:recommend pageNumber="2" recommendPosition="3" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                </div>
            </div>

            <div class="preview2">
                <div>
                    <div class="wrap2">
                        <ch:recommend pageNumber="2" recommendPosition="4" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                    </div>
                    <div class="wrap2">
                        <ch:recommend pageNumber="2" recommendPosition="5" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                    </div>
                </div>
            </div>

            <div class="preview3">
                <div class="wrap3">
                    <ch:recommend pageNumber="2" recommendPosition="6" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                </div>
            </div>
        </div>

         <div class="rightside">
            <ul style="list-style:none" class="list">
                <li class="c">
                    <ch:recommend pageNumber="2" recommendPosition="7" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="2" recommendPosition="8" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="2" recommendPosition="9" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="2" recommendPosition="10" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="2" recommendPosition="11" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="2" recommendPosition="12" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>
            </ul>
        </div>

    </div>
</div>