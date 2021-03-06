<%@ page import="com.blockchain.timebank.entity.ViewRequestDetailEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.blockchain.timebank.entity.ViewRequestDetailEntity" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Mihaly
  Date: 29/12/2017
  Time: 13:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" href="../css/weui.css">
    <link rel="stylesheet" href="../css/weui-example.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/Item.css"/>
    <script src="../js/scan/function.js"></script>
    <script src="../js/scan/configs.js"></script>
    <script src="${pageContext.request.contextPath}/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/js/scan/function.js"></script>
    <script src="${pageContext.request.contextPath}/js/scan/configs.js"></script>
    <style>
        .overtimeFlag{
            float:right;
            font-size:16px;
            color:#848c99;
            line-height:57px;
        }
    </style>
    <title>需求柜台</title>
</head>
<body>
<div class="main-container">
    <div class="main-content">
<div class="weui-tab">
    <div class="weui-tab__panel">
        <div class="weui-panel weui-panel_access" style="margin-bottom: 50px">

            <%--<div class="weui-cells">
                <div class="weui-cell weui-cell_select weui-cell_select-after">
                    <div class="weui-cell__hd">
                        <label class="weui-label">需求类型</label>
                    </div>
                    <div class="weui-cell__bd">
                        <select class="weui-select" name="requestType" id="requestType">
                            <%
                                String value = (String) request.getAttribute("type");
                                if(value.equals("volunteer")){
                            %>
                                <option value="志愿者需求" selected="selected">志愿者需求</option>
                                <option value="互助需求">互助需求</option>
                                <option value="专业需求">专业需求</option>
                            <%
                                }else if(value.equals("mutualAid")){
                            %>
                                <option value="志愿者需求">志愿者需求</option>
                                <option value="互助需求" selected="selected">互助需求</option>
                                <option value="专业需求">专业需求</option>
                            <%
                            }else if(value.equals("technic")){
                            %>
                                <option value="志愿者需求">志愿者需求</option>
                                <option value="互助需求">互助需求</option>
                                <option value="专业需求" selected="selected">专业需求</option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                </div>
            </div>--%>

            <div class="weui-navbar" style="position:fixed;">
                <%
                    String value = (String) request.getAttribute("type");
                    if(value.equals("volunteer")){
                %>
                    <div class="weui-navbar__item weui-bar__item_on"id="navbar1">
                        义工需求
                    </div>
                    <div class="weui-navbar__item"id="navbar2">
                        互助需求
                    </div>
                    <div class="weui-navbar__item"id="navbar3">
                        专业需求
                    </div>
                <%
                }else if(value.equals("mutualAid")){
                %>
                    <div class="weui-navbar__item"id="navbar1">
                        义工需求
                    </div>
                    <div class="weui-navbar__item weui-bar__item_on"id="navbar2">
                        互助需求
                    </div>
                    <div class="weui-navbar__item"id="navbar3">
                        专业需求
                    </div>
                <%
                }else if(value.equals("technic")){
                %>
                    <div class="weui-navbar__item"id="navbar1">
                        义工需求
                    </div>
                    <div class="weui-navbar__item"id="navbar2">
                        互助需求
                    </div>
                    <div class="weui-navbar__item weui-bar__item_on"id="navbar3">
                        专业需求
                    </div>
                <%
                    }
                %>
            </div>

            <div class="weui-panel__hd">
                <span>
                    <%
                        String type = null;
                        String moneyType = null;
                        switch ((String) request.getAttribute("type")){
                            case "volunteer":
                                type = "志愿者需求";
                                moneyType = "志愿者时间";
                                break;
                            case "technic":
                                type = "专业需求";
                                moneyType = "元";
                                break;
                            case "mutualAid":
                                type = "互助需求";
                                moneyType = "互助时间";
                                break;
                        }
                        out.print(type);
                    %>
                </span>
                <a href="javascript:;" onclick="window.location.href='${pageContext.request.contextPath}/request/select?type=<%out.print(request.getAttribute("type"));%>'" style="float:right;color:#337ab7">筛选</a>
            </div>
            <div class="weui-panel__bd">

                <%
                    List<ViewRequestDetailEntity> list = (List<ViewRequestDetailEntity>) request.getAttribute("list");
                    Date currentDate = new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
                    String currentTime = sdf.format(currentDate);
                    int i = 0, s = 0;
                    while (s<list.size()){
                        if(list.get(i).getEndTime().getTime() < new Date(currentTime).getTime()){
                            list.add(list.get(i));
                            list.remove(i);
                        }
                        else
                            i++;
                        s++;
                    }
                    for (ViewRequestDetailEntity viewRequestDetailEntity : list) {
                %>

                <a href="<%out.print("detail?id="+viewRequestDetailEntity.getId()+"&type="+type);%>" class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" style="" src="<%out.print(viewRequestDetailEntity.getHeadImgUrl());%>">
                    </div>
                    <div class="weui-media-box__bd">
                        <h4 class="weui-media-box__title" style="display:inline-block">
                            <img style="height:20.5px; vertical-align: text-bottom" src="../img/服务名称/<%out.print(viewRequestDetailEntity.getServiceName());%>.png" />
                            <%out.print(viewRequestDetailEntity.getServiceName());%>
                        </h4>
                        <%
                            if(viewRequestDetailEntity.getIsComplete() == 1){
                                out.print("<span class='overtimeFlag'>已完成</span>");
                            }
                            else if(viewRequestDetailEntity.getEndTime().getTime() < new Date(currentTime).getTime()){
                                out.print("<span class='overtimeFlag'>已过期</span>");
                            }
                        %>
                        <p class="weui-media-box__desc">
                            <span style="color: #7ACF41;">¥
                                <%
                                    out.print(viewRequestDetailEntity.getPrice()+moneyType);
                                %>
                            </span>
                            <br/>
                            <%out.print(viewRequestDetailEntity.getUserName());%> &nbsp;&nbsp;&nbsp;
                            <%out.print(viewRequestDetailEntity.getAddress());%>
                        </p>
                    </div>
                </a>
                <%}%>
            </div>
        </div>
    </div>
    <div class="weui-tabbar" style="height: 50px">
        <a href="${pageContext.request.contextPath}/request/list?type=volunteer" class="weui-tabbar__item">
            <img src="../img/首页.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label" style="font-size: 10px;color: #28a921;margin:0px">需求柜台</p>
        </a>
        <a href="${pageContext.request.contextPath}/request/applied?tab=1" class="weui-tabbar__item">
            <img src="../img/服务.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">承接的需求</p>
        </a>
        <a href="${pageContext.request.contextPath}/request/published?tab=1" class="weui-tabbar__item">
            <img src="../img/活动.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">发布的需求</p>
        </a>
    </div>
</div>
    </div>
    <button class="float-button" style="font-size: xx-large;" id="create">+
    </button>
</div>

<script src="../js/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    var url='${pageContext.request.contextPath}';
    $("#create").on("click", function() {
        goTo(url+"/request/add");
    });
    var contextPath="${pageContext.request.contextPath}";
    /*$(document).ready(function () {
        $("#requestType").change(function () {
            var type = $(this).val();
            if(type === "志愿者需求"){
                var targetUrl = "http://"+getDomainName()+contextPath+"/request/list?type=volunteer";
                goTo(targetUrl);
            } else if(type === "互助需求"){
                var targetUrl = "http://"+getDomainName()+contextPath+"/request/list?type=mutualAid";
                goTo(targetUrl);
            } else if(type === "专业需求"){
                var targetUrl = "http://"+getDomainName()+contextPath+"/request/list?type=technic";
                goTo(targetUrl);
            }
        });
    });*/

    $(function(){
        $("#navbar1").on('click', function () {
            var targetUrl = "http://"+getDomainName()+contextPath+"/request/list?type=volunteer";
            goTo(targetUrl);
        });
        $("#navbar2").on('click', function () {
            var targetUrl = "http://"+getDomainName()+contextPath+"/request/list?type=mutualAid";
            goTo(targetUrl);

        });
        $("#navbar3").on('click', function () {
            var targetUrl = "http://"+getDomainName()+contextPath+"/request/list?type=technic";
            goTo(targetUrl);
        });

    });
</script>

</body>
</html>
