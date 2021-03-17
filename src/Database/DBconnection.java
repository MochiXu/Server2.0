package Database;
import java.sql.*;

//用于链接mysql
public class DBconnection {
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/DeepPupil";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "xmc";
    static final String PASS = "294207";

    //相关对象初始化
    Connection conn = null;//数据库连接对象
    Statement stmt = null;//执行数据库语句对象

    public DBconnection() throws ClassNotFoundException, SQLException {
        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);
        //初始化数据库连接对象
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        //初始化数据库语句执行者
        stmt = conn.createStatement();

    }

    //数据库查询操作
    public ResultSet DB_FindDataSet(String sql) throws SQLException {
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
    //数据库更新操作
    public void DB_Update(String sql) throws SQLException {
        stmt.execute(sql);
        return;
    }
    //数据库新增操作
    public void DB_Add(String sql) throws SQLException {
        stmt.execute(sql);
        return;
    }
    //数据库删除操作
    public void DB_Del(String sql) throws SQLException {
        stmt.execute(sql);
        return;
    }

    //数据库资源释放
    public void FreeResource() throws SQLException {
        this.conn.close();
        this.stmt.close();
    }
}
