package Servlet.Index;

import Database.DBconnection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
//地图内的数据为10分钟内各个景点在最后一个记录周期内的人流分布情况
@WebServlet(name = "Servlet_index_map")
public class Servlet_index_map extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();
        if (Integer.parseInt(request.getParameter("map")) == 1) {
            try {
                printWriter.println(get_map_Json());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else if(Integer.parseInt(request.getParameter("map")) == 2){
            try {
                printWriter.println(get_map_Json2());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONArray get_map_Json() throws SQLException, ClassNotFoundException {
        //声明json对象
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        //获取无人机端数据
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("call get_Json_map1_uav();");
       // System.out.println("while01开始");
        while(resultSet01.next()){
//            JSONObject jsonObj = new JSONObject();

            String lng=resultSet01.getString(1);
            String lat=resultSet01.getString(2);
            String cnt=resultSet01.getString(3);
            jsonObj.put("lnglat",lng+","+lat);
            jsonObj.put("cnt",cnt);
            //System.out.println(jsonObj);

            jsonArray.add(jsonObj);
        }
       // System.out.println("while01结束");
        dBconnection01.FreeResource();

        //获取定点设备数据
        DBconnection dBconnection02=new DBconnection();
        ResultSet resultSet02=dBconnection02.DB_FindDataSet("call get_Json_map1_spot();");
        //System.out.println("while02开始");
        while (resultSet02.next()){
            //JSONObject jsonObj = new JSONObject();

            String lng=resultSet02.getString(1);
            String lat=resultSet02.getString(2);
            String cnt=resultSet02.getString(3);
            jsonObj.put("lnglat",lng+","+lat);
            jsonObj.put("cnt",cnt);
           // System.out.println(jsonObj);

            jsonArray.add(jsonObj);
//            System.out.println("定点设备的数据"+jsonObj);
        }

        dBconnection02.FreeResource();
        return jsonArray;
    }
    private JSONArray get_map_Json2() throws SQLException, ClassNotFoundException {
        //声明json对象
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        //获取无人机端数据
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("call get_Json_map1_uav();");
        // System.out.println("while01开始");
        while(resultSet01.next()){
//            JSONObject jsonObj = new JSONObject();

            String lng=resultSet01.getString(1);
            String lat=resultSet01.getString(2);
            String cnt=resultSet01.getString(3);
            jsonObj.put("lng",lng);
            jsonObj.put("lat",lat);
            jsonObj.put("count",cnt);
            //System.out.println(jsonObj);

            jsonArray.add(jsonObj);
        }
        // System.out.println("while01结束");
        dBconnection01.FreeResource();

        //获取定点设备数据
        DBconnection dBconnection02=new DBconnection();
        ResultSet resultSet02=dBconnection02.DB_FindDataSet("call get_Json_map1_spot();");
        //System.out.println("while02开始");
        while (resultSet02.next()){
            //JSONObject jsonObj = new JSONObject();

            String lng=resultSet02.getString(1);
            String lat=resultSet02.getString(2);
            String cnt=resultSet02.getString(3);
            jsonObj.put("lng",lng);
            jsonObj.put("lat",lat);
            jsonObj.put("count",cnt);
            // System.out.println(jsonObj);
//            System.out.println("定点设备的数据"+jsonObj);

            jsonArray.add(jsonObj);
        }
        //System.out.println("while02结束");
        dBconnection02.FreeResource();
        System.out.println("数据拼接完成，回传map_json");
        return jsonArray;
    }
}
