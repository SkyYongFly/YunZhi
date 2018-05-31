/**
 *  前端页面通用函数定义JS
 *
 *  @author zhuyong
 */

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

/**
 * 判空
 *
 * @param str
 * @returns {boolean}
 */
function isNullOrEmpty(str){
    if(null == str || undefined == str || "" == str){
        return true;
    }

    return false;
}

/**
 * 验证用户名格式
 *
 * @param   username     要验证的用户名
 * @returns {boolean}   验证通过:true ; 不通过:false
 */
function validateUsername(username) {
    var myreg = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,10}$/;
    if (!myreg.test(username)) {
        return false;
    }

    return true;
}

/**
 * 判断是否为手机号
 *
 * @param   pone
 * @returns {boolean}
 */
function isPhoneValidate(pone) {
    var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
    if (!myreg.test(pone)) {
        return false;
    }

    return true;
}

/**
 * 判断是否为电话号码
 *
 * @param   tel
 * @returns {boolean}
 */
function isTelValidate(tel) {
    var myreg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
    if (!myreg.test(tel)) {
        return false;
    }

    return true;
}

/**
 * 验证密码格式
 *
 * @param   password     要验证的密码
 * @returns {boolean}   验证通过:true ; 不通过:false
 */
function validatePassword(password) {
    var myreg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
    if (!myreg.test(password)) {
        return false;
    }

    return true;
}

/**
 * 验证验证码格式
 *
 * @param   vercode     要验证的验证码
 * @returns {boolean}   验证通过:true ; 不通过:false
 */
function isVercodeValidate(vercode) {
    var myreg = /^[0-9]{6}$/;
    if (!myreg.test(vercode)) {
        return false;
    }

    return true;
}

/**
 * 获取服务器IP及端口
 * 例如：http://localhost:8080
 *
 * @returns
 */
function getLocalhostPath(){
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    return localhostPath;
}

/**
 * 获取当前应用名称
 *
 * @returns {string}
 */
function getDisplayName(){
    var pathName = window.document.location.pathname;
    var pos = pathName.indexOf('/', 1);
    var displayName = pathName.substring(0, pos+1);
    return displayName;
}

/**
 * 获取当前应用基础请求路径
 * 例如：http://localhost:8080/YunZhi
 *
 * @returns {string}
 */
function getBaseUrl(){
    return getLocalhostPath() + getDisplayName();
}

/**
 * 去除文本中HTML标记
 *
 * @param str
 * @returns {*}
 */
function removeHTMLTag(str) {
    str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
    str = str.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
    //str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
    str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
    str=str.replace(/\s/g,''); //将空格去掉
    return str;
}

/**
 * 当前页面加载为首页
 */
function loadIndex() {
    window.location.href = getBaseUrl() + "/index.jsp";
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
 * 打开问题详情页面
 *
 * @param  qid
 */
function showQuestionDetail(qid) {
    if(!isNullOrEmpty(qid)){
        window.open(contextPath + "/jsp/question_detail.jsp?qid=" + qid);
    }
}


//TODO 弹出层被遮挡bug