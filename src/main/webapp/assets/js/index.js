/**
 *  云知问答首页逻辑处理JS
 *
 *  @author zhuyong
 */
$(document).ready(
    function() {
        $("html").niceScroll();

        $("#hotQuestionsFrame").niceScroll({
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

        $("#newestQuestionsFrame").niceScroll({
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

layui.use('table', function(){
    var table = layui.table;

    //获取最新问题
    getNewestQuestions(table);
});

layui.use('element', function(){
    var element = layui.element;

    //监听导航tab切换
    element.on('tab(navigations)', function(data){
        if(0 == data.index){
            $("#newestQuestionsFrame").getNiceScroll().hide();
            $("#hotQuestionsFrame").getNiceScroll().show();
            $("#hotQuestionsFrame").getNiceScroll().resize();
        }else if(1 == data.index){
            $("#hotQuestionsFrame").getNiceScroll().hide();
            $("#newestQuestionsFrame").getNiceScroll().show();
            $("#newestQuestionsFrame").getNiceScroll().resize();
        }
    });
});

$(function () {
    //获取用户信息
    getUserInfo();
});

/**
 * 获取系统中最新提问的问题
 */
function getNewestQuestions(table) {
    table.render({
        elem: '#newestQuestions'
        ,url: getBaseUrl() + "question/getNewestQuestions.do"
        ,page: false
        ,cols: [[
            {field:'title', title: '最新问题', sort: false}
        ]]
    });
}

/**
 * 刷新页面
 */
function refresh() {
    getNewestQuestions(layui.table);
}

/**
 * 重新加载NiceScroll滚动条
 */
function resize(index) {
    if(0 == index){
        $("#hotQuestionsFrame").getNiceScroll().resize();
    }else if(1 == index){
        $("#newestQuestionsFrame").getNiceScroll().resize();
    }
}