package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager;
import java.sql.*;
public class ConnectionDB {
	public static void main(String[] args){
	 String oracleDriver = "oracle.jdbc.driver.OracleDriver"; //<-- ����̹� ����
	 String url = "jdbc:oracle:thin:@168.78.201.50:1521:EDIDEV9I"; //<-- �������� DB�� ��ġ�� �˷��ִ� �� �Դϴ�.
	 String userid = "EXEDITEST"; //<-- ���� ����
	 String userpw = "XEDI1515"; //<-- ���� ���
	 Connection con = null; //<-- Connection  
	 try{
		 
		 System.out.println("con start1:"+con);
	  Class.forName(oracleDriver); //<-- ����̹� �ε� 
	  System.out.println("�帮�̹��� ���������� �ε� �Ͽ����ϴ�.");
	  	System.out.println("con start2:"+con);
	 }catch(ClassNotFoundException e){e.printStackTrace();}
	 
	
	try{
	  con=DriverManager.getConnection(url,userid,userpw); //<-- ������ DB�� �ּҿ� ����, ���� ����� �Ѱ��ݴϴ�.
	  System.out.println("con start3:"+con);
	  System.out.println("������ ���̽� ���� ���� ����");
	  con.close();
	  System.out.println("con start4:"+con);
	 }catch(SQLException e){e.printStackTrace();}
	}
}

