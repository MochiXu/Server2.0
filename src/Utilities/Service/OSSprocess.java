package Utilities.Service;

import Database.DBconnection;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
//上传评论图片
//上传景区图片
public class OSSprocess {
    private static String endpoint="";
    private static String accessKeyId ="";
    private static String accessKeySecret ="";
    private static String bucketName ="";
    public OSSprocess(){
        // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
        // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
        // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
        // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
        endpoint = "http://oss-cn-qingdao.aliyuncs.com";

        // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
        // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
        // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
        accessKeyId = "LTAI4FkTWtwLarvzh9MzSDDF";
        accessKeySecret = "FdIlqND5srRerHep8H2wqNuJqW8oeG";

        // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
        // Bucket命名规范如下：只能包括小写字母
        // ，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
        bucketName = "deeppupil";

    }
    public void UpLoadImage(String Path,String comments_id,String time_stamp){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }

            // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            BucketInfo info = ossClient.getBucketInfo(bucketName);
            System.out.println("Bucket " + bucketName + "的信息如下：");
            System.out.println("\t数据中心：" + info.getBucket().getLocation());
            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
            System.out.println("\t用户标志：" + info.getBucket().getOwner());

            // 文件存储入OSS，Object的名称为fileKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            String fileKey = "deep_com_image"+"/"+comments_id+"_"+time_stamp+".jpg";

            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpg");


            ossClient.putObject(bucketName, fileKey, new File(Path),objectMetadata);
            System.out.println("Object：" + fileKey + "存入OSS成功。");

            // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            System.out.println("您有以下Object：");
            for (OSSObjectSummary object : objectSummary) {
                System.out.println("\t" + object.getKey());
            }
            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl(bucketName, fileKey, expiration);
            System.out.println("文件链接地址如下\n"+url.toString());
            DB_Store(comments_id,url.toString(),fileKey);

        } catch (Exception oe) {
            oe.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    public void UpLoadImage2(String Path,String scenic_id,String date){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }


            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            String fileKey = "deep_flow_image"+"/"+date+"_"+scenic_id+".jpg";

            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpg");


            ossClient.putObject(bucketName, fileKey, new File(Path),objectMetadata);
            System.out.println("Object：" + fileKey + "存入OSS成功。");

            // 设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl(bucketName, fileKey, expiration);
            System.out.println("文件链接地址如下\n"+url.toString());
            DB_Store2(scenic_id,date,url.toString(),fileKey);

        } catch (Exception oe) {
            oe.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
    //将图片链接存储入数据库
    private void DB_Store(String com_id,String image_url,String image_key) throws SQLException, ClassNotFoundException {
        DBconnection dBconnection=new DBconnection();
        dBconnection.DB_Add("insert into person_comments_image (comments_id, image_url, image_key) VALUES ('"+com_id+"','"+image_url+"','"+image_key+"');\n");
        dBconnection.FreeResource();
    }
    //将图片链接存储入数据库
    private void DB_Store2(String scenic_id,String date,String image_url,String image_key) throws SQLException, ClassNotFoundException {
        DBconnection dBconnection1=new DBconnection();
//        dBconnection1.DB_Update("truncate table scenic_flow_image;");
        dBconnection1.FreeResource();
        DBconnection dBconnection=new DBconnection();
        dBconnection.DB_Add("insert into scenic_flow_image (scenic_id, record_date, image_url, image_key) VALUES ('"+scenic_id+"','"+date+"','"+image_url+"','"+image_key+"');");
        dBconnection.FreeResource();
    }

}
