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

@WebServlet(name = "Scenic_notice_API")
public class Scenic_notice_API extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter=response.getWriter();
        JSONArray jsonArray=new JSONArray();
        try {
            DBconnection dBconnection=new DBconnection();
            ResultSet resultSet=dBconnection.DB_FindDataSet("select notice_id,notice_content from scenic_notice;");
            while (resultSet.next()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("notice_id",resultSet.getString(1));
                jsonObject.put("content",resultSet.getString(2));
                jsonArray.put(jsonObject);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        printWriter.println(jsonArray);
    }
}
