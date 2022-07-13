package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxCompanyVO;

public class TaxCompanyNewDao {

	public TaxCompanyVO getCompInfoByBizId(String biz_id, String comp_type, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[START getCompInfoByBizId in TaxCompanyNewDao]");
		//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
		String BUSI_ENC = CommonUtil.getString("BUSI_ENC");		
		TaxCompanyVO vo = new TaxCompanyVO();
		PreparedStatement ps = null;
		try {
			String sql = "";
			//외부 업체 일때.				
			if(comp_type.equals("N")){
				//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
				if(BUSI_ENC.equals("1")){
					sql =
//						"  SELECT DECODE_SF@USER_LINK(BUSINESS_NO), DECODE_SF@USER_LINK(BUSINESS_NO),		"   +
//						"  	   	KORNAME, OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 ||' '|| A.ZIP_CD AS ADDR,		"   +
//						"  	    TEL_NO, FAX_NO, INDUSTRY_TYPE,												"   +
//						"  	    BUSINESS_STATUS, 'N' EX_IN_TYPE , '0'										"   +
//						"    FROM TP_SUPPLIER_TBL@USER_LINK A,												"   +
//						"  	   	  TP_OWNERLST_TBL@USER_LINK B												"   +
//						"   WHERE BUSINESS_NO = ENCODE_SF@USER_LINK(?)										"   +
//						"     AND A.SUPPLIER_NO = B.SUPPLIER_NO												"  ;
						//2015.12.02 차세대입찰 관련 작업  CDH
						"   SELECT DECODE_SF64@D_EDI2SRM(BUSINESS_NO), DECODE_SF64@D_EDI2SRM(BUSINESS_NO),	"   +
//						"   SELECT DECODE_SF_SNM(BUSINESS_NO), DECODE_SF_SNM(BUSINESS_NO),					"   +
						"   	   	 KORNAME, OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 ,							"   +
						"   	     TEL_NO, FAX_NO,  BUSINESS_STATUS , INDUSTRY_TYPE ,						"   +
						"   	     'N' EX_IN_TYPE , '0'													"   +
						//2015.12.02 차세대입찰 관련 작업  CDH
//						"    FROM TP_SUPPLIER_TBL@USER_LINK A,												"   +
						"    FROM TP_SUPPLIER_TBL_VIEW A,													"   +
//						"    	  (SELECT  SUPPLIER_NO,   OWNER_NAME        									"   +
						"    	  (SELECT  ID, SUPPLIER_NO,   OWNER_NAME        									"   +
						//2015.12.02 차세대입찰 관련 작업  CDH
//						" 		     FROM TP_OWNERLST_TBL@USER_LINK B      									"   +
						" 		     FROM TP_OWNERLST_TBL_VIEW B      										"   +
						" 		    WHERE STATUS = 'Y'                     									"   +
						" 			  AND OWNER_PRIOR = '1'                									"   +
						" 			)B                                     									"	+
						//2015.12.02 차세대입찰 관련 작업  CDH
						"    WHERE BUSINESS_NO = ENCODE_SF64@D_EDI2SRM(?)									"   +
//						"    WHERE BUSINESS_NO = ENCODE_SF_SNM(?)											"   +
						"      AND A.SUPPLIER_NO = B.SUPPLIER_NO											"   +
						//2016.08.17 차세대입찰 관련 작업 CDH
						"      	AND B.ID = A.ID																			"   +
						" 	 																				"  ;
				}else{	
					sql =
						//2015.12.02 차세대입찰 관련 작업  CDH
						"   SELECT BUSINESS_NO, BUSINESS_NO,												"   +
//						"   SELECT DECODE_SF_SNM(BUSINESS_NO), DECODE_SF_SNM(BUSINESS_NO),					"   +
						"   	   	 KORNAME, OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 ,							"   +
						"   	     TEL_NO, FAX_NO,  BUSINESS_STATUS , INDUSTRY_TYPE ,						"   +
						"   	     'N' EX_IN_TYPE , '0'													"   +
						//2015.12.02 차세대입찰 관련 작업  CDH
//						"    FROM TP_SUPPLIER_TBL@USER_LINK A,												"   +
						"    FROM TP_SUPPLIER_TBL_VIEW A,													"   +
						"    	  (SELECT ID, SUPPLIER_NO,   OWNER_NAME											"   +
						//2015.12.02 차세대입찰 관련 작업  CDH
//						" 		     FROM TP_OWNERLST_TBL@USER_LINK B										"   +
						" 		     FROM TP_OWNERLST_TBL_VIEW B											"   +
						" 		    WHERE STATUS = 'Y'														"   +
						" 			  AND OWNER_PRIOR = '1'													"   +
						" 			)B																		"	+
						//2015.12.02 차세대입찰 관련 작업  CDH
						"    WHERE BUSINESS_NO = ?															"   +
						"      AND A.SUPPLIER_NO = B.SUPPLIER_NO											"   +
						//2016.08.17 차세대입찰 관련 작업 CDH
						"      	AND B.ID = A.ID																			"   +
						"																					"  ;
				}
				ps = con.prepareStatement(sql);
				ps.setString(1, biz_id);

			//한국전력공사
			}else if(comp_type.equals("K")){
				sql =
					"   SELECT BUSINESS_NO, BUSINESS_NO, KORNAME,"   +
					" 		  OWNER_NAME, ADDR,"   +
					"          '' TEL, '' FAX, BUSINESS_STATUS,"   +
					"         INDUSTRY_TYPE, 'K', '00'"   +
					// 추가변경 200912
					//"	"+
					//" FROM TP_BRANCH_VIEW@SUP A"+
					"   FROM TP_BRANCH_VIEW2 A"  +
					" WHERE BUSINESS_NO = ?  "  ;
				ps = con.prepareStatement(sql);
				ps.setString(1, biz_id);

			//발전
			}else {
            	sql =
//            		" SELECT MAIN_BIZ_ID, BIZ_ID, NAME, PRESIDENT_NAME, ADDR, TEL, FAX, BIZ_TYPE, BIZ_CLASS, TYPE, COMP_CODE "   +
//            		" FROM ETS_TAX_COMPANY_INFO_TB 	"   +
//            		" where comp_code='01'	 		"   +
//            		" 	 AND BIZ_ID = ?				"   ;

           		" SELECT BIZ_NO, BIZ_NO, CO_NAME, PRESIDENT, ADDR, '', '', BIZ_TYPE, BIZ_CLASS, 	"   +
        		" 	   USER_COM as TYPE, 															"   +
        		" 	   DECODE(USER_COM ,'SE','01', 'WW','03', 'SS','04', 'EW','05') as COMP_CODE	"   +
        		//"   FROM pwedi.PWEDI_DEPCODE														"   +
        		"   FROM PWEDI_DEPCODE														"   +
        		" where USER_COM = ?  		 														"   +
        		" 	 AND BIZ_NO = ?				"   ;

            	ps = con.prepareStatement(sql);
           		ps.setString(1, comp_type);
				ps.setString(2, biz_id);
			}

//			System.out.println("SQL::::::::::::::::::::::::::::::::::::::::\n"+sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				vo.setMain_biz_id(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setPresident_name(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setAddr(CommonUtil.justNullToBlank(rs.getString(5)));

				vo.setTel(CommonUtil.justNullToBlank(rs.getString(6)));
				vo.setFax(CommonUtil.justNullToBlank(rs.getString(7)));
				vo.setBiz_type(CommonUtil.justNullToBlank(rs.getString(8)));

				vo.setBiz_class(CommonUtil.justNullToBlank(rs.getString(9)));
				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
				vo.setComp_code(CommonUtil.justNullToBlank(rs.getString(11)));
			}
			ps.close();
			System.out.println("[END getCompInfoByBizId in TaxCompanyNewDao]");
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		return vo;
	}

