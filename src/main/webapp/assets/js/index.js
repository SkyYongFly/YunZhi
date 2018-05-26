/**
 *  云知问答首页逻辑处理JS
 *
 *  @author zhuyong
 */


/**
 *  提问问题
 */
function addQuestion(){
    layer.open({
        type: 2,
        title:false,
        content: [contextPath + "/jsp/question.jsp", 'no'],
        area: ['600px', '600px']
    });
}

function closeOpenPage() {
    layer.closeAll('iframe');//关闭弹窗
}