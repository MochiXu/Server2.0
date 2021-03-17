package Servlet.Service;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(name = "Person_flow_charts")
public class Person_flow_charts extends HttpServlet {
    //存储数据，用于python端生成图片
    JSONArray jsonArray=new JSONArray();
    List<String> time_list = new ArrayList<>() {{add("8:00:00");add("9:00:00");add("10:00:00");add("11:00:00");add("12:00:00");add("13:00:00");add("14:00:00");add("15:00:00");add("16:00:00");add("17:00:00");add("18:00:00");add("19:00:00");add("20:00:00");}};

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();

        //当前数据库内的人流统计的截断时间
        String latest_date="";
        String latest_time="";

        //4天之前的日期
        String fourDays_ago="";
        //1天之前的日期
        String oneDays_ago="";
        //2天之前的日期
        String twoDays_ago="";
        //3天之前的日期
        String threeDays_ago="";
        try {
            DBconnection dBconnection=new DBconnection();
            ResultSet resultSet=dBconnection.DB_FindDataSet("select record_date,max(record_time) from flow_actual_time where record_date=(select max(flow_actual_time.record_date) from flow_actual_time)\n");
            while (resultSet.next()){
                latest_date=resultSet.getString(1);
                latest_time=resultSet.getString(2);
            }
            dBconnection.FreeResource();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
            Date today = Date_Format.parse(latest_date);
            //获取4天之前的时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_MONTH, -4);
            Date day4=calendar.getTime();
            fourDays_ago=Date_Format.format(day4);
            //获取1天前的时间
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(today);
            calendar2.add(Calendar.DAY_OF_MONTH, -1);
            Date day1=calendar2.getTime();
            oneDays_ago=Date_Format.format(day1);

            //获取2天前的时间
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(today);
            calendar3.add(Calendar.DAY_OF_MONTH, -2);
            Date day3=calendar3.getTime();
            twoDays_ago=Date_Format.format(day3);
            //获取3天前的时间
            Calendar calendar4 = Calendar.getInstance();
            calendar4.setTime(today);
            calendar4.add(Calendar.DAY_OF_MONTH, -3);
            Date day=calendar4.getTime();
            threeDays_ago=Date_Format.format(day);
            //获取当前数据库中最新时间的前一小时
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }

