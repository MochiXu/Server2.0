package Servlet.QuickAPI;

import Database.DBconnection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Scenic_info_API")
public class Scenic_info_API extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter=response.getWriter();
        switch (request.getParameter("hierarchy")){
            case "0":
                //0级数据
                try {
                    printWriter.println(getData1());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "1":
                //1级数据
                try {
                    printWriter.println(getData2(request.getParameter("scenic_id")));
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private JSONArray getData1() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("select scenic_info.scenic_id,scenic_name,scenic_location,scenic_star,scenic_fraction,image_url from scenic_info,scenic_introduce_images where scenic_info.scenic_id=scenic_introduce_images.scenic_id\n");
        while (resultSet1.next()){
            if(!resultSet1.getString(1).equals("scenic")){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("scenic_id",resultSet1.getString(1));
                jsonObject.put("name",resultSet1.getString(2));
                jsonObject.put("location",resultSet1.getString(3));
                jsonObject.put("star",resultSet1.getString(4));
                jsonObject.put("score",resultSet1.getString(5));
                jsonObject.put("image",resultSet1.getString(6));
                //获取各个景点的tags
                DBconnection dBconnection=new DBconnection();
                ResultSet resultSet=dBconnection.DB_FindDataSet("select tags from scenic_tags where scenic_id='"+resultSet1.getString(1)+"'  limit 0,2");
                int i=1;
                while (resultSet.next()){
                    jsonObject.put("type"+i,resultSet.getString(1));
                    i++;
                }
                dBconnection.FreeResource();
                jsonArray.put(jsonObject);
            }
        }
        dBconnection1.FreeResource();
        return jsonArray;
    }

    private JSONArray getData2(String scenic_id) throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("select scenic_info.scenic_id,scenic_name,scenic_location,scenic_star,scenic_fraction,image_url,scenic_introduce from scenic_info,scenic_introduce_images where scenic_info.scenic_id=scenic_introduce_images.scenic_id and scenic_info.scenic_id='"+scenic_id+"'\n\n");
        while (resultSet1.next()){
            if(!resultSet1.getString(1).equals("scenic")){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("name",resultSet1.getString(2));
                jsonObject.put("scenic_id",resultSet1.getString(1));
                jsonObject.put("location",resultSet1.getString(3));
                jsonObject.put("star",resultSet1.getString(4));
                jsonObject.put("score",resultSet1.getString(5));
                jsonObject.put("image",resultSet1.getString(6));
                jsonObject.put("detail",resultSet1.getString(7));
                //获取各个景点的tags
                DBconnection dBconnection=new DBconnection();
                ResultSet resultSet=dBconnection.DB_FindDataSet("select tags from scenic_tags where scenic_id='"+resultSet1.getString(1)+"'  limit 0,2");
                int i=1;
                while (resultSet.next()){
                    jsonObject.put("type"+i++,resultSet.getString(1));
                }
                dBconnection.FreeResource();
                jsonArray.put(jsonObject);
            }
        }
        dBconnection1.FreeResource();
        return jsonArray;
    }
}
