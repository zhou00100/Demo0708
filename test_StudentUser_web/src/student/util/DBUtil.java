package student.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//DBUtil返回连接池对象
public class DBUtil {
	static ComboPooledDataSource dataSource = null; //C3P0连接池对象
	
	/**
	 * c3p0使用标准步骤：
	 * 第一步：创建c3p0的连接池配置文件
	 * 第二步：加载连接池参数
	 * 第三步：实例化c3p0连接池对象
	 */
	static{
		try {
			  Properties prop = new Properties();//实例化属性文件处理工具
			  //加载src/"c3p0.properties-> C3p0配置文件
			  InputStream inStream  = DBUtil.class.getClassLoader().getResourceAsStream("c3p0.properties");
			  prop.load(inStream);
			  
			  dataSource = new ComboPooledDataSource();//实例化c3p0连接池
			  dataSource.setDriverClass(prop.getProperty("driverClass"));//c3p0连接池加载Oracle驱动类
			  dataSource.setJdbcUrl(prop.getProperty("jdbcUrl"));//打开指定OracleURL
			  dataSource.setUser(prop.getProperty("user"));//打开Oracle用户名
			  dataSource.setPassword(prop.getProperty("password"));//密码
			  dataSource.setInitialPoolSize(Integer.parseInt(prop.getProperty("initialPoolSize")));//连接池开始实例化创建连接数（5条连接）
			  dataSource.setMaxPoolSize(Integer.parseInt(prop.getProperty("maxPoolSize")));
			  dataSource.setMaxIdleTime(Integer.parseInt(prop.getProperty("maxIdleTime")));
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ComboPooledDataSource getDataSource() {
		return dataSource;//返回实例化后的c3p0连接池
	}
	
	//关闭资源
		public static void close(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {
			try {
				if (conn != null) {
					conn.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public static void main(String[] args) {
			ComboPooledDataSource dataSource = DBUtil.getDataSource();
			System.out.println("c3p0连接池对象："+dataSource.toString());
		}

}











