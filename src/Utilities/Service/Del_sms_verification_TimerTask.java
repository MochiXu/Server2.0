package Utilities.Service;

import Database.DBconnection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import static Servlet.Config.SMS_DEL_Interval;
//清空数据库中5分钟之内存储的短信验证码
public class Del_sms_verification_TimerTask extends TimerTask {
    @Override
    public void run() {
        try {
            Date now = new Date();
            Date old=new Date(now.getTime()-SMS_DEL_Interval);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");//可以方便地修改日期格式
            String date_now = dateFormat.format(now);//当前日期
            String time_old = timeFormat.format(old);//当前时间
            //System.out.println("当前日期："+date_now+"   "+"历史时间："+time_old);
            DBconnection dBconnection=new DBconnection();
            dBconnection.DB_Del("call del_Sms_verification('"+time_old+"','"+date_now+"');\n");
            dBconnection.FreeResource();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
