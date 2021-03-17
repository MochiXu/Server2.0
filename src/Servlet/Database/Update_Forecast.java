package Servlet.Database;

import Database.DBconnection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 用于和LSTM数据预测通信
@WebServlet(name = "Update_Forecast")
public class Update_Forecast extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理利用POST方式传递回来的预测数据
        request.setCharacterEncoding("UTF-8");
        String acception = "";
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");

        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }

        br.close();
        acception = sb.toString();
        if (acception != "") {
            JSONArray jsonArray = JSONArray.fromObject(acception);
//            JSONObject jsonObject=JSONObject.fromObject(acception);
            try {
                DBconnection dBconnection = new DBconnection();
                //首先清空数据库内已经存在的数据
                dBconnection.DB_Del("truncate table flow_forecast;");
                //新增数据
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
                    String person_count = (String) jsonObject.get("value");
                    String record_date_time = (String) jsonObject.get("time");
                    String scenic_id = (String) jsonObject.get("scenic_id");
                    String record_date = "";
                    String record_time = "";
                    SimpleDateFormat srtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat Date_F = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat Date_E = new SimpleDateFormat("HH:mm:ss");
                    try {
                        Date date = srtFormat.parse(record_date_time);
                        record_date = Date_F.format(date);
                        record_time = Date_E.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                System.out.println(scenic_id+"  "+record_date+"  "+record_time+"  "+person_count);
                    dBconnection.DB_Add("insert into flow_forecast (record_date, record_time, person_count, scenic_id) VALUES ('"+record_date+"','"+record_time+"','"+person_count+"','"+scenic_id+"');");
                }
                dBconnection.FreeResource();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //预测数据模块将会使用Get方式获取到数据库中已存储的数据
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();
        if(Integer.parseInt(request.getParameter("forecast"))==109){
            //返回景区景点历史人流量数据
            try {
                DBconnection dBconnection=new DBconnection();
                ResultSet resultSet = dBconnection.DB_FindDataSet("select * from flow_actual_time");
                int scenic_temp_index=0;
                JSONObject jsonObject=new JSONObject();//用于存储数据库中单项数据
                JSONArray jsonArray=new JSONArray();//用于存储上述所有单项数据
                while (resultSet.next()){
                    jsonObject.put(resultSet.getString(5),resultSet.getString(4));
                    scenic_temp_index++;
                    if(scenic_temp_index==4){
                        jsonObject.put("record_date",resultSet.getString(2));
                        jsonObject.put("record_time",resultSet.getString(3));
                        jsonArray.add(jsonObject);
                        scenic_temp_index=0;
                    }
                }
                printWriter.println(jsonArray);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
