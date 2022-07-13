/*
 * Created on 2005. 8. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.TaxInvoice;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.EBXMLAppException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util.TaxInvoiceRspUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util.TaxInvoiceRspVOToObjUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util.TaxVOToObjUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util.TaxinvUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxContentsVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxCountVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxFinanceContectVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxHeaderVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvRspVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLineVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMetaVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxPersonVO;
    
/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxManagementDao {

	private static Logger logger = Logger.getLogger(TaxManagementDao.class);
/////////////////////////count관련
    public TaxCountVO getCount() throws TaxInvoiceException{
        System.out.println("[START getCount]");
        Connection con = null;
        String prefix = "";
        TaxCountVO vo = new TaxCountVO();
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);
            TaxCountDao dao = new TaxCountDao();
            vo = dao.select(con);
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getCount]");
        return vo;
    }

    public String getSeqId() throws TaxInvoiceException{
        System.out.println("[START getSeqId]");
        Connection con = null;
        String prefix = "";
        String id = "";
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);
            TaxCountDao dao = new TaxCountDao();
            id = dao.getSeqId(con);
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getSeqId]");
        return id;
    }

    public void increaseCount() throws TaxInvoiceException{
        System.out.println("[START increaseCount]");
        Connection con = null;
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);

            TaxCountDao dao = new TaxCountDao();

            if(dao.isExist(con).equals("Y")){
                dao.update(con);
            }else{
                dao.insert(con);
            }
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END increaseCount]");
    }


//////////////////////////////////////////TaxInvoiceVO관련
    public TaxInvoiceVO saveTaxinvoice(TaxInvoiceVO vo) throws TaxInvoiceException{
    	logger.debug("[START saveTaxinvoice]::세금계산서 정보를 저장 "+ vo.getUuid());
        Connection con = null;
        TaxInvoiceVO selVO = new TaxInvoiceVO();
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);

            String uuid  = vo.getUuid();

            TaxMetaDao dao = new TaxMetaDao();

            String isExist = dao.isExistTax(uuid, con);

//            TaxPersonDao personDao = new TaxPersonDao();
//            vo.getMeta().setReceiver_id(personDao.getPersonIdByEmailName(vo.getMain().getBuyer_contactor_name(), vo.getMain().getBuyer_contactor_email(), con));
//            vo.getMeta().setSender_id(personDao.getPersonIdByEmailName(vo.getMain().getSupplier_contactor_name(), vo.getMain().getSupplier_contactor_email(), con));
//            vo.getMeta().setExt_buyer_sabun(personDao.getPersonIdByEmailName(vo.getMain().getBuyer_contactor_name(), vo.getMain().getBuyer_contactor_email(), con));
            vo.getMain().setUuid(vo.getUuid());

// 이거는 신기술료일때.부서, 년월, 일련번호 재무쪽에 안가게 하기 위해
// 2006.04.27 이제중
            String tempVaucher_Buseo = "";
            String tempVaucher_ym = "";
            String tempVaucher_seq = "";

            if ( (vo.getMain().getRef_other_doc_id().trim()).equals("3")) {
                tempVaucher_Buseo = vo.getMeta().getExt_voucher_buseo();
                tempVaucher_ym = vo.getMeta().getExt_voucher_yearMonth();
                tempVaucher_seq = vo.getMeta().getExt_voucher_seq();

                vo.getMeta().setExt_voucher_buseo("");
                vo.getMeta().setExt_voucher_yearMonth("");
                vo.getMeta().setExt_voucher_seq("");
            }
            //20100401 종사업장번호 저장시
            if("0000".equals(vo.getMain().getBuyer_biz_cd())){
            	vo.getMain().setBuyer_biz_cd("");
            	System.out.println("vo.getMain().getBuyer_biz_cd() ::: "+vo.getMain().getBuyer_biz_cd());
            }

            logger.debug("#########################BINDDING START:"+CommonUtil.getCurrentTime());
            TaxinvUtil xmlUtil = new TaxinvUtil();
            TaxVOToObjUtil util = new TaxVOToObjUtil(vo);
            TaxInvoice tax = util.getObj();

            ByteArrayOutputStream baos = null;
            baos = xmlUtil.generateXML(tax);
            TaxContentsDao contentsDao = new TaxContentsDao();
            TaxContentsVO contentsVO = vo.getContents();
            contentsVO.setUuid(vo.getUuid());
            contentsVO.setContents(baos.toByteArray());
            vo.setContents(contentsVO);

            logger.debug("##########################BINDDING END:"+CommonUtil.getCurrentTime());

            if ( vo.getMain().getRef_other_doc_id().equals("3")) {
                vo.getMeta().setExt_voucher_buseo(tempVaucher_Buseo);
                vo.getMeta().setExt_voucher_yearMonth(tempVaucher_ym);
                vo.getMeta().setExt_voucher_seq(tempVaucher_seq);
            }
            if(isExist.equals("Y")){
            	this.updateTaxinvoice(vo, con);
            }else{
            	this.insertTaxinvoice(vo, con);
            }
            
            selVO = this.selectTaxinvoice(vo.getUuid(), con);
//2015.12.07 내선계기 메일보낸이를 계약자로 변경 CDH
            selVO.getMeta().setContractor_email(vo.getMeta().getContractor_email());
            selVO.getMeta().setContractor_name(vo.getMeta().getContractor_name());
            selVO.getMeta().setContractor_phone(vo.getMeta().getContractor_phone());
           
            selVO.setUuid(uuid);
            con.commit();
        }
        catch (SQLException e)
        {
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        return selVO;
    }

//////////////////////////////////////////TaxInvoiceVO관련
    public TaxInvoiceVO saveTaxinvoiceContents(String uuid, byte[] data) throws TaxInvoiceException{
    	logger.debug("[START saveTaxinvoice]::세금계산서의 contentes 정보를 저장"+uuid);
        Connection con = null;
        TaxInvoiceVO selVO = new TaxInvoiceVO();
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);

            TaxContentsVO contentsVO = new TaxContentsVO();
            contentsVO.setUuid(uuid);
            contentsVO.setContents(data);


            TaxContentsDao contentsDao = new TaxContentsDao();
            contentsDao.updateByUuid(contentsVO, con);

            con.commit();
        }
        catch (SQLException e)
        {
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        return selVO;
    }

    public TaxInvoiceVO getTaxInvoiceVO(String uuid) throws TaxInvoiceException{
    	logger.debug("[START getTaxInvoiceVO]::세금계산서의 문서 정보를 조회");
        Connection con = null;
        TaxInvoiceVO selVO = new TaxInvoiceVO();
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);
            logger.debug("con : "+con);
            TaxMetaDao dao = new TaxMetaDao();
            selVO = this.selectTaxinvoice(uuid, con);
            selVO.setUuid(uuid);
            con.commit();
        }
        catch (SQLException e)
        {
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        logger.debug("[END getTaxInvoiceVO]");
        return selVO;
    }

	public void insertTaxinvoice(TaxInvoiceVO vo, Connection con) throws TaxInvoiceException, SQLException{
		logger.debug("[START insertTaxinvoice]:: 세금계산서 저장 insert");
        String uuid = vo.getUuid();
        TaxMetaDao taxAppDao = new TaxMetaDao();
        vo.getMeta().setUuid(uuid);
        taxAppDao.insert(vo.getMeta(), con);

        TaxMainDao headerDao  = new TaxMainDao();
        vo.getMain().setUuid(uuid);
        headerDao.insert(vo.getMain(), con);
        TaxLineDao lineDao  = new TaxLineDao();
        for(int i=0; i<vo.getLine().size(); i++){
            TaxLineVO lineVO = (TaxLineVO)vo.getLine().get(i);
            lineVO.setUuid(uuid);
            lineDao.insert(lineVO, con);
        }

        TaxContentsDao contentsDao = new TaxContentsDao();
        contentsDao.insert(vo.getContents(), con);
    }


    public void updateTaxinvoice(TaxInvoiceVO vo, Connection con) throws TaxInvoiceException, SQLException, EBXMLAppException{
    	logger.debug("[START updateTaxinvoice]:: 세금계산서 저장 update");

        TaxMetaDao taxAppDao = new TaxMetaDao();
        vo.getMeta().setUuid(vo.getUuid());
        taxAppDao.update(vo.getMeta(), con);

        TaxMainDao headerDao  = new TaxMainDao();
        vo.getMain().setUuid(vo.getUuid());
        headerDao.update(vo.getMain(), con);

        TaxLineDao lineDao  = new TaxLineDao();
        System.out.println("size of line:"+vo.getLine().size());

        int delNum = 0;
        String uuid = "";

        for(int i=0; i<vo.getLine().size(); i++){
            TaxLineVO lineVO = (TaxLineVO)vo.getLine().get(i);
            lineVO.setUuid(vo.getUuid());
            if(lineDao.isExist(lineVO, con).equals("Y")){
                lineDao.update(lineVO, con);
            }else{
                lineDao.insert(lineVO, con);
            }
            uuid = vo.getUuid();
            delNum = i+1;
        }

// 이제중 목록 저장시 뺀거는 삭제
//        lineDao.delete(uuid, delNum, con);

        TaxContentsDao contentsDao = new TaxContentsDao();
        contentsDao.updateByUuid(vo.getContents(), con);

    }

    public TaxInvoiceVO selectTaxinvoice(String uuid,  Connection con) throws TaxInvoiceException{
        System.out.println("[START selectTaxinvoice]:: 세금계산서 조회 select");
        TaxInvoiceVO vo = new TaxInvoiceVO();
        try{
            TaxMetaDao taxAppDao = new TaxMetaDao();
            vo.setUuid(uuid);
            vo.setMeta(taxAppDao.select(uuid, vo.getMeta(), con));

            TaxMainDao headerDao  = new TaxMainDao();
            vo.setMain(headerDao.select(uuid, vo.getMain(), con));

            TaxLineDao lineDao  = new TaxLineDao();
            vo.setLine(lineDao.select(uuid, vo.getLine(), con));
          

            TaxContentsDao contentsDao = new TaxContentsDao();
            vo.setContents(contentsDao.selectByUuid(uuid, con));
            
            
           
        }catch(Exception e){
            e.printStackTrace();
        }
        return vo;

    }
   public void setStatus(String uuid, String status)  throws TaxInvoiceException, SQLException{
        System.out.println("[START setStatus in TaxManagementDao]");
        Connection con = null;
        TaxMetaDao dao  = new TaxMetaDao();
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            System.out.println("#$#$#$#$#$#$#$#$#$#$#세금계산서 상태 변경 완료 #$#$#$#$#$#$#$#$#$#$#");
            System.out.println("세금계산서 문서 번호:"+uuid);
            System.out.println("변경후 문서 상태:"+status);
            dao.setStatus(uuid, status, con);
            System.out.println("#$#$#$#$#$#$#$#$#$#$#세금계산서 상태 변경 완료#$#$#$#$#$#$#$#$#$#$#");
            dao.setComplateDate(uuid, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END setStatus in TaxManagementDao]");
    }

    public void setResultMsg(String uuid, String msg)  throws TaxInvoiceException, SQLException{
        System.out.println("[START setStatus in TaxManagementDao]:: 문서 폐기사유 저장 ");
        Connection con = null;
        TaxMetaDao dao  = new TaxMetaDao();
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            dao.setResultMsg(uuid, msg, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
    }

    public void setReissueMsg(String uuid, String msg)  throws TaxInvoiceException, SQLException{
        System.out.println("[START setStatus in TaxManagementDao]:: 문서 재발행사유 저장 ");
        Connection con = null;
        TaxMetaDao dao  = new TaxMetaDao();
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            dao.setReissueMsg(uuid, msg, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
    }


    public byte[] getDocContents(String uuid)  throws TaxInvoiceException, SQLException{
        System.out.println("[START getSendDocList in TaxManagementDao]");
        Connection con = null;
        TaxContentsDao dao = new TaxContentsDao();
        TaxContentsVO contents = new TaxContentsVO();
        ArrayList data = new ArrayList();
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);
            contents = dao.selectByUuid(uuid, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getSendDocList in TaxManagementDao]");
        return contents.getContents();
    }




///////////////////////////////////////??????????
    public String getDocWriterType(String uuid) throws TaxInvoiceException{
        System.out.println("[START getCount]");
        Connection con = null;
        String prefix = "";
        TaxMetaVO vo = new TaxMetaVO();
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);
            TaxMetaDao dao = new TaxMetaDao();

            vo = dao.select(uuid, vo, con);
        }
        catch (SQLException e)
        {
            System.out.println(e);
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getCount]");
        return vo.getWriter_type();
    }


/*


/////////////////////////////////업체 및 담당자 관련
    public ArrayList getTaxSupplierBuyer(String biz_id, String type)  throws TaxInvoiceException, SQLException{
        System.out.println("[START getTaxSupplierBuyer in TaxManagementDao]");
        Connection con = null;
        TaxCompanyDao dao  = new TaxCompanyDao();
        ArrayList data = new ArrayList();
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);
            data = dao.getTaxSupplierBuyer( biz_id, type, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getTaxSupplierBuyer in TaxManagementDao]");
        return data;
    }


    public String getPersonId(TaxInvoiceVO vo, String type)  throws TaxInvoiceException, SQLException{
        System.out.println("[START getTaxSupplierBuyer in TaxManagementDao]");
        Connection con = null;
        String exist = "";
        TaxPersonDao dao  = new TaxPersonDao();
        ArrayList data = new ArrayList();
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);
            if(type.equals("Supplier")){
                exist = dao.getPersonIdByEmailName(vo.getMain().getSupplier_contactor_name(), vo.getMain().getSupplier_contactor_email(), con);
            }else if(type.equals("Buyer")){
                exist = dao.getPersonIdByEmailName(vo.getMain().getBuyer_contactor_name(), vo.getMain().getBuyer_contactor_email(), con);
            }
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getTaxSupplierBuyer in TaxManagementDao]");
        return exist;
    }

    public String whichEbmsConnect(String uuid) throws TaxInvoiceException{
        System.out.println("[START getTaxInvoiceVO]");
        Connection con = null;
        String prefix = "";
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            TaxCompanyDao dao = new TaxCompanyDao();
            System.out.println("uuid in save work:"+uuid);
            prefix = dao.whichEbmsConnect(uuid, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getTaxInvoiceVO]");
        return prefix;
    }

    public String isHanjunSeller(String uuid) throws TaxInvoiceException{
        System.out.println("[START getTaxInvoiceVO]");
        Connection con = null;
        String isHanjunSeller = "";
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            TaxCompanyDao dao = new TaxCompanyDao();
            System.out.println("uuid in save work:"+uuid);
            isHanjunSeller = dao.isHanjunSeller(uuid, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getTaxInvoiceVO]");
        return isHanjunSeller;
    }

// 2006.07.12 이제중
    public String IsNaubuSeller(String uuid) throws TaxInvoiceException{
        System.out.println("[START getTaxInvoiceVO]");
        Connection con = null;
        String isNaubuSeller = "";
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            TaxCompanyDao dao = new TaxCompanyDao();
            System.out.println("uuid in save work:"+uuid);
            isNaubuSeller = dao.IsNaubuSeller(uuid, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getTaxInvoiceVO]");
        return isNaubuSeller;
    }
*/
    public ArrayList getTaxInvList(String searchType, String id, String type, String name, String docState, String sDate, String eDate) throws TaxInvoiceException{
        System.out.println("[START getTaxInvList]");
        Connection con = null;
        ArrayList list = new ArrayList();
        DBConnector dbconn = new DBConnector();
        try {

            con = dbconn.getConnection();
            con.setAutoCommit(false);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getTaxInvList]");
        return list;
    }

    public String isUniqueVoucherSICode(TaxMetaVO vo) throws TaxInvoiceException{
        Connection con = null;
        String isExist = "";
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            TaxMetaDao dao = new TaxMetaDao();
            isExist = dao.isUniqueVoucherSICode(vo, con);
        }
        catch (SQLException e)
        {
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        logger.debug("[END getCount]");
        return isExist;
    }

    public String isUniqueSysTypeInfo(String bizId, String SystemTypeSel, String ConstructNoSel, String ReportTypeSel, String KisungChgNoSel, String uuid ) throws TaxInvoiceException{
        Connection con = null;
        String isExist = "";
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            TaxMetaDao dao = new TaxMetaDao();

            isExist = dao.isUniqueSysTypeInfo(bizId,SystemTypeSel,ConstructNoSel,ReportTypeSel,KisungChgNoSel,uuid,con);

        }
        catch (SQLException e)
        {
            logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        logger.debug("[END getCount]");
        return isExist;
    }

	public ArrayList getReceiveHeaderList() throws TaxInvoiceException {
		logger.debug("[START getReceiveHeaderList]");
		Connection con = null;
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			TaxMetaDao dao = new TaxMetaDao();
			data = dao.getReceiveHeaderList(con);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		logger.debug("[END getReceiveHeaderList]");
		return data;
	}

	public ArrayList getReceiveDetailList(TaxHeaderVO vo) throws TaxInvoiceException {
		logger.debug("[START getReceiveDetailList]");
		Connection con = null;
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			TaxMetaDao dao = new TaxMetaDao();
			data = dao.getReceiveDetailList(vo, con);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		logger.debug("[END getReceiveDetailList]");
		return data;
	}

	public void setStatusInfo(TaxHeaderVO vo, String uuid, String errmsg)  throws TaxInvoiceException, SQLException{
        logger.debug("[START setStatusInfo in TaxManagementDao]:: 연계테이블 상태정보 업데이트 ");
        Connection con = null;
        TaxMetaDao dao  = new TaxMetaDao();
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);

            dao.setHeaderState(vo, con);
            dao.insertStatusInfo(vo, uuid, errmsg, con);

            con.commit();
        }
        catch (SQLException e)
        {
            logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
    }

	public void updateStatusByBasicDate(String basicDate)  throws TaxInvoiceException, SQLException{
        logger.debug("[START updateStatusByBasicDate in TaxManagementDao]:: 일정기간 미확인 세금계산서 폐기처리 ");
        Connection con = null;
        TaxMetaDao dao  = new TaxMetaDao();
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);

            ArrayList uuidList = dao.selectStatusByBasicDate(basicDate, con);
            if (uuidList.size()>0) {
	            dao.updateStatusByBasicDate(basicDate, con);
	            dao.updateStatusInfo(uuidList, con);
            }
            con.commit();
        }
        catch (SQLException e)
        {
            logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
    }

	public ArrayList getReceiveContractorList() throws TaxInvoiceException {
		logger.debug("[START getReceiveContractorList]");
		Connection con = null;
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			TaxEaiContractorDao dao = new TaxEaiContractorDao();
			data = dao.getReceiveContractorList(con);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		logger.debug("[END getReceiveContractorList]");
		return data;
	}

	public void updateContractorInfo(TaxFinanceContectVO financeVO) throws TaxInvoiceException {
		logger.debug("[START updateContractorInfo]");
		Connection con = null;
		DBConnector dbconn = new DBConnector();

		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);

			//문서가 있는지 체크
			TaxMetaDao dao = new TaxMetaDao();
          	String isExist = dao.isExistSendTax(financeVO.getUuid(), con);

			if (isExist.equals("Y")) {

				//변경할  담당자 정보가 있는지 체크
				TaxPersonNewManagerDao persondao = new TaxPersonNewManagerDao();
				ArrayList personlist = new ArrayList();
				personlist = persondao.selectHanjunUserById(financeVO.getId());

				if (personlist.size()<1) {
					isExist = "E";
					logger.debug("계약담당자 변경처리 중 오류  : Uuid="+financeVO.getUuid()+":::contractor="+financeVO.getId()+" 해당 사번의 담당자정보가 없습니다.");
				} else {
					TaxPersonVO PersonVO = new TaxPersonVO();
					PersonVO = (TaxPersonVO)personlist.get(0);

					financeVO.setName(CommonUtil.nullToBlank(PersonVO.getName()));
					financeVO.setEmail(CommonUtil.nullToBlank(PersonVO.getEmail()));
					financeVO.setTel(CommonUtil.nullToBlank(PersonVO.getTel()));

//					계약담당자 업데이트
					TaxFinanceManagementDao fdao = new TaxFinanceManagementDao();
					fdao.save(financeVO);


				}

			} else {
				isExist = "E";
				logger.debug("계약담당자 변경처리 중 오류  : Uuid="+financeVO.getUuid()+":::contractor="+financeVO.getId()+" 해당 문서번호의 전송된 세금계산서가 없습니다.");
			}

			TaxEaiContractorDao eaidao = new TaxEaiContractorDao();
			eaidao.updateContractorInfo(financeVO, isExist, con);

			con.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		logger.debug("[END updateContractorInfo]");

	}
	/* 2009.08.06 */
	public void sandBySmsDate(String smsDate) throws TaxInvoiceException {
		logger.debug("[START sandBySmsDate in TaxManagementDao]:: 폐기되기 7일전 확인요청중인 세금계산서 SMS발송 ");

		Connection con = null;
        TaxMetaDao dao  = new TaxMetaDao();
        TaxInvoiceVO vo = new TaxInvoiceVO();
        DBConnector dbconn = new DBConnector();
        TaxSMSManagementDao smsDao = new TaxSMSManagementDao();
        try {
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            String receiver_comp_id="";
            String receiver_id="";
            String issueDay="";
            String comName="";
            String chaAmt="";
            String taxAmt="";
            String graAmt="";
            String email="";
            String itemName="";
            String uuid_lms="";
            
            
            /* 내선건중 폐기되기 7일전 RECEIVER_COMP_ID, RECEIVER_ID*/ 
            ArrayList receiverList = dao.selectStatusBySmsDate(smsDate, con);
            logger.debug("receiverList.size : "+receiverList.size());
            //System.out.println("smsDate=====================================>"+smsDate);
            if (receiverList.size()>0) {
            	logger.debug("폐기되기 7일전 receiverList.size() > 0 Start ");
            	//sendSMS
            	for(int i=0;i<receiverList.size();i++){
            		Map map = (Map)receiverList.get(i);
            		receiver_comp_id 	= map.get("RECEIVER_COMP_ID").toString();	//수신업체사업자번호
            		receiver_id 		= map.get("RECEIVER_ID").toString(); //수신자 아이디	
            		issueDay 			= map.get("DOC_DATE").toString();//작성일자
            		comName          	= map.get("SUPPLIER_NAME").toString();//회사명
            		chaAmt 				= map.get("CHARGE_AMT").toString();//공급가액
            		taxAmt           	= map.get("TOT_TAX_AMT").toString();//세액
            		graAmt				= map.get("TOT_AMT").toString();//총액
            		email				= map.get("SUPPLIER_CONTACTOR_EMAIL").toString();//메일
            		itemName			= map.get("NAME").toString();//품목
            		uuid_lms			= map.get("UUID").toString();//uuid
            		logger.debug("receiver_comp_id : "+receiver_comp_id);
            		logger.debug("receiver_id : "+receiver_id);
            		//해당 업체사용자 SMS 발송
            		String hp = dao.selectHpByReceiver(receiver_comp_id,receiver_id,con);
            		String uuid = vo.getUuid();
            		logger.debug("hp : "+hp);
            		if(hp != ""){
            			logger.debug(" Send SMS ");
            			String gubun="D";
            			//smsDao.sendSMS_new(hp,uuid);
            			smsDao.sendLMS(hp, uuid_lms, issueDay, comName, chaAmt, taxAmt, graAmt, email, itemName, gubun, vo);
            		}else{
            			logger.debug(" not value mobile ");
            		}
            	}
            }
        }catch (SQLException e){
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1){
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }finally {
			dbconn.closeConnection(con);
		}
		logger.debug("[END sandBySmsDate]");
	}
	
	/**
	 * 
	 * @throws TaxInvoiceException
	 */
	public void transmitList_F()throws TaxInvoiceException, Exception{
		logger.debug("[START transmitList in TaxManagementDao]:: FI ->> 전송");
		boolean trans = false; // 완료
		Connection con = null; 
        TaxMetaDao dao  = new TaxMetaDao();
        DBConnector dbconn = new DBConnector();
			try{
	            con = dbconn.getConnection();
	            con.setAutoCommit(false);
	            // STATUS_CODE = '02' , ELECTRONIC_REPORT_YN = 'F' : 동의안함 대상을 가져옴
	            ArrayList transmitList_F = dao.selectTrunce_F(con);
	            
	            if(transmitList_F.size() > 0){
	            	
	            	for(int i=0;i<transmitList_F.size();i++){
	            		//XML 생성
	            		System.out.println("#########################BINDDING START:"+CommonUtil.getCurrentTime());
	            		TaxInvRspVO vo = new TaxInvRspVO();

	            		Map map = (Map)transmitList_F.get(i);
	            		String valueChack = dao.getvalueCheck(CommonUtil.nullToBlank(map.get("MANAGEMENT_ID").toString()),con);
	            		System.out.println("valueChack : "+valueChack);
	            		
	            		if("0".equals(valueChack)){
	            			
		            		vo.setManagement_id(CommonUtil.nullToBlank(map.get("MANAGEMENT_ID").toString()));
		            		vo.setSeqno(CommonUtil.nullToBlank(map.get("SEQNO").toString()));
		            		vo.setExt_system_type(CommonUtil.nullToBlank(map.get("JOB_DEFINE").toString()));
		            		vo.setIssue_id(CommonUtil.nullToBlank(map.get("ISSUE_ID").toString()));
		            		vo.setDel_flag(CommonUtil.nullToBlank(map.get("NTS_REGIST_YN").toString()));
		            		vo.setStatus_txt(CommonUtil.nullToBlank(map.get("NTS_REGIST_TXT").toString()));
		            		vo.setIssue_day(CommonUtil.nullToBlank(map.get("ISSUE_DAY").toString()));
		            				            		
		            		TaxInvoiceRspUtil xmlUtil = new TaxInvoiceRspUtil();
		            		TaxInvoiceRspVOToObjUtil util = new TaxInvoiceRspVOToObjUtil(vo);
		            		TaxInvoice tax = util.getTaxInvoiceResponse();
		            		
		            		ByteArrayOutputStream baos = null;
		            		baos = xmlUtil.generateXML(tax);

		            		TaxContentsDao contentsdao = new TaxContentsDao();
		            		//xml 생성후 저장 ETS_ERP_XML_INFO_TB insert
		            		contentsdao.insert_FI(vo.getManagement_id(), baos.toByteArray(), con);
		            		
		            		//xml TB_TAX_BILL_INFO -> Y update
		            		dao.UpdateIssue_id(vo.getIssue_id(),con);
		            		
	            		}else{
	            			System.out.println(" f 해당정보 생성되어져 있음");
	            		}
	            		System.out.println("##########################BINDDING END:"+CommonUtil.getCurrentTime());
	            	}
	            	con.commit();
	            	System.out.println(">>>> f생성완료 처리완료. ");
	            	
	            }else{
	            	System.out.println(">>>> f데이터 없음. ");
	            }
	            ////////////////////////////////////////////////////////////////////////////
	            ////////////////////////////////////////////////////////////////////////////
			}catch (SQLException e){
	        	logger.debug(e);
	            try {
	                con.rollback();
	            } catch (SQLException e1){
	                e1.printStackTrace();
	            }
	            throw new TaxInvoiceException(this, e);
			}catch(Exception ee){
				System.out.println(ee.toString());
			}finally {
				dbconn.closeConnection(con);
			}
		//return trans;
	}
	
	/**
	 * 
	 * @throws TaxInvoiceException
	 * @throws Exception
	 */
	public void transmitList_Y()throws TaxInvoiceException, Exception{
		logger.debug("[START transmitList in TaxManagementDao]:: FI ->> 전송");
		boolean trans = false; // 완료
		Connection con = null; 
        TaxMetaDao dao  = new TaxMetaDao();
        DBConnector dbconn = new DBConnector();
        try{
            con = dbconn.getConnection();
            con.setAutoCommit(false);
            ArrayList transmitList_Y = dao.selectTrunce_Y(con);
            
            if(transmitList_Y.size() > 0){
            	for(int j=0;j<transmitList_Y.size();j++){
            		//XML 생성
            		logger.debug("#########################BINDDING START:"+CommonUtil.getCurrentTime());
            		TaxInvRspVO vo = new TaxInvRspVO();
            		Map map = (Map)transmitList_Y.get(j);
            		
            		String valueChack = dao.getvalueCheck(CommonUtil.nullToBlank(map.get("MANAGEMENT_ID").toString()),con);
            		
            		System.out.println("valueChack : "+valueChack);
            		if("0".equals(valueChack)){
            			
	            		vo.setManagement_id(CommonUtil.nullToBlank(map.get("MANAGEMENT_ID").toString()));
	            		vo.setSeqno(CommonUtil.nullToBlank(map.get("SEQNO").toString()));
	            		vo.setExt_system_type(CommonUtil.nullToBlank(map.get("JOB_DEFINE").toString()));
	            		vo.setIssue_id(CommonUtil.nullToBlank(map.get("ISSUE_ID").toString()));
	            		vo.setDel_flag(CommonUtil.nullToBlank(map.get("NTS_REGIST_YN").toString()));
	            		vo.setStatus_txt(CommonUtil.nullToBlank(map.get("NTS_REGIST_TXT").toString()));
	            		vo.setIssue_day(CommonUtil.nullToBlank(map.get("ISSUE_DAY").toString()));
	            		vo.setEsero_finish_ts(CommonUtil.nullToBlank(map.get("ESERO_FINISH_TS").toString())); // 세금계산서 국세청신고완료일시 2013.04.22 장지호
	            		
	            		TaxInvoiceRspUtil xmlUtil = new TaxInvoiceRspUtil();
	            		TaxInvoiceRspVOToObjUtil util = new TaxInvoiceRspVOToObjUtil(vo);
	            		TaxInvoice tax = util.getTaxInvoiceResponse();
	            		
	            		ByteArrayOutputStream baos = null;
	            		baos = xmlUtil.generateXML(tax);
	            		//System.out.println("baos : "+baos.toByteArray());
	            		
	            		TaxContentsDao contentsdao = new TaxContentsDao();
	            		contentsdao.insert_FI(vo.getManagement_id(), baos.toByteArray(), con);
	            		//xml TB_TAX_BILL_INFO -> Y update
	            		dao.UpdateIssue_id(vo.getIssue_id(),con);
	            		
            		}else{
            			
            			System.out.println(" f 해당정보 생성되어져 있음");
            			
            		}
            		logger.debug("##########################BINDDING END:"+CommonUtil.getCurrentTime());
            	}
            	logger.debug("Y-----");
            	con.commit();
            	System.out.println(">>>> y생성완료 처리완료. ");
            }else{
            	System.out.println(">>>> y데이터 없음. ");
            }
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
        }catch (SQLException e){
        	logger.debug(e);
            try {
                con.rollback();
            } catch (SQLException e1){
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
		}catch(Exception ee){
			System.out.println(ee.toString());
		}finally {
			dbconn.closeConnection(con);
		}
	}
	
	/**
	 * 
	 * @param tax_Date
	 * @throws TaxInvoiceException
	 * @throws Exception
	 */
	public void tax_sms_mail(String tax_Date)throws TaxInvoiceException, Exception{
		System.out.println("[START sms,mail list in TaxManagementDao]:: 한전매출 SMS , MAIL 보내기 ");
		Connection con = null; 
        TaxMetaDao dao  = new TaxMetaDao();							//
        DBConnector dbconn = new DBConnector();						//
        TaxSMSManagementDao smsDao = new TaxSMSManagementDao();		//
        TaxMailManagementDao mailDao = new TaxMailManagementDao();	//

        try{
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			/* 대상 select */
			ArrayList list = dao.getTax_Sms_Mail_List(tax_Date,con);
			
			if(list.size() > 0)
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					/* sms , mail 
					System.out.println("1 :"+map.get("IO_CODE").toString());
					System.out.println("2 :"+map.get("BIZ_MANAGE_ID").toString());
					System.out.println("3 :"+map.get("INVOICEE_PARTY_ID").toString());
					System.out.println("4 :"+map.get("INVOICER_PARTY_ID").toString());
					System.out.println("5 :"+map.get("INVOICER_PARTY_NAME").toString());
					System.out.println("6 :"+map.get("INVOICEE_PARTY_NAME").toString());
					System.out.println("7 :"+map.get("INVOICEE_CONTACT_PHONE1").toString());
					System.out.println("8 :"+map.get("INVOICEE_CONTACT_EMAIL1").toString());
					System.out.println("9 :"+map.get("INVOICEE_CONTACT_NAME1").toString());
					System.out.println("10 :"+map.get("INVOICEE_CONTACT_PHONE2").toString());
					System.out.println("11 :"+map.get("INVOICEE_CONTACT_EMAIL2").toString());
					System.out.println("12 :"+map.get("INVOICEE_CONTACT_NAME2").toString());
					System.out.println("13 :"+map.get("INVOICER_CONTACT_PHONE").toString());
					System.out.println("14 :"+map.get("INVOICER_CONTACT_EMAIL").toString());
					System.out.println("15 :"+map.get("INVOICER_CONTACT_NAME").toString());
					System.out.println("16 :"+map.get("ISSUE_ID").toString());
					*/
					
					//20170213 윤규미( 문서번호 추가)
					//smsDao.tax_SendSMS(map.get("INVOICEE_CONTACT_PHONE1").toString(),map.get("ISSUE_ID").toString());// 공급받는자 담당자 전화번호1
					
					
					mailDao.tax_seadMail(map.get("INVOICER_PARTY_NAME").toString(), 
							map.get("INVOICER_PARTY_ID").toString(), 
							map.get("INVOICER_CONTACT_NAME").toString(), 
							map.get("INVOICER_CONTACT_PHONE").toString(), 
							map.get("ISSUE_ID").toString(), 
							map.get("INVOICEE_CONTACT_EMAIL1").toString(), 
							map.get("INVOICEE_CONTACT_NAME1").toString());
					
					
					
					
					//20180122 윤규미(sms->lms)
					System.out.println("=============================tax_sendLMS Start===================");
					smsDao.tax_SendLMS(map.get("INVOICEE_CONTACT_PHONE1").toString(),
									   map.get("SVC_MANAGE_ID").toString(),
									   map.get("ISSUE_DAY").toString(),
									   map.get("INVOICER_PARTY_NAME").toString(),
									   map.get("CHARGE_TOTAL_AMOUNT").toString(),
									   map.get("TAX_TOTAL_AMOUNT").toString(),
									   map.get("GRAND_TOTAL_AMOUNT").toString(),
									   map.get("INVOICEE_CONTACT_EMAIL1").toString(),
									   map.get("ITEM_NAME").toString());
					System.out.println("=============================tax_sendLMS End===================");
					
					//20170213 윤규미( 문서번호 추가)
					//SmsDao.tax_SendSMS(map.get("INVOICEE_CONTACT_PHONE2").toString(),map.get("ISSUE_ID").toString());// 공급받는자 담당자 전화번호2
					
					
					//20180122 윤규미(sms->lms)
					System.out.println("=============================tax_sendLMS Start===================");
					smsDao.tax_SendLMS(map.get("INVOICEE_CONTACT_PHONE2").toString(),
							   map.get("SVC_MANAGE_ID").toString(),
							   map.get("ISSUE_DAY").toString(),
							   map.get("INVOICER_PARTY_NAME").toString(),
							   map.get("CHARGE_TOTAL_AMOUNT").toString(),
							   map.get("TAX_TOTAL_AMOUNT").toString(),
							   map.get("GRAND_TOTAL_AMOUNT").toString(),
							   map.get("INVOICEE_CONTACT_EMAIL1").toString(),
							   map.get("ITEM_NAME").toString());
					System.out.println("=============================tax_sendLMS End===================");
					
					
					
					
					mailDao.tax_seadMail(map.get("INVOICER_PARTY_NAME").toString(), 
											map.get("INVOICER_PARTY_ID").toString(), 
											map.get("INVOICER_CONTACT_NAME").toString(), 
											map.get("INVOICER_CONTACT_PHONE").toString(), 
											map.get("ISSUE_ID").toString(), 
											map.get("INVOICEE_CONTACT_EMAIL2").toString(), 
											map.get("INVOICEE_CONTACT_NAME2").toString());
				}
		}catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
		System.out.println("[END sms,mail list in TaxManagementDao]:: 한전매출 SMS , MAIL 보내기 ");
	}
	
	/**
	 * 
	 * @return
	 * @throws TaxInvoiceException
	 * @throws Exception
	public ArrayList getNcisReceiveHeaderList()throws TaxInvoiceException, Exception{
		System.out.println("[ Start getNcisReceiveHeaderList ]");
		
		Connection con = null;
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		
		try {
			
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			TaxMetaDao dao = new TaxMetaDao();
			data = dao.getNcisReceiveHeaderList(con);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		System.out.println("[ END getNcisReceiveHeaderList	]");
		return data;
	}
	 */
	
	/**
	 * 
	 * @param vo
	 * @return
	 * @throws TaxInvoiceException
	public ArrayList getNcisReceiveDetailList(TaxHeaderVO vo) throws TaxInvoiceException {
		System.out.println("[ START getNcisReceiveDetailList ]");
		Connection con = null;
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			TaxMetaDao dao = new TaxMetaDao();
			data = dao.getNcisReceiveDetailList(vo, con);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		System.out.println("[ END getNcisReceiveDetailList ]");
		return data;
	}
	 */
}