        try {
            get_Part1_data(fourDays_ago);
            get_Part1_data(threeDays_ago);
            get_Part1_data(twoDays_ago);
            get_Part1_data(oneDays_ago);
            get_Part2_data(latest_date,latest_time);
            get_Part3_data();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        printWriter.println(jsonArray);
        jsonArray=new JSONArray();

    }
    private void get_Part1_data(String date_in) throws SQLException, ClassNotFoundException {
        //获取特定日期的全天景区人流量分布数据
        int time_index=0;
        while (!time_list.get(time_index).equals("20:00:00")){
            DBconnection dBconnection=new DBconnection();
            String sql="select cast(sum(person_count)/6 as SIGNED)  ,scenic_id from flow_actual_time\n" +
                    "where record_date='"+date_in+"' and record_time>='"+time_list.get(time_index)+"' and record_time <'"+time_list.get(time_index+1)+"'\n" +
                    "group by scenic_id";
            ResultSet resultSet=dBconnection.DB_FindDataSet(sql);
            while (resultSet.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("person_count",resultSet.getInt(1));
                jsonObject.put("scenic_id",resultSet.getString(2));
                jsonObject.put("date",date_in);
                jsonObject.put("time",time_list.get(time_index));
                jsonObject.put("forecast",0);
                jsonArray.put(jsonObject);
            }
            time_index++;
            dBconnection.FreeResource();
        }
    }
    private void get_Part2_data(String date_in,String time_in) throws SQLException, ClassNotFoundException {
        //截断当天的人流数据
        String[] strs=time_in.split(":");
        int i=0;
        while (!time_list.get(i).split(":")[0].equals(strs[0])){
            i++;
        }
        System.out.println("正在更新客流预测图表数据，截断时间"+time_list.get(i));//获取到了截断时间
        System.out.println("正在更新客流预测图表数据，截断时间"+time_list.get(i+1));//获取到了截断时间
        //获取当天的历史数据
        int time_index=0;
        while (!time_list.get(time_index).equals(time_list.get(i+1))){
            DBconnection dBconnection=new DBconnection();
            ResultSet resultSet=dBconnection.DB_FindDataSet("select cast(sum(person_count)/(select count(*)/4 from flow_actual_time as m where m.record_date='"+date_in+"' and m.record_time>='"+time_list.get(time_index)+"' and m.record_time <'"+time_list.get(time_index+1)+"') as SIGNED)  ,scenic_id from flow_actual_time\n" +
                    "where record_date='"+date_in+"' and record_time>='"+time_list.get(time_index)+"' and record_time <'"+time_list.get(time_index+1)+"'\n" +
                    "group by scenic_id;");
            while (resultSet.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("person_count",resultSet.getInt(1));
                jsonObject.put("scenic_id",resultSet.getString(2));
                jsonObject.put("date",date_in);
                jsonObject.put("time",time_list.get(time_index));
                jsonObject.put("forecast",0);
                jsonArray.put(jsonObject);
            }
            dBconnection.FreeResource();
            time_index++;
        }
        //获取当天的预测数据
        while (!time_list.get(time_index).equals("20:00:00")){
            DBconnection dBconnection=new DBconnection();
            String sql="select cast(sum(person_count)/6 as SIGNED)  ,scenic_id from flow_forecast\n" +
                    "where record_date='"+date_in+"' and record_time>='"+time_list.get(time_index)+"' and record_time <'"+time_list.get(time_index+1)+"'\n" +
                    "group by scenic_id";
            ResultSet resultSet=dBconnection.DB_FindDataSet(sql);
            while (resultSet.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("person_count",resultSet.getInt(1));
                jsonObject.put("scenic_id",resultSet.getString(2));
                jsonObject.put("date",date_in);
                jsonObject.put("time",time_list.get(time_index));
                jsonObject.put("forecast",1);
                jsonArray.put(jsonObject);
            }
            time_index++;
            dBconnection.FreeResource();
        }
    }
    private void get_Part3_data() throws SQLException, ClassNotFoundException {
        //获取未来两天的景区人流量预测数据
        //数据库内预测数据的最大时间
        String forecast_max_time="";
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("select max(record_time) from flow_forecast where record_date=(select max(record_date) from flow_forecast)");
        while (resultSet01.next()){
            forecast_max_time=resultSet01.getString(1);
        }
        dBconnection01.FreeResource();
        String[] strs=forecast_max_time.split(":");
        System.out.println(strs[0]);
        int i=0;
        while (!time_list.get(i).split(":")[0].equals(strs[0])){
            i++;
        }
        System.out.println("get_Part3_data"+time_list.get(i));
        //数据库内预测数据的不同日期
        List<String> date_list = new ArrayList<>();
        DBconnection dBconnection02=new DBconnection();
        ResultSet resultSet02=dBconnection02.DB_FindDataSet("select distinct record_date from flow_forecast where record_date>(select max(record_date) from flow_actual_time)\n");
        while (resultSet02.next()){
            date_list.add(resultSet02.getString(1));
        }
        dBconnection02.FreeResource();
        //对除了最后一天的数据之前的所有数据进行处理
        int date_index=0;
        while (date_index!=(date_list.size()-1)){
            int time_index=0;
            while (!time_list.get(time_index).equals("20:00:00")){
                DBconnection dBconnection=new DBconnection();
                String sql="select cast(sum(person_count)/6 as SIGNED)  ,scenic_id from flow_forecast\n" +
                        "where record_date='"+date_list.get(date_index)+"' and record_time>='"+time_list.get(time_index)+"' and record_time <'"+time_list.get(time_index+1)+"'\n" +
                        "group by scenic_id";
                ResultSet resultSet=dBconnection.DB_FindDataSet(sql);
                while (resultSet.next()){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("person_count",resultSet.getInt(1));
                    jsonObject.put("scenic_id",resultSet.getString(2));
                    jsonObject.put("date",date_list.get(date_index));
                    jsonObject.put("time",time_list.get(time_index));
                    jsonObject.put("forecast",1);
                    jsonArray.put(jsonObject);
                }
                time_index++;
                dBconnection.FreeResource();
            }
            date_index++;
        }
        //对最后一天的数据进行处理
        int time_index=0;
        while (!time_list.get(time_index).equals(time_list.get(i+1))){
            DBconnection dBconnection=new DBconnection();
            String sql="select cast(sum(person_count)/(select count(*)/4 from flow_forecast as m where m.record_date='"+date_list.get(date_list.size()-1)+"' and m.record_time>='"+time_list.get(time_index)+"' and m.record_time <'"+time_list.get(time_index+1)+"') as SIGNED)  ,scenic_id from flow_forecast\n" +
                    "where record_date='"+date_list.get(date_list.size()-1)+"' and record_time>='"+time_list.get(time_index)+"' and record_time <'"+time_list.get(time_index+1)+"'\n" +
                    "group by scenic_id;";
            ResultSet resultSet=dBconnection.DB_FindDataSet(sql);
            while (resultSet.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("person_count",resultSet.getInt(1));
                jsonObject.put("scenic_id",resultSet.getString(2));
                jsonObject.put("date",date_list.get(date_list.size()-1));
                jsonObject.put("time",time_list.get(time_index));
                jsonObject.put("forecast",1);
                jsonArray.put(jsonObject);
            }
            dBconnection.FreeResource();
            time_index++;
        }

    }
}
