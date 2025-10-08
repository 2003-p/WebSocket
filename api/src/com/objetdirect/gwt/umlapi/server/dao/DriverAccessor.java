//
//package com.objetdirect.gwt.umldrawer.server.dao;
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//
//public class DriverAccessor {
//
//	//private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu5?useUnicode=true&characterEncoding=Windows-31J";
//	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6?useUnicode=true&characterEncoding=UTF8";
////	private final static String DRIVER_URL="jdbc:mysql://mysql:3306/kifu6?Unicode=true&characterEncoding=UTF8";
//	//private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6_2019?useUnicode=true&characterEncoding=UTF8";
//	//private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6_2019_11?useUnicode=true&characterEncoding=UTF8";
//	//private final static String DRIVER_URL="jdbc:mysql://localhost:3306/pat?useUnicode=true&characterEncoding=UTF8";
//
//	private final static String DRIVER_NAME="com.mysql.jdbc.Driver";
//
//
//	private final static String USER_NAME="takafumi";
//
//
//	//private final static String PASSWORD="";
//	//private final static String PASSWORD="mysqlroot";
//
//	private final static String PASSWORD="takafumi";
//
//	static final String databasename2 	= "patternDB";
//	static final String user = "root";
//	static final String password = "root";
//
//	static final String url2 =  "jdbc:mysql://localhost/" + databasename2;
//
//	public Connection createConnection(){
//		try{
//			Class.forName(DRIVER_NAME);
//			Connection con=DriverManager.getConnection(DRIVER_URL,user,password);
//			return con;
//		} catch(ClassNotFoundException e){
//			System.out.println("Can't Find JDBC Driver.\n");
//		} catch(SQLException e){
//			System.out.println("Connection Error.\n"+e);
//		}
//		return null;
//	}
//
//	public void closeConnection(Connection con){
//		try{
//			con.close();
//		}catch(Exception ex){}
//	}
//}


package com.objetdirect.gwt.umlapi.server.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DriverAccessor {

//	private static final String DRIVER_URL = "jdbc:mysql://db:3306/kifu6?useUnicode=true&characterEncoding=UTF8";
//	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
//	private static final String USER_NAME = "takafumi";
//	private static final String PASSWORD = "takafumi";
	
	
    private static HikariDataSource ds;
    static{
//        HikariConfig config = new HikariConfig("../../../../../hikari.properties");
//        HikariConfig config = new HikariConfig("C:\\pleiades\\2023-09\\workspace\\kifu\\api\\src\\hikari.properties");
//        HikariConfig config = new HikariConfig("hikari.properties");
    	HikariConfig config = new HikariConfig();
//    	config.setJdbcUrl("jdbc:mysql://db:3306/kifu6?useUnicode=true&characterEncoding=UTF8");
    	config.setJdbcUrl("jdbc:mariadb://db:3306/kifu6?useUnicode=true&characterEncoding=UTF8");
    	config.setUsername("takafumi");
    	config.setPassword("takafumi");
    	
//    	config.setJdbcUrl("jdbc:mariadb://localhost:3306/kifu6?useUnicode=true&characterEncoding=UTF8");
//    	config.setUsername("root");
//    	config.setPassword("root");
    	
    	
//    	config.setDriverClassName("com.mysql.jdbc.Driver");
//    	config.setDriverClassName("com.mysql.cj.jdbc.Driver");
    	config.setDriverClassName("org.mariadb.jdbc.Driver");
    	config.addDataSourceProperty("cachePrepStmts", "true");
    	config.addDataSourceProperty("prepStmtCacheSize", "250");
    	config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    	config.addDataSourceProperty("useServerPrepStmts", "true");
    	config.addDataSourceProperty("useLocalSessionState", "true");
    	config.addDataSourceProperty("rewriteBatchedStatements", "true");
    	config.addDataSourceProperty("cacheResultSetMetadata", "true");
    	config.addDataSourceProperty("cacheServerConfiguration", "true");
    	config.addDataSourceProperty("elideSetAutoCommits", "true");
    	config.addDataSourceProperty("maintainTimeStats", "false");
    	
    	
//    	config.addDataSourceProperty("maxLifetime", "300000");
//    	config.addDataSourceProperty("idleTimeout", "60000");
//    	config.addDataSourceProperty("maximumPoolSize", "50");
//    	config.addDataSourceProperty("maximumIdle", "30");
    	

        config.setMaxLifetime(300000);  
        config.setIdleTimeout(60000);   
        
        
        config.setMaximumPoolSize(50);  
        config.setMinimumIdle(30);
    	
    	//ロギング用
    	config.setLeakDetectionThreshold(600);
//    	config.setConnectionTestQuery("SELECT 1");

    	
        ds = new HikariDataSource(config);
    }
    public static Connection getConn() throws SQLException {
        return ds.getConnection();
    }
    
//	public Connection createConnection() {
//		try {
//			Connection con = getConn();
//			return con;
//		} catch (SQLException e) {
//			System.out.println("Connection Error.\n"+e);
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public void closeConnection(Connection con) {
//		try {
//			con.close();
//		} catch (Exception ex) {
//		}
//	}
//	
	public Connection createConnection() {
	    try {
	        Connection con = getConn();
	        return con;
	    } catch (SQLException e) {
	        System.out.println("Database connection error: " + e.getMessage());
	        e.printStackTrace(); // エラーの詳細をコンソールに出力（デバッグ用）
	        throw new RuntimeException("Failed to create database connection", e); // 例外を再スローするか、適切な例外を投げる
	    }
	}

	public void closeConnection(Connection con) {
	    try {
	        if (con != null) {
	            con.close();
	        }
	    } catch (SQLException e) {
	        System.out.println("Error closing database connection: " + e.getMessage());
	        e.printStackTrace(); // エラーの詳細をコンソールに出力（デバッグ用）
	    }
	}
}
