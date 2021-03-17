package Servlet.QuickAPI;

import Database.DBconnection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Scenic_selected_API")
public class Scenic_selected_API extends HttpServlet {
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
                    String thumbnail=request.getParameter("thumbnail");
                    if(thumbnail.equals("1")){
                        printWriter.println(getData3(request.getParameter("scenic_id")));
                    }else if(thumbnail.equals("0")) {
                        printWriter.println(getData1(request.getParameter("scenic_id")));
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "1":
                //1级数据
                try {
                    printWriter.println(getData2(request.getParameter("scenic_id"),request.getParameter("image_id")));
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
    private JSONArray getData1(String scenic_id) throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select scenic_id,image_id,image_url,count,upload_date,title from scenic_selected_images where scenic_id='"+scenic_id+"';");
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scenic_id",resultSet.getString(1));
            jsonObject.put("image_id",resultSet.getString(2));
            jsonObject.put("image_url",resultSet.getString(3));
            jsonObject.put("image_query_number",resultSet.getString(4));
            jsonObject.put("image_time",resultSet.getString(5));
            jsonObject.put("title",resultSet.getString(6));
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    private JSONArray getData2(String scenic_id, String image_id) throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select scenic_id,image_id,image_url,count,upload_date,info,title from scenic_selected_images where scenic_id='"+scenic_id+"' and image_id='"+image_id+"';");
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scenic_id",resultSet.getString(1));
            jsonObject.put("image_id",resultSet.getString(2));
            jsonObject.put("image_url",resultSet.getString(3));
            jsonObject.put("image_query_number",resultSet.getString(4));
            jsonObject.put("image_time",resultSet.getString(5));
            jsonObject.put("image_message",resultSet.getString(6));
            jsonObject.put("title",resultSet.getString(7));
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    private JSONArray getData3(String scenic_id) throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select scenic_id,image_id,image_url,count,upload_date from scenic_selected_images where scenic_id='"+scenic_id+"' limit 0,3;");
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scenic_id",resultSet.getString(1));
            jsonObject.put("image_id",resultSet.getString(2));
            jsonObject.put("image_url",resultSet.getString(3));
            jsonObject.put("image_query_number",resultSet.getString(4));
            jsonObject.put("image_time",resultSet.getString(5));
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
