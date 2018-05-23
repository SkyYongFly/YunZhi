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

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
            </div>
            <div  class="layui-col-md4">
                <p class="error">${result}</p>
            </div>
        </div>

        <form id="form" method="post" action="loginreq.do">
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
                    <button class="layui-btn layui-btn-fluid  layui-btn-normal" lay-submit>登录</button>
                </div>
            </div>
        </form>

        <div class="layui-row layui-col-space10">
            <div class="layui-col-md2 layui-col-md-offset2">
            </div>
            <div class="layui-col-md4">
                <div class="layui-row">
                    <div class="layui-col-md6">
                        <p class="label2">没有账号？</p>
                    </div>
                    <div class="layui-col-md6">
                        <button class="layui-btn layui-btn-primary">注册</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        //用户登录
        function login() {
            //手机号验证
            var phone = $("#phone").val();
            if(isNullOrEmpty(phone) || !isPhoneValidate(phone)){
                layer.msg("请正确填写手机号");
                return;
            }

            //密码验证
            var password = $("#password").val();
            if(isNullOrEmpty(password) || !validatePassword(password)){
                layer.msg("密码6到20位非重复数字、字母组成");
                return;
            }

            // $.ajax({
            //     url:  getBaseUrl() + "login/loginreq.do",
            //     type: "POST",
            //     data:JSON.stringify({"phone": phone, "password": password}),
            //     contentType:"application/json",
            //     dataType: "json",
            //     success:function (data) {
            //         // if("INVALIDATE_PASSWORD" == data){
            //         //     layer.msg("密码格式不正确");
            //         // }else if("INVALIDATE_PHONE" == data){
            //         //     layer.msg("手机号格式不正确");
            //         // }else if("PHONE_HAS_REGISTER" == data){
            //         //     layer.msg("手机已注册");
            //         // }else if("SUCCESS" == data){
            //         //     layer.msg("注册成功！");
            //         // }
            //     }
            // });
        }
    </script>

</body>
</html>
