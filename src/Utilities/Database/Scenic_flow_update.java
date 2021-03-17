package Utilities.Database;

import Database.DBconnection;
import Utilities.Service.OSSprocess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
//用于更新scenic_flow表内的数据,每5s更新一次
public class Scenic_flow_update extends TimerTask {
    @Override
    public void run() {
        try {
            Update();
            Update2();
            Update3();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void Update() throws SQLException, ClassNotFoundException {
        DBconnection dBconnection1=new DBconnection();
        ResultSet resultSet1=dBconnection1.DB_FindDataSet("select edge_receive.record_date,edge_receive.record_time,edge_receive.lng,edge_receive.lat,edge_receive.device_id,scenic_uav.scenic_id,edge_receive.person_count,edge_receive.edge_receive_pk from scenic_uav,edge_receive where scenic_uav.uav_id=edge_receive.device_id and edge_receive.process_status=1;");
        while(resultSet1.next()){
            // 更新scenic_flow内的数据
            DBconnection dBconnection2=new DBconnection();
            dBconnection2.DB_Add("insert into scenic_flow(scenic_id, record_date, record_time, spot_lng, spot_lat,person_count, data_source) VALUES ('"+resultSet1.getString(6)+"','"+resultSet1.getString(1)+"','"+resultSet1.getString(2)+"','"+resultSet1.getString(3)+"','"+resultSet1.getString(4)+"','"+resultSet1.getString(7)+"','"+resultSet1.getString(5)+"')");
            dBconnection2.FreeResource();
            // 更新edge_receive内的process_status
            DBconnection dBconnection3=new DBconnection();
            dBconnection3.DB_Update("update edge_receive set  process_status=2 where edge_receive_pk="+resultSet1.getString(8)+";\n");
            dBconnection3.FreeResource();
        }
        dBconnection1.FreeResource();

        DBconnection dBconnection04=new DBconnection();
        ResultSet resultSet2=dBconnection04.DB_FindDataSet("select edge_receive.record_date,edge_receive.record_time,edge_receive.lng,edge_receive.lat,edge_receive.device_id,scenic_spot.scenic_id,edge_receive.person_count,edge_receive.edge_receive_pk from scenic_spot,edge_receive where scenic_spot.spot_id=edge_receive.device_id and edge_receive.process_status=1;\n");
        while(resultSet2.next()){
            // 更新scenic_flow内的数据
            DBconnection dBconnection5=new DBconnection();
            dBconnection5.DB_Add("insert into scenic_flow(scenic_id, record_date, record_time, spot_lng, spot_lat,person_count, data_source) VALUES ('"+resultSet2.getString(6)+"','"+resultSet2.getString(1)+"','"+resultSet2.getString(2)+"','"+resultSet2.getString(3)+"','"+resultSet2.getString(4)+"','"+resultSet2.getString(7)+"','"+resultSet2.getString(5)+"')");
            dBconnection5.FreeResource();
            // 更新edge_receive内的process_status
            DBconnection dBconnection6=new DBconnection();
            dBconnection6.DB_Update("update edge_receive set  process_status=2 where edge_receive_pk="+resultSet2.getString(8)+";\n");
            dBconnection6.FreeResource();
        }
        dBconnection04.FreeResource();
    }
    //将等待上传到OSS的游客评论照片进行上传
    private void Update2() throws SQLException, ClassNotFoundException {
        //获取数据库中需要上传的图片
        DBconnection dBconnection=new DBconnection();
        DBconnection dBconnection2=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select comments_id,image_path from person_comments_image_temp where status='0';\n");
        OSSprocess oss=new OSSprocess();

        while(resultSet.next()){
            String comments_id=resultSet.getString(1);
            String image_path=resultSet.getString(2);
            System.out.println(image_path+" "+comments_id+" ");
            oss.UpLoadImage(image_path,comments_id, String.valueOf(System.currentTimeMillis()));
            //上传完成后将此图片的status设置为1
            String sql="update person_comments_image_temp set status=1 where comments_id='"+comments_id+"' and image_path='"+image_path+"';\n";
            dBconnection2.DB_Update(sql);
        }
        dBconnection.FreeResource();
        dBconnection2.FreeResource();
    }
    //将等待上传到OSS的景区客流趋势图片进行上传
    private void Update3() throws SQLException, ClassNotFoundException {
        //获取数据库中需要上传的图片
        DBconnection dBconnection=new DBconnection();
        DBconnection dBconnection2=new DBconnection();
        DBconnection dBconnection3=new DBconnection();
        ResultSet resultSet=dBconnection.DB_FindDataSet("select scenic_id,record_date,path from scenic_flow_image_temp where status='0';\n");
        ResultSet resultSet3=dBconnection3.DB_FindDataSet("select scenic_id,record_date,path from scenic_flow_image_temp where status='0';\n");
        OSSprocess oss=new OSSprocess();
        int count=0;
        while(resultSet3.next()){
            count++;
        }
        if (count!=0){
//            由于并发的不可测行，此处代码取消
//            dBconnection2.DB_Del("truncate table scenic_flow_image");
        }


        while(resultSet.next()){
            String scenic_id=resultSet.getString(1);
            String image_path=resultSet.getString(3);
            String record_date=resultSet.getString(2);

            oss.UpLoadImage2(image_path,scenic_id, record_date);
            //上传完成后将此图片的status设置为1
            String sql="update scenic_flow_image_temp set status=1 where scenic_id='"+scenic_id+"' and path='"+image_path+"';\n";
            dBconnection2.DB_Update(sql);
        }
        dBconnection.FreeResource();
        dBconnection2.FreeResource();
        dBconnection3.FreeResource();
    }
}
