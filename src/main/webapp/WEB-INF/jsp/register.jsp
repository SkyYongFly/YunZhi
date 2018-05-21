<%--
  Created by IntelliJ IDEA.
  User: zhuyong
  Date: 2018/5/16
  Time: 22:37
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
    <title>用户注册页面</title>

    <%@ include file="common.jsp"%>

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

        <div id="recont" class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
                <p class="label">用户名</p>
            </div>
            <div class="layui-col-md4">
                <input type="text" id="username" name="username" placeholder="请设置用户名" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
                <p class="label">密&nbsp;&nbsp;&nbsp;码</p>
            </div>
            <div class="layui-col-md4">
                <input type="text" id="password" name="password" placeholder="请设置您的密码" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
                <p class="label">手机号</p>
            </div>
            <div class="layui-col-md4">
                <input type="text" id="phone" name="phone" placeholder="请输入你的手机号" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
                <p class="label">验证码</p>
            </div>
            <div class="layui-col-md2">
                <input type="text" id="vercode" name="vercode" placeholder="请输入验证码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-col-md2">
                <button id="btncode" class="layui-btn layui-btn-primary" onclick="getVercode();">获取验证码</button>
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
            </div>
            <div class="layui-col-md4">
                <button class="layui-btn layui-btn-fluid  layui-btn-normal" onclick="register();">注册</button>
            </div>
        </div>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
            </div>
            <div class="layui-col-md4">
                <div class="layui-row">
                    <div class="layui-col-md6">
                        <p class="label2">已有账号？</p>
                    </div>
                    <div class="layui-col-md6">
                        <button class="layui-btn layui-btn-primary">登录</button>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <script type="text/javascript">
        //获取验证码
        function getVercode(){
            var phone = $("#phone").val();

            if(isNullOrEmpty(phone) || !isPhoneValidate(phone)){
                layer.msg("请正确填写手机号");
                return;
            }

            $.ajax({
                url:  getBaseUrl() + "register/getVercode.do?phone=" + phone,
                type: "GET",
                dataType: "json",
                success:function (data) {
                    if("INVALIDATE_PHONE" == data){
                        layer.msg("请正确填写手机号");
                    }else if("TO_MUCH_COUNT" == data){
                        layer.msg("发送过于频繁,请一分钟后再试");
                    }
                },
                complete:function () {
                    $("#btncode").text("重新发送");
                }
            });
        }

        //用户注册
        function register() {
            //用户名验证
            var username = $("#username").val();
            if(isNullOrEmpty(username) || !validateUsername(username)){
                layer.msg("用户名2到10位汉字或英文字母组成");
                return;
            }

            //密码验证
            var password = $("#password").val();
            if(isNullOrEmpty(password) || !validatePassword(password)){
                layer.msg("密码6到20位非重复数字、字母组成");
                return;
            }

            //手机号验证
            var phone = $("#phone").val();
            if(isNullOrEmpty(phone) || !isPhoneValidate(phone)){
                layer.msg("请正确填写手机号");
                return;
            }

            //验证码格式验证
            var vercode = $("#vercode").val();
            if(isNullOrEmpty(vercode) || !isVercodeValidate(vercode)){
                layer.msg("请正确填写验证码");
                return;
            }

            $.ajax({
                url:  getBaseUrl() + "register/registerValidate.do",
                type: "POST",
                data:JSON.stringify({"username":username, "password":password, "phone":phone, "vercode":vercode}),
                contentType:"application/json",
                dataType: "json",
                success:function (data) {
                    if("INVALIDATE_USERNAME" == data){
                        layer.msg("用户名格式不正确");
                    }else if("INVALIDATE_PASSWORD" == data){
                        layer.msg("密码格式不正确");
                    }else if("INVALIDATE_PHONE" == data){
                        layer.msg("手机号格式不正确");
                    }else if("PHONE_HAS_REGISTER" == data){
                        layer.msg("手机已注册");
                    }else if("INVALIDATE_VERCODE" == data){
                        layer.msg("验证码不正确");
                    }else if("REGISTER_FAILURE" == data){
                        layer.msg("注册失败，请重试");
                    }else if("SUCCESS" == data){
                        layer.msg("注册成功！");
                        setTimeout('',1000);
                       // $.get(getBaseUrl() + "login/getLoginPage.do");
                        window.location.href = "<%=request.getContextPath()%>/jsp/login.jsp";
                    }
                }
            });
        }
    </script>
</body>
</html>
