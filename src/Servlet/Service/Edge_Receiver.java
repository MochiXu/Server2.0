package Servlet.Service;

import Database.DBconnection;
import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;

import javax.imageio.stream.FileImageInputStream;
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

//接收边缘计算端发送的数据
@WebServlet(name = "Edge_Receiver")
public class Edge_Receiver extends HttpServlet {
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

//        System.out.println(acception);
        if (!acception.equals("")) {
            JSONObject jsonObject = JSONObject.fromObject(acception);
            //存储图片
            String time_stamp= String.valueOf(System.currentTimeMillis());//获取时间戳
            byte[] bytes = UtilHelper.base64String2ByteFun(jsonObject.get("img")+"");
            FileOutputStream out = new FileOutputStream("C:\\EdgeImage\\"+time_stamp+".jpg");
            System.out.println("接收到边缘端上传的图片");
            out.write(bytes);
            out.close();
            //写入数据至数据库
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            try {
                DBconnection dBconnection=new DBconnection();
                dBconnection.DB_Add("insert into edge_receive(lng, lat, image_source, person_count, process_status, record_date, record_time, device_id) VALUES ('"+jsonObject.get("lng")+"','"+jsonObject.get("lat")+"','"+"C:/EdgeImage/"+time_stamp+".jpg"+"','0','0','"+dateFormat.format(date)+"','"+timeFormat.format(date)+"','"+jsonObject.get("device_id")+"');");
                dBconnection.FreeResource();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/json;charset=utf-8");
        //实例化输出流对象
        PrintWriter printWriter = response.getWriter();
        if(request.getParameter("type").equals("getData")) {
            System.out.println("接收到来自服务器B的人体检测程序请求");
            //将数据库中待检测的图片封装为JSON数据
            JSONArray jsonArray = new JSONArray();
            try {
                DBconnection dBconnection = new DBconnection();
                ResultSet resultSet = dBconnection.DB_FindDataSet("select edge_receive.image_source,edge_receive.edge_receive_pk from edge_receive where edge_receive.process_status=0;");
                System.out.println("正在将边缘端图片回传给服务器B");
                while (resultSet.next()) {
                    JSONObject jsonObject = new JSONObject();
                    //获取图片路径
                    String path = resultSet.getString(1);
                    String base64str = imageToBase64String(path);
                    jsonObject.put("img", base64str);
                    jsonObject.put("id", resultSet.getString(2));
                    System.out.println("待检测的图像id为:"+resultSet.getString(2));
//                    System.out.println("待检测的图像base64为:"+base64str+"\n");
                    jsonArray.put(jsonObject);
                }
                dBconnection.FreeResource();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            printWriter.println(jsonArray);
        }



    }

    public static class UtilHelper {
        //base64字符串转byte[]
        public static byte[] base64String2ByteFun(String base64Str){
            return Base64.decodeBase64(base64Str);
        }
        //byte[]转base64
        public static String byte2Base64StringFun(byte[] b){
            return Base64.encodeBase64String(b);
        }
    }

    public String imageToBase64String(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        String str=Base64.encodeBase64String(data);
//        System.out.println(str);
        return str;
    }
}
