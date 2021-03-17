package Servlet.QuickAPI;

import Database.DBconnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "User_location_API")
public class User_location_API extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
//        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter=response.getWriter();

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");//可以方便地修改日期格式
        String date_now = dateFormat.format(now);//当前日期
        String time_now = timeFormat.format(now);//当前时间




        //环翠公园坐标位置区域
        Point[] ps01 = new Point[] { new Point(122.102866,37.508357), new Point(122.102394,37.507472), new Point(122.102802,37.506723), new Point(122.102136,37.504663), new Point(122.102201,37.503914) ,
                new Point(122.103102,37.503038), new Point(122.103928,37.502927), new Point(122.105237,37.502604), new Point(122.108348,37.503063), new Point(122.109443,37.503063) ,
                new Point(122.111224,37.503165), new Point(122.113563,37.503029), new Point(122.113563,37.503029), new Point(122.116073,37.504646), new Point(122.11721,37.504663) ,
                new Point(122.117253,37.505344), new Point(122.116052,37.505259), new Point(122.114657,37.505855), new Point(122.114614,37.506195), new Point(122.113048,37.506434) ,
                new Point(122.113048,37.507046), new Point(122.109056,37.50754), new Point(122.106567,37.507268), new Point(122.102791,37.508255) };
        //环翠公园内危险区域
        Point[] ps02=new Point[]{new Point(122.108683,37.506696),new Point(122.1082,37.506581),new Point(122.108522,37.506228),new Point(122.108801,37.506186),new Point(122.109214,37.506462)};

        //龙霄公园坐标位置区域
        Point[] ps03=new Point[]{new Point(116.94157,34.172926),new Point(116.943807,34.172948),new Point(116.945964,34.172966),new Point(116.947423,34.172948),new Point(116.947417,34.172504)
                ,new Point(116.945937,34.172509),new Point(116.94385,34.1725),new Point(116.941586,34.172517)};
        //龙霄公园危险区域
        Point[] ps04=new Point[]{new Point(116.946468,34.17293),new Point(116.946446,34.172784),new Point(116.947133,34.17277),new Point(116.947117,34.17293)};
        //龙霄公园周边区域
        Point[] ps05=new Point[]{new Point(116.943829,34.172988),new Point(116.946023,34.173014),new Point(116.947525,34.172983),new Point(116.947557,34.172486),
                new Point(116.945958,34.172469),new Point(116.944649,34.172442),new Point(116.943802,34.172446)};

        //gmf危险区域
        Point[] gmf_danger=new Point[]{new Point(120.417988,36.299813),new Point(120.417859,36.299601),new Point(120.418186,36.299571),new Point(120.418197,36.299774)};
        Point[] gmf_inner=new Point[]{new Point(120.417698,36.300068),new Point(120.417644,36.299636),new Point(120.418202,36.299588),new Point(120.418342,36.300094)};
        Point[] gmf_slider=new Point[]{new Point(120.417494,36.300107),new Point(120.417505,36.299381),new Point(120.418765,36.300513),new Point(120.418615,36.299212)};

        //xmc危险区域
        Point[] xmc_danger=new Point[]{new Point(116.980263,34.123686),new Point(116.980136,34.123188),new Point(116.980982,34.12322),new Point(116.98088,34.123677)};
        Point[] xmc_inner=new Point[]{new Point(116.979636,34.124436),new Point(116.980778,34.124379),new Point(116.980628,34.123428),new Point(116.979678,34.123437)};
        Point[] xmc_slider=new Point[]{new Point(116.979335,34.124658),new Point(116.981052,34.124521),new Point(116.979481,34.123263),new Point(116.981014,34.12314)};

