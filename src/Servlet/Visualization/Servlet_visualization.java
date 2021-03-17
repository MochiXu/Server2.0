package Servlet.Visualization;

import Database.DBconnection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.beancontext.BeanContextServiceRevokedEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Servlet.Config.QuickApp_Interval;
//这里为了测试4小时的效果所使用的时间为确定的，所以需要重新使用getBeginTime行的函数
@WebServlet(name = "Servlet_visualization")
public class Servlet_visualization extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();
        switch (Integer.parseInt(request.getParameter("map"))){
            case 1://人流空间分布数据
                try {
                    printWriter.println(get_Person_Distribution());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 2://快应用分布数据
                try {
                    printWriter.println(get_Quickapp_Distribution());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3://公共服务场所分布数据
                try {
                    printWriter.println(get_Service_Distribution());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 4://停车场位置分布数据
                try {
                    printWriter.println(get_Park_Distribution());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 5://垃圾桶位置分布数据
                try {
                    printWriter.println(get_Trash_Distribution());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 6://厕所位置分布数据
                try {
                    printWriter.println(get_Toilet_Distribution());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 7://景区内歇息处的位置分布数据
                try {
                    printWriter.println(get_Rest_Distribution());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    //获取人流空间分布数据
    private JSONArray get_Person_Distribution() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();

        //获取无人机端数据
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("call get_Json_map1_uav();");
        while(resultSet01.next()){
            JSONObject jsonObj1 = new JSONObject();
            jsonObj1.put("lnglat",resultSet01.getString(1)+","+resultSet01.getString(2));
            jsonObj1.put("cnt",resultSet01.getString(3));
            jsonArray.add(jsonObj1);
        }
        dBconnection01.FreeResource();

        //获取定点终端设备数据
        DBconnection dBconnection02=new DBconnection();
        ResultSet resultSet02=dBconnection02.DB_FindDataSet("call get_Json_map1_spot();");
        while (resultSet02.next()){
            JSONObject jsonObj2 = new JSONObject();
            jsonObj2.put("lnglat",resultSet02.getString(1)+","+resultSet02.getString(2));
            jsonObj2.put("cnt",resultSet02.getString(3));
            jsonArray.add(jsonObj2);
        }
        dBconnection02.FreeResource();

        //返回人流空间分布数据
        return jsonArray;
    }
    //获取4小时内活跃的快应用终端分布数据
    private JSONArray get_Quickapp_Distribution() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
//        ResultSet resultSet=dBconnection.DB_FindDataSet("call get_Json_map3_quickapp_location('"+getBeginTime()+"');\n");
        ResultSet resultSet=dBconnection.DB_FindDataSet("call get_Json_map3_quickapp_location('00:28:00');\n");
        while(resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("person_ip",resultSet.getString(3));
            jsonObject.put("person_lng",resultSet.getString(4));
            jsonObject.put("person_lat",resultSet.getString(5));
            jsonObject.put("person_time",resultSet.getString(2));
            jsonObject.put("person_date",resultSet.getString(1));
            jsonObject.put("lnglat",resultSet.getString(4)+", "+resultSet.getString(5));
            jsonArray.add(jsonObject);
        }
        dBconnection.FreeResource();
        return jsonArray;
    }
    //获取景区内公共服务场所的位置数据
    private JSONArray get_Service_Distribution() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();

        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select service_center_id,service_center_name,service_center_lng,service_center_lat,service_property from service_center_detail;");
        while(resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("service_center_id",resultSet.getString(1));
            jsonObject.put("service_center_name",resultSet.getString(2));
            jsonObject.put("service_center_lng",resultSet.getString(3));
            jsonObject.put("service_center_lat",resultSet.getString(4));
            jsonObject.put("service_property",resultSet.getString(5));
            jsonObject.put("lnglat",resultSet.getString(3)+", "+resultSet.getString(4));
            jsonArray.add(jsonObject);
        }
        dBconnection.FreeResource();

        return jsonArray;
    }
    //获取景区内停车场的车位及位置数据
    private JSONArray get_Park_Distribution() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select * from park_detail;");
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scenic_id",resultSet.getString(2));
            jsonObject.put("park_id",resultSet.getString(3));
            jsonObject.put("park_name",resultSet.getString(4));
            jsonObject.put("park_lng",resultSet.getString(5));
            jsonObject.put("park_lat",resultSet.getString(6));
            jsonObject.put("park_total",resultSet.getInt(7));
            jsonObject.put("park_occupy",resultSet.getInt(8));
            jsonObject.put("park_date",resultSet.getString(9));
            jsonObject.put("park_time",resultSet.getString(10));
            jsonObject.put("lnglat",resultSet.getString(5)+", "+resultSet.getString(6));

            jsonArray.add(jsonObject);
        }
        dBconnection.FreeResource();
        return jsonArray;
    }
    //获取景区内垃圾桶的位置及数据
    private JSONArray get_Trash_Distribution() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select * from trash_detail;");
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scenic_id",resultSet.getString(2));
            jsonObject.put("trash_id",resultSet.getString(3));
            jsonObject.put("trash_name",resultSet.getString(4));
            jsonObject.put("trash_lng",resultSet.getString(5));
            jsonObject.put("trash_lat",resultSet.getString(6));
            jsonObject.put("lnglat",resultSet.getString(5)+", "+resultSet.getString(6));

            jsonArray.add(jsonObject);
        }
        dBconnection.FreeResource();

        return jsonArray;
    }
    //获取景区内厕所的位置及数据
    private JSONArray get_Toilet_Distribution() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();

        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select * from toilet_detail;");
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scenic_id",resultSet.getString(2));
            jsonObject.put("toilet_id",resultSet.getString(3));
            jsonObject.put("toilet_name",resultSet.getString(4));
            jsonObject.put("toilet_lng",resultSet.getString(5));
            jsonObject.put("toilet_lat",resultSet.getString(6));
            jsonObject.put("lnglat",resultSet.getString(5)+", "+resultSet.getString(6));

            jsonArray.add(jsonObject);
        }
        dBconnection.FreeResource();

        return jsonArray;
    }
    //获取景区内歇息处的位置及数据
    private JSONArray get_Rest_Distribution() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select * from rest_detail;");
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scenic_id",resultSet.getString(2));
            jsonObject.put("rest_id",resultSet.getString(3));
            jsonObject.put("rest_name",resultSet.getString(4));
            jsonObject.put("rest_lng",resultSet.getString(5));
            jsonObject.put("rest_lat",resultSet.getString(6));
            jsonObject.put("lnglat",resultSet.getString(5)+", "+resultSet.getString(6));

            jsonArray.add(jsonObject);
        }
        dBconnection.FreeResource();

        return jsonArray;
    }

    private String getBeginTime(){
        //获取系统当前时间的前n分钟时间戳
        Date end = new Date();
        Date begin= new Date(end.getTime() - QuickApp_Interval); //数据更新时间间隔
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//可以方便地修改日期格式
        String time_begin = dateFormat.format(begin);//记录开始时间
        String time_end = dateFormat.format(end);//系统当前时间
        return time_begin;
    }
}
