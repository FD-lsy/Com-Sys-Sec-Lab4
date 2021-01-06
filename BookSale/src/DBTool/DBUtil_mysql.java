package DBTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;


public class DBUtil_mysql {
	// ���ӵ�mysql���ݿ�
	public static String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
	private static String driverClass = "com.mysql.cj.jdbc.Driver";
	private static Connection conn;
	// װ������
	static {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ��ȡ���ݿ�����
	public static Connection getConnection(String username,String password) {
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.print("���ݿ����ӳɹ���");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// �ر����ݿ�����
	public static void Close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// �������ݿ�����
	public static void main(String[] args) {
		Connection conn = DBUtil.getConnection("root","root");
		if (conn == null) {
			System.out.println("���ݿ�����ʧ�ܣ�");
		}
	}
}