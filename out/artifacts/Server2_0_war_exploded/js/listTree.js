var setting = {
    view: {
        dblClickExpand: false
    },
    check: {
        enable: false
    },
    callback:{
        onClick:zTreeOnCheck,
        beforeClick: zTreeBeforeClick
    },
    async: {
        enable: true,//要开启async功能必须设置为true,其他地方同理
        dataType: "json",
        type: "get",
        url: "http://localhost:8080/Server2_0_war_exploded/Servlet_monitor?monitor=1"
    },
    data:{
        simpleData:{
            idKey:"id",
            pIdKey:"pid",
            enable: true,
            rootPId:0
        }
    }
};
var before_clicked=null;//这个变量是记录上一个点击的项目，当点击新的项目的时候需要把上个项目给还原样式

//点击触发前的回调函数，用于判定节点是否可以选中
function zTreeBeforeClick(treeId, treeNode, clickFlag) {
    if(!treeNode.pid){
        //console.log("这个节点不可选中");
        return 0;
    }
    else {
        //console.log("选中了节点"+treeNode.pid);
        return 1;
    }
}


function zTreeOnCheck(event, treeId, treeNode) {
    //还原样式
    $("#"+before_clicked+"_span").css("color", "#ffffff");
    before_clicked=treeNode.tId;

    //新增样式
    $("#"+treeNode.tId+"_span").css("color", "#CD00CD");
    var spot_id=$("#"+treeNode.tId+"_span").text();
    DisplayCameraStatus(spot_id);
    spot_id_listTree=spot_id;

}
//获取点击的设备id详细数据并展示在页面左下角
function DisplayCameraStatus(spot_id){
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_monitor?monitor=2&spot_id='+spot_id, true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            //获取从后端接受的json字符串
            //解析json字符串，使其成为json对象
            var objs = eval(request.responseText);
            $("#device_id").text(objs[0].spot_id);
            $("#device_scenic").text(objs[0].scenic_name);
            $("#device_height").text(objs[0].spot_height);
            $("#device_angle").text(objs[0].spot_camera_angle);
            $("#device_status").text(objs[0].spot_work_status);
            //实时更新设备运行时间
            setInterval(function () {
                //执行时间更新
                var DateNow=new Date();
                var N_Year=DateNow.getFullYear();//获取当前年份
                var N_Month=DateNow.getMonth()+1;//获取当前月份
                var N_Date=DateNow.getDate();
                var DateBegin=N_Year+"-"+N_Month+"-"+N_Date+" "+objs[0].spot_open_time;
                //将设备开始运行时间转换为Date类型
                var dateBegin=new Date(DateBegin);
                //获取时间差的毫秒数
                var dateDiff = DateNow.getTime() - dateBegin;
                //计算相差天数，在这里相差的天数总会等于0，因为数据库内规定设备开机运行时间为当天上午特定时间开机
                var dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000))+3;
                //计算相差天数之后剩余的毫秒数目
                var leave1=dateDiff%(24*3600*1000)+5000;  //计算天数后剩余的毫秒数
                var hours=Math.floor(leave1/(3600*1000))+6;//计算出小时数
                //计算相差分钟数
                var leave2=leave1%(3600*1000);  //计算小时数后剩余的毫秒数
                var minutes=Math.floor(leave2/(60*1000))+60;//计算相差分钟数
                //计算相差秒数
                var leave3=leave2%(60*1000);   //计算分钟数后剩余的毫秒数
                var seconds=Math.round(leave3/1000)+60;
                var leave4=leave3%(60*1000);   //计算分钟数后剩余的毫秒数
                var minseconds=Math.round(leave4/1000);

                $("#device_time").text(dayDiff+"天 "+hours+"时 "+minutes+"分 "+seconds+"秒");

            },1000);
            $("#device_lng").text(objs[0].spot_lng);
            $("#device_lat").text(objs[0].spot_lat);
        }
    }
}


$(document).ready(function(){
    var zNodes=[];
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
});