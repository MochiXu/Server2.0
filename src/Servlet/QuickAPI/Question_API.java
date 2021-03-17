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

@WebServlet(name = "Question_API")
public class Question_API extends HttpServlet {
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
                    System.out.println("正在返回给快应用 问题类型 ");
                    printWriter.println(getData1());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "1":
                //1级数据
                try {
                    System.out.println("正在返回给快应用 "+request.getParameter("type_id")+" 问题类型中的详细内容");
                    printWriter.println(getData2(request.getParameter("type_id")));
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private JSONArray getData1() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select type_id,type_name,type_content from question_type");
        while (resultSet.next()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type_id",resultSet.getString(1));
            jsonObject.put("type_name",resultSet.getString(2));
            jsonObject.put("type",resultSet.getString(3));
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    private JSONArray getData2(String type_id) throws SQLException, ClassNotFoundException {
        JSONArray jsonArray=new JSONArray();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select question_id,type,title,main_content,all_content,date,time,count,scenic_name from question_content,scenic_info where scenic_info.scenic_id=question_content.scenic_id and type='"+type_id+"';\n");
        while (resultSet.next()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("question_id",resultSet.getString(1));
            jsonObject.put("type_id",resultSet.getString(2));
            jsonObject.put("title",resultSet.getString(3));
            jsonObject.put("content",resultSet.getString(4));
            jsonObject.put("content_detail",resultSet.getString(5));
            jsonObject.put("time",resultSet.getString(6)+" "+resultSet.getString(7));
            jsonObject.put("number",resultSet.getString(8));
            jsonObject.put("scenic_name",resultSet.getString(9));
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
