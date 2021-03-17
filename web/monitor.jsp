<%--
  Created by IntelliJ IDEA.
  User: mochi
  Date: 2020/1/11
  Time: 下午3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>实时监控</title>
    <link rel="stylesheet" href="css/base.css">
    <link href="css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="css/videoJS/video-js.min.css" rel="stylesheet">
    <style>
        .info {
            background: #363F49;
            color: #A0A7B4;
            padding: 10px;
            max-width: 300px;
            min-width: 200px;
            font-size: 12px;
        }

        .info tr .content {
            text-align: right;
            color: #D3D8E0;
            max-width: 200px;
        }
    </style>
</head>
<style>

</style>
<body onload="test()">

<!--顶部-->
<header class="header left">
    <div class="left nav">
        <ul>
            <li><i class="nav_1"></i><a href="index.jsp">数据概览</a></li>
            <li class="nav_active"><i class="nav_2"></i><a href="monitor.jsp">实时监控</a></li>
            <li><i class="nav_3"></i><a href="visualization.jsp">空间分布</a></li>


        </ul>
    </div>
    <div class="header_center left">
        <h2><strong>深瞳智慧景区辅助管理系统</strong></h2>
        <p class="color_font"><small>Auxiliary management system of deep pupil intelligent scenic spot</small></p>
    </div>
    <div class="right nav text_right">
        <ul>
            <li><i class="nav_7"></i><a href="analyze.jsp">数据分析</a></li>
            <li><i class="nav_8"></i><a href="publish.jsp">公告发布</a></li>
            <li><i class="nav_4"></i><a href="">待定功能</a></li>
        </ul>
    </div>

</header>
<!--内容部分-->
<div class="con1 left" id="car_control">
    <div class="left car_left">
        <div class="left_up bow_shadow">
            <p>
                <input type="text" placeholder="输入景点名称" class="carNo_input"><input type="button" class="find_but"/>
            </p>
            <p class="set_list"><i class="list_i"></i> 设备列表：</p>
            <p>
            <ul id="treeDemo" class="ztree"></ul>
            </p>
        </div>
        <div class="left_down bow_shadow">
            <div class="text_center"><a href="javascript:void (0)" class="tab_a tab_aActive">基本信息</a><a
                    href="javascript:void (0)" class="tab_a">云台控制</a></div>
            <div class="car_content">
                <p><span>设备编号：</span><span id="device_id"></span></p>
                <p><span>所属景点：</span><span id="device_scenic"></span></p>
                <p><span>监控高度：</span><span id="device_height"></span></p>
                <p><span>监控仰角：</span><span id="device_angle"></span></p>
                <p><span>工作状态：</span><span id="device_status"></span></p>
                <p><span>运行时常：</span><span id="device_time"></span></p>
                <p><span>设备经度：</span><span id="device_lng"></span></p>
                <p><span>设备维度：</span><span id="device_lat"></span></p>
            </div>
        </div>
    </div>
    <div class="left car_center">
        <div class="car_center_div">
            <div class="car_center_div_div">
                <video id="rtmpVideo1" class="video-js" height="266px" width="327px" controls preload="auto">
                    <%--            <source src=" rtmp://192.168.2.179/live/" type="rtmp/mp4">--%>
                        <source src="rtmp://www.uav-space.com/vod2/uspace3.mp4" type="rtmp/mp4">
                </video>
            </div>
            <div class="car_center_div_div">
                <video id="rtmpVideo2" controls preload="auto" height="266px" width="327px" class="magin_left video-js">
                    <%--            <source src=" rtmp://192.168.2.179/live/" type="rtmp/flv">--%>
                        <source src="rtmp://www.uav-space.com/vod2/uspace3.mp4" type="rtmp/mp4">
                </video>
            </div>
        </div>
        <div class="car_center_div">
            <div class="car_center_div_div">
                <video id="rtmpVideo3" controls preload="auto" height="266px" width="327px" class="magin_top video-js">
                    <%--            <source src=" rtmp://192.168.2.179/live/" type="rtmp/flv">--%>
                        <source src="rtmp://58.200.131.2:1935/livetv/gdtv" type="rtmp/mp4">
<%--                        <source src="rtmp://47.95.245.186:1935/live/" type="rtmp/mp4">--%>
                </video>
            </div>
            <div class="car_center_div_div">
                <video id="rtmpVideo4" controls preload="auto" height="266px" width="327px"
                       class="magin_top magin_left video-js">
                    <%--            <source src=" rtmp://192.168.2.179/live/" type="rtmp/flv">--%>
