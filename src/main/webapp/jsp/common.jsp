<%--
  Created by IntelliJ IDEA.
  User: zhuyong
  Date: 2018/5/16
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>公共JS页面</title>

    <%--引入Layui样式--%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/layui/css/layui.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/icons/iconfont.css"/>
    <%--引用字体库--%>
    <link href='http://cdn.webfont.youziku.com/webfonts/nomal/118383/19673/5afd7be4f629db0e501e98d3.css' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/common.css"/>

    <%--引入jquery--%>
    <script src="<%=request.getContextPath() %>/assets/plugins/js/jquery-3.3.1.min.js"></script>
    <%--引入Layui脚本--%>
    <script src="<%=request.getContextPath() %>/assets/plugins/layui/layui.all.js"></script>
    <%--引入滚动条JS--%>
    <script src="<%=request.getContextPath() %>/assets/plugins/js/jquery.nicescroll.min.js"></script>

    <%--引入公共JS--%>
    <script src="<%=request.getContextPath() %>/assets/js/common.js"></script>
    <%--引入回答点赞处理JS--%>
    <script src="<%=request.getContextPath() %>/assets/js/star.js"></script>
</head>
<body>
    <script type="text/javascript">
        var contextPath = "<%=request.getContextPath() %>";
    </script>

</body>
</html>
