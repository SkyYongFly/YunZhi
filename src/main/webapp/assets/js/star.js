/**
 *  回答点赞处理JS
 *
 *  @author zhuyong
 */


/**
 * 显示回答点赞数
 *
 * @param satr
 * @returns {string}
 */
function showAnswerStar(satr) {
    if(undefined == satr ||  0 == satr){
        return "点赞";
    }

    return satr + " 条点赞";
}


/**
 * 给指定回答点赞或者取消点赞
 *
 * @param   qid    问题ID
 * @param   aid    回答ID
 */
function star(qid, aid) {
    if(isNullOrEmpty(aid) || isNullOrEmpty(qid)){
        return;
    }

    $.ajax({
        url:  getBaseUrl() + "answer/starAction.do?aid=" + aid + "&qid=" + qid,
        type: "GET",
        contentType:"application/json",
        success:function (data) {
            if(data){
                $("#" + aid + "stars").html("&nbsp;&nbsp;" + data.stars);
            }
        }
    });
}