//gmfGY危险区域
        Point[] gmf2_danger=new Point[]{new Point(120.410936,36.291617),new Point(120.410984,36.291133),new Point(120.411188,36.291142),new Point(120.411193,36.291635)};
        Point[] gmf2_inner=new Point[]{new Point(120.410687,36.292654),new Point(120.412592,36.292662),new Point(120.412516,36.290868),new Point(120.41065,36.290881)};
        Point[] gmf2_slider=new Point[]{new Point(120.410499,36.292809),new Point(120.412629,36.292883),new Point(120.412602,36.290838),new Point(120.410553,36.290868)};

        //龙霄公园第二版本
        Point[] LX_danger=new Point[]{new Point(116.94702,34.172845),new Point(116.947326,34.172836),new Point(116.947272,34.172554),new Point(116.947036,34.172536)};
        Point[] LX_slider=new Point[]{new Point(116.947508,34.172978),new Point(116.946022,34.172996),new Point(116.943823,34.173005),new Point(116.943839,34.172468),new Point(116.945947,34.172481),new Point(116.947487,34.172481)};
        Point[] LX_inner=new Point[]{new Point(116.947347,34.172903),new Point(116.945958,34.172938),new Point(116.943898,34.172907),new Point(116.943898,34.172552),new Point(116.943898,34.172552),new Point(116.947358,34.172548)};

        //测试地点周边区域
        Point UserPoint = new Point(Double.parseDouble(request.getParameter("lng")),Double.parseDouble(request.getParameter("lat")));
        System.out.println(UserPoint.getX()+"  "+UserPoint.getY());
        System.out.println(isPtInPoly(UserPoint.getX(),UserPoint.getY(),ps04));
        if(isPtInPoly(UserPoint.getX(),UserPoint.getY(),LX_danger)){
            //检测到游客位于危险区域
//            System.out.println(UserPoint.getX()+"  "+UserPoint.getY());

            System.out.println("游客位于危险区域");
            //更新危险区域游客数据
            try {
                del_notice(request.getParameter("ip"));
                DBconnection dBconnection00=new DBconnection();
                //首先更新此游客的位置
                dBconnection00.DB_Add("insert into person_actual_location(person_ip, person_tel, record_date, record_time, scenic_id, person_lng, person_lat) VALUES ('"+request.getParameter("ip")+"','"+request.getParameter("tel")+"','"+date_now+"','"+time_now+"','huancui_JiMuTing','"+UserPoint.getX()+"','"+UserPoint.getY()+"');");

                ResultSet resultSet=dBconnection00.DB_FindDataSet("select count(*) from dangerous_person where person_ip='"+request.getParameter("ip")+"';");
                int value=0;
                while (resultSet.next()){
                    value=resultSet.getInt(1);
                }
                dBconnection00.FreeResource();
                //值为0说明这个人是从外面进入到了危险区域里面
                if(value==0) {
                    DBconnection dBconnection01 = new DBconnection();
                    dBconnection01.DB_Add("insert into dangerous_person(record_date, record_time, person_lng, person_lat, person_ip) VALUES ('" + date_now + "','" + time_now + "','" + UserPoint.getX() + "','" + UserPoint.getY() + "','" + request.getParameter("ip") + "');");
                    //增加当日的游客误入景区次数
                    //先获取数据库中关于当日景区危险区域历史次数是否存在
                    ResultSet resultSet1=dBconnection01.DB_FindDataSet("select count(*) from danger_count_history where record_date='"+date_now+"';");
                    int hiscount=0;
                    while(resultSet1.next()){
                        hiscount=resultSet1.getInt(1);
                    }
                    dBconnection01.FreeResource();

                    if(hiscount==0){
                        //说明此人是今日第一个进入危险区域的
                        DBconnection dBconnection=new DBconnection();
                        dBconnection.DB_Add("insert into danger_count_history(record_date,count)values('"+date_now+"','1')");
                        dBconnection.FreeResource();
                    }else {
                        //说明此人不是第一个进入危险区域的
                        DBconnection dBconnection=new DBconnection();
                        dBconnection.DB_Add("update danger_count_history set count=count+1 where record_date='"+date_now+"';");
                        dBconnection.FreeResource();
                    }
                    //为游客推送危险通知
                    printWriter.println("Danger");
                    printWriter.println("游客位于危险区域，将为其推送危险提醒");

                }
                //一直呆在危险区域
                printWriter.println("XXXX");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        }else if(isPtInPoly(UserPoint.getX(),UserPoint.getY(),LX_inner)){
            //检测游客是否位于景区内部
            System.out.println("游客位于景区内部");
            try {
                del_danger(request.getParameter("ip"));
                DBconnection dBconnection02=new DBconnection();
                ResultSet resultSet=dBconnection02.DB_FindDataSet("select count(*) from who_inner_scenic where person_ip='"+request.getParameter("ip")+"';");
                int resultCount=0;
                while(resultSet.next()){
                    resultCount=resultSet.getInt(1);
                }
                dBconnection02.FreeResource();
                if(resultCount==0){
                    //数据库中没有游客在景点内的数据，即将更新数据库并推送欢迎通知
                    DBconnection dBconnection03=new DBconnection();
                    dBconnection03.DB_Add("insert into person_actual_location(person_ip, person_tel, record_date, record_time, scenic_id, person_lng, person_lat) VALUES ('"+request.getParameter("ip")+"','"+request.getParameter("tel")+"','"+date_now+"','"+time_now+"','huancui_JiMuTing','"+UserPoint.getX()+"','"+UserPoint.getY()+"');");
                    dBconnection03.DB_Add("insert into who_inner_scenic(person_ip,  record_time, record_date) VALUES ('"+request.getParameter("ip")+"','"+time_now+"','"+date_now+"');");
                    dBconnection03.FreeResource();
                    //为游客推送欢迎通知
                    printWriter.println("Welcom");
                    printWriter.println("游客进入景区，为其推送欢迎通知");
                }else {
                    //更新数据库中游客的实时位置
                    DBconnection dBconnection04=new DBconnection();
                    dBconnection04.DB_Add("insert into person_actual_location(person_ip, person_tel, record_date, record_time, scenic_id, person_lng, person_lat) VALUES ('"+request.getParameter("ip")+"','"+request.getParameter("tel")+"','"+date_now+"','"+time_now+"','huancui_JiMuTing','"+UserPoint.getX()+"','"+UserPoint.getY()+"');");
                    dBconnection04.FreeResource();
                    printWriter.println("BBBB");
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else if(isPtInPoly(UserPoint.getX(),UserPoint.getY(),LX_slider)){
            //游客位于公园周边位置
//            System.out.println("游客位于公园周边");
            try {
                del_danger(request.getParameter("ip"));
                DBconnection dBconnection05=new DBconnection();
                ResultSet resultSet=dBconnection05.DB_FindDataSet("select count(*) from who_inner_scenic where person_ip='"+request.getParameter("ip")+"';");
                int resultCount=0;
                while(resultSet.next()){
                    resultCount=resultSet.getInt(1);
                }
                dBconnection05.FreeResource();
                if(resultCount==0){
                    //说明游客从外部进入，展示靠近公园通知
                    //判断Notice区域是否有这位游客，如果有这位游客了那么就不再进行Notice
                    DBconnection dBconnection0A=new DBconnection();
                    ResultSet resultSet0A=dBconnection0A.DB_FindDataSet("select count(*) from who_inner_notice where person_ip='"+request.getParameter("ip")+"';");
                    int value0A=0;
                    while(resultSet0A.next()){
                        value0A=resultSet0A.getInt(1);
                    }
                    if(value0A==0){
                        //Notice区域没有这个游客
                        printWriter.println("Notice");
                        //通知了Notice后将其加入Notice区域
                        DBconnection dBconnection04=new DBconnection();
                        dBconnection04.DB_Add("insert into who_inner_notice(person_ip,  record_date, record_time) VALUES ('"+request.getParameter("ip")+"','"+date_now+"','"+time_now+"');");
                        dBconnection04.FreeResource();
                    }else {
                        //Notice有这个游客，就不通知
                        printWriter.println("XXXX");
                        System.out.println("Notice区域已经存在此游客，快应用将不会继续推送");
                    }

                }else{
                    //说明游客从内部离开，展示欢送界面
                    //更新数据库
                    DBconnection dBconnection06=new DBconnection();
                    dBconnection06.DB_Del("delete from who_inner_scenic where person_ip='"+request.getParameter("ip")+"';");
                    dBconnection06.DB_Del("update person_actual_location set record_time='00:00:00' where person_ip='"+request.getParameter("ip")+"';");
                    dBconnection06.FreeResource();
                    printWriter.println("Bye");
                    printWriter.println("游客从景区内离开，为其推送反馈通道");
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                del_danger(request.getParameter("ip"));
                del_notice(request.getParameter("ip"));
                DBconnection dBconnection07=new DBconnection();
                ResultSet resultSet=dBconnection07.DB_FindDataSet("select count(*) from who_inner_scenic where person_ip='"+request.getParameter("ip")+"';");
                int resultCount=0;
                while(resultSet.next()){
                    resultCount=resultSet.getInt(1);
                }
                dBconnection07.FreeResource();
                if(resultCount==0){
                    //说明游客本来就没有进入景区
                    printWriter.println("XXXX");
                    System.out.println("游客未进入景区场景化服务范围内，快应用将不会继续推送");
                }else{
                    //推送Bye
                    DBconnection dBconnection08=new DBconnection();
                    del_notice(request.getParameter("ip"));
                    del_danger(request.getParameter("ip"));
                    dBconnection08.DB_Del("delete from who_inner_scenic where person_ip='"+request.getParameter("ip")+"';");
                    dBconnection08.DB_Del("update person_actual_location set record_time='00:00:00' where person_ip='"+request.getParameter("ip")+"';");
                    //ByeBye了之后需要将其时间破坏一下
                    dBconnection08.FreeResource();
                    printWriter.println("Bye");
                    System.out.println("游客正在离开景区场景化服务范围，快应用将不会继续推送");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
//            printWriter.println("XXXX");
        }
    }

    // Point类
    public static class Point {
        private Double x;
        private Double y;
        public Point (Double x , Double y) {
            this.x = x;
            this.y = y;
        }
        public Double getX() {
            return x;
        }
        public void setX(Double x) {
            this.x = x;
        }
        public Double getY() {
            return y;
        }
        public void setY(Double y) {
            this.y = y;
        }

    }
    //判断游客是否在规定区域内
    public static boolean isPtInPoly (double ALon , double ALat , Point[] ps) {
        int iSum, iCount, iIndex;
        double dLon1 = 0, dLon2 = 0, dLat1 = 0, dLat2 = 0, dLon;
        if (ps.length < 3) {
            return false;
        }
        iSum = 0;
        iCount = ps.length;
        for (iIndex = 0; iIndex<iCount;iIndex++) {
            if (iIndex == iCount - 1) {
                dLon1 = ps[iIndex].getX();
                dLat1 = ps[iIndex].getY();
                dLon2 = ps[0].getX();
                dLat2 = ps[0].getY();
            } else {
                dLon1 = ps[iIndex].getX();
                dLat1 = ps[iIndex].getY();
                dLon2 = ps[iIndex + 1].getX();
                dLat2 = ps[iIndex + 1].getY();
            }
            // 以下语句判断A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上
            if (((ALat >= dLat1) && (ALat < dLat2)) || ((ALat >= dLat2) && (ALat < dLat1))) {
                if (Math.abs(dLat1 - dLat2) > 0) {
                    //得到 A点向左射线与边的交点的x坐标：
                    dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - ALat) ) / (dLat1 - dLat2);
                    // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：
                    if (dLon < ALon) {
                        iSum++;
                    }
                }
            }
        }
        return (iSum % 2) != 0;
    }

    private void del_danger(String ip) throws SQLException, ClassNotFoundException {
        DBconnection dBconnection=new DBconnection();
        dBconnection.DB_Del("delete from dangerous_person where person_ip='"+ip+"';");
        dBconnection.FreeResource();
    }

    private void del_notice(String ip) throws SQLException, ClassNotFoundException {
        DBconnection dBconnection=new DBconnection();
        dBconnection.DB_Del("delete from who_inner_notice where person_ip='"+ip+"';");
        dBconnection.FreeResource();
    }
}
