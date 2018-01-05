<%@ page import="com.blockchain.timebank.entity.ViewVolunteerRequestDetailEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Mihaly
  Date: 02/01/2018
  Time: 13:26
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,user-scalable=0">
    <title>已发布</title>
    <!-- 引入 WeUI -->
    <link rel="stylesheet" href="../css/weui.min.css" />
</head>
<body>
<%
    List<ViewVolunteerRequestDetailEntity> requestList = (List<ViewVolunteerRequestDetailEntity>) request.getAttribute("requestPublished");
%>
<div class="page">
    <div class="page__bd" style="height: 100%;">
        <div class="weui-cell" style="font-size: 15px;background-color: #f8f8f8;position:fixed;width:100%;z-index:1000;padding-bottom:0px">
            <div id="return" onclick="history.go(-1)"><img src="../img/返回.png" width="20" height="15"alt="">已发布</div>
        </div>
        <div class="weui-navbar" style="position:fixed; top:34px">
            <div class="weui-navbar__item weui-bar__item_on"id="navbar1"style="color: #7ACF41">
                已发布
            </div>
            <div class="weui-navbar__item"id="navbar2">
                待确认
            </div>
            <div class="weui-navbar__item"id="navbar3">
                待服务
            </div>
            <div class="weui-navbar__item"id="navbar4">
                待付款
            </div>
            <div class="weui-navbar__item"id="navbar5">
                已完成
            </div>
        </div>
        <div class="weui-tab">
            <!--tab_pannel为navbar中自带的显示界面详细-->
            <div class="weui-tab__panel" style="padding-bottom: 50px; padding-top: 70px">
                <!--以下为界面显示部分，需要循环的部分，以下可修改-->
                <%
                    for (int i=0;i<requestList.size();i++) {
                %>
                <div class="weui-panel__bd">
                    <div class="weui-media-box weui-media-box_appmsg">
                        <div class="weui-media-box__hd">
                            <img class="weui-media-box__thumb" width="60" height="60"src="../img/服务名称/<%out.print(requestList.get(i).getServiceName());%>.png" alt="">
                        </div>
                        <div class="weui-media-box__bd">
                            <div class="weui-flex">
                                <div class="weui-flex__item"diaplay="none"><h4 class="weui-media-box__title" style="color: #7ACF41;"><%out.print(requestList.get(i).getServiceName());%></h4></div>
                                <div class="weui-flex__item"display="none"></div>
                                <div class="weui-flex__item"display="none"></div>
                            </div>
                            <p class="weui-media-box__desc">需求类型:<%out.print(requestList.get(i).getServiceType().replace("服务", "需求"));%></p>
                            <p class="weui-media-box__desc">开始时间:
                                <%
                                    Timestamp timestamp = requestList.get(i).getBeginTime();
                                    Date date = new Date(timestamp.getTime());
                                    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    out.print(bartDateFormat.format(date));
                                %></p>
                            <p class="weui-media-box__desc">结束时间:
                                <%
                                    Timestamp timestamp2 = requestList.get(i).getEndTime();
                                    Date date2 = new Date(timestamp2.getTime());
                                    SimpleDateFormat bartDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                                    out.print(bartDateFormat2.format(date2));
                                %></p>
                            <p class="weui-media-box__desc">需求地址:<%out.print(requestList.get(i).getAddress());%></p>
                            <p class="weui-media-box__desc">预留联系方式:<%out.print(requestList.get(i).getUserPhone());%></p>
                        </div>
                    </div>
                </div>
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-flex">
                            <div class="weui-flex__item"diaplay="none"></div>
                            <div class="weui-flex__item"display="none"></div>
                            <div class="weui-flex__item"display="none"></div>
                            <div class="weui-flex__item"><a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_primary">查看</a></div>
                        </div>
                    </div>
                </div>
                <div style="background-color: #f8f8f8; height:10px;"></div>
                <%}%>
                <!--一个订单详情结束，以上可修改-->
            </div>
            <!--weui-tab_panel的结束位置-->
        </div>
    </div>
    <div class="weui-tabbar" style="height: 50px">
        <a href="${pageContext.request.contextPath}/index" class="weui-tabbar__item">
            <img src="../img/首页.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">首页</p>
        </a>
        <a href="${pageContext.request.contextPath}/publish/category" class="weui-tabbar__item">
            <img src="../img/服务.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label" style="font-size: 10px;color: #28a921;">服务</p>
        </a>
        <a href="${pageContext.request.contextPath}/publish/activities_category" class="weui-tabbar__item">
            <img src="../img/活动.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">活动</p>
        </a>
        <a href="${pageContext.request.contextPath}/user/" class="weui-tabbar__item">
            <img src="../img/我的.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">我</p>
        </a>
    </div>
</div>
<script src="../js/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("#navbar1").on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            location.href="queryRequestAlreadyPublish";
        });
        $("#navbar2").on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            location.href="queryRequestWaitingConfirm";

        });
        $("#navbar3").on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            location.href="queryRequestWaitingService";

        });
        $("#navbar4").on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            location.href="queryRequestWaitingCollect";
        });
        $("#navbar5").on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            location.href="queryRequestAlreadyComplete";
        });
    });
</script>

</body>
</html>