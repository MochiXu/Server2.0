package DataProcess;

import Database.DBconnection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data_index {
    public Data_index(){}

    public int Tag_flow_peak() throws SQLException, ClassNotFoundException {
        // 获取人流量峰值
        DBconnection dBconnection01=new DBconnection();
        ResultSet resultSet01=dBconnection01.DB_FindDataSet("call get_Scenic_maxPersonCount;");
        int result01=0;
        while (resultSet01.next()){
            result01=resultSet01.getInt(1);
        }
        dBconnection01.FreeResource();
        return result01;
    }

    public int Tag_flow_actual() throws SQLException, ClassNotFoundException {
        // 获取人流量实时数据
        DBconnection dBconnection02=new DBconnection();
        ResultSet resultSet02=dBconnection02.DB_FindDataSet("call get_Scenic_actualPersonCount;");
        int result02=0;
        while (resultSet02.next()){
            result02=resultSet02.getInt(1);
        }
        dBconnection02.FreeResource();
        return result02;
    }


}
