package Servlet.Service;

import Database.DBconnection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

@WebServlet(name = "Detect_People")
public class Detect_People extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acception = "";
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        acception = sb.toString();

        System.out.println(acception);
        if (!acception.equals("")) {
            JSONArray jsonArray = JSONArray.fromObject(acception);
            try {
                DBconnection dBconnection=new DBconnection();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
                    int person_count = (int) jsonObject.get("person_count");
                    int img_id = (int) jsonObject.get("img_id");
                    System.out.println("img_id:"+img_id+" person_count:"+person_count);

                    dBconnection.DB_Update("update edge_receive set edge_receive.person_count="+person_count+",edge_receive.process_status=1 where edge_receive_pk="+img_id+";");
                }
                dBconnection.FreeResource();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
