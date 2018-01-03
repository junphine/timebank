<%@ page import="java.util.List" %>
<%@ page import="com.blockchain.timebank.entity.TeamEntity" %><%--
  Created by IntelliJ IDEA.
  User: weiyi
  Date: 2017/12/24
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,user-scalable=0">
    <title>我的团体</title>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="../css/weui.min.css" />
    <script src="../js/zepto/zepto.min.js"></script>
    <script src="../js/zepto/weui.min.js"></script>
    <script src="../js/scan/configs.js"></script>
    <script src="../js/scan/function.js"></script>
</head>
<body>
<%
    List<TeamEntity> teamList = (List<TeamEntity>) request.getAttribute("allTeamList");
%>
<div class="page">
    <div class="weui-navbar" style="top: 0px;margin : 0px 0px 10px 0px;">
        <div class="weui-navbar__item" style="background-color: #888;border: #888;">
            <p style="font-size: 20px;">我的团体</p>
        </div>
    </div>
</div>
<div  style="margin-top: 60px;">
<%
    if(teamList.size()==0){
%>
<div class="container" style="margin-left: 20px;margin-top: 60px;">
    <div class="page home js_show">
        <div class="page__hd">
            <h1 class="page__title">
                <font face="黑体" style="font-size: 40px;">Sorry</font>
            </h1>
            <p class="page__desc">你还没有自己的小团体</p>
        </div>
    </div>
    <div style="text-align:center;margin-top: 40px;height:100px;">
        <div style="margin:50px;">
            <a href="${pageContext.request.contextPath}/team/createPage" class="weui-btn weui-btn_plain-default">创建自己的团体</a>
        </div>
    </div>
</div>
<%} else{
    for (int i=0;i<teamList.size();i++) {
%>
<div class="weui-panel__bd">
    <div class="weui-cells weui-cells_checkbox" style="margin-top:0px;">
        <label class="weui-cell weui-check__label" for=<%out.print(teamList.get(i).getId());%>>
            <div class="weui-cell__hd" style="position: relative;margin-right: 10px;" >
                <img src="../img/teamHeadImg/<%teamList.get(i).getHeadImg();%>" style="width: 50px;display: block">
            </div>
            <div class="weui-cell__bd">
                <p><%out.print(teamList.get(i).getName());%></p>
                <p style="font-size: 13px;color: #888888;"><%out.print(teamList.get(i).getDescription());%></p>
            </div>
            <% if(teamList.get(i).isDeleted()){%>
            <p class="weui-btn weui-btn_mini weui-btn_primary">已删除</p>
            <%}else{%>
            <div class="weui-cell__bd" style="text-align: right;">
                <a href="${pageContext.request.contextPath}/team/modifyPage?teamId=<%out.print(teamList.get(i).getId());%>" class="weui-btn weui-btn_mini weui-btn_primary">编辑</a>
                <a href="${pageContext.request.contextPath}/team/myTeamMember?teamId=<%out.print(teamList.get(i).getId());%>" class="weui-btn weui-btn_mini weui-btn_primary">成员</a>
            </div>
            <%--<a href="${pageContext.request.contextPath}/team/modifyPage?teamId=<%out.print(teamList.get(i).getId());%>" class="weui-btn weui-btn_mini weui-btn_primary">编辑</a>--%>
            <%}%>
        </label>
    </div>
</div>
<%}%>
    <div class="weui-tabbar">
        <a href="${pageContext.request.contextPath}/team/createPage" class="weui-tabbar__item">
            <span style="display: inline-block;">
                <img src="../img/add.png" alt="" class="weui-tabbar__icon" style="width: 30px;display: block">
            </span>
            <p class="weui-tabbar__label">新建团体</p>
        </a>
    </div>
    <%}%>
</div>
</body>
<script src="../js/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
</script>
</html>
