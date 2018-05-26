/**
 *  云知问答首页逻辑处理JS
 *
 *  @author zhuyong
 */

layui.use('table', function(){
    var table = layui.table;

    table.render({
        elem: '#test'
        ,url:'/demo/table/user/'
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,cols: [[
            {field:'id', width:80, title: 'ID', sort: true}
            ,{field:'username', width:80, title: '用户名'}
            ,{field:'sex', width:80, title: '性别', sort: true}
            ,{field:'city', width:80, title: '城市'}
            ,{field:'sign', title: '签名', width: '30%', minWidth: 100} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
            ,{field:'experience', title: '积分', sort: true}
            ,{field:'score', title: '评分', sort: true}
            ,{field:'classify', title: '职业'}
            ,{field:'wealth', width:137, title: '财富', sort: true}
        ]]
    });
});


$(function () {
    //获取用户信息
    getUserInfo();

    //获取最新问题
    getNewestQuestions();
});

/**
 * 获取用户信息
 */
function getUserInfo() {
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
 * 获取系统中最新提问的问题
 */
function getNewestQuestions() {
    $.ajax({
        url: getBaseUrl() + "question/getNewestQuestions.do",
        type: "GET",
        contentType: "application/json",
        success: function (data) {
            if(data && data.length > 0){

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