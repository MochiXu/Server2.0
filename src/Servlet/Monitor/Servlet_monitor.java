package Servlet.Monitor;

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
// 服务于监控界面，用于数据监控
@WebServlet(name = "Servlet_monitor")
public class Servlet_monitor extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();
        switch (Integer.parseInt(request.getParameter("monitor"))){
            case 1:
                try {
                    //获取监控设备树列表
                    printWriter.println(get_device_Json());
                    System.out.println("获取景区监控设备列表");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    //获取具体设备数据
                    String spot_id_in=request.getParameter("spot_id");
                    printWriter.println(get_device_info_Json(spot_id_in));
                    System.out.println("获取景区监控设备详细数据");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                //获取地图json数据--设备在地图上的位置
                try {
                    printWriter.println(get_device_map_Json());
                    System.out.println("获取景区监控定位位置");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                break;
        }
    }

    //获取设备树数据
    private JSONArray get_device_Json() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        //新增根节点
        JSONObject jsonObject_root=new JSONObject();
        jsonObject_root.put("name","智慧景区智能终端设备树");
        jsonObject_root.put("open",true);
        jsonObject_root.put("id",0);
        jsonObject_root.put("icon","img/root.png");
        jsonArray.add(jsonObject_root);
        //获取景点名称
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select scenic_info.scenic_name from scenic_info,scenic_spot where scenic_info.scenic_id=scenic_spot.scenic_id group by scenic_info.scenic_name;");
        int scenic_id=0;
        while(resultSet.next()) {
            scenic_id++;
            JSONObject jsonObject_scenic=new JSONObject();
            jsonObject_scenic.put("name",resultSet.getString(1));
            jsonObject_scenic.put("open",true);
            jsonObject_scenic.put("id",scenic_id);
            jsonObject_scenic.put("icon","img/device_list.png");
            jsonArray.add(jsonObject_scenic);
            DBconnection dBconnection1 = new DBconnection();
            ResultSet resultSet1 = dBconnection1.DB_FindDataSet("select scenic_spot.spot_id from scenic_spot,scenic_info where scenic_spot.scenic_id=scenic_info.scenic_id and scenic_info.scenic_name='" + resultSet.getString(1) + "';");
            int spot_id = scenic_id*10;
            JSONObject jsonObject_spot=new JSONObject();
            while (resultSet1.next()) {
                spot_id++;
                jsonObject_spot.put("name", resultSet1.getString(1));
                jsonObject_spot.put("id",spot_id);
                jsonObject_spot.put("pid",scenic_id);
                jsonObject_spot.put("icon","img/camera.png");
                jsonArray.add(jsonObject_spot);

            }
            dBconnection1.FreeResource();
        }
        dBconnection.FreeResource();
//        System.out.println(jsonArray);
        return jsonArray;
    }

    //获取设备具体信息
    private JSONArray get_device_info_Json(String spot_id_in) throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        //连接数据，获取有关此摄像头的具体信息
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select spot_detail.spot_id,scenic_info.scenic_name,spot_detail.spot_height,spot_detail.spot_camera_angle,spot_detail.spot_work_status,spot_detail.spot_open_time,spot_detail.spot_lng,spot_detail.spot_lat from spot_detail,scenic_info,scenic_spot where spot_detail.spot_id='"+spot_id_in+"' and spot_detail.spot_id=scenic_spot.spot_id and scenic_spot.scenic_id=scenic_info.scenic_id;");
        while (resultSet.next()){
            jsonObject.put("spot_id",resultSet.getString(1));
            jsonObject.put("scenic_name",resultSet.getString(2));
            jsonObject.put("spot_height",resultSet.getString(3));
            jsonObject.put("spot_camera_angle",resultSet.getString(4));
            jsonObject.put("spot_work_status",resultSet.getString(5));
            jsonObject.put("spot_open_time",resultSet.getString(6));
            jsonObject.put("spot_lng",resultSet.getString(7));
            jsonObject.put("spot_lat",resultSet.getString(8));
        }
        dBconnection.FreeResource();
        jsonArray.add(jsonObject);
        return jsonArray;
    }


    //获取设备位置地图源数据
    private JSONArray get_device_map_Json() throws SQLException, ClassNotFoundException {
        //声明json对象
        JSONArray jsonArray=new JSONArray();

        //获取spot位置数据
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select scenic_info.scenic_name,spot_detail.spot_id,spot_detail.spot_lng,spot_detail.spot_lat from scenic_info,spot_detail,scenic_spot where scenic_info.scenic_id=scenic_spot.scenic_id and scenic_spot.spot_id=spot_detail.spot_id;");
        while(resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("lng",resultSet.getString(3));
            jsonObject.put("lat",resultSet.getString(4));
            jsonObject.put("lnglat",resultSet.getString(3)+","+resultSet.getString(4));
            jsonObject.put("title",resultSet.getString(1)+": "+resultSet.getString(2));
            jsonObject.put("spot_id",resultSet.getString(2));
            jsonArray.add(jsonObject);
        }
        dBconnection.FreeResource();
        return jsonArray;
    }
}
