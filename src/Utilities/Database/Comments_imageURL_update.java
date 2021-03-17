package Utilities.Database;

import Database.DBconnection;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
//同时更新评论以及人流预测的图片
public class Comments_imageURL_update extends TimerTask {
    @Override
    public void run() {
        System.out.println("Comments_imageURL_update");
        try {
            Update_CommentsImage();
            Update_ForecastImage();
            Update_ScenicImage();
            Update_SelectedImage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void Update_CommentsImage() throws SQLException, ClassNotFoundException {
        //刷新OSS内所有评论图片的链接
        List<String> file_key=new ArrayList<>();//用于保存数据库内所有图片文件的OSS路径
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("select image_key from person_comments_image;\n");
        while (resultSet01.next()){
            file_key.add(resultSet01.getString(1));
        }
        dBconnection01.FreeResource();
        //建立对象存储链接
        OSS ossClient = new OSSClientBuilder().build("http://oss-cn-qingdao.aliyuncs.com", "LTAI4FkTWtwLarvzh9MzSDDF", "FdIlqND5srRerHep8H2wqNuJqW8oeG");
        //对所有file_key进行链接更新
        for (String s : file_key) {
            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl("deeppupil", s.toString(), expiration);
            System.out.println("person_comments_image文件链接地址如下\n" + url.toString());
            //将新的url存储入数据库
            DBconnection dBconnection02 = new DBconnection();
            dBconnection02.DB_Update("update person_comments_image set image_url='" + url.toString() + "' where image_key='" + s.toString() + "';\n");
            dBconnection02.FreeResource();
        }
        ossClient.shutdown();
    }
    private void Update_ForecastImage() throws SQLException, ClassNotFoundException {
        List<String> file_key=new ArrayList<>();
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("select image_key from scenic_flow_image;\n");
        while (resultSet01.next()){
            file_key.add(resultSet01.getString(1));
        }
        dBconnection01.FreeResource();
        //建立对象存储链接
        OSS ossClient = new OSSClientBuilder().build("http://oss-cn-qingdao.aliyuncs.com", "LTAI4FkTWtwLarvzh9MzSDDF", "FdIlqND5srRerHep8H2wqNuJqW8oeG");
        //对所有file_key进行链接更新
        for (String s : file_key) {
            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl("deeppupil", s.toString(), expiration);
            System.out.println("scenic_flow_image文件链接地址如下\n" + url.toString());
            //将新的url存储入数据库
            DBconnection dBconnection02 = new DBconnection();
            dBconnection02.DB_Update("update scenic_flow_image set image_url='" + url.toString() + "' where image_key='" + s.toString() + "';\n");
            dBconnection02.FreeResource();
        }
        ossClient.shutdown();
    }
    private void Update_ScenicImage() throws SQLException, ClassNotFoundException {
        List<String> file_key=new ArrayList<>();
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("select image_key from scenic_introduce_images;\n");
        while (resultSet01.next()){
            file_key.add(resultSet01.getString(1));
        }
        dBconnection01.FreeResource();
        //建立对象存储链接
        OSS ossClient = new OSSClientBuilder().build("http://oss-cn-qingdao.aliyuncs.com", "LTAI4FkTWtwLarvzh9MzSDDF", "FdIlqND5srRerHep8H2wqNuJqW8oeG");
        //对所有file_key进行链接更新
        for (String s : file_key) {
            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl("deeppupil", s.toString(), expiration);
            System.out.println("scenic_introduce_images文件链接地址如下\n" + url.toString());
            //将新的url存储入数据库
            DBconnection dBconnection02 = new DBconnection();
            dBconnection02.DB_Update("update scenic_introduce_images set image_url='" + url.toString() + "' where image_key='" + s.toString() + "';\n");
            dBconnection02.FreeResource();
        }
        ossClient.shutdown();
    }
    private void Update_SelectedImage() throws SQLException, ClassNotFoundException {
        List<String> file_key=new ArrayList<>();
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("select image_key from scenic_selected_images;");
        while (resultSet01.next()){
            file_key.add(resultSet01.getString(1));
        }
        dBconnection01.FreeResource();
        //建立对象存储链接
        OSS ossClient = new OSSClientBuilder().build("http://oss-cn-qingdao.aliyuncs.com", "LTAI4FkTWtwLarvzh9MzSDDF", "FdIlqND5srRerHep8H2wqNuJqW8oeG");
        //对所有file_key进行链接更新
        for (String s : file_key) {
            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl("deeppupil", s.toString(), expiration);
            System.out.println("scenic_sel_images文件链接地址如下\n" + url.toString());
            //将新的url存储入数据库
            DBconnection dBconnection02 = new DBconnection();
            dBconnection02.DB_Update("update scenic_selected_images set image_url='" + url.toString() + "' where image_key='" + s.toString() + "';\n");
            dBconnection02.FreeResource();
        }
        ossClient.shutdown();
    }

}
