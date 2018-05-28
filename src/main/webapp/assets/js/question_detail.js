/**
 *  问题详情页面逻辑处理JS
 *
 *  @author zhuyong
 */

//实例化编辑器
var um = UE.getEditor('container',{
    toolbars: [
        [
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'indent', //首行缩进
            'italic', //斜体
            'underline', //下划线
            'strikethrough', //删除线
            'subscript', //下标
            'fontborder', //字符边框
            'superscript', //上标
            'formatmatch', //格式刷
            'blockquote', //引用
            'selectall', //全选
            'horizontal', //分隔线
            'removeformat', //清除格式
            'time', //时间
            'date', //日期
            'unlink', //取消链接
            'cleardoc', //清空文档
            'insertcode', //代码语言
            'fontfamily', //字体
            'fontsize', //字号
            'paragraph', //段落格式
            'link', //超链接
            'emotion', //表情
            'spechars', //特殊字符
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter', //居中对齐
            'justifyjustify', //两端对齐
            'forecolor', //字体颜色
            'backcolor', //背景色
            'insertorderedlist', //有序列表
            'insertunorderedlist', //无序列表
            'fullscreen', //全屏
            'directionalityltr', //从左向右输入
            'directionalityrtl', //从右向左输入
            'rowspacingtop', //段前距
            'rowspacingbottom', //段后距
            'pagebreak', //分页
            'imagenone', //默认
            'imageleft', //左浮动
            'imageright', //右浮动
            'imagecenter', //居中
            'lineheight', //行间距
            'edittip ', //编辑提示
            'customstyle', //自定义标题
            'touppercase', //字母大写
            'tolowercase', //字母小写
        ]
    ],
    initialFrameWidth : "100%",
    initialFrameHeight: 400,
    saveInterval:30000,
    maximumWords:3000,
    elementPathEnabled:false
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
            }
        }
    });
}

/**
 * 提交回答
 */
function addAnswer() {debugger;
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
            }
        }
    });
}
