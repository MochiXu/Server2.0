/**
 * Created by 30947 on 2018/7/18.
 */
$(function(){

    char1();
    char2();
    char3();
    char4();
    char5();
    init_line1_01();
    init_line1_02();
    init_line1_03();
    init_line2_01();
    init_line2_02();
    init_line3_01();
    init_line3_02();
});

//统计分析图
function char1() {

    var myChart = echarts.init($("#char1")[0]);

    //设置数组，记录景区景点名称【更新频率：每十分钟】
    var data_legend = new Array();
    data_legend[0] = '景点A';
    data_legend[1] = '景点B';
    data_legend[2] = '景点C';
    data_legend[3] = '景点D';
    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_index_charts?chart=1', true);
        request.send();
        //3.监听
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                //声明一个json数组对象，用于图表data
                var jsonData = [];
                //获取从后端接受的json字符串
                var XHR = request.responseText;
                //解析json字符串，使其成为json对象
                var objs = eval(XHR);

                //对图表内的两组数据进行赋值
                for (var j = 0; j < objs.length; j++) {
                    var json = {};//临时json变量
                    data_legend[j] = objs[j].scenic_name;
                    json.value = objs[j].person_count;
                    json.name = objs[j].scenic_name;
                    jsonData.push(json)
                }
                option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'right',
                        textStyle: {
                            color: '#ffffff',

                        },
                        data: data_legend
                    },

                    calculable: false,
                    series: [
                        {
                            name: '景点名称',
                            type: 'pie',
                            radius: ['40%', '70%'],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    },
                                    labelLine: {
                                        show: false
                                    }
                                },
                                emphasis: {
                                    label: {
                                        show: true,
                                        position: 'center',
                                        textStyle: {
                                            fontSize: '20',
                                            fontWeight: 'bold'
                                        }
                                    }
                                }
                            },
                            data: jsonData
                        }
                    ]
                };

                myChart.setOption(option);
                window.addEventListener('resize', function () {
                    myChart.resize();
                });
                //更新chart1_tbody
                jsonData.sort(function(a,b){
                    if(a.value<b.value){
                        return 1;
                    }else if(a.value>b.value){
                        return -1;
                    }
                    return 0;
                });
                var s1='<tr><td>';
                var s2='</td><td>';
                var s3='</td><td>';
                var s4='</td></tr>\n';
                var innerH="";
                for(var i=0;i<jsonData.length;i++){
                    var t=i+1;
                    innerH=innerH+s1+t+s2+jsonData[i].name+s3+jsonData[i].value+s4;
                }
                document.getElementById("chart1_tbody").innerHTML = innerH;
            }
        }




    }, 3000);
}
function char2() {

    var myChart = echarts.init($("#char2")[0]);

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_index_charts?chart=2', true);
        request.send();
        //3.监听
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                //声明一个json数组对象，用于图表data
                //声明四个数组对象，存储随机获取到的四个景点游客游玩时长数据
                var arrScenicName=[];
                var arrScenic01 = [];
                var arrScenic02 = [];
                var arrScenic03 = [];
                var arrScenic04 = [];
                //获取从后端接受的json字符串
                var XHR = request.responseText;
                //alert(XHR);
                //解析json字符串，使其成为json对象
                var objs = eval(XHR);
                //对图表内的两组数据进行赋值
                for (var j = 0; j < objs.length; j++) {
                    arrScenicName.push(objs[j].scenic_name);
                    arrScenic01.push(objs[j].h01);
                    arrScenic02.push(objs[j].h02);
                    arrScenic03.push(objs[j].h03);
                    arrScenic04.push(objs[j].h04);
                }
                option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {show: 'true', borderWidth: '0'},
                    legend: {
                        data: ['<1h', '1h~2h', '2h~3h', '>3h'],
                        textStyle: {
                            color: '#ffffff'
                        }
                    },

                    calculable: false,
                    xAxis: [
                        {
                            type: 'value',
                            axisLabel: {
                                show: true,
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            splitLine: {
                                lineStyle: {
                                    color: ['#f2f2f2'],
                                    width: 0,
                                    type: 'solid'
                                }
                            }

                        }
                    ],
                    yAxis: [
                        {
                            type: 'category',
                            data: arrScenicName,
                            axisLabel: {
                                show: true,
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            splitLine: {
                                lineStyle: {
                                    width: 0,
                                    type: 'solid'
                                }
                            }
                        }
                    ],
                    series: [
                        {
                            name: '<1h',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {normal: {label: {show: true, position: 'insideRight'}}},
                            data: arrScenic01
                        },
                        {
                            name: '1h~2h',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {normal: {label: {show: true, position: 'insideRight'}}},
                            data: arrScenic02
                        },
                        {
                            name: '2h~3h',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {normal: {label: {show: true, position: 'insideRight'}}},
                            data: arrScenic03
                        },
                        {
                            name: '>3h',
                            type: 'bar',
                            stack: '总量',
                            itemStyle: {normal: {label: {show: true, position: 'insideRight'}}},
                            data: arrScenic04
                        }

                    ]
                };
                myChart.setOption(option);
                window.addEventListener('resize', function () {myChart.resize();});

                //计算每个景区的游客平均游玩时长
                var person_num = [];//每个景点游客的总数
                for(var e=0;e<arrScenicName.length;e++){
                    person_num.push(arrScenic01[e]+arrScenic02[e]+arrScenic03[e]+arrScenic04[e]);
                }
                jsonData2=[];
                for(var e2=0;e2<arrScenicName.length;e2++){
                    var json={};
                    json.value=((arrScenic01[e2]*0.5+arrScenic02[e2]*1.5+arrScenic03[e2]*2.5+arrScenic04[e2]*3.5)/person_num[e2]).toFixed(2);
                    json.name=arrScenicName[e2];
                    jsonData2.push(json);
                }

                //更新chart2_tbody
                jsonData2.sort(function(a,b){
                    if(a.value<b.value){
                        return 1;
                    }else if(a.value>b.value){
                        return -1;
                    }
                    return 0;
                });
                var s1='<tr><td>';
                var s2='</td><td>';
                var s3='</td><td>';
                var s4='</td></tr>\n';
                var innerH="";
                for(var i=0;i<jsonData2.length;i++){
                    var t=i+1;
                    innerH=innerH+s1+t+s2+jsonData2[i].name+s3+jsonData2[i].value+"小时"+s4;
                }
                document.getElementById("chart2_tbody").innerHTML = innerH;
            }
        }
    }, 3000);


}
//客流预测分析图
function char3() {

    var myChart = echarts.init($("#char3")[0]);

    setInterval(function () {
        //1.创建XMLHttpRequest对象
        var request = new XMLHttpRequest();
        //2.发送请求
        request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=8', true);
        request.send();
        //3.监听
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                //声明两个数组对象，一个用于存储横坐标数据，另一个用于存储图表数据
                var arrDate = new Array();
                var arrPeak = new Array();
                var arrPeak2 = new Array();

                //获取从后端接受的json字符串
                var XHR = request.responseText;
                //解析json字符串，使其成为json对象
                var objs = eval(XHR);
                //对图表内的两组数据进行赋值
                for (var j = 0; j < objs.length; j++) {

                    if(j>objs.length-3){
                        arrDate.push(objs[j].date);
                        arrPeak.push('-');
                        arrPeak2.push(objs[j].peak_num);
                    }else if(j<objs.length-3) {
                        arrDate.push(objs[j].date);
                        arrPeak.push(objs[j].peak_num);
                        arrPeak2.push('-');
                    }else if(j===objs.length-3){
                        arrDate.push(objs[j].date);
                        arrPeak.push(objs[j].peak_num);
                        arrPeak2.push(objs[j].peak_num);
                    }
                }

                option = {
                    legend: {
                        data: ['日客流量峰值','客流量峰值预测'],
                        textStyle: {
                            color: '#ffffff'
                        }
                    },

                    tooltip: {
                        trigger: 'axis',
                        // formatter: "日客流量峰值: <br/>{b} : {c}"
                        formatter:function(params) {
                            //console.log(params);
                            for(var i=0;i<params.length;i++){
                                if(params[i]['data']!=='-'){
                                    return params[i]['0']+": "+params[i]['data']+"人次";
                                }
                            }
                            return "";

                        }
                    },
                    grid: {
                        borderWidth: 0
                    },
                    xAxis: {
                        type: 'category',
                        splitLine: {show: false},
                        axisLabel: {                 //坐标轴刻度标签的相关设置
                            show: true,              //是否显示
                            interval: "auto",        //坐标轴刻度标签的显示间隔，在类目轴中有效。默认会采用标签不重叠的策略间隔显示标签。可以设置成 0 强制显示所有标签。如果设置为 1，表示『隔一个标签显示一个标签』，如果值为 2，表示隔两个标签显示一个标签，以此类推
                            rotate: 45,              //刻度标签旋转的角度，在类目轴的类目标签显示不下的时候可以通过旋转防止标签之间重叠。旋转的角度从 -90 度到 90 度
                            textStyle: {
                                color: '#fff'
                            }
                        },
                        data: arrDate
                    },
                    yAxis: {
                        type: 'value',
                        splitLine: {show: false},
                        axisLabel: {
                            formatter: '{value} 人次',
                            textStyle: {
                                color: '#fff'
                            }
                        }
                    },
                    series: [{
                        name: '日客流量峰值',
                        data: arrPeak,
                        type: 'line',
                        smooth: true,
                        connectNulls: true,
                        itemStyle: {
                            normal: {
                                color: "#2ec7c9",
                                lineStyle: {
                                    color: "#2ec7c9"
                                }
                            }
                        }
                    },{
                        name: '客流量峰值预测',
                        data: arrPeak2,
                        type: 'line',
                        smooth: true,
                        connectNulls: true,
                        itemStyle: {
                            normal: {
                                color: "#c9000d",
                                lineStyle: {
                                    color: "#21c900"
                                }
                            }
                        }
                    }]
                };
                myChart.setOption(option);
                window.addEventListener('resize', function () {
                    myChart.resize();
                });
                //更新chart3_tbody
                objs.sort(function(a,b){
                    if(a.peak_num<b.peak_num){
                        return 1;
                    }else if(a.peak_num>b.peak_num){
                        return -1;
                    }
                    return 0;
                });
                var s1='<tr><td>';
                var s2='</td><td>';
                var s3='</td><td>';
                var s4='</td></tr>\n';
                var innerH="";
                for(var i=0;i<objs.length;i++){
                    if(i<=5) {
                        var t = i + 1;
                        innerH = innerH + s1 + t + s2 + objs[i].date + s3 + objs[i].peak_num + "人次" + s4;
                    }
                }
                document.getElementById("chart3_tbody").innerHTML = innerH;
            }
        }

    }, 3000);

}
function char4() {

    var myChart = echarts.init($("#char4")[0]);

    option = {
        grid: {show:'true',borderWidth:'0'},
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },

            formatter: function (params) {
                var tar = params[0];
                return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
            }
        },

        xAxis : [
            {
                type : 'category',
                splitLine: {show:false},
                data : ['客运车','危险品车','网约车','学生校车'],
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#fff'
                    }
                }

            }
        ],
        yAxis : [
            {
                type : 'value',
                splitLine: {show:false},
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#fff'
                    }
                }
            }
        ],
        series : [

            {
                name:'报警数量',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'inside'}}},
                data:[2900, 1200, 300, 200, 900, 300]
            }
        ]
    };

    myChart.setOption(option);
    window.addEventListener('resize', function () {myChart.resize();})

}

