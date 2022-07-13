package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager;
import java.sql.*;
public class ConnectionDB {
	public static void main(String[] args){
	 String oracleDriver = "oracle.jdbc.driver.OracleDriver"; //<-- 드라이버 설정
	 String url = "jdbc:oracle:thin:@168.78.201.50:1521:EDIDEV9I"; //<-- 실직적인 DB의 위치를 알려주는 것 입니다.
	 String userid = "EXEDITEST"; //<-- 접속 계정
	 String userpw = "XEDI1515"; //<-- 계정 비번
	 Connection con = null; //<-- Connection  
	 try{
		 
		 System.out.println("con start1:"+con);
	  Class.forName(oracleDriver); //<-- 드라이버 로드 
	  System.out.println("드리이버가 성공적으로 로드 하였습니다.");
	  	System.out.println("con start2:"+con);
	 }catch(ClassNotFoundException e){e.printStackTrace();}
	 
	
	try{
	  con=DriverManager.getConnection(url,userid,userpw); //<-- 연결할 DB의 주소와 계정, 계정 비번을 넘겨줍니다.
	  System.out.println("con start3:"+con);
	  System.out.println("데이터 베이스 연결 성공 ㅋㅋ");
	  con.close();
	  System.out.println("con start4:"+con);
	 }catch(SQLException e){e.printStackTrace();}
	}
}

