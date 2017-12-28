<%@ page import="java.util.List" %>
<%@ page import="com.blockchain.timebank.entity.TeamUserEntity" %>
<%@ page import="com.blockchain.timebank.entity.UserEntity" %><%--
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
    <title>团体成员</title>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="../css/weui.min.css" />
    <script src="../js/zepto/zepto.min.js"></script>
    <script src="../js/zepto/weui.min.js"></script>
    <script src="../js/scan/configs.js"></script>
    <script src="../js/scan/function.js"></script>
</head>
<body>
<%
    String teamId=(String) request.getAttribute("teamId");
    List<UserEntity> teamUserList=(List<UserEntity>) request.getAttribute("userList");
    List<UserEntity> lockedUserList=(List<UserEntity>) request.getAttribute("lockedList");
%>
<div class="page">
    <div class="weui-navbar" style="top: 0px;margin : 0px 0px 10px 0px;">
        <div class="weui-navbar__item" style="background-color: #888;border: #888;">
            <p style="font-size: 20px;">团体成员</p>
        </div>
    </div>
</div>
<div  style="margin-top: 60px;">
<%
    if(teamUserList.size()==0 && lockedUserList.size()==0){
%>
    <div class="container" style="margin-left: 20px;margin-top: 60px;">
    <div class="page home js_show">
        <div class="page__hd">
            <h1 class="page__title">
                <font face="黑体" style="font-size: 40px;">Sorry</font>
            </h1>
            <p class="page__desc">还没有人加入你的团体</p>
        </div>
    </div>
</div>
<%}
    for (int i=0;i<teamUserList.size();i++) {
%>
<div class="weui-panel__bd">
    <div class="weui-cells weui-cells_checkbox" style="margin-top:0px;">
        <label class="weui-cell weui-check__label" for=<%out.print(teamUserList.get(i).getId());%>>
            <a class="weui-cell__hd" style="position: relative;margin-right: 10px;">
                <img src="../img/ezio.jpg" style="width: 50px;display: block">
            </a>
            <div class="weui-cell__bd">
                <p><%out.print(teamUserList.get(i).getName());%></p>
                <p style="font-size: 13px;color: #888888;"><%out.print(teamUserList.get(i).getBirth());%></p>
            </div>
            <a class="weui-btn weui-btn_mini weui-btn_primary" onclick="lockTeamMember(this)" name=<%out.print(teamId);%> id=<%out.print(teamUserList.get(i).getId());%>>锁定</a>
        </label>
    </div>
</div>
<%
    }
    for(int i=0;i<lockedUserList.size();i++){
%>
    <div class="weui-panel__bd">
        <div class="weui-cells weui-cells_checkbox" style="margin-top:0px;">
            <label class="weui-cell weui-check__label" for=<%out.print(lockedUserList.get(i).getId());%>>
                <a class="weui-cell__hd" style="position: relative;margin-right: 10px;">
                    <img src="../img/ezio.jpg" style="width: 50px;display: block">
                </a>
                <div class="weui-cell__bd">
                    <p><%out.print(lockedUserList.get(i).getName());%></p>
                    <p style="font-size: 13px;color: #888888;"><%out.print(lockedUserList.get(i).getBirth());%></p>
                </div>
                <a class="weui-btn weui-btn_mini weui-btn_primary" style="background-color: coral" onclick="UnlockTeamMember(this)" name=<%out.print(teamId);%> id=<%out.print(lockedUserList.get(i).getId());%>>解锁</a>
            </label>
        </div>
    </div>
    <%}%>
</div>
</body>
<script src="../js/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    function lockTeamMember(t) {
        var contextPath="${pageContext.request.contextPath}"
        var targetUrl = "http://"+getDomainName()+contextPath+"/team/lockMember";
        var teamId=t.name;
        var userId=t.id;
        if(teamId!=null && userId!=null){
            $.ajax({
                type: 'POST',
                cache: false,
                url: targetUrl,
                data: "userId="+userId+"&teamId="+teamId,
                beforeSend: function (XHR) {
                    dialogLoading = showLoading();
                },
                success: function (data) {
                    if(data==="success"){
                        showAlert("锁定成功",function () {
                            location.reload();
                        });
                    }
                    if(data==="failure"){
                        showAlert("锁定失败");
                    }
                },
                error: function (xhr, type) {
                    showAlert("锁定失败");
                },
                complete: function (xhr, type) {
                    dialogLoading.hide();
                }
            });
        }
    }
    function UnlockTeamMember(t) {
        var contextPath="${pageContext.request.contextPath}"
        var targetUrl = "http://"+getDomainName()+contextPath+"/team/UnlockMember";
        var teamId=t.name;
        var userId=t.id;
        if(teamId!=null && userId!=null){
            $.ajax({
                type: 'POST',
                cache: false,
                url: targetUrl,
                data: "userId="+userId+"&teamId="+teamId,
                beforeSend: function (XHR) {
                    dialogLoading = showLoading();
                },
                success: function (data) {
                    if(data==="success"){
                        showAlert("解锁成功",function () {
                            location.reload();
                        });
                    }
                    if(data==="failure"){
                        showAlert("解锁失败");
                    }
                },
                error: function (xhr, type) {
                    showAlert("解锁失败");
                },
                complete: function (xhr, type) {
                    dialogLoading.hide();
                }
            });
        }
    }
</script>
</html>