// 维护需求
function char5() {
    var myChart = echarts.init($("#char4")[0]);
    setInterval(
        function () {
            //1.创建XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //2.发送请求
            request.open('get', 'http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=9', true);
            request.send();
            //3.监听
            request.onreadystatechange = function () {
                if (request.readyState === 4 && request.status === 200) {
                    //声明两个数组对象，一个用于存储横坐标数据，另一个用于存储图表数据
                    var arrCount = new Array();
                    var arrName = new Array();
                    //获取从后端接受的json字符串
                    var XHR = request.responseText;
                    //解析json字符串，使其成为json对象
                    var objs = eval(XHR);
                    //对图表内的两组数据进行赋值
                    for (var j = 0; j < objs.length; j++) {
                        arrCount.push(objs[j].scenic_value);
                        arrName.push(objs[j].scenic_name);
                    }
                    option = {
                        grid: {show: 'true', borderWidth: '0'},
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            },

                            formatter: function (params) {
                                var tar = params[0];
                                return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
                            }
                        },

                        xAxis: [
                            {
                                type: 'category',
                                splitLine: {show: false},
                                data: arrName,
                                axisLabel: {
                                    show: true,
                                    textStyle: {
                                        color: '#fff'
                                    }
                                }

                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                splitLine: {show: false},
                                axisLabel: {
                                    show: true,
                                    textStyle: {
                                        color: '#fff'
                                    }
                                }
                            }
                        ],
                        series: [

                            {
                                name: '求助数量',
                                type: 'bar',
                                stack: '总量',
                                itemStyle: {
                                    normal: {
                                        color: function (params) {

                                            // build a color map as your need.

                                            var colorList = [
                                                '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                                '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                                '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                                            ];
                                            return colorList[params.dataIndex]
                                        }, label: {show: true, position: 'inside'}
                                    }
                                },
                                data: arrCount
                            }
                        ]
                    };

                    myChart.setOption(option);
                    window.addEventListener('resize', function () {
                        myChart.resize();
                    });
                    //更新chart4_tbody
                    objs.sort(function(a,b){
                        if(a.scenic_value<b.scenic_value){
                            return 1;
                        }else if(a.scenic_value>b.scenic_value){
                            return -1;
                        }
                        return 0;
                    });
                    var s1='<tr><td>';
                    var s2='</td><td>';
                    var s3='</td><td>';
                    var s4='</td></tr>\n';
                    var innerH="";
                    for(var i=0;i<objs.length;i++){
                        if(i<=5) {
                            var t = i + 1;
                            innerH = innerH + s1 + t + s2 + objs[i].scenic_name + s3 + objs[i].scenic_value + s4;
                        }
                    }
                    document.getElementById("chart4_tbody").innerHTML = innerH;
                }
            }
        }, 3000);


}

