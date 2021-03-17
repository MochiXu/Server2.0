package Servlet.Service;

import Utilities.Service.OSSprocess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Forecast_upload")
public class Forecast_upload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();
        String Path=request.getParameter("path");//获取图片的路径
        String Scenic=request.getParameter("scenic");//获取图片的景点id
        String Date=request.getParameter("date");//获取图片的日期
        System.out.println(Date+"---"+Scenic+":"+Path);
        OSSprocess osSprocess=new OSSprocess();
        osSprocess.UpLoadImage2(Path,Scenic,Date);
    }
}
