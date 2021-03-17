<%@ page import="Database.DBconnection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: mochi
  Date: 2020/1/11
  Time: 下午3:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据可视化</title>
    <link rel="stylesheet" href="js/bstable/css/bootstrap.min.css">
    <link href="css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="js/jQueryPage/src/jquery.page.css">
    <link rel="stylesheet" href="css/base.css">
    <style>/* 图例样式 */
    .legend {
        position: absolute;
        top: 120px;
        left: 20px;
    }
    .legend ul {
        padding: 0;
        margin: 0;
        list-style: none;
    }</style>
</head>
<body>
<!--顶部-->
<header class="header left">
    <div class="left nav">
        <ul>
            <li><i class="nav_1"></i><a href="index.jsp">数据概览</a></li>
            <li><i class="nav_2"></i><a href="monitor.jsp">实时监控</a></li>
            <li class="nav_active"><i class="nav_3"></i><a href="visualization.jsp">空间分布</a></li>
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
<div class="map_con left" id="car_control">
    <!--左侧地图-->
    <div class="left map_left">
        <div class="map_box" id="map_box"></div>
        <div class="legend">
            <ul class="colors" id="legend-color"></ul>
            <ul class="labels" id="legend-label"></ul>
        </div>
        <div class="map_top">
            <ul>
                <li class="active"><i class="glyphicon glyphicon-pencil"></i><a href="javascript:void (0)"class="active">图层选择</a></li>
                <li><i class="glyphicon glyphicon-zoom-in"></i><a href="javascript:void (0)">缩小</a></li>
                <li><i class="glyphicon glyphicon-zoom-out"></i><a href="javascript:void (0)">放大</a></li>
                <li><i class="glyphicon glyphicon-screenshot"></i><a href="javascript:void (0)">对比</a></li>
                <li><i class="glyphicon glyphicon-search"></i><a href="javascript:void (0)">搜索</a></li>
                <li><i class="glyphicon glyphicon-wrench"></i><a href="javascript:void (0)">工具</a></li>
                <li><i class="glyphicon glyphicon-fullscreen"></i><a href="javascript:void (0)">全屏</a></li>
            </ul>
        </div>
        <div id="map_source" class="map_select">
            <p><input id="source01" name="checkbox_source" type="checkbox" checked="checked" onclick="checkbox_Click(this)"><span>人流空间分布</span></p>
            <p><input id="source02" name="checkbox_source" type="checkbox" onclick="checkbox_Click(this)"><span>活跃的快应用</span></p>
            <p><input id="source03" name="checkbox_source" type="checkbox" onclick="checkbox_Click(this)"><span>公共服务场所</span></p>
            <p><input id="source04" name="checkbox_source" type="checkbox" onclick="checkbox_Click(this)"><span>停车场</span></p>
            <p><input id="source05" name="checkbox_source" type="checkbox" onclick="checkbox_Click(this)"><span>垃圾桶</span></p>
            <p><input id="source06" name="checkbox_source" type="checkbox" onclick="checkbox_Click(this)"><span>厕所</span></p>
            <p><input id="source07" name="checkbox_source" type="checkbox" onclick="checkbox_Click(this)"><span>休息处</span></p>
        </div>
        <div id="style_change" class="map_select2">
            <p><input id="style01" name="style_update" onclick="checkbox_Click2(this)" type="checkbox"><span>标准地图-normal</span></p>
            <p><input id="style02" name="style_update" onclick="checkbox_Click2(this)" type="checkbox"><span>马卡龙-macaron</span></p>
            <p><input id="style03" name="style_update" onclick="checkbox_Click2(this)" type="checkbox"><span>涂鸦-graffiti</span></p>
            <p><input id="style04" name="style_update" onclick="checkbox_Click2(this)" type="checkbox"><span>幻影黑-dark</span></p>
            <p><input id="style05" name="style_update" onclick="checkbox_Click2(this)" type="checkbox"><span>极夜蓝-darkblue</span></p>
            <p><input id="style06" name="style_update" onclick="checkbox_Click2(this)" type="checkbox"  checked="checked"><span>雅士灰-grey</span></p>
        </div>
    </div>

    <!--右侧功能栏-->
    <div class="right map_right ">
        <div class="map_right_top">
            <ul>
                <li class="li_active">数据概况</li>
                <li>停车场概况</li>