function init_line1_01() {
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=1',true);
    request.send();
    //3.监听
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            var XHR = request.responseText;
            document.getElementById("line1-01").innerText=XHR;
        }
    }
}
function init_line1_02() {
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=2',true);
    request.send();
    //3.监听
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            var XHR = request.responseText;
            document.getElementById("line1-02").innerText=XHR;
        }
    }
}
function init_line1_03() {
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=3',true);
    request.send();
    //3.监听
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            var XHR = request.responseText;
            document.getElementById("line1-03").innerText=XHR;
        }
    }
}
function init_line2_01() {
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=4',true);
    request.send();
    //3.监听
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            var XHR = request.responseText;
            document.getElementById("line2-01").innerText=XHR;
        }
    }
}
function init_line2_02() {
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=5',true);
    request.send();
    //3.监听
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            var XHR = request.responseText;
            document.getElementById("line2-02").innerText=XHR;
        }
    }
}
function init_line3_01() {
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=6',true);
    request.send();
    //3.监听
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            var XHR = request.responseText;
            console.log(XHR)
            document.getElementById("line3-01").innerText=XHR;
        }
    }
}
function init_line3_02() {
    //1.创建XMLHttpRequest对象
    var request = new XMLHttpRequest();
    //2.发送请求
    request.open('get','http://localhost:8080/Server2_0_war_exploded/Servlet_analyze?forecast=7',true);
    request.send();
    //3.监听
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            var XHR = request.responseText;
            document.getElementById("line3-02").innerText=XHR;
        }
    }
}