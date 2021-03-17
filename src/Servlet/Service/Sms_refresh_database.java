package Servlet.Service;

import Utilities.Service.Del_sms_verification_TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;

import static Servlet.Config.Record_Interval;
import static Servlet.Config.SMS_DEL_Interval;
//系统延迟5分钟开始清空数据库内的验证码，在后续的运行期间每隔5分钟继续清空一次
@WebServlet(name = "Sms_refresh_database")
public class Sms_refresh_database extends HttpServlet {

    @Override
    public void init() throws ServletException {
        //自动执行，每隔一定的时间清空数据库内暂存的用户验证码
        Del_sms_verification_TimerTask timerTask=new Del_sms_verification_TimerTask();
        Timer timer = new Timer();
//        long delay = 600 * 1000;//延迟10分钟启动task
//        long intevalPeriod = 600 * 1000;//任务执行间隔为10分钟

        long delay =SMS_DEL_Interval;//延迟ns启动任务
        long intevalPeriod = SMS_DEL_Interval;//任务执行间隔为ns
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(timerTask, delay, intevalPeriod);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
