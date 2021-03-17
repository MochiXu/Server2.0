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
    var service_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAAAsTAAALEwEAmpwYAAAKxGlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiB4bXA6Q3JlYXRlRGF0ZT0iMjAyMC0wMi0xM1QyMDoyMDozMSswODowMCIgeG1wOk1vZGlmeURhdGU9IjIwMjAtMDItMTNUMjA6NDc6MDYrMDg6MDAiIHhtcDpNZXRhZGF0YURhdGU9IjIwMjAtMDItMTNUMjA6NDc6MDYrMDg6MDAiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NDY2N2JiOTUtYmM2MS0wMDQxLTliN2QtZGI5NTNjYTViNTM5IiB4bXBNTTpEb2N1bWVudElEPSJhZG9iZTpkb2NpZDpwaG90b3Nob3A6MTViODY2YmEtN2M1NS00ZDQ5LTgyODQtMjA4MzMyZWE2ZGQ1IiB4bXBNTTpPcmlnaW5hbERvY3VtZW50SUQ9InhtcC5kaWQ6MmEzZWM2N2EtOTQxYi05ZTQ2LTkxYjctOTQ5M2U0OWIzOGZjIj4gPHhtcE1NOkhpc3Rvcnk+IDxyZGY6U2VxPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY3JlYXRlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDoyYTNlYzY3YS05NDFiLTllNDYtOTFiNy05NDkzZTQ5YjM4ZmMiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMjA6MjA6MzErMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY29udmVydGVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJmcm9tIGltYWdlL3BuZyB0byBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDozY2JhNWJmOS02MmM2LTE3NGUtODgwNi1kNTFmZDQzYzgwNDQiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMjA6MzQ6MjArMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6ZDEzZGUwZGQtNzgwYS03OTRkLTlhMzUtMDM5NGQwM2UzODU4IiBzdEV2dDp3aGVuPSIyMDIwLTAyLTEzVDIwOjM0OjM5KzA4OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNvbnZlcnRlZCIgc3RFdnQ6cGFyYW1ldGVycz0iZnJvbSBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIHRvIGltYWdlL3BuZyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iZGVyaXZlZCIgc3RFdnQ6cGFyYW1ldGVycz0iY29udmVydGVkIGZyb20gYXBwbGljYXRpb24vdm5kLmFkb2JlLnBob3Rvc2hvcCB0byBpbWFnZS9wbmciLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjY0YTFlMWZiLWFhNGMtOTE0ZS04MDAzLWZjN2ZhODhmYzdhYiIgc3RFdnQ6d2hlbj0iMjAyMC0wMi0xM1QyMDozNDozOSswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo0NjY3YmI5NS1iYzYxLTAwNDEtOWI3ZC1kYjk1M2NhNWI1MzkiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMjA6NDc6MDYrMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6ZDEzZGUwZGQtNzgwYS03OTRkLTlhMzUtMDM5NGQwM2UzODU4IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjJhM2VjNjdhLTk0MWItOWU0Ni05MWI3LTk0OTNlNDliMzhmYyIgc3RSZWY6b3JpZ2luYWxEb2N1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';

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=3', true);
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
                    source: service_png,
                    style: {
                        size: 18
                    }
                });
                layerX.render();
                //显示服务中心的名字
                for (var i = 0; i < jsonData.length; i++) {
                    var name = jsonData[i].service_center_name;
                    marker = new AMap.Marker({ //添加自定义点标记
                        map: mapX,
                        position: [jsonData[i].service_center_lng, jsonData[i].service_center_lat], //基点位置
                        draggable: false, //是否可拖动
                        content: '<div style="color: #25f3e6;border:1px;solid #ccc;background:#515250;white-space:nowrap; padding:3px;font-size:14px;">' + name + '</div>' //自定义点标记覆盖物内容
                    });
                    markersX.push(marker);
                }
            }
        }
    },5000);
</script>
</body>
</html>
