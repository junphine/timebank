<%@ page import="com.blockchain.timebank.entity.ViewRequestOrderDetailEntity" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Mihaly
  Date: 02/01/2018
  Time: 22:56
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,user-scalable=0">
    <title>支付详情</title>
    <!-- 引入 WeUI -->
    <link rel="stylesheet" href="../css/weui.min.css" />
    <script src="../js/zepto/zepto.min.js"></script>
    <script src="../js/zepto/weui.min.js"></script>
    <script src="../js/scan/function.js"></script>
    <script src="../js/scan/configs.js"></script>
</head>
<body>
<%
    ViewRequestOrderDetailEntity matchDetail = (ViewRequestOrderDetailEntity) request.getAttribute("viewMatchDetailEntity");
    String type = null;
    switch (matchDetail.getServiceType()){
        case "volunteer":
            type = "志愿者需求";
            break;
        case "technic":
            type = "专业需求";
            break;
        case "mutualAid":
            type = "互助需求";
            break;
    }

%>

<div class="weui-form-preview">
    <div class="weui-form-preview__hd">
        <label class="weui-form-preview__label">付款金额:</label>
        <em class="weui-form-preview__value" style="color: #7ACF41">
            <input id="servicePrice" class="weui-input" name="price" type="number" pattern="[0-9]*" placeholder="<%out.print(matchDetail.getPayMoney());%>" min="0" step="0.01" style="text-align:right"/>
        </em>
    </div>
    <div class="weui-form-preview__bd">
        <p>
            <label class="weui-form-preview__label">订单编号:</label>
            <span class="weui-form-preview__value"><%out.print(matchDetail.getId());%></span>
        </p>
        <p>
            <label class="weui-form-preview__label">服务类型:</label>
            <span class="weui-form-preview__value"><%out.print(type);%></span>
        </p>
        <p>
            <label class="weui-form-preview__label">服务名称:</label>
            <span class="weui-form-preview__value"><%out.print(matchDetail.getServiceName());%></span>
        </p>
        <p>
            <label class="weui-form-preview__label">需求人:</label>
            <span class="weui-form-preview__value"><%out.print(matchDetail.getRequestUserName());%></span>
        </p>
        <p>
            <label class="weui-form-preview__label">服务时长:</label>
            <span class="weui-form-preview__value">
                <%
                    Timestamp beginTimestamp = matchDetail.getActualBeginTime();
                    Date date = new Date(beginTimestamp.getTime());
                    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    Timestamp endTimestamp = matchDetail.getActualEndTime();
                    Date date2 = new Date(endTimestamp.getTime());
                    SimpleDateFormat bartDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    out.print(bartDateFormat.format(date)+" -- "+bartDateFormat2.format(date2));
                %></span>
        </p>
        <p>
            <label class="weui-form-preview__label">服务地点:</label>
            <span class="weui-form-preview__value"><%out.print(matchDetail.getAddress());%></span>
        </p>
    </div>
    <div class="weui-form-preview__ft">
        <a class="weui-form-preview__btn weui-form-preview__btn_default" href="${pageContext.request.contextPath}/request/applied?tab=2">返回</a>
        <a class="weui-form-preview__btn weui-form-preview__btn_primary" id="payBtn">支付</a>
    </div>
</div>

<script src="../js/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
    var xmlHttpRequest;
    $(function(){
        if(window.XMLHttpRequest){
            xmlHttpRequest=new XMLHttpRequest();
        }else{
            xmlHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlHttpRequest.open("GET","AjaxServlet",true);
    });

    var contextPath="${pageContext.request.contextPath}";
    var type='<%=matchDetail.getServiceType()%>';
    var matchID = '<%=matchDetail.getId()%>';
    $(function(){
        $("#payBtn").on('click',function () {
            var targetUrl = "${pageContext.request.contextPath}/request/requestUserPayTimeVol";
            var targetUrl2 = "${pageContext.request.contextPath}/request/published?tab=5";
            var volunteerService = 'volunteer';
            var mutualHelpService = 'mutualAid';
            var professionService = 'technic';
            var price = <%out.print(matchDetail.getPayMoney());%>;
            if($("#servicePrice").val() !== ""){
                price = $("#servicePrice").val();
            }
            if(type === volunteerService || type === mutualHelpService){
                $.ajax({
                    type: 'POST',
                    cache: false,
                    url: targetUrl,
                    //dataType:'JSONP',
                    data: "matchID=" + matchID + "?price=" + price,
                    beforeSend: function (XHR) {
                        dialogLoading = showLoading();
                    },
                    success: function (data) {
                        //alert(data);
                        showAlert("支付成功",function () {
                            goTo(targetUrl2);
                        })
                    },
                    error: function (xhr, type) {
                        //alert(type);
                        showAlert("支付失败",function () {
                            //goTo("http://www.hlb9978.com/user/queryOrderWaitingPay");
                        })
                    },
                    complete: function (xhr, type) {
                        dialogLoading.hide();
                    }
                });
            }else if(type === professionService){
                if(confirm("请扫描对方微信收款码支付" + price + "元")){
                    wx.scanQRCode(
                        {
                            needResult: 0,
                            success: function (res) {

                                //alert(JSON.stringify(res.resultStr));

                            }
                        }
                    );
                }
            }

        });
    });
</script>

</body>
</html>
