/**
 *  问题详情页面逻辑处理JS
 *
 *  @author zhuyong
 */

//实例化编辑器
var um = UM.getEditor('container',{
    toolbars: [
        ['fullscreen', 'source', 'undo', 'redo', 'bold']
    ],
    initialFrameWidth : "100%",
    initialFrameHeight: 400
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
