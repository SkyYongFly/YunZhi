/**
 *  云知问答首页逻辑处理JS
 *
 *  @author zhuyong
 */

//每次加载最新问题数量
var QUESTIONS_NUM = 10;


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

layui.use('flow', function(){
    var flow = layui.flow;
    flow.load({
        elem: '#questions'
        ,done: function(page, next){
            var lis = [];
            $.get('question/getNewestQuestionsDetails.do?page='+page, function(res){
                layui.each(res, function(index, item){
                    lis.push(
                        '<div class="layui-card">' +
                            '<div class="layui-card-header" onclick="showQuestionDetail(' + item.qid + ');">' + item.title + '</div>' +
                            '<div class="layui-card-body">' + item.title + '</div>' +
                        '</div>'
                    );
                });

                next(lis.join(''), page < res.length / QUESTIONS_NUM);
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
