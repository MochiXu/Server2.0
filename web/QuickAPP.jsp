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
    <title>HeatMap</title>
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
    var map = new AMap.Map('container', {
        features: ['bg', 'road','building','point'],
        mapStyle: 'amap://styles/grey',
        // mapStyle: 'amap://styles/normal',
        center: [122.109712, 37.506669],
        pitch: 0,
        zoom: 16,
        viewMode: '3D'
    });

    var layer = new Loca.HeatmapLayer({
        map: map,
    });

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_index_map?map=2', true);
        request.send();
        //3.监听
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                //获取从后端接受的json字符串
                var XHR = request.responseText;
                //解析json字符串，使其成为json对象
                var objs = eval(XHR);
                var list = [];
                var i = -1,
                    length = objs.length;
                while (++i < length) {
                    var item = objs[i];
                    list.push({
                        coordinate: [item.lng, item.lat],
                        count: item.count
                    })
                }

                layer.setData(list, {
                    lnglat: 'coordinate',
                    value: 'count'
                });

                layer.setOptions({
                    style: {
                        radius: 16,
                        color: {
                            0.5: '#2c7bb6',
                            0.65: '#abd9e9',
                            0.7: '#ffffbf',
                            0.9: '#fde468',
                            1.0: '#d7191c'
                        }
                    }
                });
                layer.render();
            }
        }
    },5000);
</script>
</body>
</html>
