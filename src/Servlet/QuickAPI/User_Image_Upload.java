package Servlet.QuickAPI;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "User_Image_Upload")
public class User_Image_Upload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            response.setContentType("text/html;charset=utf-8");
//            创建DiskFileItemFactory工厂对象
            DiskFileItemFactory factory=new DiskFileItemFactory();
//            设置文件缓存目录，如果该文件夹不存在则创建一个
            File f=new File("C:\\UIUI");
            if (!f.exists()){
                System.out.println("文件夹不存在");
                f.mkdirs();
            }
            System.out.println("设置文件夹缓存目录");
            factory.setRepository(f);

            ServletFileUpload fileUpload=new ServletFileUpload(factory);
            System.out.println("创建ServletFileUpload对象");
//            设置字符编码
            fileUpload.setHeaderEncoding("utf-8");
//            解析request，将form表单的各个字段封装为FileItem对象
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            System.out.println("解析request，将form表单的各个字段封装为FileItem对象");
//            获取字符流
            PrintWriter writer=response.getWriter();
//            遍历List集合
            for (FileItem fileItem:fileItems) {
//            判断是否为普通字段
                if (fileItem.isFormField()){
                    System.out.println("判断为普通字段");
//                    获取字段名称
                    String name = fileItem.getFieldName();
                    if(name.equals("name")){
//                        如果字段值不为空
                        if (!fileItem.getString().equals("")){
                            String value=fileItem.getString("utf-8");
                            writer.print("上传者："+value+"<br />");
                        }
                    }
                }
                else {
                    System.out.println("判断为非普通字段");
                    //获取上传的文件名
                    String filename=fileItem.getName();
//                    处理上传文件
                    if(filename!=null&&filename!=""){
                        System.out.print("上传的文件名称是："+filename+"");
//                        保持文件名唯一
                        filename= UUID.randomUUID().toString()+"_"+filename;
                        String webpath="/upload/";
//                        创建文件路径
                        String filepath=getServletContext().getRealPath(webpath+filename);
                        System.out.println("文件路径是"+filepath);
                        //创建File对象
                        File file=new File(filepath);
                        //创建文件夹
                        file.getParentFile().mkdirs();
                        //创建文件
                        file.createNewFile();
                        //获取上传文件流
                        InputStream in=fileItem.getInputStream();
//                        使用 FileOutputStream打开服务器端的上传文件
                        FileOutputStream out=new FileOutputStream(file);
//                        流的对拷
                        byte[] bytes=new byte[1024];//每次读取一个字节
                        int len;
//                        开始读取上传文件的字节，并将其输出到服务器端的上传文件输出流中
                        while ((len=in.read(bytes))>0)
                            out.write(bytes,0,len);
                        in.close();
                        out.close();
                        fileItem.delete();
                        writer.print("文件上传成功！");
                    }
                }


            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
