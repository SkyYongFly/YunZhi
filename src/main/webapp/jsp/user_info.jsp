<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  用户个人中心页面
  User: zhuyong
  Date: 2018/5/31  21:38
--%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>个人中心</title>

    <%--引入公共JSP页面--%>
    <%@ include file="common.jsp"%>
    <%--引入样式--%>
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
                            <button class="layui-btn layui-btn-primary" onclick="loadIndex();">首页</button>
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
                    <div class="layui-col-md10">
                        <button class="layui-btn layui-btn-primary" onclick="message();">消息</button>
                        <button class="layui-btn layui-btn-primary" onclick="logout();">退出</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-container">
        <div  id="center" class="layui-row layui-col-space30">
            <div class="layui-col-md8">
                <div class="layui-row">
                    <%--用户头像--%>
                    <div class="layui-col-md3">
                        <img id="userHeadImg" name="file" class="userBigHead"/>
                    </div>
                    <div class="layui-col-md9">
                        <div class="layui-card" style="margin-top:50px">
                            <%--用户昵称--%>
                            <div class="layui-card-header userNameBig">
                                <p id="username"></p>
                            </div>
                            <%--用户签名--%>
                            <div class="layui-card-body" style="height: 30px;display: inline-block">
                                <div class="layui-row">
                                    <div class="layui-col-md10">
                                        <p id="signature"></p>
                                    </div>
                                    <div class="layui-col-md2">
                                        <button class="layui-btn  layui-btn-primary layui-btn-sm">编辑签名</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="navigation"  class="layui-row layui-col-space10">
                    <div class="layui-tab layui-tab-brief" lay-filter="navigations">
                        <ul class="layui-tab-title  usernavtab">
                            <li lay-id="hot" class="layui-this">
                                <i class="layui-icon layui-icon-list"></i> &nbsp;提问
                            </li>
                            <li lay-id="question">
                                <i class="layui-icon layui-icon-chat"></i>&nbsp;回答
                            </li>
                            <li lay-id="answer" >
                                <i class="layui-icon layui-icon-rate"></i>&nbsp;收藏
                            </li>
                        </ul>
                        <div class="layui-tab-content" style="padding: 20px 0 0 0">
                            <div class="layui-tab-item layui-show">
                                <iframe id="userQuestiosFrame" src="user_questions.jsp" class="user-iframe"></iframe>
                            </div>
                            <div class="layui-tab-item">
                            </div>
                            <div class="layui-tab-item">内容3</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="layui-col-md4">
                <div class="layui-row">
                    <div class="layui-card" style="height: 200px;width: 100%">
                        关注的话题
                    </div>
                </div>

                <div class="layui-row">
                    <div class="layui-card" style="height: 400px;margin-top: 30px">
                        关注话题热门内容
                    </div>
                </div>
            </div>
        </div>
    </div>

    <form id="form" method="post" action="">
    </form>

    <%--引入用户信息处理JS--%>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/user_info.js"></script>
</body>
</html>