	public ArrayList selectCompanyList(String biz_id, String comp_name, String comp_type, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[START selectCompanyList in TaxCompanyNewDao]");
		//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
		String BUSI_ENC = CommonUtil.getString("BUSI_ENC");			
		ArrayList data = new ArrayList();
		PreparedStatement ps = null;
		String join_biz_id = "";
		String join_comp_name = "";
		String sql = "";
		try {
			//외부 업체 일때.
			if(comp_type.equals("N")){
				if(biz_id != null && !biz_id.equals("")){
					//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
					if(BUSI_ENC.equals("1")){
						//2015.12.02 차세대입찰 관련 작업  CDH
						join_biz_id = "     AND A.BUSINESS_NO = ENCODE_SF64@D_EDI2SRM(?)" ;
//						join_biz_id = "     AND A.BUSINESS_NO = ENCODE_SF_SNM(?)" ;
					}else{
						join_biz_id = "     AND A.BUSINESS_NO = ?" ;
//						join_biz_id = "     AND A.BUSINESS_NO = ENCODE_SF_SNM(?)" ;
					}
				}
				if(comp_name != null && !comp_name.equals("")){
					join_comp_name = " 	AND A.KORNAME LIKE '%'||?||'%'"  ;
				}
				if(BUSI_ENC.equals("1")){				
					sql =
//					"  SELECT DECODE_SF@USER_LINK(CORP_NO) AS CORP_NO,								"   +
//					"  	    DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,						"   +
//					"  	    A.KORNAME,																"   +
//					"         OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 ||' '|| A.ZIP_CD AS ADDR,			"   +
//					"  	    A.TEL_NO, A.FAX_NO, A.INDUSTRY_TYPE,									"   +
//					"  	    A.BUSINESS_STATUS, 'N' EX_IN_TYPE , '0'									"   +
//					"    FROM TP_SUPPLIER_TBL@USER_LINK A,											"   +
//					"  	   	  TP_OWNERLST_TBL@USER_LINK B											"   +
//					"   WHERE A.SUPPLIER_NO = B.SUPPLIER_NO											"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
					"   SELECT DECODE_SF64@D_EDI2SRM(BUSINESS_NO), DECODE_SF64@D_EDI2SRM(BUSINESS_NO),"	+
//					"   SELECT DECODE_SF_SNM(BUSINESS_NO), DECODE_SF_SNM(BUSINESS_NO),				"   +
					"   	   KORNAME, OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 ,						"   +
					"   	   TEL_NO, FAX_NO,  BUSINESS_STATUS , INDUSTRY_TYPE,					"	+
					"          'N' EX_IN_TYPE , '0'													"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
//					"    FROM TP_SUPPLIER_TBL@USER_LINK A,											"   +
					"    FROM TP_SUPPLIER_TBL_VIEW A,												"   +
					"    	  (SELECT ID, SUPPLIER_NO,   OWNER_NAME        								"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
//					" 		     FROM TP_OWNERLST_TBL@USER_LINK B      								"   +
					" 		     FROM TP_OWNERLST_TBL_VIEW B      									"   +
					" 		    WHERE STATUS = 'Y'                     								"   +
					" 			  AND OWNER_PRIOR = '1'                								"   +
					" 			)B                                     								"	+
					"    WHERE A.SUPPLIER_NO = B.SUPPLIER_NO										"   +
					//2016.08.17 차세대입찰 관련 작업 CDH
					"      	AND B.ID = A.ID																			"   +
					join_biz_id +
					join_comp_name;
				}else{
					sql =
//					"  SELECT DECODE_SF@USER_LINK(CORP_NO) AS CORP_NO,								"   +
//					"  	    DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,						"   +
//					"  	    A.KORNAME,																"   +
//					"         OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 ||' '|| A.ZIP_CD AS ADDR,			"   +
//					"  	    A.TEL_NO, A.FAX_NO, A.INDUSTRY_TYPE,									"   +
//					"  	    A.BUSINESS_STATUS, 'N' EX_IN_TYPE , '0'									"   +
//					"    FROM TP_SUPPLIER_TBL@USER_LINK A,											"   +
//					"  	   	  TP_OWNERLST_TBL@USER_LINK B											"   +
//					"   WHERE A.SUPPLIER_NO = B.SUPPLIER_NO											"   +			
					//2015.12.02 차세대입찰 관련 작업  CDH
					"   SELECT BUSINESS_NO, BUSINESS_NO,											"   +
//					"   SELECT DECODE_SF_SNM(BUSINESS_NO), DECODE_SF_SNM(BUSINESS_NO),				"   +
					"   	   KORNAME, OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 ,						"   +
					"   	   TEL_NO, FAX_NO,  BUSINESS_STATUS , INDUSTRY_TYPE,					"	+
					"          'N' EX_IN_TYPE , '0'													"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
//					"    FROM TP_SUPPLIER_TBL@USER_LINK A,											"   +
					"    FROM TP_SUPPLIER_TBL_VIEW A,												"   +
					"    	  (SELECT ID, SUPPLIER_NO,   OWNER_NAME        								"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
//					" 		     FROM TP_OWNERLST_TBL@USER_LINK B      								"   +
					" 		     FROM TP_OWNERLST_TBL_VIEW B      									"   +
					" 		    WHERE STATUS = 'Y'                     								"   +
					" 			  AND OWNER_PRIOR = '1'                								"   +
					" 			)B                                     								"	+
					"    WHERE A.SUPPLIER_NO = B.SUPPLIER_NO										"   +
						//2016.08.17 차세대입찰 관련 작업 CDH
					"      	AND B.ID = A.ID																			"   +
					
					join_biz_id +
					join_comp_name;
				}					
				ps = con.prepareStatement(sql);

			}else if(comp_type.equals("K")){
				System.out.println("################################################\n");
				System.out.println("	biz_id  : "+biz_id);
				System.out.println("################################################\n");

				if(biz_id != null && !biz_id.equals("")){
					//join_biz_id = "    AND BUSINESS_NO = ?"    ;
					join_biz_id = "    AND BUY_REGIST_ID = ? "    ;//변경
				}
				if(comp_name != null && !comp_name.equals("")){
					join_comp_name = "    AND KORNAME LIKE '%'||?||'%'"  ;
				}
				sql =
					"   SELECT BUSINESS_NO, BUSINESS_NO, KORNAME,"   +
					" 		  OWNER_NAME, ADDR,"   +
					"          '' TEL, '' FAX,  BUSINESS_STATUS, INDUSTRY_TYPE,"   +
					"          'K', '00' "   +
					//
					" 		, ZBRAN_NAME, A.BUY_REGIST_ID "+
					//"   FROM TP_BRANCH_VIEW2 A"  +
//2015.12.02 차세대입찰 관련 작업  CDH
//					"   FROM TP_BRANCH_VIEW@SUP A	"  +
					"   FROM FI_KEPCO_BIZ_VIEW A	"  +

					"  WHERE A.BUSINESS_NO IS NOT NULL "   +
					// 추가 200912
					" AND A.BUY_REGIST_ID IS NOT NULL "+
					join_biz_id +
					join_comp_name;
				ps = con.prepareStatement(sql);

			//남동발전
			}else if(comp_type.equals("D")){

				if(biz_id != null && !biz_id.equals("")){
					join_biz_id = "    AND BIZ_ID = ?"    ;
				}
				if(comp_name != null && !comp_name.equals("")){
					join_comp_name = "    AND NAME LIKE '%'||?||'%'"  ;
				}

				sql =
            		" SELECT MAIN_BIZ_ID, BIZ_ID, NAME, PRESIDENT_NAME, ADDR, TEL, FAX, BIZ_TYPE, BIZ_CLASS, TYPE, COMP_CODE "   +
            		" FROM ETS_TAX_COMPANY_INFO_TB 	"   +
            		" where comp_code='01'	 		"   +

	            	join_biz_id +
					join_comp_name;
				ps = con.prepareStatement(sql);

			}

			int psIdx = 1;

			if(biz_id != null && !biz_id.equals("")){
				ps.setString(psIdx++, biz_id);
			}
			if(comp_name != null && !comp_name.equals("")){
				ps.setString(psIdx++, comp_name);
			}

			//System.out.println("SQL::::::::::::::::::::::::::::::::::::::::\n"+sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TaxCompanyVO vo = new TaxCompanyVO();
				vo.setMain_biz_id(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setPresident_name(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setAddr(CommonUtil.justNullToBlank(rs.getString(5)));

				vo.setTel(CommonUtil.justNullToBlank(rs.getString(6)));
				vo.setFax(CommonUtil.justNullToBlank(rs.getString(7)));
				vo.setBiz_type(CommonUtil.justNullToBlank(rs.getString(8)));

				vo.setBiz_class(CommonUtil.justNullToBlank(rs.getString(9)));
				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
				vo.setComp_code(CommonUtil.justNullToBlank(rs.getString(11)));
				// 수정추가 200912
				vo.setZbran_name(CommonUtil.justNullToBlank(rs.getString(12))); // 지점명
				vo.setBuy_regist_id(CommonUtil.justNullToBlank(rs.getString(13))); // 한전종사업장번호

				data.add(vo);
			}
			ps.close();
			System.out.println("[END selectCompanyList in TaxCompanyNewDao]");
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		return data;
	}


	public ArrayList selectPWCompanyList(String biz_id, String comp_name, String comp_type, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[START selectPWCompanyList in TaxCompanyNewDao]");
		ArrayList data = new ArrayList();
		PreparedStatement ps = null;
		String join_biz_id = "";
		String join_comp_name = "";


		String sql = "";
		try {

			//SE: 남동, SS: 남부, EW: 동서, WW: 서부

			if(biz_id != null && !biz_id.equals("")){
				join_biz_id = "    AND BIZ_NO = ?"    ;
			}
			if(comp_name != null && !comp_name.equals("")){
				join_comp_name = "    AND CO_NAME LIKE '%'||?||'%'"  ;
			}

			sql =
        		" SELECT BIZ_NO, BIZ_NO, CO_NAME, PRESIDENT, ADDR, '', '', BIZ_TYPE, BIZ_CLASS, 	"   +
//        		" 	   DECODE(USER_COM ,'SE','D', USER_COM) as TYPE, 								"   +
        		" 	   USER_COM as TYPE, 															"   +
        		" 	   DECODE(USER_COM ,'SE','01', 'WW','03', 'SS','04', 'EW','05') as COMP_CODE	"   +
        		//"   FROM pwedi.PWEDI_DEPCODE														"   +
        		"   FROM PWEDI_DEPCODE														"   +
//        		" where USER_COM = DECODE(? ,'01','SE', '03','WW', '04','SS','05', 'EW') 		 	"   +
        		" where USER_COM = ?  		 														"   +

            	join_biz_id +
				join_comp_name;

			ps = con.prepareStatement(sql);

			int psIdx = 1;

			ps.setString(psIdx++, comp_type);

			if(biz_id != null && !biz_id.equals("")){
				ps.setString(psIdx++, biz_id);
			}
			if(comp_name != null && !comp_name.equals("")){
				ps.setString(psIdx++, comp_name);
			}

			//System.out.println("SQL::::::::::::::::::::::::::::::::::::::::\n"+sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TaxCompanyVO vo = new TaxCompanyVO();
				vo.setMain_biz_id(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setPresident_name(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setAddr(CommonUtil.justNullToBlank(rs.getString(5)));

				vo.setTel(CommonUtil.justNullToBlank(rs.getString(6)));
				vo.setFax(CommonUtil.justNullToBlank(rs.getString(7)));
				vo.setBiz_type(CommonUtil.justNullToBlank(rs.getString(8)));

				vo.setBiz_class(CommonUtil.justNullToBlank(rs.getString(9)));
				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
				vo.setComp_code(CommonUtil.justNullToBlank(rs.getString(11)));
				data.add(vo);
			}
			ps.close();
			System.out.println("[END selectPWCompanyList in TaxCompanyNewDao]");
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		return data;
	}

	public ArrayList selectOftenCompanyList(String id, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[START selectOftenCompanyList in TaxCompanyNewDao]");
		ArrayList data = new ArrayList();
		PreparedStatement ps = null;

		String sql = "";
		try {

			sql =
				"   SELECT BUSINESS_NO, BUSINESS_NO, KORNAME,"   +
				" 		  OWNER_NAME, ADDR,"   +
				"          '' TEL, '' FAX,  BUSINESS_STATUS, INDUSTRY_TYPE,"   +
				"          'K', '00'"   +
				"   FROM TP_BRANCH_VIEW2 A, ETS_TAX_OFTEN_USE_INFO_TB C"  +
				"  WHERE A.BUSINESS_NO IS NOT NULL and C.LINK_ID = A.BUSINESS_NO and C.PARENT_ID=? and C.comp_code ='00'" +
				" union " +

//				" SELECT MAIN_BIZ_ID, BIZ_ID, NAME, PRESIDENT_NAME, ADDR, TEL, FAX, BIZ_TYPE, BIZ_CLASS, TYPE, C.COMP_CODE "   +
//        		" FROM ETS_TAX_COMPANY_INFO_TB, ETS_TAX_OFTEN_USE_INFO_TB C 	"   +
//        		" where C.LINK_ID = BIZ_ID and C.PARENT_ID=? and C.comp_code ='01'" ;

				" SELECT BIZ_NO, BIZ_NO, CO_NAME, PRESIDENT, ADDR, '', '', BIZ_TYPE, BIZ_CLASS, "   +
				" 	 USER_COM as TYPE, 															"   +
				"    DECODE(USER_COM ,'SE','01', 'WW','03', 'SS','04', 'EW','05') as COMP_CODE	"   +
				//"  FROM pwedi.PWEDI_DEPCODE, ETS_TAX_OFTEN_USE_INFO_TB C 						"   +
				"  FROM PWEDI_DEPCODE, ETS_TAX_OFTEN_USE_INFO_TB C 						"   +
				"  where C.LINK_ID = BIZ_NO and C.PARENT_ID=? 									"   ;

            ps = con.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TaxCompanyVO vo = new TaxCompanyVO();
				vo.setMain_biz_id(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setPresident_name(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setAddr(CommonUtil.justNullToBlank(rs.getString(5)));

				vo.setTel(CommonUtil.justNullToBlank(rs.getString(6)));
				vo.setFax(CommonUtil.justNullToBlank(rs.getString(7)));
				vo.setBiz_type(CommonUtil.justNullToBlank(rs.getString(8)));

				vo.setBiz_class(CommonUtil.justNullToBlank(rs.getString(9)));
				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
				vo.setComp_code(CommonUtil.justNullToBlank(rs.getString(11)));
				data.add(vo);
			}
			ps.close();
			System.out.println("[END selectOftenCompanyList in TaxCompanyNewDao]");
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		return data;
	}

	// KDN ERP연계 관련 사업자번호 관리
    public boolean getBiznumInfo(String biznum, String comp, Connection con) throws SQLException, TaxInvoiceException {
        System.out.println("[START getBiznumInfo]");
        boolean adminYN = false;
        PreparedStatement ps = null;
        try{
            String sql = "SELECT BIZNUM FROM ETS_TAX_BIZNUM_INFO_TB WHERE BIZNUM=? AND COMP=? "   ;

        	ps = con.prepareStatement(sql);
        	ps.setString(1, biznum);
        	ps.setString(2, comp);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            	adminYN = true;
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END getBiznumInfo]");
        return adminYN;
    }

    public ArrayList getBiznumInfoList(String comp, Connection con) throws SQLException, TaxInvoiceException {
        System.out.println("[START getBiznumInfoList]");
        ArrayList adminlist =  new ArrayList();
        PreparedStatement ps = null;
        try{
            String sql = "SELECT BIZNUM, COMP FROM ETS_TAX_BIZNUM_INFO_TB " ;
            if (!comp.equals("")) sql = sql + " WHERE COMP=? "   ;

        	ps = con.prepareStatement(sql);
        	if (!comp.equals("")) ps.setString(1, comp);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            	String[] rec = {rs.getString(1),rs.getString(2)};
            	adminlist.add(rec);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END getBiznumInfoList]");
        return adminlist;
    }

    public void insertBiznumInfo(String biznum, String comp, Connection con) throws SQLException, TaxInvoiceException {
        System.out.println("[START insertBiznumInfo]");
        PreparedStatement ps = null;
        try{
            String sql = "INSERT INTO ETS_TAX_BIZNUM_INFO_TB(BIZNUM, COMP) VALUES(?, ?) " ;

        	ps = con.prepareStatement(sql);
        	ps.setString(1, biznum);
        	ps.setString(2, comp);
            ps.executeQuery();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END insertBiznumInfo]");
    }

    public void deleteBiznumInfo(String biznum, String comp, Connection con) throws SQLException, TaxInvoiceException {
        System.out.println("[START deleteBiznumInfo]");
        PreparedStatement ps = null;
        try{
            String sql = "DELETE ETS_TAX_BIZNUM_INFO_TB WHERE BIZNUM=? AND COMP=? " ;

        	ps = con.prepareStatement(sql);
        	ps.setString(1, biznum);
        	ps.setString(2, comp);
            ps.executeQuery();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END deleteBiznumInfo]");
    }



}
