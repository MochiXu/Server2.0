package Servlet.Service;

import Database.DBconnection;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

//接收用户的评论
//用户给予的参数--评论id、用户id、tel、经纬度坐标、景点id、图片流、评论文字、星级评分、用户上传图片个数
//其中经纬度坐标以及景点id可能均为空值
@WebServlet(name = "Comments_receive")
public class Comments_receive extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //转到doGet
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();

        String comments_id="";
        String user_id="";//用户id
        double user_lng=0,user_lat=0;//用户经纬度
        String scenic_id="";//用户所评分的景点
        String user_comments="";//用户评论
        int user_star=0;//用户评分星级
        int image_num=0;//用户上传图片个数

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();

        //若参数内含有用户所评分的景点，则忽略用户经纬度
        //当缺失用户评分的景点信息，但是具有经纬度时，则根据经纬度判定用户所属景点
        //当两者信息均缺失，默认景点id为“scenic”，快应用端加载用户评论的时候每个景点都会加载这个评论
        user_id=request.getParameter("user_id");
        comments_id=request.getParameter("comments_id");
        if(request.getParameter("user_lng")!=null)
            user_lng= Double.parseDouble(request.getParameter("user_lng"));
        if(request.getParameter("user_lat")!=null)
            user_lat= Double.parseDouble(request.getParameter("user_lat"));
        if(request.getParameter("scenic_id")!=null)
            scenic_id=request.getParameter("scenic_id");
        user_comments=request.getParameter("user_comments");
        if(request.getParameter("user_star")!=null)
            user_star= Integer.parseInt(request.getParameter("user_star"));
        if(request.getParameter("image_num")!=null)
            image_num= Integer.parseInt(request.getParameter("image_num"));

        //准备存储数据
        if(request.getParameter("scenic_id")!=null){
            //直接存储
            try {
                DBconnection dBconnection01=new DBconnection();
//                dBconnection01.DB_Add("insert into person_comments (comments_id, user_id, user_lng, user_lat, scenic_id, user_comments, user_star, image_num,com_date,com_time) VALUES (\""+comments_id+"\",\""+user_id+"\","+user_lng+","+user_lat+",\""+scenic_id+"\",\""+user_comments+"\","+user_star+","+image_num+"\",\""+dateFormat.format(date)+"\",\""+timeFormat.format(date)+")");
                dBconnection01.DB_Add("insert into person_comments(comments_id, user_id, user_lng, user_lat, scenic_id, user_comments, user_star, image_num, com_date, com_time) VALUES ('"+comments_id+"','"+user_id+"','"+user_lng+"','"+user_lat+"','"+scenic_id+"','"+user_comments+"','"+user_star+"','"+image_num+"','"+dateFormat.format(date)+"','"+timeFormat.format(date)+"');");
                dBconnection01.FreeResource();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else if(request.getParameter("user_lat")!=null&&request.getParameter("user_lng")!=null){
            //判定此位置所在哪个景点的范围内，若不属于任何一个景点，默认景点id为scenic
            //边界归属判定
            String scenic_id_confirm="scenic";
            //具体判定过程没有写目前
            try {
                DBconnection dBconnection02=new DBconnection();
                dBconnection02.DB_Add("insert into person_comments(comments_id, user_id, user_lng, user_lat, scenic_id, user_comments, user_star, image_num, com_date, com_time) VALUES ('"+comments_id+"','"+user_id+"','"+user_lng+"','"+user_lat+"','"+scenic_id_confirm+"','"+user_comments+"','"+user_star+"','"+image_num+"','"+dateFormat.format(date)+"','"+timeFormat.format(date)+"');");
                dBconnection02.FreeResource();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else {
            //默认景点id是“scenic”，利用此id将以上数据存储到数据库
            try {
                DBconnection dBconnection03=new DBconnection();
                dBconnection03.DB_Add("insert into person_comments(comments_id, user_id, user_lng, user_lat, scenic_id, user_comments, user_star, image_num, com_date, com_time) VALUES ('"+comments_id+"','"+user_id+"','"+user_lng+"','"+user_lat+"','scenic','"+user_comments+"','"+user_star+"','"+image_num+"','"+dateFormat.format(date)+"','"+timeFormat.format(date)+"');");
                dBconnection03.FreeResource();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
