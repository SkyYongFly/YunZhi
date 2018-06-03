<%--
  Created by IntelliJ IDEA.
  User: zhuyong
  Date: 2018/5/13
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String contextPath = request.getContextPath();
%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>用户登录页面</title>

    <%@ include file="../../jsp/common.jsp"%>

    <style type="text/css">
        .layui-col-space10{
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div id="register" class="layui-container">
        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
                <img src="<%=request.getContextPath() %>/assets/images/symbol/yunzhi.png"/>
            </div>
            <div  id="solgan" class="layui-col-md4">
                在云端遇见智慧的声音
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
            </div>
            <div  class="layui-col-md4">
                <p class="error">${result}</p>
            </div>
        </div>

        <div id="recont" class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
                <p class="label">手机号</p>
            </div>
            <div class="layui-col-md4">
                <input type="text" id="phone" name="phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
                <p class="label">密&nbsp;&nbsp;&nbsp;码</p>
            </div>
            <div class="layui-col-md4">
                <input type="text" id="password" name="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
            </div>
            <div class="layui-col-md4">
                <button class="layui-btn layui-btn-fluid  layui-btn-normal" onclick="login();">登录</button>
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
            </div>
            <div class="layui-col-md4">
                <div class="layui-row">
                    <div class="layui-col-md6">
                        <p class="label2">没有账号？</p>
                    </div>
                    <div class="layui-col-md6">
                        <button onclick="showRegisterPage();" class="layui-btn layui-btn-primary">注册</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <form id="form" method="post" action="">
    </form>

    <script type="text/javascript">
        //用户登录
        function login() {
            //手机号验证
            var phone = $("#phone").val();
            if(isNullOrEmpty(phone) || !isPhoneValidate(phone)){
                layer.msg("请正确填写手机号");
                return false;
            }

            //密码验证
            var password = $("#password").val();
            if(isNullOrEmpty(password) || !validatePassword(password)){
                layer.msg("密码6到20位非重复数字、字母组成");
                return false;
            }

            $.ajax({
                url:  getBaseUrl() + "login/loginValidate.do",
                type: "POST",
                data:JSON.stringify({"password":password, "phone":phone}),
                contentType:"application/json",
                dataType: "json",
                success:function (data) {
                    layer.msg(data.message);

                    //成功
                    if(1 == data.code){
                        setTimeout(function() {
                            $("#form").attr("action", getBaseUrl() + "login/index.do");
                            $("#form").submit();
                        },2000);
                    }
                }
            });
        }
    </script>

</body>
</html>
