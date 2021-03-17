<%--
  Created by IntelliJ IDEA.
  User: mochi
  Date: 2020/1/11
  Time: 下午3:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据分析</title>
    <link rel="stylesheet" href="css/base.css">
</head>
<body>
<!--顶部-->
<header class="header left">
    <div class="left nav">
        <ul>
            <li ><i class="nav_1"></i><a href="index.jsp">数据概览</a> </li>
            <li><i class="nav_2"></i><a href="monitor.jsp">实时监控</a> </li>
            <li><i class="nav_3"></i><a href="visualization.jsp">空间分布</a> </li>
        </ul>
    </div>
    <div class="header_center left">
        <h2><strong>深瞳智慧景区辅助管理系统</strong></h2>
        <p class="color_font"><small>Auxiliary management system of deep pupil intelligent scenic spot</small></p>
    </div>
    <div class="right nav text_right">
        <ul> <li class="nav_active"><i class="nav_7"></i><a href="analyze.jsp">数据分析</a> </li>
            <li><i class="nav_8"></i><a href="publish.jsp">公告发布</a> </li>
            <li><i class="nav_4"></i><a href="">待定功能</a> </li>
        </ul>
    </div>

</header>
<!--内容部分-->
<div class="con left">
    <!--选择时间-->
    <div class="static_top left">
        <i></i><span>统计概况</span>
    </div>
    <!--数据总概-->
    <div class="stiatic_top_con left">

        <table>
<%--            第一行--%>
            <tr>
                <td class="labe_td">总概：</td>
                <td colspan="3" class="sky">今日景区接纳游客同昨日相比<span id="line1-01">增加了21%</span>，近日景区客流量<span id="line1-02">平稳波动</span>，预计未来三日客流量将介于<span id="line1-03">XX人次</span></td>
            </tr>
<%--            第二行--%>
            <tr>
                <td class="labe_td">客流量预测：</td>
                <td>在未来三天，景区内客流量分别约为<span id="line2-01">2-23日: 178人次,2-23日: 178人次,2-23日: 178人次</span></td>
                <td class="labe_td">景区客流实时饱和度：</td>
                <td class="org"><span id="line2-02">90%</span></td>
            </tr>
<%--            第三行--%>
            <tr>
                <td class="labe_td">景点实时资源压力：</td>
                <td ><span id="line3-01"></span></td>
                <td class="labe_td">重点对象：</td>
                <td><span id="line3-02">暂无</span></td>
            </tr>
        </table>

    </div>
    <!--统计分析图-->
    <div class="static_top left">
        <i></i><span>统计分析一</span>
    </div>
    <div class="div_any">
        <div class="left div_any03">
            <div class="div_any_child01 left">
                <div class="div_any_title"><img src="img/title_1.png">景点游客实时数量 </div>
                <p id="char1" class="p_chart"></p>
                <div class="char_table"><div class="table_p table_p01">
                    <table>
                        <thead><tr>
                            <th>排名</th>
                            <th>景点名称</th>
                            <th>实时游客人数</th>
                        </tr>
                        </thead>
                        <tbody id="chart1_tbody">
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        </tbody>
                    </table>
                </div></div>

            </div>
            <div class="div_any_child01 left">
                <div class="div_any_title"><img src="img/title_2.png">景点游客游玩时长 </div>
                <p id="char2" class="p_chart"></p>
                <div class="char_table"><div class="table_p table_p01">
                    <table>
                        <thead><tr>
                            <th>排名</th>
                            <th>景点名称</th>
                            <th>游客平均游玩时长</th>
                        </tr>
                        </thead>
                        <tbody id="chart2_tbody">
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        </tbody>
                    </table>
                </div></div>
            </div>
        </div>

        <div class="static_top left">
            <i></i><span>统计分析一</span>
        </div>
        <div class="right div_any03">
            <div class="div_any_child01 left">
                <div class="div_any_title"><img src="img/title_4.png">客流量走势分析 </div>
                <p id="char3" class="p_chart"></p>
                <div class="char_table"><div class="table_p table_p01">
                    <table>
                        <thead><tr>
                            <th>排名</th>
                            <th>日期</th>
                            <th>游客人数</th>
                        </tr>
                        </thead>
                        <tbody id="chart3_tbody">
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        </tbody>
                    </table>
                </div></div>
            </div>
            <div class="div_any_child01 left">
                <div class="div_any_title"><img src="img/title_5.png">景点设备维护需求 </div>
                <p id="char4" class="p_chart"></p>
                <div class="char_table"><div class="table_p table_p01">
                    <table>
                        <thead><tr>
                            <th>排名</th>
                            <th>景点名称</th>
                            <th>维护需求</th>
                        </tr>
                        </thead>
                        <tbody id="chart4_tbody">
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        <tr><td>1</td><td>京A12345</td><td>134.2</td></tr>
                        </tbody>
                    </table>
                </div></div>
            </div>
        </div>

    </div>


</div>
<script src="js/jquery/jQuery-2.2.0.min.js"></script>
<script src="js/echarts-all.js"></script>
<script src="js/base.js"></script>
<script src="js/static.js"></script>

</body>
</html>
