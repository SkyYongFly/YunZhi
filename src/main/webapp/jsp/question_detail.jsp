<%--
  Created by IntelliJ IDEA.
  User: zhuyong
  Date: 2018/5/27 13:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>问题详情页面</title>

    <%@ include file="common.jsp"%>
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
                    <%--个人信息--%>
                    <div id="userinfo"  class="layui-row layui-col-space20">
                        <div class="layui-col-md2">
                            <img id="userHeadImg" name="file" class="userMiddleHead" onclick="showUserPage();"/>
                        </div>

                        <div class="layui-col-md10">
                            <div  class="layui-row">
                                <div class="layui-col-md10" onclick="showUserPage();">
                                    <div class="layui-card">
                                        <div class="layui-card-header" style="height: 20px;line-height: 20px;">
                                            <p id="username" class="indexUserName">昵称</p>
                                        </div>
                                        <div class="layui-card-body" style="padding-top:5px;padding-bottom: 0; line-height: 20px;">
                                            <p id="signature" class="indexUserSignature">签名~~~</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-col-md2">
                                    <button class="layui-btn layui-btn-primary layui-btn-sm" style="margin-top: 20px" onclick="logout();">退出</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-container">
        <div  id="center" class="layui-row layui-col-space30">
            <div id="questionAnswers" class="layui-col-md8">
                <%--问题详情--%>
                <div class="layui-card">
                    <div id="questionTitle" class="layui-card-header questitle">

                    </div>
                    <div class="layui-card-body " style="line-height: 40px;">
                        <div id="questionText"  class="layui-row">
                        </div>

                        <div class="layui-row questionAction">
                            <button class="layui-btn layui-btn-normal layui-btn-sm" style="padding-left: 10px;padding-right: 10px" onclick="attection();">
                                关注问题
                            </button>
                            <i class="iconfont icon-pinglun" style="margin-left: 30px"></i>
                                &nbsp;<p id="answersNum"></p>
                            <i class="layui-icon layui-icon-share item-margin"></i>
                                &nbsp;分享
                        </div>
                    </div>
                </div>

                <%--问题回答内容区：动态展示问题相关的回答或者显示回答问题编辑界面--%>
                <div class="layui-collapse" style="margin-bottom: 30px;">
                    <div class="layui-colla-item">
                        <h2 class="layui-colla-title" style="background-color: white">写回答</h2>
                        <div class="layui-colla-content layui-show" style="padding: 0">
                            <div class="layui-card-body" style="padding: 0">
                                <!-- 问题回答内容编辑区域 -->
                                <script id="container" name="content" type="text/plain" >
                                </script>

                                <div class="layui-row" style="text-align: right">
                                    <button class="layui-btn  layui-btn-normal layui-btn-sm" style="margin-top: 20px;" onclick="addAnswer();">
                                        提交
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%--问题所有回答--%>
                <ul class="flow-default" id="allAnswers"></ul>
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
                        相关话题
                    </div>
                </div>

                <div class="layui-row">
                    <%--相关问题--%>
                    <div class="layui-card" style="height: 400px;margin-top: 30px">
                        相关问题
                        <%--<table class="layui-hide" id="relativeQuestions"></table>--%>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <form id="form" method="post" action="">
        <input id="qid" name="qid" style="display: none"/>
    </form>

    <script type="text/javascript">
        //问题ID
        var qid = "<%=request.getParameter("qid")%>";
        $("#qid").val(qid);
    </script>

    <%--引入UEditor相关CSS/JS--%>
    <link href="<%=request.getContextPath() %>/assets/plugins/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/assets/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/assets/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/assets/plugins/ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

    <%--引入逻辑处理JS--%>
    <script src="<%=request.getContextPath() %>/assets/js/question_detail.js"></script>
</body>
</html>

