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
    var trash_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAACXBIWXMAAAsTAAALEwEAmpwYAAAKxGlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiB4bXA6Q3JlYXRlRGF0ZT0iMjAyMC0wMi0xM1QxNzozNzo0MCswODowMCIgeG1wOk1vZGlmeURhdGU9IjIwMjAtMDItMTNUMTc6NDU6MDErMDg6MDAiIHhtcDpNZXRhZGF0YURhdGU9IjIwMjAtMDItMTNUMTc6NDU6MDErMDg6MDAiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NDU0MGUyZmQtMWFjNi0zZjQyLWJjODctNTc5ZDBlNTMxOTcyIiB4bXBNTTpEb2N1bWVudElEPSJhZG9iZTpkb2NpZDpwaG90b3Nob3A6MDZiY2UwMzYtMGQxYi0xNjQ0LWFhYzgtODhjNzA2NzgxZjZmIiB4bXBNTTpPcmlnaW5hbERvY3VtZW50SUQ9InhtcC5kaWQ6YjgzZjQxMTQtM2ZiOS0wMjQ5LWFjNGItYjYwOWMwYTAxYWM0Ij4gPHhtcE1NOkhpc3Rvcnk+IDxyZGY6U2VxPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY3JlYXRlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDpiODNmNDExNC0zZmI5LTAyNDktYWM0Yi1iNjA5YzBhMDFhYzQiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMTc6Mzc6NDArMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY29udmVydGVkIiBzdEV2dDpwYXJhbWV0ZXJzPSJmcm9tIGltYWdlL3BuZyB0byBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDoyMGRkOGFkMi1kNTE1LTlmNGQtODI4MC02YTU0OGM5OGRjOTAiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMTc6NDI6MzQrMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6ZjExZjBjMjgtMmYzMC02MzQ1LThhMGItMDM2ZjBiZDE5ZWRiIiBzdEV2dDp3aGVuPSIyMDIwLTAyLTEzVDE3OjQyOjU4KzA4OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNvbnZlcnRlZCIgc3RFdnQ6cGFyYW1ldGVycz0iZnJvbSBhcHBsaWNhdGlvbi92bmQuYWRvYmUucGhvdG9zaG9wIHRvIGltYWdlL3BuZyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iZGVyaXZlZCIgc3RFdnQ6cGFyYW1ldGVycz0iY29udmVydGVkIGZyb20gYXBwbGljYXRpb24vdm5kLmFkb2JlLnBob3Rvc2hvcCB0byBpbWFnZS9wbmciLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjFmMmZhMmUzLTA0Y2EtNmQ0Mi05ZWIwLTE5MGRjMTcwY2NkYyIgc3RFdnQ6d2hlbj0iMjAyMC0wMi0xM1QxNzo0Mjo1OCswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo0NTQwZTJmZC0xYWM2LTNmNDItYmM4Ny01NzlkMGU1MzE5NzIiIHN0RXZ0OndoZW49IjIwMjAtMDItMTNUMTc6NDU6MDErMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6ZjExZjBjMjgtMmYzMC02MzQ1LThhMGItMDM2ZjBiZDE5ZWRiIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOmI4M2Y0MTE0LTNmYjktMDI0OS1hYzRiLWI2MDljMGEwMWFjNCIgc3RSZWY6b3JpZ2luYWxEb2N1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';

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=5', true);
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
                    source: trash_png,
                    style: {
                        size: 18
                    }
                });
                layerX.render();
                // //显示服务中心的名字
                // for (var i = 0; i < jsonData.length; i++) {
                //     var name = jsonData[i].rest_name;
                //     marker = new AMap.Marker({ //添加自定义点标记
                //         map: mapX,
                //         position: [jsonData[i].rest_lng, jsonData[i].rest_lat], //基点位置
                //         draggable: false, //是否可拖动
                //         content: '<div style="color: #25f3e6 border:1px solid #ccc;background:#515250;white-space:nowrap; padding:3px;font-size:14px;">' + name + '</div>' //自定义点标记覆盖物内容
                //     });
                //     markersX.push(marker);
                // }
            }
        }
    },5000);
</script>
</body>
</html>
