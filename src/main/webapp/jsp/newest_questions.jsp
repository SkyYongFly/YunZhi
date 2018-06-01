<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    展示最新问题

-- User: zhuyong
-- Date: 2018/5/31
--%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>最新问题展示页面</title>
    <%@ include file="common.jsp"%>
</head>
<body>
    <ul class="flow-default" id="questions"></ul>

    <input type="text" id="time" name="time" style="display: none"/>

    <script type="text/javascript">
        var QUESTIONS_NUM = 10;
        //缓存用户进入首页的时间
        $("#time").val((new Date()).valueOf());

        //加载最新的问题
        layui.use('flow', function(){
            var time = $("#time").val();
            var flow = layui.flow;

            flow.load({
                elem: '#questions'
                ,done: function(page, next){
                    var lis = [];
                    $.get(getBaseUrl() + 'question/getNewestQuestionsDetails.do?page='+page + '&time=' + time, function(res){
                        layui.each(res.questions, function(index, item){
                            lis.push(
                                '<div class="layui-card">' +
                                    '<div class="layui-card-header userHeadParentElement" onclick="showUserDetail(' + item.userid + ');">' +
                                        '<img src="' + showUserHeadImg(item.userheadimg) + '" class="userSmallHead"/>' +
                                        '<a class="userName">' + item.username + '</a>' +
                                        '<a class="userSignature">' + showSignature(item.signature) + '</a>' +
                                    '</div>' +
                                    '<div class="layui-card-body">' +
                                        '<div class="layui-row questionDetailTitle" onclick="showQuestionDetail(' + item.qid + ');">' +
                                            item.title +
                                        '</div>' +
                                        '<div class="layui-row questionDetailText"  onclick="showQuestionDetail(' + item.qid + ');">' +
                                            showQuestionText(item.text) +
                                        '</div>' +
                                        '<div class="layui-row questionAction">' +
                                            '<i class="iconfont icon-pinglun"></i>&nbsp;&nbsp;' + showAnswersNum(item.answersnum) +
                                            '<i class="layui-icon layui-icon-share item-margin"></i>&nbsp;&nbsp;分享' +
                                            '<i class="layui-icon layui-icon-rate-solid item-margin"></i>&nbsp;&nbsp;收藏' +
                                            '<i class="layui-icon layui-icon-flag item-margin"></i>&nbsp;&nbsp;举报' +
                                        '</div>' +
                                    '</div>' +
                                '</div>'
                            );
                        });

                        next(lis.join(''), page < res.sum / QUESTIONS_NUM);
                        window.parent.resize(1);
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

        /**
         * 显示问题内容
         */
        function showQuestionText(text) {
            if(isNullOrEmpty(text)){
                return "";
            }

            if(text.length < 85){
                return text;
            }

            return text.substr(0, 85) + " ... " + '<a href="#/" class="read-more">查看全文 &raquo;</a>';
        }

        /**
         * 显示问题回答数
         *
         * @param answersnum
         * @returns {string}
         */
        function showAnswersNum(answersnum) {
            if(undefined == answersnum || 0 == answersnum){
                return "添加回答";
            }

            return answersnum + " 条回答";
        }

        /**
         * 显示回答点赞数
         *
         * @param hotsatr
         * @returns {string}
         */
        function showAnswerStar(hotsatr) {
            if(undefined == hotsatr ||  0 == hotsatr){
                return "点赞";
            }

            return hotsatr + " 条点赞";
        }

    </script>
</body>
</html>
