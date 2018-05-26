<%--
  Created by IntelliJ IDEA.
  User: zhuyong
  Date: 2018/5/24  20:35
  Description:
        提问问题页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>提问问题</title>

    <%@ include file="common.jsp"%>
    <script href="<%=request.getContextPath() %>/assets/plugins/layui/formSelects-v3.js" type="text/javascript"></script>
</head>
<body>
    <div class="layui-container container_location">
        <div class="layui-row layui-col-space10">
            <div  id="quetitle" class="layui-col-md8">
                我的问题
            </div>
        </div>

        <form id="form" class="layui-form">

            <div class="layui-row layui-col-space10 row_margin">
                <div  class="layui-col-md8">
                    <textarea id="title"  name="title" placeholder="问题标题" class="layui-textarea"></textarea>
                </div>
            </div>

            <div class="layui-row layui-col-space10 row_margin2">
                <div class="layui-col-md8">
                    <select id="topic" xm-select="city" lay-search style="width: 100%;">
                        <option value="">添加话题</option>
                        <optgroup label="文学">
                            <option value="0101">古典文学</option>
                            <option value="0102">现代诗歌</option>
                        </optgroup>
                        <optgroup label="科技">
                            <option value="0201">宇宙航天</option>
                            <option value="0202">计算机</option>
                        </optgroup>
                    </select>
                </div>
            </div>

            <div class="layui-row layui-col-space10 row_margin2">
                <div class="layui-col-md8" style="text-align: left;padding-left:10px">
                    问题描述（可选）：
                </div>
            </div>

            <div class="layui-row layui-col-space10 row_margin2">
                <div class="layui-col-md8">
                    <textarea id="text" name="text" placeholder="问题描述" class="layui-textarea"></textarea>
                </div>
            </div>

        </form>

        <div class="layui-row layui-col-space10 row_margin3">
            <div class="layui-col-md2 layui-col-md-offset2">
            </div>
            <div class="layui-col-md4" style="text-align: center">
                <button class="layui-btn layui-btn-normal" onclick="submit();">提交问题</button>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        layui.use('form', function(){
            var form = layui.form;
            form.render();
        });

        //提交问题
        function submit() {
            var title = $("#title").val();
            if(isNullOrEmpty(title)){
                layer.msg("标题不能为空");
                return false;
            }

            // var topic = $("#topic").val();
            // if(isNullOrEmpty(topic)){
            //     layer.msg("话题不能为空");
            //     return false;
            // }

            var text = $("#text").val();

            $.ajax({
                url: getBaseUrl() + "question/addQuestion.do",
                type: "POST",
                data: JSON.stringify({"title":title, "text":text}),
                contentType: "application/json",
                dataType: "json",
                success: function (data) {
                    layer.msg(data.message);

                    //成功
                    if(1 == data.code){
                        setTimeout(function() {
                            window.parent.closeOpenPage();
                        },1000);
                    }
                }
            });

        }
    </script>
</body>
</html>