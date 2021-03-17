/**
 * Created by 30947 on 2018/7/20.
 */
$(function () {
    getHt();
    initMap();
});

//获取div的高度
function getHt() {
    var all_height = $(window).height();
    var div_height = all_height - 84;
    $("#car_control").css("height", div_height + "px");
}

//声明地图
var map;
//声明地图图层
var layer;
var infoWin;
var tableDom;
//点标记
var marker;
//是否移入了鼠标，若移入鼠标则将此值更改为1，移出鼠标则将此值改为0
var mouse = 0;
//设置json数据的下标
var json_index = 0;

//通过listTree传递过来的spot_id
var spot_id_listTree='spot_id_listTree';


var Spot_mapData = [];
//每隔两个小时对Spot_mapData进行一次全局更新
setInterval(function () {
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_monitor?monitor=3', true);
    request.send();
    //3.监听
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            Spot_mapData = eval(request.responseText);
        }
    }
}, 7200000);//两个小时是7200000毫秒

//加载地图
function initMap() {
    map = new AMap.Map('map', {
        mapStyle: 'amap://styles/grey',
        zoom: 16.2,
        center: [122.103455, 37.504711],
        features: ['bg', 'road'],
        viewMode: '3D'
    });
    layer = new Loca.PointLayer({
        eventSupport: true,
        map: map
    });
    //首次初始化map的数据源
    var request_init = new XMLHttpRequest();
    request_init.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_monitor?monitor=3', true);
    request_init.send();
    request_init.onreadystatechange = function () {
        if (request_init.readyState === 4 && request_init.status === 200) {
            Spot_mapData = eval(request_init.responseText);
        }
    };
    //每隔5s重绘地图
    setInterval(function () {
        layer.on('mousemove', function (ev) {
            mouse = 1;

            // 事件类型
            var type = ev.type;
            console.log("事件类型" + type);
            // 当前元素的原始数据
            var rawData = ev.rawData;
            // 原始鼠标事件
            var originalEvent = ev.originalEvent;
            console.log("originalEvent: " + originalEvent);

            openInfoWin_mouse(map, originalEvent, {
                '名称': rawData.title,
                '位置': rawData.lnglat
            });
        });

        layer.on('mouseleave', function (ev) {
            mouse = 0;
            closeInfoWin_mouse();
        });
        layer.setData(Spot_mapData, {
            lnglat: function (data) {
                var item = data.value;
                console.log("item" + item);
                return [item.lng, item.lat];
            }
        });
        layer.setOptions({
            style: {
                radius: 10,
                color: '#07E8E4',
                borderColor: '#07E8E4',
                borderWidth: 1.5,
                opacity: 0.8
            },
            selectStyle: {
                radius: 14,
                color: '#FFF684'
            }
        });

        layer.render();
    }, 5000);


}

//鼠标--打开自定义窗体
function openInfoWin_mouse(map, event, content) {
    clearMarker();

    if (!infoWin) {
        infoWin = new AMap.InfoWindow({
            isCustom: true,  //使用自定义窗体
            offset: new AMap.Pixel(130, 100),
            // position:new AMap.LngLat("122.103455","37.504711",false)
        });
    }

    var x = event.offsetX;
    var y = event.offsetY;
    console.log("offsetX,Y: " + x + ":" + y);
    var lngLat = map.containerToLngLat(new AMap.Pixel(x, y));
    //这里的containerToLngLat是可以把鼠标在窗口上的坐标位置转换成为地理上的经纬度坐标
    console.log("containerToLngLat: " + lngLat);

    if (!tableDom) {
        //这里创建了信息框
        let infoDom = document.createElement('div');
        infoDom.className = 'info';
        tableDom = document.createElement('table');
        infoDom.appendChild(tableDom);
        infoWin.setContent(infoDom);
    }

    var trStr = '';
    for (var name in content) {
        var val = content[name];
        trStr +=
            '<tr>' +
            '<td class="label">' + name + '</td>' +
            '<td>&nbsp;</td>' +
            '<td class="content">' + val + '</td>' +
            '</tr>'
    }

    tableDom.innerHTML = trStr;
    //打开自定义窗体
    infoWin.open(map, lngLat);
}

//鼠标--关闭自定义窗体
function closeInfoWin_mouse() {
    if (infoWin) {
        infoWin.close();
    }
}

//自动--打开特定坐标点的窗体
function openInfoWin_auto(lng, lat, content) {
    //首先改变地图的中心点
    map.panTo([lng, lat]);;
    if (!infoWin) {
        infoWin = new AMap.InfoWindow({
            isCustom: true,  //使用自定义窗体
            offset: new AMap.Pixel(130, 100),
            // position:new AMap.LngLat("122.103455","37.504711",false)
        });
    }

    if (!tableDom) {
        //这里创建了信息框
        let infoDom = document.createElement('div');
        infoDom.className = 'info';
        tableDom = document.createElement('table');
        infoDom.appendChild(tableDom);
        infoWin.setContent(infoDom);
    }

    var trStr = '';
    for (var name in content) {
        var val = content[name];
        trStr +=
            '<tr>' +
            '<td class="label">' + name + '</td>' +
            '<td>&nbsp;</td>' +
            '<td class="content">' + val + '</td>' +
            '</tr>'
    }

    tableDom.innerHTML = trStr;

    var lngLat = new AMap.LngLat(lng, lat, false);
    //打开自定义窗体
    infoWin.open(map, lngLat);
}

//自动--关闭特定坐标点的窗体
function closeInfoWin_auto() {
    if (infoWin) {
        infoWin.close();
    }
}

//循环显示地图上的点信息
setInterval(function () {
    if (mouse === 0&&spot_id_listTree==='spot_id_listTree') {
        closeInfoWin_auto();
        clearMarker();
        //设置信息框的内容
        var content = {'名称': Spot_mapData[json_index].title,'位置': Spot_mapData[json_index].lnglat};
        //设置显示信息的坐标精度
        var lng = Spot_mapData[json_index].lng;
        //设置显示信息的坐标维度
        var lat = Spot_mapData[json_index].lat;
        addMarker(lng, lat,"img/camera.png");
        openInfoWin_auto(lng, lat, content);
        json_index++;
        //更新当前数据的下标
        if (json_index === Spot_mapData.length) {
            json_index = 0;
        }
    }
}, 4000);

//监听有没有点击listTree--
setInterval(function () {
    if(mouse===0&&spot_id_listTree!=='spot_id_listTree'){
        closeInfoWin_auto();
        clearMarker();
        var i;
        for(i=0;i<Spot_mapData.length;i++){
            if(Spot_mapData[i].spot_id===spot_id_listTree){
                //设置信息框的内容
                var content2 = {'名称': Spot_mapData[i].title,'位置': Spot_mapData[i].lnglat};
                //设置显示信息的坐标精度
                var lng2 = Spot_mapData[i].lng;
                //设置显示信息的坐标维度
                var lat2 = Spot_mapData[i].lat;
                addMarker(lng2, lat2,"img/find01.png");
                openInfoWin_auto(lng2, lat2, content2);
                json_index=i+1;
            }
        }
        spot_id_listTree='spot_id_listTree';
        time.sleep();
    }
},500);

// 实例化点标记
function addMarker(lng, lat,icon) {
    marker = new AMap.Marker({
        icon: icon,
        position: [lng, lat],
        offset: new AMap.Pixel(-9, -9)
    });
    marker.setMap(map);
}

// 清除 marker
function clearMarker() {
    if (marker) {
        marker.setMap(null);
        marker = null;
    }
}
