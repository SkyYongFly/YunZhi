<%--
  Created by IntelliJ IDEA.
  User: zhuyong
  Date: 2018/5/10
  Time: 23:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
    <script>
        $.ajax({
            url:"http://localhost:8080/WenDa/user/getAllUsers",
            method:"GET",
            contentType:"applocation/json",
            success:function (data) {
                alert(data);
            }
        });

    </script>
</body>
</html>
