package com.flyme.common.util.other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * 数据库连接辅助类
 */
public class DataBaseUtils {
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("mysql-jdbc");
	private static Connection connection;
	private static Statement stmt;// 定义STMT

	/**
	 * 获取数据库连接
	 */
	public static synchronized Connection getConnection() {
		String user = bundle.getString("jdbc.username");
		String password = bundle.getString("jdbc.password");
		String host = bundle.getString("jdbc.host");
		String dbname = bundle.getString("jdbc.dbname");
		String port = bundle.getString("jdbc.port");
		String driverClassName = bundle.getString("jdbc.driverClassName");
		String url = "jdbc:mysql://"+host+":"+port+"/"+dbname;
		try {
			Class.forName(driverClassName);
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.print("找不到驱动");
		} catch (SQLException e) {
			System.out.print("连接数据库失败");
		}
		return connection;

	}

	public static int executeUpdate(String sql) throws Exception {
		int i = 0;
		try {
			stmt = getConnection().createStatement();
			i = stmt.executeUpdate(sql);
		} catch (SQLException sqlexception) {
		}
		return i;
	}

	public static void closeConnection() {
		try {
			if ((connection != null) && (!connection.isClosed()))
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void closePreparedStatement(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pstmt = null;
		}
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static String find(String sql) throws SQLException, ClassNotFoundException {
		String value = "";
		PreparedStatement pst = null;
		try {
			pst = getConnection().prepareStatement(sql);
			ResultSet resultSet = pst.executeQuery();
			if (resultSet.next()) {
				value = resultSet.getString(1);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pst.close();
				pst = null;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return value;

	}

	public static void main(String[] arsg) {
		Connection com = getConnection();
		System.out.println();
		System.out.println("3");
	}
}
