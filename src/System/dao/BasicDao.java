package System.dao;


import System.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;

@SuppressWarnings({"all"})
public class BasicDao<T> {//需要用泛型以便于不同dao使用

    private QueryRunner qr = new QueryRunner();

    //通用的DML方法，针对任意表
    public int update(String sql, Object... param) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            int affectedRow = qr.update(connection, sql, param);
            return affectedRow;//思考：此处return了后续关闭资源还能生效吗？
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.Close(null, null, connection);
        }
    }

    //通用的DQL方法，返回多行元素
    public List<T> queryMul(String sql, Class<T> tClass, Object... param) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return
                qr.query(connection, sql, new BeanListHandler<T>(tClass), param);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.Close(null, null, connection);
        }
    }

    //通用的DQL方法，返回单行元素
    public T querySingle(String sql, Class<T> tClass, Object... param) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return
                    qr.query(connection, sql, new BeanHandler<T>(tClass), param);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.Close(null, null, connection);
        }
    }

    //通用的DQL方法，返回单值元素
    public Object queryScalar(String sql,  Object... param) {
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection();
            return
                    qr.query(connection, sql, new ScalarHandler<>(), param);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.Close(null, null, connection);
        }
    }
}
