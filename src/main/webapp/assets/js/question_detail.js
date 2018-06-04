/**
 *  问题详情页面逻辑处理JS
 *
 *  @author zhuyong
 */

var ANSWERS_PAGE_SIZE = 10;

layui.use('element', function(){
    var element = layui.element;
    element.init();
});

//实例化编辑器
var um = UE.getEditor('container',{
    toolbars: [
        [
            'bold', //加粗
            'indent', //首行缩进
            'italic', //斜体
            'blockquote', //引用
            'horizontal', //分隔线
            'removeformat', //清除格式
            'insertcode', //代码语言
            'link', //超链接
            'forecolor', //字体颜色
            'insertorderedlist', //有序列表
            'insertunorderedlist', //无序列表
            'fullscreen', //全屏
            'customstyle' //自定义标题
        ]
    ],
    initialFrameWidth : "100%",
    initialFrameHeight: 200,
    saveInterval:30000,
    maximumWords:3000,
    elementPathEnabled:false
});


//加载问题所有回答
layui.use('flow', function(){
    var flow = layui.flow;
    var qid = $("#qid").val();

    flow.load({
        elem: '#allAnswers'
        ,done: function(page, next){
            var lis = [];
            $.get(getBaseUrl() + 'answer/getQuestionAllAnswers.do?page='+page + "&qid=" + qid, function(res){
                layui.each(res.answersList, function(index, item){
                    lis.push(
                        '<div class="layui-card">' +
                            '<div class="layui-card-header userHeadParentElement" onclick="showUserDetail(' + item.userid + ');">' +
                                '<img src="' + showUserHeadImg(item.userheadimg) + '" class="userSmallHead"/>'  +
                                '<a class="userName">' + item.username + '</a>' +
                                '<a class="userSignature">' + showSignature(item.signature) + '</a>' +
                            '</div>' +
                            '<div class="layui-card-body">' +
                                '<div class="layui-row hotanwer">' +
                                    showAnswerText(item.text) +
                                '</div>' +
                                '<div class="layui-row questionAction">' +
                                    '<button class="layui-btn layui-btn-sm starbtn"  onclick="star(' + item.qid + "," + item.aid + ');">' +
                                        '<i class="layui-icon layui-icon-praise" id="' + item.aid +'stars">&nbsp;&nbsp;' + item.star + '</i>' +
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
                next(lis.join(''), page < res.sum / ANSWERS_PAGE_SIZE);
            });
        }
    });
});



$(function () {
    //获取用户信息
    getUserInfo();

    getQuestionDetail();
});

/**
 * 关注问题
 */
function attection() {

}

/**
 * 获取问题详情
 */
function getQuestionDetail() {
    var qid = $("#qid").val();

    $.ajax({
        url:  getBaseUrl() + "question/getQuestionDetail.do?qid=" + qid,
        type: "GET",
        contentType:"application/json",
        success:function (data) {
            if(data){
                $("#questionTitle").text(data.title);
                $("#questionText").text(data.text);
                $("#answersNum").text(data.answersnum + "条回答");
            }
        }
    });
}

/**
 * 添加回答
 */
function editAnswer() {
    $("#editAnwer").show();
}

/**
 * 提交回答
 */
function addAnswer() {
    var text = UE.getEditor('container').getContent();

    if(isNullOrEmpty(text)){
        return;
    }

    var qid = $("#qid").val();
    $.ajax({
        url:  getBaseUrl() + "answer/addAnswer.do",
        type: "POST",
        data:JSON.stringify({"qid": qid, "text": text}),
        contentType:"application/json",
        dataType: "json",
        success:function (data) {
            if(data){
                layer.msg(data.message);

                if(1 == data.code){
                    window.location.reload();
                }
            }
        }
    });
}



//***************************************
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
 * 显示问题回答内容
 */
function showAnswerText(hotanswer) {
    if(isNullOrEmpty(hotanswer)){
        return "";
    }

    hotanswerText = removeHTMLTag(hotanswer);

    if(hotanswerText.length < 500){
        return hotanswer;
    }

    return hotanswer.substr(0, 500) + " ... " + '<a href="#/" class="read-more">查看全文 &raquo;</a>';
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

