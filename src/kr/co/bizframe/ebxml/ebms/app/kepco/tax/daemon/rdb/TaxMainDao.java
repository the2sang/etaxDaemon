/*
 * Created on 2005. 8. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMainVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */   
public class TaxMainDao {
    public void insert(TaxMainVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START INSERT ETS_TAX_MAIN_INFO_TB]");
        PreparedStatement ps = null;
        try{  
            String sql =
                " INSERT INTO ETS_TAX_MAIN_INFO_TB(  "   +
                "       UUID,                    MANAGEMENT_ID,           DOC_DATE, "   +
                "       BLANK_NUM,               DOC_TYPE_CODE,           DEMAND_TYPE, "   +
                "       VOLUM_ID,                ISSUE_ID,                SEQ_ID, "   +
                "       REF_PUR_ORD_ID,          REF_PUR_ORD_DATE,        REF_INV_DOC_ID, "   +
                "       REF_INV_DOC_DATE,        REF_OTHER_DOC_ID,        DOC_DESC, "   +
                "       IMPORT_REG_ID,           BATCH_ISSUE_SDATE,       BATCH_ISSUE_EDATE, "   +
                "       TOT_IMPROT_CNT,          SUPPLIER_BIZ_ID,         SUPPLIER_ID, "   +
                "       SUPPLIER_NAME,           SUPPLIER_PRESIDENT_NAME, SUPPLIER_ADDR, "   +
                "       SUPPLIER_BIZ_TYPE,       SUPPLIER_BIZ_CLASS,      SUPPLIER_CONTACTOR_DEPT, "   +
                "       SUPPLIER_CONTACTOR_NAME, SUPPLIER_CONTACTOR_TEL,  SUPPLIER_CONTACTOR_EMAIL, "   +
                "       BUYER_BIZ_ID,            BUYER_ID,                BUYER_NAME, "   +
                "       BUYER_PRESIDENT_NAME,    BUYER_ADDR,              BUYER_BIZ_TYPE, "   +
                "       BUYER_BIZ_CLASS,         BUYER_CONTACTOR_DEPT,    BUYER_CONTACTOR_NAME, "   +
                "       BUYER_CONTACTOR_TEL,     BUYER_CONTACTOR_EMAIL ,                        "   +
                "       CHARGE_AMT,              TOT_TAX_AMT,             TOT_FORE_AMT,            " +
                "       TOT_QUANTITY,            TOT_AMT,    "   +
                "       PAYMENT_CASH_DC_AMT,     PAYMENT_CASH_FC_AMT,     PAYMENT_CHECK_DC_AMT, " +
                "       PAYMENT_CHECK_FC_AMT,    PAYMENT_BILL_DC_AMT,     PAYMENT_BILL_FC_AMT, " +
                "       PAYMENT_CREDIT_DC_AMT,   PAYMENT_CREDIT_FC_AMT,	  BUSINESS_TYPE_CODE "+

                //"		,NTS_ISSUE_ID,			 BEF_NTS_ISSUE_ID,		  MODIFY_CODE,		"+
                //"		SUPPLIER_BIZ_CD,		 " +
                "		,BUYER_BIZ_CD,			  DOC_TYPE_DETAIL,	 PURPOSE_CODE"+
                "       ) VALUES ("   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,   "   +
                "       ?,?,?, "   +
                "       ?,?,   "   +
                "       ?,?,?, "   +
                "       ?,?,?, "   +
                "       ?,?,?  "   +
                "		,?,?,?   "   +
                "       )      "  ;

            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
//          "       UUID,                    MANAGEMENT_ID,           DOC_DATE, "   +
            ps.setString(1, vo.getUuid());
            ps.setString(2, vo.getManagement_id());
            ps.setString(3, vo.getDoc_date());
//          "       BLANK_NUM,               DOC_TYPE_CODE,           DEMAND_TYPE, "   +
            ps.setString(4, vo.getBlank_num());
            ps.setString(5, vo.getDoc_type_code());
            ps.setString(6, vo.getDemand_type());
//          "       VOLUM_ID,                ISSUE_ID,                SEQ_ID, "   +
            ps.setString(7, vo.getVolum_id());
            ps.setString(8, vo.getIssue_id());
            ps.setString(9, vo.getSeq_id());
//          "       REF_PUR_ORD_ID,          REF_PUR_ORD_DATE,        REF_INV_DOC_ID, "   +
            ps.setString(10, vo.getRef_pur_ord_id());
            ps.setString(11, vo.getRef_pur_ord_date());
            ps.setString(12, vo.getRef_inv_doc_id());
//          "       REF_INV_DOC_DATE, REF_OTHER_DOC_ID, DOC_DESC, "   +
            ps.setString(13, vo.getRef_inv_doc_date());
            ps.setString(14, vo.getRef_other_doc_id());
            ps.setString(15, vo.getDoc_desc());
//          "       IMPORT_REG_ID, BATCH_ISSUE_SDATE, BATCH_ISSUE_EDATE, "   +
            ps.setString(16, vo.getImport_reg_id());
            ps.setString(17, vo.getBatch_issue_sdate());
            ps.setString(18, vo.getBatch_issue_edate());
//          "       TOT_IMPROT_CNT, SUPPLIER_BIZ_ID, SUPPLIER_ID, "   +
            ps.setString(19, vo.getTot_improt_cnt());
            ps.setString(20, vo.getSupplier_biz_id());
            ps.setString(21, vo.getSupplier_id());
//          "       SUPPLIER_NAME, SUPPLIER_PRESIDENT_NAME, SUPPLIER_ADDR, "   +
            ps.setString(22, vo.getSupplier_name());
            ps.setString(23, vo.getSupplier_president_name());
            ps.setString(24, vo.getSupplier_addr());
//          "       SUPPLIER_BIZ_TYPE, SUPPLIER_BIZ_CLASS, SUPPLIER_CONTACTOR_DEPT, "   +
            ps.setString(25, vo.getSupplier_biz_type());
            ps.setString(26, vo.getSupplier_biz_class());
            ps.setString(27, vo.getSupplier_contactor_dept());
//          "       SUPPLIER_CONTACTOR_NAME, SUPPLIER_CONTACTOR_TEL, SUPPLIER_CONTACTOR_EMAIL, "   +
            ps.setString(28, vo.getSupplier_contactor_name());
            ps.setString(29, vo.getSupplier_contactor_tel());
            ps.setString(30, vo.getSupplier_contactor_email());
//          "       BUYER_BIZ_ID, BUYER_ID, BUYER_NAME, "   +
            ps.setString(31, vo.getBuyer_biz_id());
            ps.setString(32, vo.getBuyer_id());
            ps.setString(33, vo.getBuyer_name());
//          "       BUYER_PRESIDENT_NAME, BUYER_ADDR, BUYER_BIZ_TYPE, "   +
            ps.setString(34, vo.getBuyer_president_name());
            ps.setString(35, vo.getBuyer_addr());
            ps.setString(36, vo.getBuyer_biz_type());
//          "       BUYER_BIZ_CLASS, BUYER_CONTACTOR_DEPT, BUYER_CONTACTOR_NAME, "   +
            ps.setString(37, vo.getBuyer_biz_class());
            ps.setString(38, vo.getBuyer_contactor_dept());
            ps.setString(39, vo.getBuyer_contactor_name());
//          "       BUYER_CONTACTOR_TEL, BUYER_CONTACTOR_EMAIL"
            ps.setString(40, vo.getBuyer_contactor_tel());
            ps.setString(41, vo.getBuyer_contactor_email());
//          SET SUMMARY INFO
            ps.setString(42, vo.getCharge_amt());
            ps.setString(43, vo.getTot_tax_amt());
            ps.setString(44, vo.getTot_fore_amt());

            ps.setString(45, vo.getTot_quantity());
            ps.setString(46, vo.getTot_amt());

            ps.setString(47, vo.getPayment_cash_dc_amt());
            ps.setString(48, vo.getPayment_cash_fc_amt());

            ps.setString(49, vo.getPayment_check_dc_amt());
            ps.setString(50, vo.getPayment_check_fc_amt());

            ps.setString(51, vo.getPayment_bill_dc_amt());
            ps.setString(52, vo.getPayment_bill_fc_amt());

            ps.setString(53, vo.getPayment_credit_dc_amt());
            ps.setString(54, vo.getPayment_credit_fc_amt());

            ps.setString(55, vo.getBusiness_type_code());

            // 200911 추가 KHS
            //ps.setString(56, vo.getNts_issue_id()); 		// 국세청 승인번호
            //ps.setString(57, vo.getBef_nts_issue_id()); 	// 국세청 이전 승인번호
            //ps.setString(58, vo.getModify_code()); 			// 수정사유코드
            //ps.setString(59, vo.getSupplier_biz_cd());		// 공급자 종사업장 번호
            ps.setString(56, vo.getBuyer_biz_cd());			// 공급받는자 종사업장 번호
            ps.setString(57, vo.getDoc_type_detail());		// 세금계산서 종류 코드
            ps.setString(58, "02");		// PURPOSE_CODE (01 : 영수, 02 : 청구)
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END INSERT ETS_TAX_MAIN_INFO_TB]");
    }

    public void update(TaxMainVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START UPDATE ETS_TAX_MAIN_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql =
                " UPDATE ETS_TAX_MAIN_INFO_TB SET  "   +
                "       MANAGEMENT_ID = ? ,           DOC_DATE = ? , "   +
                "       BLANK_NUM = ? ,               DOC_TYPE_CODE = ? ,           DEMAND_TYPE = ? , "   +
                "       VOLUM_ID = ? ,                ISSUE_ID = ? ,                SEQ_ID = ? , "   +
                "       REF_PUR_ORD_ID = ? ,          REF_PUR_ORD_DATE = ? ,        REF_INV_DOC_ID = ? , "   +
                "       REF_INV_DOC_DATE = ? ,        REF_OTHER_DOC_ID = ? ,        DOC_DESC = ? , "   +
                "       IMPORT_REG_ID = ? ,           BATCH_ISSUE_SDATE = ? ,       BATCH_ISSUE_EDATE = ? , "   +
                "       TOT_IMPROT_CNT = ? ,          SUPPLIER_BIZ_ID = ? ,         SUPPLIER_ID = ? , "   +
                "       SUPPLIER_NAME = ? ,           SUPPLIER_PRESIDENT_NAME = ? , SUPPLIER_ADDR = ? , "   +
                "       SUPPLIER_BIZ_TYPE = ? ,       SUPPLIER_BIZ_CLASS = ? ,      SUPPLIER_CONTACTOR_DEPT = ? , "   +
                "       SUPPLIER_CONTACTOR_NAME = ? , SUPPLIER_CONTACTOR_TEL = ? ,  SUPPLIER_CONTACTOR_EMAIL = ? , "   +
                "       BUYER_BIZ_ID = ? ,            BUYER_ID = ? ,                BUYER_NAME = ? , "   +
                "       BUYER_PRESIDENT_NAME = ? ,    BUYER_ADDR = ? ,              BUYER_BIZ_TYPE = ? , "   +
                "       BUYER_BIZ_CLASS = ? ,         BUYER_CONTACTOR_DEPT = ? ,    BUYER_CONTACTOR_NAME = ? , "   +
                "       BUYER_CONTACTOR_TEL = ? ,     BUYER_CONTACTOR_EMAIL  = ?,                              "   +
                "       CHARGE_AMT = ? ,              TOT_TAX_AMT = ? ,             TOT_FORE_AMT = ? ,            " +
                "       TOT_QUANTITY = ? ,            TOT_AMT = ?, "   +
                "       PAYMENT_CASH_DC_AMT = ? ,     PAYMENT_CASH_FC_AMT = ? ,     PAYMENT_CHECK_DC_AMT = ? , " +
                "       PAYMENT_CHECK_FC_AMT = ? ,    PAYMENT_BILL_DC_AMT = ? ,     PAYMENT_BILL_FC_AMT = ? , " +
                "       PAYMENT_CREDIT_DC_AMT = ? ,   PAYMENT_CREDIT_FC_AMT = ? ,   BUSINESS_TYPE_CODE = ?   "+

                "		,NTS_ISSUE_ID = ? ,			  BEF_NTS_ISSUE_ID = ? ,		MODIFY_CODE = ? ,	"+
                "		SUPPLIER_BIZ_CD = ? ,		  BUYER_BIZ_CD = ? ,			DOC_TYPE_DETAIL = ?	"+
                " WHERE UUID = ?   ";


            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
//          "       UUID,                    MANAGEMENT_ID,           DOC_DATE, "   +

            ps.setString(1, vo.getManagement_id());
            ps.setString(2, vo.getDoc_date());
//          "       BLANK_NUM,               DOC_TYPE_CODE,           DEMAND_TYPE, "   +
            ps.setString(3, vo.getBlank_num());
            ps.setString(4, vo.getDoc_type_code());
            ps.setString(5, vo.getDemand_type());
//          "       VOLUM_ID,                ISSUE_ID,                SEQ_ID, "   +
            ps.setString(6, vo.getVolum_id());
            ps.setString(7, vo.getIssue_id());
            ps.setString(8, vo.getSeq_id());
//          "       REF_PUR_ORD_ID,          REF_PUR_ORD_DATE,        REF_INV_DOC_ID, "   +
            ps.setString(9, vo.getRef_pur_ord_id());
            ps.setString(10, vo.getRef_pur_ord_date());
            ps.setString(11, vo.getRef_inv_doc_id());
//          "       REF_INV_DOC_DATE, REF_OTHER_DOC_ID, DOC_DESC, "   +
            ps.setString(12, vo.getRef_inv_doc_date());
            ps.setString(13, vo.getRef_other_doc_id());
            ps.setString(14, vo.getDoc_desc());
//          "       IMPORT_REG_ID, BATCH_ISSUE_SDATE, BATCH_ISSUE_EDATE, "   +
            ps.setString(15, vo.getImport_reg_id());
            ps.setString(16, vo.getBatch_issue_sdate());
            ps.setString(17, vo.getBatch_issue_edate());
//          "       TOT_IMPROT_CNT, SUPPLIER_BIZ_ID, SUPPLIER_ID, "   +
            ps.setString(18, vo.getTot_improt_cnt());
            ps.setString(19, vo.getSupplier_biz_id());
            ps.setString(20, vo.getSupplier_id());
//          "       SUPPLIER_NAME, SUPPLIER_PRESIDENT_NAME, SUPPLIER_ADDR, "   +
            ps.setString(21, vo.getSupplier_name());
            System.out.println("vo.getSupplier_president_name():::::"+vo.getSupplier_president_name());
            ps.setString(22, vo.getSupplier_president_name());
            ps.setString(23, vo.getSupplier_addr());
//          "       SUPPLIER_BIZ_TYPE, SUPPLIER_BIZ_CLASS, SUPPLIER_CONTACTOR_DEPT, "   +
            ps.setString(24, vo.getSupplier_biz_type());
            ps.setString(25, vo.getSupplier_biz_class());
            ps.setString(26, vo.getSupplier_contactor_dept());
//          "       SUPPLIER_CONTACTOR_NAME, SUPPLIER_CONTACTOR_TEL, SUPPLIER_CONTACTOR_EMAIL, "   +
            ps.setString(27, vo.getSupplier_contactor_name());
            ps.setString(28, vo.getSupplier_contactor_tel());
            ps.setString(29, vo.getSupplier_contactor_email());
//          "       BUYER_BIZ_ID, BUYER_ID, BUYER_NAME, "   +
            ps.setString(30, vo.getBuyer_biz_id());
            ps.setString(31, vo.getBuyer_id());
            ps.setString(32, vo.getBuyer_name());
//          "       BUYER_PRESIDENT_NAME, BUYER_ADDR, BUYER_BIZ_TYPE, "   +
            ps.setString(33, vo.getBuyer_president_name());
            System.out.println("vo.getBuyer_president_name():::::"+vo.getBuyer_president_name());
            ps.setString(34, vo.getBuyer_addr());
            ps.setString(35, vo.getBuyer_biz_type());
//          "       BUYER_BIZ_CLASS, BUYER_CONTACTOR_DEPT, BUYER_CONTACTOR_NAME, "   +
            ps.setString(36, vo.getBuyer_biz_class());
            ps.setString(37, vo.getBuyer_contactor_dept());
            ps.setString(38, vo.getBuyer_contactor_name());
//          "       BUYER_CONTACTOR_TEL, BUYER_CONTACTOR_EMAIL"
            ps.setString(39, vo.getBuyer_contactor_tel());
            ps.setString(40, vo.getBuyer_contactor_email());

//          SET SUMMARY INFO
            ps.setString(41, vo.getCharge_amt());
            ps.setString(42, vo.getTot_tax_amt());
            ps.setString(43, vo.getTot_fore_amt());

            ps.setString(44, vo.getTot_quantity());
            ps.setString(45, vo.getTot_amt());

            ps.setString(46, vo.getPayment_cash_dc_amt());
            ps.setString(47, vo.getPayment_cash_fc_amt());
            ps.setString(48, vo.getPayment_cash_dc_amt());

            ps.setString(49, vo.getPayment_cash_fc_amt());
            ps.setString(50, vo.getPayment_bill_dc_amt());
            ps.setString(51, vo.getPayment_bill_fc_amt());

            ps.setString(52, vo.getPayment_credit_dc_amt());
            ps.setString(53, vo.getPayment_credit_fc_amt());

            ps.setString(54, vo.getBusiness_type_code());

            // 200911 추가 KHS
            ps.setString(55, vo.getNts_issue_id());
            ps.setString(56, vo.getBef_nts_issue_id());
            ps.setString(57, vo.getModify_code());
            ps.setString(58, vo.getSupplier_biz_cd());
            ps.setString(59, vo.getBuyer_biz_cd());
            ps.setString(60, vo.getDoc_type_detail());

            ps.setString(61, vo.getUuid());

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_MAIN_INFO_TB]");
    }

    public TaxMainVO select(String uuid, TaxMainVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START SELECT ETS_TAX_MAIN_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql =
                "SELECT UUID,                    MANAGEMENT_ID,           DOC_DATE, "   +
                "       BLANK_NUM,               DOC_TYPE_CODE,           DEMAND_TYPE, "   +
                "       VOLUM_ID,                ISSUE_ID,                SEQ_ID, "   +
                "       REF_PUR_ORD_ID,          REF_PUR_ORD_DATE,        REF_INV_DOC_ID, "   +
                "       REF_INV_DOC_DATE,        REF_OTHER_DOC_ID,        DOC_DESC, "   +
                "       IMPORT_REG_ID,           BATCH_ISSUE_SDATE,       BATCH_ISSUE_EDATE, "   +
                "       TOT_IMPROT_CNT,          SUPPLIER_BIZ_ID,         SUPPLIER_ID, "   +
                "       SUPPLIER_NAME,           SUPPLIER_PRESIDENT_NAME, SUPPLIER_ADDR, "   +
                "       SUPPLIER_BIZ_TYPE,       SUPPLIER_BIZ_CLASS,      SUPPLIER_CONTACTOR_DEPT, "   +
                "       SUPPLIER_CONTACTOR_NAME, SUPPLIER_CONTACTOR_TEL,  SUPPLIER_CONTACTOR_EMAIL, "   +
                "       BUYER_BIZ_ID,            BUYER_ID,                BUYER_NAME, "   +
                "       BUYER_PRESIDENT_NAME,    BUYER_ADDR,              BUYER_BIZ_TYPE, "   +
                "       BUYER_BIZ_CLASS,         BUYER_CONTACTOR_DEPT,    BUYER_CONTACTOR_NAME, "   +
                "       BUYER_CONTACTOR_TEL,     BUYER_CONTACTOR_EMAIL,                               "   +
                "       CHARGE_AMT,              TOT_TAX_AMT,             TOT_FORE_AMT,            " +
                "       TOT_QUANTITY,            TOT_AMT,       "   +
                "       PAYMENT_CASH_DC_AMT,     PAYMENT_CASH_FC_AMT,     PAYMENT_CHECK_DC_AMT, " +
                "       PAYMENT_CHECK_FC_AMT,    PAYMENT_BILL_DC_AMT,     PAYMENT_BILL_FC_AMT, " +
                "       PAYMENT_CREDIT_DC_AMT,   PAYMENT_CREDIT_FC_AMT,	  BUSINESS_TYPE_CODE,   "  +
                "       ASP_ISSUE_ID   " +      //2011.12.15 추가
                "  FROM ETS_TAX_MAIN_INFO_TB " +
                " WHERE UUID = ?  "  ;



            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                vo.setUuid(CommonUtil.justNullToBlank(rs.getString(1)));
                vo.setManagement_id(CommonUtil.justNullToBlank(rs.getString(2)));
                vo.setDoc_date(CommonUtil.justNullToBlank(rs.getString(3)));

                vo.setBlank_num(CommonUtil.justNullToBlank(rs.getString(4)));
                vo.setDoc_type_code(CommonUtil.justNullToBlank(rs.getString(5)));
                vo.setDemand_type(CommonUtil.justNullToBlank(rs.getString(6)));

                vo.setVolum_id(CommonUtil.justNullToBlank(rs.getString(7)));
                vo.setIssue_id(CommonUtil.justNullToBlank(rs.getString(8)));
                vo.setSeq_id(CommonUtil.justNullToBlank(rs.getString(9)));

                vo.setRef_pur_ord_id(CommonUtil.justNullToBlank(rs.getString(10)));
                vo.setRef_pur_ord_date(CommonUtil.justNullToBlank(rs.getString(11)));
                vo.setRef_inv_doc_id(CommonUtil.justNullToBlank(rs.getString(12)));

                vo.setRef_inv_doc_date(CommonUtil.justNullToBlank(rs.getString(13)));
                vo.setRef_other_doc_id(CommonUtil.justNullToBlank(rs.getString(14)));
                vo.setDoc_desc(CommonUtil.justNullToBlank(rs.getString(15)));

                vo.setImport_reg_id(CommonUtil.justNullToBlank(rs.getString(16)));
                vo.setBatch_issue_sdate(CommonUtil.justNullToBlank(rs.getString(17)));
                vo.setBatch_issue_edate(CommonUtil.justNullToBlank(rs.getString(18)));

                vo.setTot_improt_cnt(CommonUtil.justNullToBlank(rs.getString(19)));
                vo.setSupplier_biz_id(CommonUtil.justNullToBlank(rs.getString(20)));
                vo.setSupplier_id(CommonUtil.justNullToBlank(rs.getString(21)));

                vo.setSupplier_name(CommonUtil.justNullToBlank(rs.getString(22)));
                vo.setSupplier_president_name(CommonUtil.justNullToBlank(rs.getString(23)));
                vo.setSupplier_addr(CommonUtil.justNullToBlank(rs.getString(24)));

                vo.setSupplier_biz_type(CommonUtil.justNullToBlank(rs.getString(25)));
                vo.setSupplier_biz_class(CommonUtil.justNullToBlank(rs.getString(26)));
                vo.setSupplier_contactor_dept(CommonUtil.justNullToBlank(rs.getString(27)));

                vo.setSupplier_contactor_name(CommonUtil.justNullToBlank(rs.getString(28)));
                vo.setSupplier_contactor_tel(CommonUtil.justNullToBlank(rs.getString(29)));
                vo.setSupplier_contactor_email(CommonUtil.justNullToBlank(rs.getString(30)));

                vo.setBuyer_biz_id(CommonUtil.justNullToBlank(rs.getString(31)));
                vo.setBuyer_id(CommonUtil.justNullToBlank(rs.getString(32)));
                vo.setBuyer_name(CommonUtil.justNullToBlank(rs.getString(33)));
                vo.setBuyer_president_name(CommonUtil.justNullToBlank(rs.getString(34)));
                vo.setBuyer_addr(CommonUtil.justNullToBlank(rs.getString(35)));
                vo.setBuyer_biz_type(CommonUtil.justNullToBlank(rs.getString(36)));

                vo.setBuyer_biz_class(CommonUtil.justNullToBlank(rs.getString(37)));
                vo.setBuyer_contactor_dept(CommonUtil.justNullToBlank(rs.getString(38)));
                vo.setBuyer_contactor_name(CommonUtil.justNullToBlank(rs.getString(39)));

                vo.setBuyer_contactor_tel(CommonUtil.justNullToBlank(rs.getString(40)));
                vo.setBuyer_contactor_email(CommonUtil.justNullToBlank(rs.getString(41)));

//              SET SUMMARY INFO
                vo.setCharge_amt(CommonUtil.justNullToBlank(rs.getString(42)));
                vo.setTot_tax_amt(CommonUtil.justNullToBlank(rs.getString(43)));
                vo.setTot_fore_amt(CommonUtil.justNullToBlank(rs.getString(44)));

                vo.setTot_quantity(CommonUtil.justNullToBlank(rs.getString(45)));
                vo.setTot_amt(CommonUtil.justNullToBlank(rs.getString(46)));

                vo.setPayment_cash_dc_amt(CommonUtil.justNullToBlank(rs.getString(47)));
                vo.setPayment_cash_fc_amt(CommonUtil.justNullToBlank(rs.getString(48)));
                vo.setPayment_check_dc_amt(CommonUtil.justNullToBlank(rs.getString(49)));

                vo.setPayment_check_fc_amt(CommonUtil.justNullToBlank(rs.getString(50)));
                vo.setPayment_bill_dc_amt(CommonUtil.justNullToBlank(rs.getString(51)));
                vo.setPayment_bill_fc_amt(CommonUtil.justNullToBlank(rs.getString(52)));

                vo.setPayment_credit_dc_amt(CommonUtil.justNullToBlank(rs.getString(53)));
                vo.setPayment_credit_fc_amt(CommonUtil.justNullToBlank(rs.getString(54)));

                vo.setBusiness_type_code(CommonUtil.justNullToBlank(rs.getString(55)));
              //2011.12.15 추가
                vo.setAsp_issue_id(CommonUtil.justNullToBlank(rs.getString(56)));
            }
            
            
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END SELECT ETS_TAX_MAIN_INFO_TB]");
        return vo;
    }
}
