package Servlet.Service;

import Database.DBconnection;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Servlet.Config.SMS_DEL_Interval;

@WebServlet(name = "Sms_code")
public class Sms_send_code extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        String person_tel=request.getParameter("tel");

        //生成随机验证码
        int max=9999,min=1000;
        int code = (int) (Math.random()*(max-min)+min);
//        System.out.println("产生的验证码为："+code);

        //计算验证码有效时间
        int effective_time=SMS_DEL_Interval/60000;

        //发送短信
        int appid = 1400308601;
        String appkey = "b7b19834cad283c60bf88b9fb81fe535";
        String[] phoneNumbers = {person_tel}; //手机号可以添很多。
        int templateId = 526927;
        String smsSign = "立方体X";
        try {
            String[] params = {code+"",effective_time+""};  //第一个参数传递{1}位置想要的内容，第二个传递{2}的内容，以此类推。具体看步骤5
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],templateId, params, smsSign, "", "");
            System.out.println("发送动态验证码"+result);
        } catch (HTTPException | JSONException | IOException e) {
            e.printStackTrace();
        }

        //更新数据库内容
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");//可以方便地修改日期格式
        String date_now = dateFormat.format(now);//当前日期
        String time_now = timeFormat.format(now);//当前时间

        try {
            DBconnection dBconnection=new DBconnection();
            dBconnection.DB_Add("insert into sms_verification (person_tel, sms_code, record_date, record_time) VALUES ('"+person_tel+"','"+code+"','"+date_now+"','"+time_now+"');\n");
            dBconnection.FreeResource();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
