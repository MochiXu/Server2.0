package Servlet.Database;

import Utilities.Database.Comments_imageURL_update;
import Utilities.Database.Flow_actual_time_update_TimerTask;
import Utilities.Database.Scenic_flow_update;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;

import static Servlet.Config.Record_Interval;
//用于更新flow_actual_time表内数据,每十分钟刷新一次各个景点的实时客流量
//用于更新comments_image表内数据，每5小时刷新一次各个评论id下的图片链接

@WebServlet(name = "Update_Data")
public class Update_Data extends HttpServlet {
    @Override
    public void init() throws ServletException {
        Flow_actual_time_update_TimerTask task=new Flow_actual_time_update_TimerTask();
        Comments_imageURL_update task2=new Comments_imageURL_update();
        Scenic_flow_update task3=new Scenic_flow_update();

        Timer timer = new Timer();
        Timer timer2 = new Timer();
        Timer timer3=new Timer();
//        long delay = 600 * 1000;//延迟10分钟启动task
//        long intevalPeriod = 600 * 1000;//任务执行间隔为10分钟

        long delay =Record_Interval;//延迟ns启动任务
        long intevalPeriod = Record_Interval;//任务执行间隔为ns
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
        timer2.scheduleAtFixedRate(task2, 0, 18000000);
        timer3.scheduleAtFixedRate(task3,0,5000);//每5s更新一次scenic——flow
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
