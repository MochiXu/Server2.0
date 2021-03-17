package Servlet;

public  class Config {
    public static int Record_Interval=60000000;//刷新数据时间间隔--十分钟刷新一次
//    public static int Record_Interval=600000;//刷新数据时间间隔--十分钟刷新一次
    public static int SMS_DEL_Interval=300000;//清空数据库内存储的短信验证码时间间隔--五分钟清空一次
    public static int QuickApp_Interval=14400000;//记录活跃的快应用用户时间间隔为4小时以内
    //public static int Spot_Map_Interval=7200000;//每两个小时更新一下前端内景区定点设备地图数据--js内无法使用Config，所以是规定死的
    public static int Scenic_Max_PersonCount=200;//景区饱和人流量
    public static int Dangerous_Count=10;//今日误入景区危险区域游客数量
}
