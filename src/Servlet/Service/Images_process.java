package Servlet.Service;

import Utilities.Service.OSSprocess;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@WebServlet(name = "Images_process")
public class Images_process extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //转到doGet
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //不可以在接受图片流之前对request进行访问，这样可以概率性的干扰字节流的传输
//        String comments_id = request.getParameter("comments_id");//用户评论的id
//        System.out.println(comments_id);
        String path="C:\\"+"comments_factory";
        savePicByIo(request,path);
    }
    public void savePicByIo(HttpServletRequest request, String path) throws IOException {

        // 判断是否有路径
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        System.out.println("文件路径OK");
        ServletInputStream inputStream = request.getInputStream();
        String fileName = UUID.randomUUID().toString().replace("-","") + ".jpg";
        File tempFile = new File(path,fileName);
        if (!tempFile.exists()) {
            OutputStream os = new FileOutputStream(tempFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            byte[] buf = new byte[1024];
            int length;
            length = inputStream.read(buf,0,buf.length);
            while (length != -1) {
                bos.write(buf, 0 , length);
                length = inputStream.read(buf);
            }
            bos.close();
            os.close();
            inputStream.close();
        }
        //接受完毕图片之后将此图片传递至OSS
        OSSprocess oss=new OSSprocess();
        oss.UpLoadImage(path+"\\"+fileName,request.getParameter("comments_id"), String.valueOf(System.currentTimeMillis()));
    }

}
