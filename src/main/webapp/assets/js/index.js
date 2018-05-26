/**
 *  云知问答首页逻辑处理JS
 *
 *  @author zhuyong
 */



$(function () {
    setUserInfo();
});

/**
 * 获取用户信息
 */
function setUserInfo() {
    $.ajax({
        url:  getBaseUrl() + "login/getUserInfo.do",
        type: "GET",
        contentType:"application/json",
        success:function (data) {
            if(!isNullOrEmpty(data.id)){
                $("#haslogin").show();
                $("#usericon").show();
                $("#hasnotlogin").hide();

                $("#username").text(data.username);
                if(!isNullOrEmpty(data.signature)) {
                    $("#signature").text(data.signature);
                }
            }else{
                $("#hasnotlogin").show();
                $("#haslogin").hide();
                $("#usericon").hide();
            }
        }
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
        area: ['600px', '600px']
    });
}

/**
 * 请求登录页面
 */
function login() {
    $("#form").attr("action", getBaseUrl() + "login/getLoginPage.do");
    $("#form").submit();
}

//请求注册页面
function register() {
    $("#form").attr("action", getBaseUrl() + "register/getPage.do");
    $("#form").submit();
}














function closeOpenPage() {
    layer.closeAll('iframe');//关闭弹窗
}