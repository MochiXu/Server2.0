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
    var rest_png='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAEL0lEQVRYR82YTWhcVRSAvzPJiz/YNokIVvGnQkvcdOGiGwsVq2JRa/1F1NKFpeAuGd1160KoNEURVzW1YhMRTAuBVlGxoFRQ0CwkiqAYsNU2TWwSM39v3pVz30s68zpv7h2dIT0wzAzv3HO/e37uffcINTI4bnqlxB4D9wG9En/7SQREIPppNsKAMZd10f85QoRFAiYO7pbdtcNXbA1+aHZhGBa4048I1DhVDyg1GIFJdLPsRwYkR7lwHY+884J8pnoWUOHEMO4LJgoWgv12iIWqJovJ0LVmQojUpv7OQbWPHW8+L6dEw0qJ7708pyt0eGGFQUNXcS8iUviwwQK6CYdflkAGx8ygwLDLE9YLCudS1Ll0Qg/dqByHPktkDRMyNGaOA483m1fU9U0M1Xmt7F6EFolROEeKSEBRhkbNlwjbGgFab3mEyY7VyXRSlxiwnvPJXy2SoTFb9A1FWoBTjzjD3wLc8pozAb3hdPvQhbTRc7WmGgK2BKeec9D55lzDNEuHuFFB9HTD9s1wa3/KhE/haLolelPTMHsJfpmGila6h9R5UDde9V5a9j8Ffdd7WHOonC/EClO/wYkv/OzVA2Yk+ht1p6Of4UZay4DlEA6+52fnMmAIuYyQtRtQ0V4/3ApgEtqsXO8E4Nw8rFsD84vw+1k4/R0sFa+Eth50VW0nANMoCnz4Ywj1SK2RGNCxyXYC8KNPYfoc3LYetm+BG3tjL56ZTAHmjxmjW0sz6QRgbQ4ObIBd98NfMzByIg141BjXKdBpwP51sO9p+KcAbx1LAb5yNPssXlbtNOCmO+DJB2BhCd4evcoA774LdmyFngC+/RE+/+YqArypH156IgbSrUYL54oqXs0QBwE8fC9c/BvO/ND4FVFWA7AawYERz5PEB/C15+DawM9gllYYwWwpfvrnDBxJbSdZ49weNJB/DG7p+3+AhRAWkjelyZ/g5Nd+9tyAEQysh70P+RnM0rpYhKoBDe+R43Bhzs+eGzC5tz67DbZs9DOa1looQ6EaF8Gpr2DyZ387TkC949o7MXDPRnhmKwRdfhOox+bLUNH8uwQTp+HsBb+xy1pOQL2A25t/Ijlg0+2weQOsTb1l2zPJQKEEswswvxSfDn+chxnPkKbxWwZstn7tw+gNr53iBtS3bM9J9XJkOwZtFDegb8cgub21FVBA8u+bOTH0Nl10ssE6HaOdA19dpzEwXRjJf2CmpMpAWwC1qVVyt9w82GKVbhYlP2oOSIVXmyZ/zVbjMh5pvqbuFa4xmc/XMmIbmLklfpUq2YfZahTKNVSG90nPSgs4V2E8qweodSIt5JbNQ4/2WqbnBIp9PKh96romei7k3UxPaug8ezH/NczWEQGhuYGdh16Uk7qAuvuSDXeR/UQ8SsTNddWdOlHatWFbR+cw0kXFdPPJob2ys9b2v9A9Cey6DhBkAAAAAElFTkSuQmCC';

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_visualization?map=7', true);
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
                    source: rest_png,
                    style: {
                        size: 18
                    }
                });
                layerX.render();
                //显示服务中心的名字
                for (var i = 0; i < jsonData.length; i++) {
                    var name = jsonData[i].rest_name;
                    marker = new AMap.Marker({ //添加自定义点标记
                        map: mapX,
                        position: [jsonData[i].rest_lng, jsonData[i].rest_lat], //基点位置
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
