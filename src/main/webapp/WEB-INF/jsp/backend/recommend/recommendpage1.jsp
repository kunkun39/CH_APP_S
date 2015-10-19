<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div id="part_1" class="tv_main_bg">
    <div class="tv_main">

        <div class="leftside">
            <ul class="list">
                 <li class="c1">
                    <p class="icon">
                        <img src="${pageContext.request.contextPath}/images/category/icon_search.png" alt="">
                    </p>
                    <br/>
                    <a class="flag">&nbsp;搜索</a>
                </li>

                <li class="c2">
                    <p class="icon">
                        <img src="${pageContext.request.contextPath}/images/category/icon_ranklist.png" alt="">
                    </p>
                    <br/>
                    <a class="flag">&nbsp;排行榜</a>
                </li>

                <li class="c3">
                    <p class="icon">
                        <img src="${pageContext.request.contextPath}/images/category/icon_nativeapp.png" alt="">
                    </p>
                    <br/>
                    <a class="flag">&nbsp;本地下载</a>
                </li>

                <li class="c4">
                    <p class="icon">
                        <img src="${pageContext.request.contextPath}/images/category/icon_nesessary.png" alt="">
                    </p>
                    <br/>
                    <a class="flag">&nbsp;装机必备</a>
                </li>
            </ul>
        </div>

        <div class="centerside">
            <div class="preview">
                <div class="wrap">
                    <ch:recommend pageNumber="1" recommendPosition="1" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                </div>
            </div>

            <div class="preview1">
                <div class="wrap1">
                    <ch:recommend pageNumber="1" recommendPosition="2" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                </div>
                <div class="wrap1">
                    <ch:recommend pageNumber="1" recommendPosition="3" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                </div>
            </div>

            <div class="preview2">
                <div>
                    <div class="wrap2">
                        <ch:recommend pageNumber="1" recommendPosition="4" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                    </div>
                    <div class="wrap2">
                        <ch:recommend pageNumber="1" recommendPosition="5" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                    </div>
                </div>
            </div>

            <div class="preview3">
                <div class="wrap3">
                    <ch:recommend pageNumber="1" recommendPosition="6" fileRequestHost="${fileRequestHost}" recommends="${recommends}"/>
                </div>
            </div>
        </div>

         <div class="rightside">
            <ul class="list">
                <li class="c">
                    <ch:recommend pageNumber="1" recommendPosition="7" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="1" recommendPosition="8" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="1" recommendPosition="9" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="1" recommendPosition="10" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="1" recommendPosition="11" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>

                <li class="c">
                    <ch:recommend pageNumber="1" recommendPosition="12" fileRequestHost="${fileRequestHost}" recommends="${recommends}" subRecommend="true"/>
                </li>
            </ul>
        </div>
    </div>
</div>