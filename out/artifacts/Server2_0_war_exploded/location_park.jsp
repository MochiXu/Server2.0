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
    var park_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAAAsTAAALEwEAmpwYAAAFHGlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIwLTAyLTEzVDIwOjQ0OjUxKzA4OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMC0wMi0xM1QyMjowNToxMiswODowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMC0wMi0xM1QyMjowNToxMiswODowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJzUkdCIElFQzYxOTY2LTIuMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo3Mzc5MzEwOS01Nzkx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';
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

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=4', true);
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
                    source: park_png,
                    style: {
                        size: 18
                    }
                });
                layerX.render();
                //显示服务中心的名字
                for (var i = 0; i < jsonData.length; i++) {
                    var name = jsonData[i].park_name;
                    marker = new AMap.Marker({ //添加自定义点标记
                        map: mapX,
                        position: [jsonData[i].park_lng, jsonData[i].park_lat], //基点位置
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
