package com.db;
import java.sql.*;
/*
 * ���ڶ����ݿ�Ļ�������
 */

public class DBManager {
//	���ݿ�������
	public static final String DRIVER="org.postgresql.Driver";
	public static final String USER="postgres";
	public static final String PASS="Admin12345";
	public static final String URL="jdbc:postgresql://localhost:5432/onecard_db";
// 	�������ģʽ
	private static DBManager per=null;
	private Connection conn=null;
	private Statement stmt=null;
	
//	����ʽ�������ģʽ
	
	private DBManager(){
		
	}
	public static DBManager createInstance(){
		if(per==null){
			per=new DBManager();
			per.initDB();
			
		}
		return per;
	}
	//��������
	public void initDB(){
			try {
				Class.forName(DRIVER);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	//�������ݿ� ��ȡ����Ͷ���
	 public void connectDB() {
	        System.out.println("Connecting to database...");
	        try {
	            conn = DriverManager.getConnection(URL, USER, PASS);
	            stmt = (Statement)conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	    }
	 // �ر����ݿ� �رն����ͷž��
	    public void closeDB() {
	        System.out.println("Close connection to database..");
	        try {
	        	if(stmt!=null)
	            stmt.close();
	        	if(conn!=null)
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Close connection successful");
	    }
	 // ��ѯ
	    public ResultSet executeQuery(String sql) {
	        ResultSet rs = null;
	        try {
	            rs = stmt.executeQuery(sql);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return rs;
	    }
	 // ����/ɾ��/�޸�
	    public int executeUpdate(String sql) {
	        int ret = 0;
	        try {
	            ret = stmt.executeUpdate(sql);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return ret;
	    }
	

}
