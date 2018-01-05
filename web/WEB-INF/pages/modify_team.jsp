<%@ page import="com.blockchain.timebank.entity.TeamEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>修改信息</title>
    <link rel="stylesheet" href="../css/weui.css">
    <link rel="stylesheet" href="../css/weui-example.css">
    <script src="../js/zepto/zepto.min.js"></script>
    <script src="../js/zepto/weui.min.js"></script>
    <script src="../js/scan/function.js"></script>
    <script src="../js/scan/configs.js"></script>
    <script src="../js/jquery/jquery-3.2.1.min.js"></script>
</head>
<body>
<%
    TeamEntity team=(TeamEntity)request.getAttribute("teamEntity");
%>
<div class="weui-cells weui-cells_form">
    <div class="weui-panel__hd weui-cells__title">
        <div class="weui-flex__item"id="return" onclick="history.go(-1)" >
            <p><img src="../img/return.png" width="20" height="15"alt="">修改团体信息</p>
        </div>
    </div>
    <div class="weui-cells_form weui-cells">
        <div class="weui-cell" style="margin-left: 130px;">
            <div class="weui-cell__bd">
                <div class="weui-flex">
                    <div class="weui-flex__item weui-flex justify align">
                        <div class="weui-uploader">
                            <div class="weui-uploader__hd">
                                <p class="weui-uploader__title">团队头像</p>
                            </div>
                            <div class="weui-uploader__bd">
                                <img src="../img/teamHeadImg/<%out.print(team.getHeadImg());%>" style="width:100px;display: block">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd">
            <label class="weui-label">团队名称</label>
        </div>
        <div class="weui-cell__bd">
            <input class="weui-input" name=<%out.print(team.getId());%> id="team_name" value=<%out.print(team.getName());%>>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">地点</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" id="team_location" value=<%out.print(team.getAddress());%>/>
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">团队介绍</label></div>
    </div>
    <div style="text-align:center;">
        <textarea id="describe" rows="10" cols="30" maxlength=200 style="width:250px; height:180px; border:solid 1px #4d4d4d;resize:none; font-size: 16px; padding:3px;border-radius: 1px;"><%out.print(team.getDescription());%></textarea>
    </div>
    <div style="padding: 10px; margin-top: 10px;">
        <a href="javascript:;" class="weui-btn weui-btn_primary" id="modifyTeam" type="button">确认修改</a>
    </div>
    <div style="padding: 10px; margin-bottom: 20px;">
        <a href="javascript:;" class="weui-btn weui-btn_primary" id="deleteTeam" type="button" style="background-color: #ce3c39;">解散</a>
    </div>
</div>

</body>
<script src="../js/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("#modifyTeam").on('click', function () {
            var contextPath="${pageContext.request.contextPath}"
            var targetUrl = "http://"+getDomainName()+contextPath+"/team/modifyTeam";
            var teamId=document.getElementById("team_name").name;
            var teamName=document.getElementById("team_name").value;
            var describe=document.getElementById("describe").value;
            $.ajax({
                type: 'POST',
                cache: false,
                url: targetUrl,
                data: "teamId="+teamId+"&teamName="+teamName+"&describe="+describe,
                beforeSend: function (XHR) {
                    dialogLoading = showLoading();
                },
                success: function (data) {
                    if(data==="success"){
                        showAlert("修改成功",function () {
                            window.location.href="${pageContext.request.contextPath}/team/myTeams"
                        });
                    }
                    if(data==="nameExist"){
                        showAlert("该名已被使用");
                    }
                    if(data==="failure"){
                        showAlert("修改失败");
                    }
                },
                error: function (xhr, type) {
                    showAlert("修改失败");
                },
                complete: function (xhr, type) {
                    dialogLoading.hide();
                }
            });
        });
        $("#deleteTeam").on('click', function () {
            var contextPath="${pageContext.request.contextPath}"
            var targetUrl = "http://"+getDomainName()+contextPath+"/team/deleteTeam";
            var teamId=document.getElementById("team_name").name;
            $.ajax({
                type: 'POST',
                cache: false,
                url: targetUrl,
                data: "teamId="+teamId,
                beforeSend: function (XHR) {
                    dialogLoading = showLoading();
                },
                success: function (data) {
                    if(data==="success"){
                        showAlert("删除成功",function () {
                            window.location.href="${pageContext.request.contextPath}/team/myTeams"
                        });
                    }
                    if(data==="failure"){
                        showAlert("删除失败");
                    }
                },
                error: function (xhr, type) {
                    showAlert("操作失败");
                },
                complete: function (xhr, type) {
                    dialogLoading.hide();
                }
            });
        });
    });
</script>
</html>