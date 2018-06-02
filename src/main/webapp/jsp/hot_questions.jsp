<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    展示最热门问题

-- User: zhuyong
-- Date: 2018/5/31
--%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>热门问题展示页面</title>
    <%@ include file="common.jsp"%>
</head>
<body>
    <%--热门问题相关信息--%>
    <ul class="flow-default" id="hotQuestions"></ul>
    <%--请求缓存标识--%>
    <input type="text" id="token" name="token" style="display: none"/>

    <script type="text/javascript">
        //每次加载最新问题数量
        var QUESTIONS_NUM = 10;
        //初始化缓存标识
        $("#token").val(null);

        //加载热门问题、回答
        layui.use('flow', function(){
            var flow = layui.flow;

            flow.load({
                elem: '#hotQuestions'
                ,done: function(page, next){
                    var lis = [];
                    $.get(getBaseUrl() + 'question/getHotQuestionsDetails.do?page='+page + '&token=' + $("#token").val(), function(res){
                        layui.each(res.questions, function(index, item){
                            lis.push(
                                '<div class="layui-card">' +
                                    '<div class="layui-card-header userHeadParentElement" onclick="showUserDetail(' + item.hotuserid + ');">' +
                                        '<img src="' + showUserHeadImg(item.hotuserheadimg) + '" class="userSmallHead"/>' +
                                        '<a class="userName">' + item.hotusername + '</a>' +
                                        '<a class="userSignature">' + showSignature(item.hotsignature) + '</a>' +
                                    '</div>' +
                                    '<div class="layui-card-body">' +
                                        '<div class="layui-row questionDetailTitle" onclick="showQuestionDetail(' + item.qid + ');">' +
                                            item.title +
                                        '</div>' +
                                        '<div class="layui-row hotanwer"  onclick="showQuestionDetail(' + item.qid + ');">' +
                                            showAnswerText(item.hotanswer) +
                                        '</div>' +
                                        '<div class="layui-row questionAction">' +
                                            '<button class="layui-btn layui-btn-sm starbtn"  onclick="star(' + item.qid + "," + item.hotanswerid + ');">' +
                                                '<i class="layui-icon layui-icon-praise" id="' + item.hotanswerid +'stars">&nbsp;&nbsp;' + item.hotstar + '</i>' +
                                            '</button>' +
                                            '<i class="layui-icon layui-icon-share item-margin"></i>&nbsp;&nbsp;分享' +
                                            '<i class="layui-icon layui-icon-rate-solid item-margin"></i>&nbsp;&nbsp;收藏' +
                                            '<i class="layui-icon layui-icon-flag item-margin"></i>&nbsp;&nbsp;举报' +
                                        '</div>' +
                                    '</div>' +
                                '</div>'
                            );
                        });

                        //设置分页信息
                        next(lis.join(''), page < res.sum / QUESTIONS_NUM);
                        //设置缓存标识
                        $("#token").val(res.token);

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
         * 显示问题热门回答内容
         */
        function showAnswerText(hotanswer) {
            if(isNullOrEmpty(hotanswer)){
                return "";
            }

            hotanswer = removeHTMLTag(hotanswer);

            if(hotanswer.length < 130){
                return hotanswer;
            }

            return hotanswer.substr(0, 130) + " ... " + '<a href="#/" class="read-more">查看全文 &raquo;</a>';
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
    </script>
</body>
</html>
