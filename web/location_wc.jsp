<%--
  Created by IntelliJ IDEA.
  User: XMC
  Date: 2020/5/13
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ParkMap</title>
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        #container {
            width: 100%;
            height: 100%;
            position: absolute;
        }
    </style>
</head>
<body>
<div id="container" class="container"></div>
<script type="text/javascript" src="https://webapi.amap.com/maps?key=c466eb7b9e1399a963003019fc6af363&v=1.4.15&plugin=Map3D"></script>
<script type="text/javascript" src="https://webapi.amap.com/loca?key=c466eb7b9e1399a963003019fc6af363&v=1.3.2"></script>
<script>
    var markersX = [];
    var mapX = new AMap.Map('container', {
        mapStyle: 'amap://styles/grey',
        center: [122.109712, 37.506669],
        pitch: 0,
        zoom: 16,
        viewMode: '3D'
    });

    var layerX = new Loca.IconLayer({
        map: mapX,
    });
    var toilet_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAAAsTAAALEwEAmpwYAAAF7GlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIwLTAyLTEzVDIyOjI2OjU1KzA4OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMC0wMi0xM1QyMjoyODoyNyswODowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMC0wMi0xM1QyMjoyODoyNyswODowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJzUkdCIElFQzYxOTY2LTIuMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozNzNjMjk1Mi0yNDFi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';

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=6', true);
        request.send();
        //3.监听
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                //声明一个json数组对象，用于地图的数据源
                var jsonData = eval(request.responseText);
                layerX.setData(jsonData, {
                    lnglat: 'lnglat'
                });
                layerX.setOptions({
                    source: toilet_png,
                    style: {
                        size: 18
                    }
                });
                layerX.render();
                //显示服务中心的名字
                for (var i = 0; i < jsonData.length; i++) {
                    var name = jsonData[i].toilet_name;
                    marker = new AMap.Marker({ //添加自定义点标记
                        map: mapX,
                        position: [jsonData[i].toilet_lng, jsonData[i].toilet_lat], //基点位置
                        draggable: false, //是否可拖动
                        content: '<div style="color: #f0f0f0; border:1px; solid #ccc;background:#515250;white-space:nowrap; padding:3px;font-size:14px;">' + name + '</div>' //自定义点标记覆盖物内容
                    });
                    markersX.push(marker);
                }
            }
        }
    },5000);
</script>
</body>
</html>
