package cn.appsys.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * �������ݿ�Ļ���--��̬��
 * @author Administrator
 *
 */
public class BaseDao {

	static{//��̬�����,������ص�ʱ��ִ��
		init();
	}

	private static String driver;
	private static String url;
	private static String user;
	private static String password;

	//��ʼ�����Ӳ���,�������ļ�����
	public static void init(){
		Properties params=new Properties();
		String configFile = "database.properties";
		InputStream is= BaseDao.class.getClassLoader().getResourceAsStream(configFile);
		try {
			params.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver=params.getProperty("driverClassName");
		url=params.getProperty("url");
		user=params.getProperty("username");
		password=params.getProperty("password");

	}


	/**
	 * ��ȡ���ݿ�����
	 * @return
	 */
	public static Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}
	/**
	 * ��ѯ����
	 * @param connection
	 * @param pstm
	 * @param rs
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet execute(Connection connection, PreparedStatement pstm, ResultSet rs,
                                    String sql, Object[] params) throws Exception {
		pstm = connection.prepareStatement(sql);
		for(int i = 0; i < params.length; i++){
			pstm.setObject(i+1, params[i]);
		}
		rs = pstm.executeQuery();
		return rs;
	}
	/**
	 * ���²���
	 * @param connection
	 * @param pstm
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static int execute(Connection connection, PreparedStatement pstm,
                              String sql, Object[] params) throws Exception {
		int updateRows = 0;
		pstm = connection.prepareStatement(sql);
		for(int i = 0; i < params.length; i++){
			pstm.setObject(i+1, params[i]);
		}
		updateRows = pstm.executeUpdate();
		return updateRows;
	}

	/**
	 * �ͷ���Դ
	 * @param connection
	 * @param pstm
	 * @param rs
	 * @return
	 */
	public static boolean closeResource(Connection connection, PreparedStatement pstm, ResultSet rs){
		boolean flag = true;
		if(rs != null){
			try {
				rs.close();
				rs = null;//GC����
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
		}
		if(pstm != null){
			try {
				pstm.close();
				pstm = null;//GC����
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
		}
		if(connection != null){
			try {
				connection.close();
				connection = null;//GC����
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
		}
		
		return flag;
	}

}
