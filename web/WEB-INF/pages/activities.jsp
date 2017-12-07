<%--
  Created by IntelliJ IDEA.
  User: bobo9978
  Date: 2017/12/7
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>服务列表</title>
    <link rel="stylesheet" href="../css/weui.css">
    <link rel="stylesheet" href="../css/weui-example.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>
<body>


<div class="weui-tab">
    <div class="weui-tab__panel">
        <div class="weui-panel weui-panel_access">
            <div class="weui-panel__hd">团体活动</div>
            <div class="weui-panel__bd">


                <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" src="../img/userdetails/专业服务认证.png" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <h4 class="weui-media-box__title">活动1</h4>
                        <p class="weui-media-box__desc">显示详情1</p>
                        <p class="weui-media-box__desc">显示详情2</p>
                        <ul class="weui-media-box__info">
                            <li class="weui-media-box__info__meta">内容1</li>
                            <li class="weui-media-box__info__meta">内容2</li>
                            <li class="weui-media-box__info__meta weui-media-box__info__meta_extra"></li>
                            <li class="weui-media-box__info__meta">内容3</li>
                        </ul>
                    </div>
                </a>

                <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" src="../img/userdetails/专业服务认证.png" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <h4 class="weui-media-box__title">活动2</h4>
                        <p class="weui-media-box__desc">显示详情1</p>
                        <p class="weui-media-box__desc">显示详情2</p>
                        <ul class="weui-media-box__info">
                            <li class="weui-media-box__info__meta">内容1</li>
                            <li class="weui-media-box__info__meta">内容2</li>
                            <li class="weui-media-box__info__meta weui-media-box__info__meta_extra"></li>
                            <li class="weui-media-box__info__meta">内容3</li>
                        </ul>
                    </div>
                </a>

                <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" src="../img/userdetails/专业服务认证.png" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <h4 class="weui-media-box__title">活动3</h4>
                        <p class="weui-media-box__desc">显示详情1</p>
                        <p class="weui-media-box__desc">显示详情2</p>
                        <ul class="weui-media-box__info">
                            <li class="weui-media-box__info__meta">内容1</li>
                            <li class="weui-media-box__info__meta">内容2</li>
                            <li class="weui-media-box__info__meta weui-media-box__info__meta_extra"></li>
                            <li class="weui-media-box__info__meta">内容3</li>
                        </ul>
                    </div>
                </a>

            </div>


        </div>

    </div>

    <div class="weui-tabbar">
        <a href="${pageContext.request.contextPath}/index" class="weui-tabbar__item">
            <img src="../img/首页.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">首页</p>
        </a>
        <a href="${pageContext.request.contextPath}/publish/category" class="weui-tabbar__item">
            <img src="../img/服务.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">服务</p>
        </a>
        <a href="${pageContext.request.contextPath}/user/" class="weui-tabbar__item">
            <img src="../img/我的.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">我</p>
        </a>
    </div>
</div>

<!-- jQuery 3 -->
<script src="../js/jquery/jquery-3.2.1.min.js"></script>

<script>
    $(document).ready(function () {
        $('.weui-tabbar:eq(0)').find('a:eq(1)').addClass("weui-bar__item_on");
    });
</script>

</body>
</html>