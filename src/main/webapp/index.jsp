<%--
  Created by IntelliJ IDEA.
  User: zhuyong
  Date: 2018/5/10  23:32
  Description:云知问答首页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>云知首页</title>

    <%@ include file="jsp/common.jsp"%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/index.css"/>
</head>
<body>
    <div class="layui-card" style="padding-top: 20px">
        <div class="layui-container">
            <div id="header" class="layui-row layui-col-space30">
                <%--左侧内容区--%>
                <div class="layui-col-md8">
                    <div class="layui-row layui-col-space10">
                        <%--标志--%>
                        <div class="layui-col-md2">
                            <img src="<%=request.getContextPath() %>/assets/images/symbol/yunzhi.png"/>
                        </div>
                        <%--首页--%>
                        <div class="layui-col-md2">
                            <button class="layui-btn layui-btn-primary">首页</button>
                        </div>
                        <%--搜索--%>
                        <div class="layui-col-md6">
                            <input type="text" name="title" placeholder="请输入搜索内容" autocomplete="off" class="layui-input">
                        </div>
                        <%--提问--%>
                        <div class="layui-col-md2">
                            <button class="layui-btn layui-btn-normal" onclick="addQuestion();">提问</button>
                        </div>
                    </div>
                </div>

                <%--右侧内容区--%>
                <div class="layui-col-md4">
                    <%--个人信息--%>
                    <div id="userinfo"  class="layui-row layui-col-space20">
                        <div class="layui-col-md2">
                            <i  id="usericon" class="layui-icon layui-icon-friends" style="font-size: 40px; color: #1E9FFF;display: none"></i>
                        </div>

                        <div class="layui-col-md10">

                            <%--已经登录显示--%>
                            <div id="haslogin" style="display: none">
                                <div class="layui-card">
                                    <div class="layui-card-header" style="height: 20px;line-height: 20px;">
                                        <p id="username">昵称</p>
                                    </div>
                                    <div class="layui-card-body" style="padding-top:5px;padding-bottom: 0; line-height: 20px;">
                                        <p id="signature">签名~~~</p>
                                    </div>
                                </div>
                            </div>

                            <%--未登录显示--%>
                            <div id="hasnotlogin">
                                <button class="layui-btn layui-btn-primary" onclick="login();">登录</button>
                                <button class="layui-btn layui-btn-primary" onclick="register();">注册</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-container">
        <div  id="center" class="layui-row layui-col-space30">
            <div class="layui-col-md8">
                <%--导航选项卡--%>
                <div id="navigation"  class="layui-row layui-col-space10">
                    <div class="layui-tab layui-tab-brief" lay-filter="navigations">
                        <ul class="layui-tab-title">
                            <li lay-id="hot" class="layui-this">热门</li>
                            <li lay-id="question" >问题</li>
                            <li lay-id="answer" >回答</li>
                        </ul>
                        <div class="layui-tab-content">
                            <div class="layui-tab-item layui-show">

                                <%--热门内容区域--%>
                                <div class="layui-card">
                                    <div class="layui-card-header">卡片面板</div>
                                    <div class="layui-card-body">
                                        卡片式面板面板通常用于非白色背景色的主体内<br>
                                        从而映衬出边框投影
                                    </div>
                                </div>

                            </div>
                            <div class="layui-tab-item">
                                <%--加载问题相关信息--%>
                                <ul class="flow-default" id="questions"></ul>
                            </div>
                            <div class="layui-tab-item">内容3</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md4">
                <%--个人提醒--%>
                <div id="usertips"  class="layui-row">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-primary">
                            <i class="layui-icon layui-icon-release" style="font-size: 25px; color: #1E9FFF;"></i>&nbsp;&nbsp;消息
                        </button>
                        <button class="layui-btn layui-btn-primary">
                            <i class="layui-icon layui-icon-face-surprised" style="font-size: 25px; color: #1E9FFF;"></i>&nbsp;&nbsp;问题
                        </button>
                        <button class="layui-btn layui-btn-primary">
                            <i class="layui-icon layui-icon-edit" style="font-size: 25px; color: #1E9FFF;"></i>&nbsp;&nbsp;回答
                        </button>
                    </div>
                </div>

                    <div class="layui-row">
                        <div class="layui-card" style="height: 200px;width: 100%">
                            <table class="layui-table">
                                <tbody>
                                <tr>
                                    <td>
                                        <i class="layui-icon layui-icon-rate-solid" style="font-size: 25px; color: #77839c;"></i>
                                        &nbsp;&nbsp;我的收藏
                                    </td>
                                    <td class="td_text">
                                        <span class="layui-badge layui-bg-gray">1</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <i class="layui-icon layui-icon-face-smile-b" style="font-size: 25px; color: #77839c;"></i>
                                        &nbsp;&nbsp;我的关注
                                    </td>
                                    <td class="td_text">
                                        <span class="layui-badge layui-bg-gray">1</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <i class="layui-icon layui-icon-praise" style="font-size: 25px; color: #77839c;"></i>
                                        &nbsp;&nbsp;我的点赞
                                    </td>
                                    <td class="td_text">
                                        <span class="layui-badge layui-bg-gray">1</span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="layui-row">
                        <%--最新问题--%>
                        <div class="layui-card" style="height: 400px;margin-top: 30px">
                            <table class="layui-hide" id="newestQuestions"></table>
                        </div>
                    </div>

            </div>
        </div>
    </div>

    <form id="form" method="post" action="">
        <input type="text" id="time" name="time"/>
    </form>

    <%--引入逻辑处理JS--%>
    <script src="<%=request.getContextPath() %>/assets/js/index.js"></script>
</body>
</html>
