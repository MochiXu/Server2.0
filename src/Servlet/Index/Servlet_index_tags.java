package Servlet.Index;

import DataProcess.Data_index;
import Database.DBconnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Servlet.Config.Dangerous_Count;

@WebServlet(name = "Servlet_index")
public class Servlet_index_tags extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //response.setContentType("text/json;charset=utf-8");

        Data_index data_index=new Data_index();
        // 判断前台获取数据的类型【标签】
        String tag=request.getParameter("tag");
        switch (Integer.parseInt(tag)){
            case 1:
                // 人流量峰值
                try {
                    System.out.println("回传tag-客流峰值");
                    response.getWriter().print(data_index.Tag_flow_peak()+"");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                // 实时人流量
                try {
                    System.out.println("回传tag-实时客流量");
                    response.getWriter().print(data_index.Tag_flow_actual()+"");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                // 快应用并发峰值
                response.getWriter().print("1");
                System.out.println("回传tag-模拟快应用并发量");
                break;
            case 4:
                // 活跃的快应用终端（台）
                response.getWriter().print("1");
                System.out.println("回传tag-快应用终端数量");
                break;
            case 5:
                // 今日游客误入禁区次数
                try {
                    response.getWriter().print(get_dangerH_count());
                    System.out.println("回传tag-误入禁区次数");
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case 6:
                // 违禁区域内游客实时数量
                try {
                    response.getWriter().print(get_danger_count());
                    System.out.println("回传tag-违禁区域游客数量");
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }
    private int get_danger_count() throws SQLException, ClassNotFoundException {
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select count(*) from dangerous_person");
        int count=0;
        while(resultSet.next()){
            count=resultSet.getInt(1);
        }
        dBconnection.FreeResource();
        return count;
    }

    private int get_dangerH_count() throws SQLException, ClassNotFoundException {
        DBconnection dBconnection=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select danger_count_history.count from danger_count_history where record_date=(select max(record_date) from danger_count_history);");
        int count=0;
        while(resultSet.next()){
            count=resultSet.getInt(1);
        }
        dBconnection.FreeResource();
        return count;
    }
}
