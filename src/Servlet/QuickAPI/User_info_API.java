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

@WebServlet(name = "User_info_API")
public class User_info_API extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter=response.getWriter();
        String User_mac=request.getParameter("device_mac");
        JSONArray jsonArray=new JSONArray();
        if(User_mac!=null){
            try {
                //判断此mac是否存在
                DBconnection dBconnection1=new DBconnection();
                ResultSet resultSet1=dBconnection1.DB_FindDataSet("select * from person_info where person_info.device_mac='"+User_mac+"'\n");
                if(resultSet1.next()){
                    //resultSet1不为空，则查询对应的游客详细数据
                    DBconnection dBconnection=new DBconnection();
                    ResultSet resultSet=dBconnection.DB_FindDataSet("select person_info.person_id,person_info.person_tel,person_info.passwd,person_record.record_date,person_record.record_time from person_info,person_record where person_info.device_mac='"+User_mac+"' and person_info.person_id=person_record.person_id ;\n");
                    while (resultSet.next()){
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("user_id",resultSet.getString(1));
                        jsonObject.put("password",resultSet.getString(3));
                        jsonObject.put("number",resultSet.getString(2));
                        jsonObject.put("time",resultSet.getString(4)+" "+resultSet.getString(5));
                        jsonArray.put(jsonObject);
                        printWriter.println(jsonArray);
                    }
                }else {
                    printWriter.println("not found!");
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
