/**
 *  用户个人中心处理JS
 *
 *  @author zhuyong
 */

$(document).ready(
    function() {
        //加载用户头像
        loadUserHeadImg();
        //加载用户昵称、签名信息
        loadUserInfo();

        $("html").niceScroll();
        $("#userQuestiosFrame").niceScroll({
            styler:"fb",
            cursorcolor:"#EAEAEA",
            cursorwidth: '3',
            cursorborderradius: '10px',
            background: '#e4e4e4',
            spacebarenabled:false,
            cursorborder: '',
            scrollspeed:40,
            autohidemode:false
        });
    }
);

layui.use('element', function(){
    var element = layui.element;

    //监听导航tab切换
    element.on('tab(navigations)', function(data){
    });
});

/**
 * 重新加载NiceScroll滚动条
 */
function resize(index) {
    if(0 == index){
        $("#userQuestiosFrame").getNiceScroll().resize();
    }else if(1 == index){
        $("#userAnswersFrame").getNiceScroll().resize();
    }
}

/**
 * 头像上传
 */
layui.use('upload', function(){
    var upload = layui.upload;
    var url = getBaseUrl() + 'files/upload.do?type=USERHEADIMG';

    //执行实例
    var uploadInst = upload.render({
        elem: '#userHeadImg',   //绑定元素
        url: url,               //上传接口
        accept: 'images',       //只允许上传图片
        acceptMime: 'image/*',  //只显示图片文件
        field:"file",           //文件域字段名
        size: 10240,            //10M
        done: function(res){
            if(1 == res.code){
                loadUserHeadImg();
            }else{
                layer.msg(res.message);
            }
        },
        error: function(){
        }
    });
});

/**
 * 获取用户信息
 */
function loadUserInfo() {
    $.ajax({
        url:  getBaseUrl() + "user/getUserInfo.do",
        type: "GET",
        contentType:"application/json",
        success:function (data) {
            if(data){
                $("#username").text(data.username);
                $("#signature").text(data.signature);

                if(isNullOrEmpty(data.signature)){
                    $("#signature").text("（哎，签名哪去了~~~）");
                }
            }
        }
    });
}