<%--                        <source src="rtmp://47.95.245.186:1935/live/" type="rtmp/mp4">--%>
                        <source src="rtmp://s3.nsloop.com:3882/live/" type="rtmp/mp4">
                </video>
            </div>
        </div>


    </div>
    <div class="right car_right" id="map"></div>


</div>
<script>
    function test() {
        let temp = document.getElementById("rtmpVideo1");
        let temp2 = document.getElementById("rtmpVideo2");
        let temp3 = document.getElementById("rtmpVideo3");
        let temp4 = document.getElementById("rtmpVideo4");
        let text = "width:" + screen.width / 4.6 + "px;height:" + screen.height / 2.9 + "px;";
        console.log(text);
        temp.style.cssText = text;
        temp2.style.cssText = text;
        temp3.style.cssText = text;
        temp4.style.cssText = text;
    }
</script>
<script src="js/jquery/jQuery-2.2.0.min.js"></script>
<script src="js/echarts-all.js"></script>
<script src="js/base.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5ieMMexWmzB9jivTq6oCRX9j&callback"></script>
<script type="text/javascript" src="https://webapi.amap.com/maps?key=c466eb7b9e1399a963003019fc6af363&v=1.4.15&plugin=Map3D"></script>
<script type="text/javascript" src="https://webapi.amap.com/loca?key=c466eb7b9e1399a963003019fc6af363&v=1.3.2"></script>
<script src="js/car_control.js"></script>
<script src="js/ztree/jquery.ztree.all-3.5.js"></script>
<script src="js/listTree.js"></script>
<script src="js/videoJS/video.min.js"></script>
<script src="js/videoJS/videojs-flash.min.js"></script>
<script>
    videojs.options.flash.swf = 'js/videoJS/video-js.swf';
    // 初始化视频，设为全局变量
    var myPlayer = videojs('rtmpVideo1', {
        autoplay: true,
        controls: true,//控制条
        muted: true,// 静音
        preload: "auto",// 预加载
        language: "zh-CN",// 初始化语言
        playbackRates: [1, 2, 3, 4, 5, 8, 10, 20],// 播放速度
        techOrder: ["flash", "html5"]
    }, function () {
        console.log("--------------成功初始化视频--------------");
        myPlayer.one("playing", function () {         // 监听播放
            console.log("开始播放");
        });
        myPlayer.one("error", function (error) {      // 监听错误
            console.error("监听到异常，错误信息：%o", error);
        });
    });
    var myPlayer2 = videojs('rtmpVideo2', {
        autoplay: true,
        controls: true,//控制条
        muted: true,// 静音
        preload: "auto",// 预加载
        language: "zh-CN",// 初始化语言
        playbackRates: [1, 2, 3, 4, 5, 8, 10, 20],// 播放速度
        techOrder: ["flash", "html5"]
    }, function () {
        console.log("--------------成功初始化视频--------------");
        myPlayer.one("playing", function () {         // 监听播放
            console.log("开始播放");
        });
        myPlayer.one("error", function (error) {      // 监听错误
            console.error("监听到异常，错误信息：%o", error);
        });
    });
    var myPlayer3 = videojs('rtmpVideo3', {
        autoplay: true,
        controls: true,//控制条
        muted: true,// 静音
        preload: "auto",// 预加载
        language: "zh-CN",// 初始化语言
        playbackRates: [1, 2, 3, 4, 5, 8, 10, 20],// 播放速度
        techOrder: ["flash", "html5"]
    }, function () {
        console.log("--------------成功初始化视频--------------");
        myPlayer.one("playing", function () {         // 监听播放
            console.log("开始播放");
        });
        myPlayer.one("error", function (error) {      // 监听错误
            console.error("监听到异常，错误信息：%o", error);
        });
    });
    var myPlayer4 = videojs('rtmpVideo4', {
        autoplay: true,
        controls: true,//控制条
        muted: true,// 静音
        preload: "auto",// 预加载
        language: "zh-CN",// 初始化语言
        playbackRates: [1, 2, 3, 4, 5, 8, 10, 20],// 播放速度
        techOrder: ["flash", "html5"]
    }, function () {
        console.log("--------------成功初始化视频--------------");
        myPlayer.one("playing", function () {         // 监听播放
            console.log("开始播放");
        });
        myPlayer.one("error", function (error) {      // 监听错误
            console.error("监听到异常，错误信息：%o", error);
        });
    });
</script>

</body>
</html>