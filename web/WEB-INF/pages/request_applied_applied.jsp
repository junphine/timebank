<%@ page import="com.blockchain.timebank.entity.ViewVolunteerRequestMatchDetailEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: Mihaly
  Date: 02/01/2018
  Time: 21:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,user-scalable=0">
    <title>已预约</title>
    <!-- 引入 WeUI -->
    <link rel="stylesheet" href="../css/weui.min.css" />
</head>
<body>

<div class="page">

    <div class="page__bd" style="height: 100%;">
        <div class="weui-cell" style="font-size: 15px;background-color: #f8f8f8;position:fixed;width:100%;z-index:1000;padding-bottom:0px">
            <div class="weui-flex__item"id="return" onclick="history.go(-1)" >
                <p><img src="../img/返回.png" width="20" height="15"alt="">已预约</p>
            </div>
            <div class="weui-flex__item"></div>
            <div class="weui-flex__item"></div>
        </div>
        <div class="weui-navbar" style="position:fixed; top:34px">
            <div class="weui-navbar__item weui-bar__item_on"id="navbar1">
                已预约
            </div>
            <div class="weui-navbar__item"id="navbar2">
                待服务
            </div>
            <div class="weui-navbar__item"id="navbar3">
                待收款
            </div>
            <div class="weui-navbar__item"id="navbar4">
                已完成
            </div>
        </div>
        <!--tab_pannel为navbar中自带的显示界面详细-->
        <div class="weui-tab">
            <div class="weui-tab__panel" style="padding-bottom: 50px; padding-top: 70px">
                <!--以下为界面显示部分，需要循环的部分，以下可修改-->
                <%
                    List<ViewVolunteerRequestMatchDetailEntity> matchDetailList = (List<ViewVolunteerRequestMatchDetailEntity>) request.getAttribute("matchDetailList");
                %>
                <%
                    for (int i=0;i<matchDetailList.size();i++) {
                %>
                <div class="weui-panel__bd">
                    <div class="weui-media-box weui-media-box_appmsg">
                        <div class="weui-media-box__hd">
                            <img class="weui-media-box__thumb" width="60" height="60" src="../img/服务名称/<%out.print(matchDetailList.get(i).getServiceName());%>.png" alt="">
                        </div>
                        <div class="weui-media-box__bd">
                            <div class="weui-flex">
                                <div class="weui-flex__item"diaplay="none"><h4 class="weui-media-box__title"><%out.print(matchDetailList.get(i).getServiceName());%></h4></div>
                                <div class="weui-flex__item"display="none"></div>
                                <div class="weui-flex__item"display="none"></div>
                            </div>
                            <p class="weui-media-box__desc">订单编号: <%out.print(matchDetailList.get(i).getId());%></p>
                            <p class="weui-media-box__desc">需求人员: <%out.print(matchDetailList.get(i).getRequestUserName());%></p>
                            <p class="weui-media-box__desc"style="color: #7ACF41">开始时间:
                                <%
                                    Timestamp beginTimestamp = matchDetailList.get(i).getBeginTime();
                                    Date date = new Date(beginTimestamp.getTime());
                                    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                    out.print(bartDateFormat.format(date));
                                %></p>
                            <p class="weui-media-box__desc"style="color: #7ACF41">结束时间:
                                <%
                                    Timestamp endTimestamp = matchDetailList.get(i).getEndTime();
                                    Date date2 = new Date(endTimestamp.getTime());
                                    SimpleDateFormat bartDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                    out.print(bartDateFormat2.format(date2));
                                %></p>
                            <p class="weui-media-box__desc">需求地点:<%out.print(matchDetailList.get(i).getAddress());%></p>
                            <p class="weui-media-box__desc">需求人手机号:<%out.print(matchDetailList.get(i).getRequestUserPhone());%></p>
                        </div>

                    </div>
                    <%--<div class="weui-cell">--%>
                        <%--<div class="weui-cell__bd">--%>
                            <%--<div class="weui-flex">--%>
                                <%--<div class="weui-flex__item"diaplay="none"></div>--%>
                                <%--<div class="weui-flex__item"display="none"></div>--%>
                                <%--<div class="weui-flex__item"display="none"></div>--%>
                                <%--<div class="weui-flex__item"><a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_primary">删除</a></div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
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
            location.href="queryRequestMatchAlreadyApply";
        });
        $("#navbar2").on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            location.href="queryRequestMatchWaitingService";

        });
        $("#navbar3").on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            location.href="queryRequestMatchWaitingPay";

        });
        $("#navbar4").on('click', function () {
            $(this).addClass('weui-bar__item_on').siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
            location.href="queryRequestMatchAlreadyComplete";
        });
    });
</script>

</body>
</html>
