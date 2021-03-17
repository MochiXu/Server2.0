package Servlet.Index;


import Database.DBconnection;
import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static Servlet.Config.Record_Interval;
// 首页四张chart图表内容
@WebServlet(name = "Servlet_index_charts")
public class Servlet_index_charts extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter=response.getWriter();
        switch (Integer.parseInt(request.getParameter("chart"))){
            case 1:
                try {
                    printWriter.println(get_chart1_Json());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    printWriter.println(get_chart2_Json());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    printWriter.println(get_chart3_Json());
                } catch (SQLException | ClassNotFoundException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    printWriter.println(get_chart4_Json());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }



    }
    private JSONArray get_chart1_Json() throws SQLException, ClassNotFoundException {
        //声明json对象
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("call get_Json_chart1();\n");
        while (resultSet01.next()){
            jsonObj.put("scenic_id",resultSet01.getString(1));
            jsonObj.put("scenic_name",resultSet01.getString(2));
            jsonObj.put("person_count",resultSet01.getString(3));
            jsonArray.add(jsonObj);
        }
        dBconnection01.FreeResource();
        System.out.println("更新了chart1");
        return jsonArray;
    }
    private JSONArray get_chart2_Json() throws SQLException, ClassNotFoundException {
        //声明json对象
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        //随机获取到景区四个景点
        String[] Scenic_name=new String[4];
        String[] Scenic_id=new String[4];

        //连接数据库获取景点数据--这里是随机选取的四个景点【能够保证景点排列顺序固定】
        DBconnection dBconnection02=new DBconnection();
        ResultSet resultSet02=dBconnection02.DB_FindDataSet("select * from(SELECT scenic_info.scenic_id,scenic_name FROM scenic_info where scenic_id!='scenic' ORDER BY RAND() LIMIT 4) as sisn order by scenic_id;\n");
        int index=0;
        while (resultSet02.next()){
            Scenic_id[index]=resultSet02.getString(1);
            Scenic_name[index]=resultSet02.getString(2);
            index++;
        }
        dBconnection02.FreeResource();

        //连接数据库获取对应景点内游客游玩时长及人数
        for(int i=0;i<4;i++) {
            DBconnection dBconnection02_02 = new DBconnection();
            ResultSet resultSet02_02 = dBconnection02_02.DB_FindDataSet("call get_Json_chart2('"+Scenic_id[i]+"');\n");
            //下标0，1，2，3分别对应<1h，1h~2h，2h~3h，>3h
            int[] interval=new int[]{0,0,0,0};
            while (resultSet02_02.next()){
//                System.out.println(resultSet02_02.getInt(3));
                if(resultSet02_02.getInt(3)<60){
                    interval[0]++;
                }
                if(resultSet02_02.getInt(3)>=60&&resultSet02_02.getInt(3)<120){
                    interval[1]++;
                }
                if(resultSet02_02.getInt(3)>=120&&resultSet02_02.getInt(3)<180){
                    interval[2]++;
                }
                if(resultSet02_02.getInt(3)>=180){
                    interval[3]++;
                }
            }
            //System.out.println(Scenic_name[i]+" "+interval[0]+" "+interval[1]+" "+interval[2]+" "+interval[3]);
            jsonObj.put("scenic_name",Scenic_name[i]);
            jsonObj.put("h01",interval[0]);
            jsonObj.put("h02",interval[1]);
            jsonObj.put("h03",interval[2]);
            jsonObj.put("h04",interval[3]);
            jsonArray.add(jsonObj);
            dBconnection02_02.FreeResource();
        }
//        System.out.println(jsonArray);
        System.out.println("更新了chart2");
        return jsonArray;
    }

    private JSONArray get_chart3_Json() throws SQLException, ClassNotFoundException, ParseException {
        //声明Json对象
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        //获取到scenic_person_sum中的最新人流记录日期
        String latest_date="";
        DBconnection dBconnection003=new DBconnection();
        ResultSet resultSet003=dBconnection003.DB_FindDataSet("select max(record_date) from scenic_person_sum;\n");
        while(resultSet003.next()){
            latest_date=resultSet003.getString(1);
        }
        dBconnection003.FreeResource();
        //将时间往前推7天
        //获取30天之前的时间
        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Date_Format.parse(latest_date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date day7=calendar.getTime();
        String Day7=Date_Format.format(day7);
        //获取对应时间段内的图表数据
        DBconnection dBconnection03=new DBconnection();
        ResultSet resultSet03=dBconnection03.DB_FindDataSet("call get_Json_chart3('"+Day7+"');");
        while (resultSet03.next()){
            jsonObj.put("date",resultSet03.getString(1));
            jsonObj.put("peak_num",resultSet03.getString(2));
            jsonArray.add(jsonObj);
        }
        dBconnection03.FreeResource();
        System.out.println("更新了chart3");
        return jsonArray;
    }
    private String get_SevenDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 7);
        Date time = c.getTime();
        String preDay = sdf.format(time);
        //System.out.println(preDay);
        return preDay;
    }
    private JSONArray get_chart4_Json() throws SQLException, ClassNotFoundException {
        //声明json对象
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        DBconnection dBconnection04=new DBconnection();
        ResultSet resultSet01=dBconnection04.DB_FindDataSet("call get_Json_chart4();\n");
        while (resultSet01.next()){
            jsonObj.put("help_count",resultSet01.getString(1));
            jsonObj.put("scenic_name",resultSet01.getString(2));
            jsonArray.add(jsonObj);
        }
        dBconnection04.FreeResource();
        System.out.println("更新了chart4");
        return jsonArray;
    }
}
