package Utilities.Database;

import Database.DBconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import static Servlet.Config.Record_Interval;

//用于更新flow_actual_time表内数据
//这里将循环间隔十分钟统计各景点内的人流总数，统计日期并非当前系统日期，而是数据库内的最新日期。
public class Flow_actual_time_update_TimerTask extends TimerTask {
    @Override
    public void run() {
        //run my timer task
        try {
            Update();
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void Update() throws SQLException, ClassNotFoundException, ParseException {
        List<String> scenic_id = new ArrayList<>();//用于记录特定时间间隔内scenic_flow内出现的景区id
        int person_sum = 0;//景点内游客总数

        DBconnection dBconnection01 = new DBconnection();
        ResultSet resultSet01 = dBconnection01.DB_FindDataSet("call get_ScenicFlowScenicID('" + getBeginTime() + "');");
        //获取到系统前十分钟内的景区id号
        while (resultSet01.next()) {
            scenic_id.add(resultSet01.getString(1));
        }
        dBconnection01.FreeResource();

        //最后写入数据库的日期以后端为标准，每次间隔十分钟更新各景点人群数目以及整个景区的人群数目
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");//可以方便地修改日期格式
        String date_now = dateFormat.format(now);//当前日期
        String time_now = timeFormat.format(now);//当前时间


        //更新数据库内flow_actual_time表内容
        for (int i = 0; i < scenic_id.size(); i++) {
            //利用景区id获取到此景区内的客流数目
            int temp = getPersonCountFromScenicId(scenic_id.get(i));
            DBconnection dBconnection02 = new DBconnection();
            dBconnection02.DB_Add("insert into flow_actual_time (record_date, record_time, person_count, scenic_id) VALUES ('" + date_now + "','" + time_now + "'," + temp + ",'" + scenic_id.get(i) + "')");
            dBconnection02.FreeResource();
            person_sum += temp;
        }

        //更新数据库内scenic_person_sum的内容
        DBconnection dBconnection03 = new DBconnection();
        dBconnection03.DB_Add("insert into scenic_person_sum (record_date, record_time, person_sum) values ('" + date_now + "','" + time_now + "'," + person_sum + ")");
        dBconnection03.FreeResource();
    }

    //输入scenic_id，返回此景点的人数
    private int getPersonCountFromScenicId(String scenic_id) throws SQLException, ClassNotFoundException, ParseException {
        List<String> device_id = new ArrayList<>();//用于记录特定景区下活跃的设备id
        List<Double> round_time = new ArrayList<>();//用于记录设备id对应的记录周期
        List<Integer> person_AllCount = new ArrayList<>();//记录特定时间间隔内的人群数目
        List<Double> record_frequency = new ArrayList<>();//用于计算设备记录一整个周期的次数
        List<Integer> person_Average = new ArrayList<>();//记录特定时间间隔内设备捕获到的人群平均数目
        int person_count = 0;

        //获取景区下所有设备id
        DBconnection dBconnection01 = new DBconnection();
        ResultSet resultSet01 = dBconnection01.DB_FindDataSet("call get_ScenicFlowDeviceIdFromScenicID('" + getBeginTime() + "','" + scenic_id + "');");
        while (resultSet01.next()) {
            device_id.add(resultSet01.getString(1));
            //System.out.println("景区id:"+scenic_id+"设备编号:"+resultSet01.getString(1));
        }
        dBconnection01.FreeResource();

        //获取当前景点内不同设备id对应的运行周期
        for (int i = 0; i < device_id.size(); i++) {
            DBconnection dBconnection02 = new DBconnection();
            ResultSet resultSet02 = dBconnection02.DB_FindDataSet("call get_DeviceRoundTime('" + device_id.get(i) + "','" + scenic_id + "');");
            while (resultSet02.next()) {
                round_time.add(resultSet02.getDouble(1));
                //System.out.println("设备编号:"+device_id.get(i)+" 运行周期:"+round_time.get(i));
            }
            dBconnection02.FreeResource();
        }

        //获取设备在特定时间间隔内记录下来的人群总数
        for (int i = 0; i < device_id.size(); i++) {
            DBconnection dBconnection03 = new DBconnection();
            ResultSet resultSet03 = dBconnection03.DB_FindDataSet("call get_ScenicPersonAllCount_DeviceID('" + getBeginTime() + "', '" + device_id.get(i) + "','" + scenic_id + "');");
            while (resultSet03.next()) {
                person_AllCount.add(resultSet03.getInt(1));
                //System.out.println("设备编号:"+device_id.get(i)+" 人群总数:"+person_AllCount.get(i));
            }
            dBconnection03.FreeResource();
        }

        //计算各个设备工作的频次
        for (int i = 0; i < device_id.size(); i++) {
            double temp_work_count = Record_Interval / round_time.get(i);
            record_frequency.add(temp_work_count);
            //System.out.println("设备编号:"+device_id.get(i)+" 工作频次:"+record_frequency.get(i));

        }

        //计算各个设备在各自周期内计算出来的人群平均数量
        for (int i = 0; i < device_id.size(); i++) {
            Double count = Double.valueOf(person_AllCount.get(i)) / record_frequency.get(i);
            person_Average.add(count.intValue());
            //System.out.println("设备编号:"+device_id.get(i)+" 人群平均数量:"+person_Average.get(i));

        }

        //累加求和
        for (int i = 0; i < person_Average.size(); i++) {
            person_count += person_Average.get(i);
        }
        return person_count;
    }

    private String getBeginTime() throws SQLException, ClassNotFoundException, ParseException {
        //获取系统当前时间的前n分钟时间戳--这里以系统内的最新日期和最新时间为标准
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select max(record_time) from scenic_flow where record_date=(select max(record_date) from scenic_flow);");
        String max_time="";
        while(resultSet.next()){
            max_time=resultSet.getString(1);
        }
        dBconnection.FreeResource();
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
        Date current = time_format.parse(max_time);
        Date begin = new Date(current.getTime() - Record_Interval); //数据更新时间间隔
        String time_begin = time_format.format(begin);//记录开始时间
        return time_begin;
    }
}
