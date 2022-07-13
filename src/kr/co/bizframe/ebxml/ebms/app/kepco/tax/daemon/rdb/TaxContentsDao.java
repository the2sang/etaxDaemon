/*
 * Created on 2005. 5. 10.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxContentsVO;
import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxContentsDao {
    
    public void insert(TaxContentsVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        PreparedStatement ps = null;
        try{
            con.setAutoCommit(false);
            String sql = 
                " INSERT INTO ETS_TAX_XML_INFO_TB (UUID, CONTENTS, ORIG_CONTENTS) " +
                " VALUES (?, EMPTY_BLOB(), EMPTY_BLOB()) ";
            ps = con.prepareStatement(sql);
            ps.setString(1, vo.getUuid());
//            ps.executeUpdate(sql);
            ps.executeUpdate();
            ps.close();
            
            ps = con.prepareStatement("SELECT UUID, CONTENTS, ORIG_CONTENTS " +
                                      "  FROM ETS_TAX_XML_INFO_TB " +
                                      " WHERE UUID = ? FOR UPDATE"
                                      );
            
            ps.setString(1, vo.getUuid());
            ResultSet rs = ps.executeQuery();
            BLOB origBlob = null;
            BLOB contentBlob = null;
            while(rs.next()){
                contentBlob = ((OracleResultSet) rs).getBLOB(2);
                origBlob = ((OracleResultSet) rs).getBLOB(3);
            }
            
            OutputStream cos = contentBlob.getBinaryOutputStream();
            OutputStream cos2 = origBlob.getBinaryOutputStream();
          
            if(vo.getContents() != null){
                ByteArrayInputStream bais = new ByteArrayInputStream(vo.getContents());
                byte[] buffer = new byte[1024];
                int length = 0;
                while((length=bais.read(buffer)) != -1) {
                    cos.write(buffer,0,length);
                    cos2.write(buffer,0,length);
                }    
                cos.flush();
                cos2.flush();
                bais.close();
                cos.close();
                cos2.close();
            }
            ps.close();
        }catch  (SQLException e) {
            throw new TaxInvoiceException(this, e);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(ps!=null) ps.close();
        }
    }
    
    public void updateByUuid(TaxContentsVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
    	System.out.println("contents update@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        PreparedStatement ps = null;
        ByteArrayOutputStream baos = null;
        DBConnector dbconn = null;
        boolean closeCon = false;
        try {
            if(con == null){
                dbconn = new DBConnector();
                con = dbconn.getConnection();	
                closeCon = true;
            }
            con.setAutoCommit(false);
            String updateEmptyBlob = 
                "  UPDATE ETS_TAX_XML_INFO_TB SET "   +
                "         CONTENTS = EMPTY_BLOB() "  +
                "   WHERE UUID = ?";
            ps = con.prepareStatement(updateEmptyBlob);
            ps.setString(1, vo.getUuid());
//            System.out.println("ETS_TAX_XML_INFO_TB select:"+vo.getUuid());
//            ps.executeUpdate(updateEmptyBlob);
            ps.executeUpdate();
            ps.close();
            
            String sql = 
                "SELECT UUID, CONTENTS " +
                "  FROM ETS_TAX_XML_INFO_TB WHERE UUID = ? " +
                "   FOR UPDATE    " +
                " NOWAIT ";
            ps = con.prepareStatement(sql);
            ps.setString(1, vo.getUuid());
            ResultSet rs = ps.executeQuery();
            BLOB contentsBlob = null;
            while(rs.next()){
                contentsBlob = ((OracleResultSet) rs).getBLOB(2);
            }
            OutputStream cos = contentsBlob.getBinaryOutputStream();
            //contents¸¦ insert;
            if(vo.getContents() != null){
                ByteArrayInputStream bais = new ByteArrayInputStream(vo.getContents());
                byte[] buffer = new byte[5000];
                int length = 0;
                cos.flush();
                while((length=bais.read(buffer)) != -1)
                    cos.write(buffer,0,length);
                cos.close();
                bais.close();
            }
            //signature¸¦ insert
            rs.close();
            ps.close();
        }catch (SQLException e){
            throw new TaxInvoiceException(this, e);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(ps != null) ps.close();
            if(closeCon){
                dbconn.closeConnection(con);
            }
        }
    }
    
    public TaxContentsVO selectByUuid(String uuid, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START SELECT ETS_TAX_XML_INFO_TB]");
        PreparedStatement ps = null;
        ByteArrayOutputStream baos = null;
        TaxContentsVO vo = new TaxContentsVO();
        DBConnector dbconn = null;
        boolean closeCon = false;
        try {
            if(con == null){
                dbconn = new DBConnector();
                con = dbconn.getConnection();	
                closeCon = true;
            }
            String sql = 
                " SELECT UUID, CONTENTS " +
                "   FROM ETS_TAX_XML_INFO_TB     " +
                "  WHERE UUID = ? "  ;
            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
//                System.out.println("rs.next select blob#######");
                BLOB recBlob = ((OracleResultSet)rs).getBLOB(2);
                InputStream recIs = recBlob.getBinaryStream();
                ByteArrayOutputStream recBaos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int size = 0;
                while((size=recIs.read(buffer)) != -1){
                    recBaos.write(buffer, 0, size);
                }
                vo.setUuid(uuid);
                vo.setContents(recBaos.toByteArray());
                recBaos.close();
                recIs.close();
            }
            
            rs.close();
            ps.close();
            if(closeCon){
                dbconn.closeConnection(con);
            }
            System.out.println("[END SELECT ETS_TAX_XML_INFO_TB]");
        }catch (SQLException e){
            throw new TaxInvoiceException(this, e);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(ps != null) ps.close();
            if(closeCon){
                dbconn.closeConnection(con);
            }
        }
        return vo;
    }
    
    /**
     * 
     * @param vo
     * @param con
     * @throws SQLException
     * @throws TaxInvoiceException
     */
    public void insert_FI(String uuid, byte[] contents, Connection con)throws SQLException, TaxInvoiceException {
    	PreparedStatement ps = null;
        try{
            con.setAutoCommit(false);
            String sql =   
            	" INSERT INTO  ETS_ERP_XML_INFO_TB (UUID, CONTENTS, ORIG_CONTENTS, STATUS) " +
            	" VALUES (?, EMPTY_BLOB(), EMPTY_BLOB(), 'SED') ";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ps.executeUpdate();
            ps.close();
            
            ps = con.prepareStatement("SELECT UUID, CONTENTS, ORIG_CONTENTS " +
                    "  FROM  ETS_ERP_XML_INFO_TB  " +
                    " WHERE UUID = ? FOR UPDATE"
                    );
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            BLOB origBlob = null;
            BLOB contentBlob = null;
            while(rs.next()){
                contentBlob = ((OracleResultSet) rs).getBLOB(2);
                origBlob = ((OracleResultSet) rs).getBLOB(3);
            }
            
            OutputStream cos = contentBlob.getBinaryOutputStream();
            OutputStream cos2 = origBlob.getBinaryOutputStream();
          
            if(contents != null){
                ByteArrayInputStream bais = new ByteArrayInputStream(contents);
                byte[] buffer = new byte[1024];
                int length = 0;
                while((length=bais.read(buffer)) != -1) {
                    cos.write(buffer,0,length);
                    cos2.write(buffer,0,length);
                }    
                cos.flush();
                cos2.flush();
                bais.close();
                cos.close();
                cos2.close();
            }
            ps.close();
        }catch  (SQLException e) {
            throw new TaxInvoiceException(this, e);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(ps!=null) ps.close();
        }
    } 
}
