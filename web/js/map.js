
$(function(){


    initMap();





});


//加载地图
function initMap(){

    var map = new AMap.Map('map_div', {
        features: ['bg', 'road','point'],
        mapStyle: 'amap://styles/grey',
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
                            0.5: '#52ff5a',
                            0.65: '#c1fc38',
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


}

