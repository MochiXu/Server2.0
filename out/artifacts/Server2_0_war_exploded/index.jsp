<%--
  Created by IntelliJ IDEA.
  User: mochi
  Date: 2020/1/11
  Time: 下午2:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--全局变量--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>DeepPupil</title>
    <link rel="stylesheet" href="css/base.css">
    <script src="js/jquery/jQuery-2.2.0.min.js"></script>
    <script src="js/echarts-all.js"></script>
    <script src="js/base.js"></script>
    <script src="js/index.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5ieMMexWmzB9jivTq6oCRX9j&callback"></script>
    <script src="js/map.js"></script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?key=c466eb7b9e1399a963003019fc6af363&v=1.4.15&plugin=Map3D"></script>
    <script type="text/javascript" src="https://webapi.amap.com/loca?key=c466eb7b9e1399a963003019fc6af363&v=1.3.2"></script>
    <script src="js/heatData2.js"></script>
</head>
<body>
<!--顶部-->
<header class="header left">
    <div class="left nav">
        <ul>
            <li class="nav_active"><i class="nav_1"></i><a href="index.jsp">数据概览</a></li>
            <li><i class="nav_2"></i><a href="monitor.jsp">实时监控</a></li>
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
<div class="con left">
    <!--选择时间-->
    <div class="select_time">
        <div class="static_top left">
            <i></i><span>总体概况</span>
        </div>
    </div>
    <!--数据总概-->
    <div class="con_div">
        <div class="con_div_text left">
            <div class="con_div_text01 left">
                <img src="img/info_1.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>人流量峰值(人次)</p>
                    <p id="flow_peak" style="" onclick="">12356</p>
                </div>
            </div>
            <div class="con_div_text01 right">
                <img src="img/info_2.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>实时人流量(人次)</p>
                    <p id="flow_actual" style="" onclick="">12356</p>
                </div>
            </div>
        </div>
        <div class="con_div_text left">
            <div class="con_div_text01 left">
                <img src="img/info_4.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>快应用并发峰值(次)</p>
                    <p id="quickapp_concurrency" style="" onclick="" class="sky">12356</p>
                </div>
            </div>
            <div class="con_div_text01 right">
                <img src="img/info_5.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>活跃的快应用终端(台)</p>
                    <p id="quickapp_active" style="" onclick="" class="sky">12356</p>
                </div>
            </div>
        </div>
        <div class="con_div_text left">

            <div class="con_div_text01 left">
                <img src="img/info_6.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>今日游客误入禁区次数(人次)</p>
                    <p id="forbidden_daily" style="" onclick="" class="org">12356</p>
                </div>
            </div>
            <div class="con_div_text01 right">
                <img src="img/info_7.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>违禁区域内游客实时数量(人)</p>
                    <p id="forbidden_actual" onclick="" class="org">12356</p>
                </div>
            </div>
        </div>
    </div>
    <!--统计分析图-->
    <div class="div_any">
        <div class="left div_any01">
            <div class="div_any_child">
                <div class="div_any_title"><img src="img/chart_1.png">景点游客实时数量</div>
                <p id="char1" class="p_chart"></p>
            </div>
            <div class="div_any_child">
                <div class="div_any_title"><img src="img/chart_2.png">景点游客游玩时长</div>
                <p id="char2" class="p_chart"></p>
            </div>
        </div>
        <div class="div_any02 left ">
            <div class="div_any_child div_height">
                <div class="div_any_title any_title_width"><img src="img/chart_m.png">景区人流热力图</div>
                <div id="map_div"></div>
<%--                <div class="legend">--%>
<%--                    <ul class="colors" id="legend-color"></ul>--%>
<%--                    <ul class="labels" id="legend-label"></ul>--%>
<%--                </div>--%>
            </div>
        </div>
        <div class="right div_any01">
            <div class="div_any_child">
                <div class="div_any_title"><img src="img/title_4.png">历史人流量走势</div>
                <p id="char3" class="p_chart"></p>
            </div>
            <div class="div_any_child">
                <div class="div_any_title"><img src="img/title_5.png">各景点游客求助次数</div>
                <p id="char4" class="p_chart"></p>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(document).ready(//当页面准备好加载完成的时候触发
        // 用于获取人流量峰值
        setInterval(function() {
            //1.创建XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //2.发送请求
            request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_index_tags?tag=1',true);
            request.send();
            //3.监听
            request.onreadystatechange = function() {
                if (request.readyState === 4 && request.status === 200) {
                    var XHR = request.responseText;
                    // 动态更新 flow_peak
                    document.getElementById("flow_peak").innerText=XHR;
                }
            }
        },5000));
    $(document).ready(//当页面准备好加载完成的时候触发
        // 用于获取实时人流量
        setInterval(function() {
            //1.创建XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //2.发送请求
            request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_index_tags?tag=2',true);
            request.send();
            //3.监听
            request.onreadystatechange = function() {
                if (request.readyState === 4 && request.status === 200) {
                    var XHR = request.responseText;
                    // 动态更新 flow_actual
                    document.getElementById("flow_actual").innerText=XHR;
                }
            }
        },5000));
    $(document).ready(//当页面准备好加载完成的时候触发
        // 用于获取快应用并发峰值
        setInterval(function() {
            //1.创建XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //2.发送请求
            request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_index_tags?tag=3',true);
            request.send();
            //3.监听
            request.onreadystatechange = function() {
                if (request.readyState === 4 && request.status === 200) {
                    var XHR = request.responseText;
                    // 动态更新 quickapp_concurrency
                    document.getElementById("quickapp_concurrency").innerText=XHR;
                }
            }
        },5000));
    $(document).ready(//当页面准备好加载完成的时候触发
        // 活跃的快应用终端
        setInterval(function() {
            //1.创建XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //2.发送请求
            request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_index_tags?tag=4',true);
            request.send();
            //3.监听
            request.onreadystatechange = function() {
                if (request.readyState === 4 && request.status === 200) {
                    var XHR = request.responseText;
                    // 动态更新 quickapp_active
                    document.getElementById("quickapp_active").innerText=XHR;
                }
            }
        },5000));
    $(document).ready(//当页面准备好加载完成的时候触发
        // 今日游客误入禁区次数
        setInterval(function() {
            //1.创建XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //2.发送请求
            request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_index_tags?tag=5',true);
            request.send();
            //3.监听
            request.onreadystatechange = function() {
                if (request.readyState === 4 && request.status === 200) {
                    var XHR = request.responseText;
                    // 动态更新 forbidden_daily
                    document.getElementById("forbidden_daily").innerText=XHR;
                }
            }
        },5000));
    $(document).ready(//当页面准备好加载完成的时候触发
        // 违禁区域内游客实时数量
        setInterval(function() {
            //1.创建XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //2.发送请求
            request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_index_tags?tag=6',true);
            request.send();
            //3.监听
            request.onreadystatechange = function() {
                if (request.readyState === 4 && request.status === 200) {
                    var XHR = request.responseText;
                    // 动态更新 forbidden_actual
                    document.getElementById("forbidden_actual").innerText=XHR;
                }
            }
        },5000));

</script>
</body>
</html>
