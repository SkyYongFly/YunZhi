/**
 *  前端页面通用函数定义JS
 *
 *  @author zhuyong
 */

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
