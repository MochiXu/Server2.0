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

@WebServlet(name = "Scenic_flow_API")
public class Scenic_flow_API extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter=response.getWriter();
        String scenic_id=request.getParameter("scenic_id");
        JSONArray jsonArray=new JSONArray();
        try {
            DBconnection dBconnection=new DBconnection();
            ResultSet resultSet=dBconnection.DB_FindDataSet("select * from scenic_flow_image where scenic_id='"+scenic_id+"'");
            while (resultSet.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("time",resultSet.getString(3));
                jsonObject.put("image",resultSet.getString(4));
                jsonArray.put(jsonObject);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("正在回传快应用");
        printWriter.println(jsonArray);
    }
}
