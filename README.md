# Server2.0
## 📝介绍
Server2.0为Tomcat服务器程序，遵循MVC设计典范，是快应用、边缘端设备、OSS存储服务等程序正常工作运行的保障。Server2.0主要负责系统的数据处理、数据格式转换、数据存储、数据请求响应等工作，Server2.0运行于服务器A。

## 🔨功能定位
![](https://i2.tiimg.com/716900/7cfb88d466048c45.jpg)


## 💻服务器端、X86开发平台端软件环境

| <img src="https://i2.tiimg.com/716900/865a9bfbffb6b742.png" alt="Windows" width="24px" height="24px" /><br/>Windows | <img src="https://i2.tiimg.com/716900/0ca7a41bc10100f0.png" alt="Ubuntu" width="24px" height="24px" /><br/>Ubuntu | <img src="https://i2.tiimg.com/716900/5da75e19e484588d.png" alt="Mysql" width="24px" height="24px" /><br/>Mysql | <img src="https://i2.tiimg.com/716900/63f5f049489a54a3.png" alt="Opera" width="24px" height="24px" /><br/>Tomcat |
| --------- | --------- | --------- | --------- | 
| Win10 / Win7| 18.04 LTS| 8.0|  9.0 

## 🖥 浏览器支持列表

- 景区管理者仅利用浏览器即可访问 **DeepPupil** 基于Web端提供的服务
- 服务器端呈现、支持现代主流浏览器
- 浏览器需对**Flash**提供支持

| <img src="https://i1.fuimg.com/716900/6f315bcd77c1be2a.png" alt="IE / Edge" width="24px" height="24px" /><br/>IE / Edge | <img src="https://i1.fuimg.com/716900/019888a645ef7faa.png" alt="Firefox" width="24px" height="24px" /><br/>Firefox | <img src="https://i1.fuimg.com/716900/dc4d903149eea9db.png" alt="Chrome" width="24px" height="24px" /><br/>Chrome | <img src="https://i1.fuimg.com/716900/bda2ee747d6a1385.png" alt="Safari" width="24px" height="24px" /><br/>Safari | <img src="https://i1.fuimg.com/716900/8aa993bcfb531b29.png" alt="Samsung" width="24px" height="24px" /><br/>Samsung | <img src="https://i1.fuimg.com/716900/b8c1e6dd6140e20b.png" alt="Opera" width="24px" height="24px" /><br/>Opera | <img src="https://i1.fuimg.com/716900/3cd1f14d9830a99a.png" alt="Opera Mini" width="24px" height="24px" /><br/>Opera Mini |
| --------- | --------- | --------- | --------- | --------- | --------- | --------- |
| IE11, Edge| last 2 versions| last 4 versions| last 2 versions| last 2 versions| last 2 versions| last 3 versions
## 🚀快速开始
1. 使用jet brain出品的IDEA编译器打开Server2.0。
2. 在计算机上部署团队提供的数据库，数据库备份文件在Server2.0/DB目录下，其中“DeepPupil.sql”为数据库表结构及表内容备份文件，“存储过程.txt”为数据库存储过程备份文件，请依次恢复这两项内容。
3. 将Server2.0内DBconnection.java文件中的数据库用户名和密码更改正确。
4. 开始运行Server2.0


## 📦项目依赖文件
```
aliyun_java_sdk_3.8.0
commons-beanutils-1.9.4.jar
commons-collections-3.2.2.jar
commons-lang-2.6.jar
commons-logging-1.2.jar
ezmorph-1.0.6.jar
json-lib-2.4-jdk15.jar
qcloudsms-1.0.6.jar
commons-codec-1.14.jar
mysql-connector-java-8.0.18.jar
```

## 🌱重要功能实现步骤
### 1.构建地图控制器对象
```javascript
new AMap.Map("container", {
        viewMode: '3D',
        pitch: 80,
        resizeEnable: true,
        center: [109.629757, 18.253862],
        zoom: 11.5
    });

    /*positional arguments:
        :param viewMode: 视图模式，可选2D与3D
        :param pitch:视图倾斜角
        :param resizeEnable:地图是否可缩放
        :param center:地图默认中心位置坐标
        :param zoom:地图默认缩放级别
    return value:
        :return:Amap.Map地图控制器对象
    */
```
### 2.初始化热力图对象
```javascript
var heatmap = new AMap.Heatmap(map, heatmapOpts);
setInterval(function () {
    heatmap.setDataSet({
        data: heatmapData,
        max: 100
    })
}, 2000);

    /*positional arguments:
        :param map: 地图控制器对象
        :param heatmapOpts:3D热力图初始参数
    return value:
        :return:Amap.Heatmap热力图对象
    */
```
### 3.ajax异步请求数据库服务器内数据源，利用此数据每隔5s动态更新客流量热力图
```javascript
$(function () {
        $(document).ready(
            function () {
                setInterval(function () {
                    $.ajax({
                        url: "json",
                        type: "POST",
                        dataType: "json",
                        success: function (data) {
                            heatmapData = data;
                        },
                        error: function (e) {
                            alert("error");
                        }
                    })
                }, 500);
            }
        )
    }
```
### 4.前端利用浏览器渲染数据
![](https://i2.tiimg.com/716900/1faed161ade06149.png)

---
## 🌈系统效果展示
### **1.景区数据概览模块**

![](https://i2.tiimg.com/716900/a87e28d6d0932810.png)
### **2.客流量数据、景区资源数据可视化模块**

![](https://i2.tiimg.com/716900/9eab45e7d04affc4.png)
### **3.景区资源数据决策评估系统**

![](https://i2.tiimg.com/716900/0a1aacdf93001743.jpg)
### **4.景区监控模块**

![](https://i2.tiimg.com/716900/4e3ab26847f6f740.png)

## ✅历史版本
### **版本2.0（New）（2020/5/27）**
* 重构了部分功能结构。
* 优化了内存占用情况。
* 现已支持Windows10新版Edge。
* 修复了已知Bug。

### **版本1.8（2020/3/27）**
* 更新景区资源压力分析程序。
* 优化数据库访问方式，在不影响前端数据呈现的前提下减少了对数据库服务器的并发访问。
* 更新OSS存储服务API独写过程，提升程序工作效率。

### **版本1.7（2020/2/3）**
* 修改景区客流量分布热力图的呈现样式，最终以柱状热力图形式展示。
* 引入RTMP协议，可直接播放监控视频流。
* 后端引入OSS云端存储，并为不同用户提供资源隔离机制。

### **版本1.4（2019/12/17）**
* 完成整体前端呈现框架样式。
* 引入Echarts辅助数据可视化的呈现。
* 建立了同数据库服务器的可靠通信。
* 引入后端SMS动态验证码请求功能。

### **版本1.3（2019/11/20）**
* 重构数据库表结构。
* 优化异步请求过程。
* 新增了停车场数据监控入口。
* 更新后端数据交互。

### **版本1.0（2019/11/15）**
* 完成整体前端呈现框架静态样式。
* 完善后端基本数据交互操作。

# ❤️鸣谢
感谢partnerX对热力图渲染核心功能的构建、感谢partnerF对前端整体框架的设计、感谢partnerW对前端各个模块子功能的打磨，最后感谢各开源工程提供的前端样式资源。