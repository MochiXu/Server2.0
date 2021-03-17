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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "User_comments_API")
public class User_comments_API extends HttpServlet {
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
            ResultSet resultSet=dBconnection.DB_FindDataSet("select comments_id,user_id,scenic_id,user_comments from person_comments where  scenic_id in('"+request.getParameter("scenic_id")+"','scenic')");
            while (resultSet.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("scenic_id",resultSet.getString(3));
                jsonObject.put("user_id",resultSet.getString(2));
                jsonObject.put("message",resultSet.getString(4));
                jsonObject.put("images",getUrl(resultSet.getString(1)));
                jsonArray.put(jsonObject);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        printWriter.println(jsonArray);
    }

    //获取游客评论中三张图片链接
    private List<String> getUrl(String com_id) throws SQLException, ClassNotFoundException {
        List<String> urls=new ArrayList<>();
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select image_url from person_comments_image where comments_id='"+com_id+"'  limit 0,3;\n");
        while (resultSet.next()){
            urls.add(resultSet.getString(1));
        }
        dBconnection.FreeResource();
        return urls;
    }
}
