/**
 *  云知问答首页逻辑处理JS
 *
 *  @author zhuyong
 */

//每次加载最新问题数量
var QUESTIONS_NUM = 10;
//缓存用户进入首页的时间
$("#time").val((new Date()).valueOf());


layui.use('table', function(){
    var table = layui.table;

    //获取最新问题
    getNewestQuestions(table);
});

layui.use('element', function(){
    var element = layui.element;

    //监听导航tab切换
    element.on('tab(navigations)', function(data){
        element.render('tab', "navigations");
    });
});

//加载热门问题、回答
layui.use('flow', function(){
    var time = $("#time").val();
    var flow = layui.flow;

    flow.load({
        elem: '#hotQuestions'
        ,done: function(page, next){
            var lis = [];
            $.get('question/getHotQuestionsDetails.do?page='+page + '&time=' + time, function(res){
                layui.each(res.questions, function(index, item){
                    lis.push(
                        '<div class="layui-card">' +
                        '<div class="layui-card-header userHeadParentElement" onclick="showUserDetail(' + item.userid + ');">' +
                        '<img src="assets/plugins/layui/images/user1.jpg" class="userSmallHead"/>' +
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
            });
        }
    });
});

//加载最新的问题
layui.use('flow', function(){
    var time = $("#time").val();
    var flow = layui.flow;

    flow.load({
        elem: '#questions'
        ,done: function(page, next){
            var lis = [];
            $.get('question/getNewestQuestionsDetails.do?page='+page + '&time=' + time, function(res){
                layui.each(res.questions, function(index, item){
                    lis.push(
                        '<div class="layui-card">' +
                            '<div class="layui-card-header userHeadParentElement" onclick="showUserDetail(' + item.userid + ');">' +
                                '<img src="assets/plugins/layui/images/user1.jpg" class="userSmallHead"/>' +
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
            });
        }
    });
});


$(function () {
    //获取用户信息
    getUserInfo();
});

/**
 * 获取系统中最新提问的问题
 */
function getNewestQuestions(table) {
    table.render({
        elem: '#newestQuestions'
        ,url: getBaseUrl() + "question/getNewestQuestions.do"
        ,page: false
        ,cols: [[
            {field:'title', title: '最新问题', sort: false}
        ]]
    });
}


/**
 *  提问问题
 */
function addQuestion(){
    layer.open({
        type: 2,
        title:false,
        content: [contextPath + "/jsp/question.jsp", 'no'],
        area: ['600px', '600px'],
        end:function (data) {
            refresh();
        }
    });
}

/**
 * 刷新页面
 */
function refresh() {
    getNewestQuestions(layui.table);
}

/**
 * 打开问题详情页面
 *
 * @param  qid
 */
function showQuestionDetail(qid) {
    if(!isNullOrEmpty(qid)){
        window.open("jsp/question_detail.jsp?qid=" + qid);
    }
}

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
    if(0 == answersnum){
        return "添加回答";
    }

    return answersnum + " 条回答";
}
