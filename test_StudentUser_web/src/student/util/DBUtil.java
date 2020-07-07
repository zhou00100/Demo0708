package student.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//DBUtil�������ӳض���
public class DBUtil {
	static ComboPooledDataSource dataSource = null; //C3P0���ӳض���
	
	/**
	 * c3p0ʹ�ñ�׼���裺
	 * ��һ��������c3p0�����ӳ������ļ�
	 * �ڶ������������ӳز���
	 * ��������ʵ����c3p0���ӳض���
	 */
	static{
		try {
			  Properties prop = new Properties();//ʵ���������ļ�������
			  //����src/"c3p0.properties-> C3p0�����ļ�
			  InputStream inStream  = DBUtil.class.getClassLoader().getResourceAsStream("c3p0.properties");
			  prop.load(inStream);
			  
			  dataSource = new ComboPooledDataSource();//ʵ����c3p0���ӳ�
			  dataSource.setDriverClass(prop.getProperty("driverClass"));//c3p0���ӳؼ���Oracle������
			  dataSource.setJdbcUrl(prop.getProperty("jdbcUrl"));//��ָ��OracleURL
			  dataSource.setUser(prop.getProperty("user"));//��Oracle�û���
			  dataSource.setPassword(prop.getProperty("password"));//����
			  dataSource.setInitialPoolSize(Integer.parseInt(prop.getProperty("initialPoolSize")));//���ӳؿ�ʼʵ����������������5�����ӣ�
			  dataSource.setMaxPoolSize(Integer.parseInt(prop.getProperty("maxPoolSize")));
			  dataSource.setMaxIdleTime(Integer.parseInt(prop.getProperty("maxIdleTime")));
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ComboPooledDataSource getDataSource() {
		return dataSource;//����ʵ�������c3p0���ӳ�
	}
	
	//�ر���Դ
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
			System.out.println("c3p0���ӳض���"+dataSource.toString());
		}

}











