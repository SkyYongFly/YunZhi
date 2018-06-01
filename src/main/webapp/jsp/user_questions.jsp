<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    用户提问的问题列表

-- User: zhuyong
-- Date: 2018/5/31
--%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>用户提问问题列表</title>
    <%@ include file="common.jsp"%>
</head>
<body>
    <ul class="flow-default" id="questions"></ul>

    <script type="text/javascript">
        var QUESTIONS_NUM = 10;

        //加载最新的问题
        layui.use('flow', function(){
            var flow = layui.flow;

            flow.load({
                elem: '#questions'
                ,done: function(page, next){
                    var lis = [];
                    $.get(getBaseUrl() + 'question/getUserQuestions.do?page=' + page, function(res){
                        layui.each(res.questions, function(index, item){
                            lis.push(
                                '<div class="layui-card">' +
                                    '<div class="layui-card-header userHeadParentElement">' +
                                        '<div class="layui-row questionDetailTitle" onclick="showQuestionDetail(' + item.qid + ');">' +
                                            item.title +
                                        '</div>' +
                                    '</div>' +
                                    '<div class="layui-card-body">' +
                                        '<div class="layui-row questionDetailText"  onclick="showQuestionDetail(' + item.qid + ');">' +
                                            item.text +
                                        '</div>' +
                                        '<div class="layui-row questionAction">' +
                                            '<i class="iconfont icon-pinglun"></i>&nbsp;&nbsp;' + item.answersnum + "条回答" +
                                            '<i class="layui-icon layui-icon-date item-margin"></i>&nbsp;&nbsp;' + timestampToTime(item.createtime) +
                                        '</div>' +
                                    '</div>' +
                                '</div>'
                            );
                        });

                        next(lis.join(''), page < res.sum / QUESTIONS_NUM);
                        window.parent.resize(0);
                    });
                }
            });
        });

        /**
         * 显示签名
         */
        function showSignature(signature) {
            if(isNullOrEmpty(signature)){
                return "";
            }

            return signature;
        }
    </script>
</body>
</html>
