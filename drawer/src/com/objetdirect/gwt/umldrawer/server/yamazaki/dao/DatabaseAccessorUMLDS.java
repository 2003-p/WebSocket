package com.objetdirect.gwt.umldrawer.server.yamazaki.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseAccessorUMLDS {
	// private Connection connection;
	// private HikariDataSource hikari;
	private Connection con;

	// private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6?Unicode=true&characterEncoding=UTF8";
	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/umlds?Unicode=true&characterEncoding=UTF8";
	private final static String CHAR_URL = "?Unicode=true&characterEncoding=UTF8";
	private final static String DRIVER_NAME="com.mysql.jdbc.Driver";
	static final String USER = "root";
	static final String PASSWORD = "root";
	static final String DATABASE_NAME = "kifu6";

	static final String url =  "jdbc:mysql://localhost/:3306/";

	private final HikariConfig config;
	private final HikariDataSource hikarisource;
	private static DatabaseAccessorUMLDS singletonDatabaseAccessorYamazaki = new DatabaseAccessorUMLDS();

	private DatabaseAccessorUMLDS()
	{
		config = new HikariConfig();
		System.out.println("HikariCP");
//		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl(DRIVER_URL);
		config.setUsername(USER);
		config.setPassword(PASSWORD);
		hikarisource = new HikariDataSource(config);
		con = null;
	}

	public static DatabaseAccessorUMLDS getInstance()
	{
		return singletonDatabaseAccessorYamazaki;
	}

//	protected Connection createConnection()
//	{
//		try
//		{
//			Class.forName(DRIVER_NAME);
//			Connection con=DriverManager.getConnection(DRIVER_URL,USER,PASSWORD);
//			return con;
//		} catch(ClassNotFoundException e)
//		{
//			System.out.println("Can't Find JDBC Driver.\n");
//		} catch(SQLException e)
//		{
//			System.out.println("Connection Error.\n"+e);
//		}
//		return null;
//	}
//	protected HikariConfig createHikariConfig()
//	{
//		HikariConfig config = new HikariConfig();
//		System.out.println("HikariCP");
////	config.setDriverClassName("com.mysql.jdbc.Driver");
//		config.setJdbcUrl(DRIVER_URL);
//		config.setUsername(USER);
//		config.setPassword(PASSWORD);
//
//		return config;
//	}
//
//	protected void createHikariConfig(String database)
//	{
//		config = new HikariConfig();
//		System.out.println("HikariCP");
////	config.setDriverClassName("com.mysql.jdbc.Driver");
//		config.setJdbcUrl(database);
//		config.setUsername(USER);
//		config.setPassword(PASSWORD);
//	}

	protected Connection createHikariConnection()
	{


		try {
			return hikarisource.getConnection();
		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}

		return con;

	}

	protected void closeConnection(Connection con){
		try
		{
			con.close();
		}catch(Exception ex){}
	}

	public String getDatabaseElements() {
		return null;
	}
}