<%--                <li>未来数据走势</li>--%>
            </ul>
        </div>
        <%--        "map_con"是内容区--%>
        <div class="map_con">
<%--            左侧内容区域--%>
            <div class="map_con_div" style="display: block">
                <%--                图表--%>
                <div class="div_any_child">
                    <div class="div_any_title"><img src="img/title_4.png">各景点游客实时数据 </div>
                    <p id="char1" class="p_chart2"></p>
                </div>
                <%--                图表下侧的信息--%>
                <div class="div_any_child">
                    <div class="div_any_title"><img src="img/title_4.png">游客游玩时长数据</div>
                    <p id="char2" class="p_chart2"></p>
                </div>
            </div>
<%--            中间内容区域--%>
            <div class="map_con_div">
                <div class="map_work">
                    <div class="map_resList">
                        <ul>
                            <%
                                //初始化车辆数据
                                try {
                                    DBconnection dBconnection=new DBconnection();
                                    ResultSet resultSet= dBconnection.DB_FindDataSet("select * from park_detail;\n");
                                    while(resultSet.next()){
                                        out.print("<li><p><strong>");
                                        out.print(resultSet.getString(4));
                                        out.print("</strong><span class=\"right\"><strong id=\""+resultSet.getString(3)+"_Surplus\">剩余:");
                                        out.print(resultSet.getInt(7)-resultSet.getInt(8));
                                        out.print("辆</strong><a> <img src=\"img/find01.png\"></a>\n</span>\n</p>\n<p><strong id=\""+resultSet.getString(3)+"_Update\">");
                                        out.print("更新日期:"+resultSet.getString(9)+" "+resultSet.getString(10));
                                        out.print("</strong><span class=\"right\"><strong id=\""+resultSet.getString(3)+"_Total\">总数:");
                                        out.print(resultSet.getString(7));
                                        out.print("辆</strong><a> <img src=\"img/find01.png\"></a>\n</span></p></li>");
                                    }
                                    dBconnection.FreeResource();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            %>
<%--                            <li>--%>
<%--                                <p>--%>
<%--                                    <strong>爱尚美术馆</strong>--%>
<%--                                    <span class="right">--%>
<%--                                        <strong >已使用:XX辆</strong>--%>
<%--                                        <a> <img src="img/find01.png"></a>--%>
<%--                                    </span>--%>
<%--                                </p>--%>
<%--                                <p><strong>--%>
<%--                                    四川省成都市高新区北美一路31号--%>
<%--                                </strong>--%>
<%--                                    <span class="right">--%>
<%--                                        <strong>剩余:XX辆</strong>--%>
<%--                                        <a> <img src="img/find01.png"></a>--%>
<%--                                    </span></p></li>--%>

                        </ul>
                        <div id="page"></div>
                    </div>
                </div>
            </div>
<%--            右侧内容区域--%>
            <div class="map_con_div">
                <div class=" bow_shadow">
<%--                    <p>--%>
<%--                        <input type="text" placeholder="输入关键字" class="carNo_input"><input type="button"--%>
<%--                                                                                          class="find_but"/>--%>
<%--                    </p>--%>

<%--                    <p class="set_list"><i class="list_i"></i> 专题列表：</p>--%>
<%--                    <p>--%>
<%--                    <ul id="treeDemo" class="ztree"></ul>--%>
<%--    <p id="char1" class="p_chart2"></p>--%>

<%--    </p>--%>
                </div>
            </div>

        </div>

    </div>

</div>
<script src="js/jquery/jQuery-2.2.0.min.js"></script>
<script src="js/base.js"></script>
<script src="js/echarts-all.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5ieMMexWmzB9jivTq6oCRX9j&callback"></script>
<script src="js/jQueryPage/src/jquery.page.js"></script>
<script src="js/ztree/jquery.ztree.all-3.5.js"></script>
<script src="js/listTree.js"></script>
<script src="js/map_control.js"></script>
<script type="text/javascript" src="https://webapi.amap.com/maps?key=c466eb7b9e1399a963003019fc6af363&v=1.4.15&plugin=Map3D"></script>
<script type="text/javascript" src="https://webapi.amap.com/loca?key=c466eb7b9e1399a963003019fc6af363&v=1.3.2"></script>
</body>
</html>