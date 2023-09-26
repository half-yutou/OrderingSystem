package System.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/*
基于Druid数据池的工具类
 */
public class DruidUtils {
    //声明数据库连接池
    private static DataSource dataSource;

    //静态代码块对datasource进行初始化
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取连接
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    //关闭数据源
    //注意！！！此处的关闭数据源并非将连接断开，而是将连接从程序归还给数据库连接池
    public static void Close(ResultSet Set, PreparedStatement PreState, Connection connection) {
        try {
            if (Set != null) Set.close();
            if (PreState != null) PreState.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
