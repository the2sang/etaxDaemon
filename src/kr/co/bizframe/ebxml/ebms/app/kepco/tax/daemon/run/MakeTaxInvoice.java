/* wsx3edc
 * Created on 2005. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.run;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxCompanyNewManagerDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxPersonNewManagerDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxCompanyVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxDetailVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxFinanceContectVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxHeaderVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLineVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxPersonVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxReceiptVO;

/**
 * @author shin sung uk 
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MakeTaxInvoice {

	private static Logger logger = Logger.getLogger(MakeTaxInvoice.class);

	private TaxInvoiceVO vo = new TaxInvoiceVO();

	private TaxFinanceContectVO financeVO = new TaxFinanceContectVO();

	private String result = "";

	private String resultMsg = "";

	private ArrayList details = new ArrayList();

//	private ArrayList data = new ArrayList(); //excel VO data
//	private ArrayList results = new ArrayList(); //result (정상 or 오류)
//	private ArrayList resultMsgs = new ArrayList(); //result message

	public void setBuyerInfo(String bizNO) {

		TaxCompanyNewManagerDao dao = new TaxCompanyNewManagerDao();
		ArrayList complist = new ArrayList();
		try {
			//종사업장번호로 검색 (한전)
			if("".equals(bizNO)){ // 추가 종사업장번호가 없을시
				result = "ERR"; resultMsg = "공급받는자  종사업장번호가 없습니다.";
			}else{
				complist = dao.selectCompanyList(bizNO,"","K");

				//if (complist.size()<1) { result = "ERR"; resultMsg = "공급받는자  사업자번호로  검색되는 사업자정보가 없습니다.";}
				if (complist.size()<1) { result = "ERR"; resultMsg = "공급받는자  종사업장번호로 검색되는 사업자정보가 없습니다.";}
				else {
					TaxCompanyVO CompanyVO = new TaxCompanyVO();
					CompanyVO = (TaxCompanyVO)complist.get(0);

					//공급받는자 정보
					vo.getMain().setBuyer_biz_id(CommonUtil.nullToBlank(CompanyVO.getBiz_id()));         //수요자 사업자 등록 번호
					vo.getMain().setBuyer_president_name(CommonUtil.nullToBlank(CompanyVO.getPresident_name())); //수요자 성명
					vo.getMain().setBuyer_name(CommonUtil.nullToBlank(CompanyVO.getName()));       		//상호
					vo.getMain().setBuyer_biz_type(CommonUtil.nullToBlank(CompanyVO.getBiz_type()));       //업태
					vo.getMain().setBuyer_biz_class(CommonUtil.nullToBlank(CompanyVO.getBiz_class()));      //종목
					vo.getMain().setBuyer_addr(CommonUtil.nullToBlank(CompanyVO.getAddr())); //주소
					vo.getMeta().setComp_code(CommonUtil.nullToBlank(CompanyVO.getComp_code()));
					vo.getMain().setBuyer_biz_cd(CommonUtil.nullToBlank(CompanyVO.getBuy_regist_id())); //종사업장번호 추가
					vo.getMain().setZbran_name(CommonUtil.nullToBlank(CompanyVO.getZbran_name())); // 지점명 20100204
					// 추가변경 200912
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (TaxInvoiceException e) {
			e.printStackTrace();
		}

	}


	public void setInspectorInfo(String id) {

		TaxPersonNewManagerDao dao = new TaxPersonNewManagerDao();
		ArrayList personlist = new ArrayList();
		try {
			personlist = dao.selectHanjunUserById(id);
			if (personlist.size()<1) { result = "ERR"; resultMsg = "해당 사번으로  검색되는 담당자정보가 없습니다.";}
			else {
				TaxPersonVO PersonVO = new TaxPersonVO();
				PersonVO = (TaxPersonVO)personlist.get(0);

				//공급받는자 담당자 정보
				vo.getMain().setBuyer_id(CommonUtil.nullToBlank(PersonVO.getId()));
				vo.getMain().setBuyer_contactor_name(CommonUtil.nullToBlank(PersonVO.getName()));
				vo.getMain().setBuyer_contactor_email(CommonUtil.nullToBlank(PersonVO.getEmail()));
				vo.getMain().setBuyer_contactor_tel(CommonUtil.nullToBlank(PersonVO.getTel()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TaxInvoiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setFinanceInfo(String id) {

		TaxPersonNewManagerDao dao = new TaxPersonNewManagerDao();
		ArrayList personlist = new ArrayList();
		try {
			personlist = dao.selectHanjunUserById(id);
			if (personlist.size()<1) { result = "ERR"; resultMsg = "해당 사번으로  검색되는 담당자정보가 없습니다.";}
			else {
				TaxPersonVO PersonVO = new TaxPersonVO();
				PersonVO = (TaxPersonVO)personlist.get(0);

				financeVO.setId(CommonUtil.nullToBlank(PersonVO.getId()));
				financeVO.setName(CommonUtil.nullToBlank(PersonVO.getName()));
				financeVO.setEmail(CommonUtil.nullToBlank(PersonVO.getEmail()));
				financeVO.setTel(CommonUtil.nullToBlank(PersonVO.getTel()));
				//2015.12.07 내선계기 메일보낸이를 계약자로 변경 CDH
				vo.getMeta().setContractor_email(CommonUtil.nullToBlank(PersonVO.getEmail()));
				vo.getMeta().setContractor_name(CommonUtil.nullToBlank(PersonVO.getName()));
				vo.getMeta().setContractor_phone(CommonUtil.nullToBlank(PersonVO.getTel()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TaxInvoiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Parse Excel Data
	 * @throws TaxInvoiceException
	 * @throws
	 * */
	public Hashtable makeTaxinvoice(TaxHeaderVO headerVO, ArrayList detailArray) throws IOException, SQLException, TaxInvoiceException {

		Hashtable finalData = new Hashtable(); //return value
//		ArrayList data = new ArrayList(); //excel VO data
//		ArrayList results = new ArrayList(); //result (정상 or 오류)
//		ArrayList resultMsgs = new ArrayList(); //result message
 
		Hashtable compHt = new Hashtable(); //comp info from excel
		Object[] object = new Object[2];
		object[0] = new TaxCompanyVO();
		object[1] = new TaxPersonVO();
		compHt.put(headerVO.getBiz_no(), object);

		if (detailArray.size()<1) { result="ERR"; resultMsg="DETAIL TABLE에 상세정보가 없습니다."; }

		//if (!result.equals("ERR")) setBuyerInfo(headerVO.getBuyer_biz_id());
		if (!result.equals("ERR")) setBuyerInfo(headerVO.getBuyer_biz_cd()); 				// 종사업장번호로 검색
		if (!result.equals("ERR")) setInspectorInfo(headerVO.getInspector_id()); 			// 한전 담당자 검색
		if (!result.equals("ERR")) setFinanceInfo(headerVO.getContractor_id()); 			// 한전 담당자 검색
		if (!result.equals("ERR")) compHt = settingCompInfo(headerVO.getBiz_no(), compHt); 	// 업체정보 검색
		
		// 로직 추가변경
		// K1ETAX1022 = 내선계기    K1NCIS1000 = 영업 검침원 용역 수수료,   K1NDIS1000 = 영업 조류예방활동비
		//2015.08.20 로직추가 
		//K1PPAS1000 = PPA시스템
		if("K1NCIS1000".equals(headerVO.getRel_system_id()) || "K1NDIS1000".equals(headerVO.getRel_system_id()) || "K1PPAS1000".equals(headerVO.getRel_system_id())){
			if (!result.equals("ERR")) makeVONcisFromExcel(headerVO, detailArray, compHt); // 201009 Add
		}else if("K1ETAX1022".equals(headerVO.getRel_system_id())){//내선계기
			if (!result.equals("ERR")) makeVOFromExcel(headerVO, detailArray, compHt); // 기존로직
		}else{
//			System.out.println("CDH id를 못가져와서 에러");
			result="ERR"; resultMsg="System ID가 존재하지 않습니다.";
		}

		if ("".equals(headerVO.getDoc_type_detail())){
			result="ERR"; resultMsg="세금계산서 종류 가 없습니다.";
		}else{
			vo.getMain().setDoc_type_detail(headerVO.getDoc_type_detail());
			//vo.setSms_yn(headerVO.getSms_yn());
			//vo.setSms_sender_name(headerVO.getSms_sender_name());
			//vo.setSms_sender_tel(headerVO.getSms_sender_tel());
			
		
		}

		//System.out.println(":::::::::::::::2009 12 >>"+vo.getMain().getDoc_type_detail());

		finalData.put("taxVO", vo);
		finalData.put("financeVO", financeVO);
		finalData.put("detailArry", details);
		finalData.put("result", result);
		finalData.put("resultMsg", resultMsg);
		
		finalData.put("sms_yn", headerVO.getSms_yn());
		finalData.put("sms_name", headerVO.getSms_sender_name());
		finalData.put("sms_tel", headerVO.getSms_sender_tel());

		System.out.println("세금계산서 vo생성 완료 [makeTaxinvoice]");
		return finalData;
	}

	private Hashtable settingCompInfo(String bizNO, Hashtable compHt) throws SQLException, TaxInvoiceException {
//		ArrayList bizList = getBizIdList(compHt);
		TaxPersonNewManagerDao dao = new TaxPersonNewManagerDao();
		compHt = dao.getOutterInfoForExcelPublish(bizNO, compHt);

//		System.out.println("compHt.size()=="+((TaxCompanyVO)(((Object[])(compHt.get(bizNO)))[0])).getBiz_id()   );
//		System.out.println("compHt.size()=="+((TaxCompanyVO)(((Object[])(compHt.get(bizNO)))[0])).getName()   );

		if (((TaxCompanyVO)(((Object[])(compHt.get(bizNO)))[0])).getBiz_id().equals("")) 
		{ result = "ERR"; resultMsg = "공급자 사업자번호로  검색되는 업체정보가 없습니다.";}

		return compHt;
	}

	private ArrayList getBizIdList(Hashtable compHt) {
		ArrayList bizList = new ArrayList();
		Enumeration key = compHt.keys();

		while (key.hasMoreElements()) {
			bizList.add(key.nextElement());
		}

		return bizList;
	}

	public void makeVOFromExcel(TaxHeaderVO headerVO, ArrayList detailArray, Hashtable compHt) throws IOException, SQLException, TaxInvoiceException {

		String biz_id = headerVO.getBiz_no();
		String doc_date = headerVO.getPub_ymd();
		String const_no = headerVO.getCons_no();
		String doc_desc = headerVO.getComp_no();							//공사업체코드
		String const_name = headerVO.getCons_nm();							//공사명

		long totMeterAmt = 0;
		long totInbAmt = 0;
		long totIdfmAmt = 0;	//계기함부설비 20101004 추가
		long totProfAmt = 0;

		TaxDetailVO dVO = new TaxDetailVO();

		String uuid = CommonUtil.getUUID();

		try {

	//		금액 누적 , 상세정보저장
			for (int i = 0; i < detailArray.size(); i++) {
				dVO = (TaxDetailVO)detailArray.get(i);

				TaxReceiptVO ReceiptVO = new TaxReceiptVO();
				ReceiptVO.setBiz_id(headerVO.getBiz_no());						//공사업체사업자번호
				ReceiptVO.setConst_no(headerVO.getCons_no());					//공사번호
				ReceiptVO.setProf_cons_no(headerVO.getProf_cons_no());			//수익적공사번호
				ReceiptVO.setReceipt_no(dVO.getAcptno());						//접수번호
				ReceiptVO.setReceipt_kind(dVO.getAcpt_knd_nm());				//접수종류명
				ReceiptVO.setCust_name(dVO.getCustnm());						//고객명
				ReceiptVO.setAddress(dVO.getAddress());							//주소
				ReceiptVO.setConst_date(dVO.getOper_ymd());						//시공일자
				ReceiptVO.setMatrbill_comp_amt(dVO.getMatrbill_comp_amt());		//도급재료비
				ReceiptVO.setComp_amt(dVO.getComp_amt());						//도급공사비
				ReceiptVO.setProf_comp_amt(dVO.getProf_comp_amt());				//수익적공사비

				long tempAmt = Long.parseLong(CommonUtil.nullToZero(dVO.getMatrbill_comp_amt())) + Long.parseLong(CommonUtil.nullToZero(dVO.getComp_amt()));

				if ( dVO.getCons_knd_cd().equals("01") || dVO.getCons_knd_cd().equals("03")) {			//계기부설비
					totMeterAmt += tempAmt;
					ReceiptVO.setAmt1(Long.toString(tempAmt));
					
					if(dVO.getCons_knd_cd().equals("03")){
						ReceiptVO.setAmt3(Long.toString(tempAmt));
					}
					
				} else if ( dVO.getCons_knd_cd().equals("02") ) {	//인입선공사비
					totInbAmt += tempAmt;
					ReceiptVO.setAmt2(Long.toString(tempAmt));
				}
				
				//} else if ( dVO.getCons_knd_cd().equals("03") ) { 	//계기함부설비 20101004 추가
				//	totIdfmAmt += tempAmt;
				//	ReceiptVO.setAmt3(Long.toString(tempAmt));		//변경해야함.setAmt3

				totProfAmt += Long.parseLong(CommonUtil.nullToZero(dVO.getProf_comp_amt()));

				ReceiptVO.setUuid(uuid);
				details.add(ReceiptVO);
			}

		} catch (Exception e) {
    		result = "ERR";
			resultMsg =  "세금계산서 금액 처리 중 오류 발생";
    		logger.debug("makeVOFromExcel Error.. " + e);
        }

		if (!result.equals("ERR")) {

			String amt1 = Long.toString(totMeterAmt);
			String amt2 = Long.toString(totInbAmt);
			String amt3 = Long.toString(totIdfmAmt);		//계기함부설비 20101004 추가

			//validation
			CheckExcelLoadData cData = new CheckExcelLoadData();

			byte checkLength[] = const_name.getBytes();
			int lenFlag = checkLength.length + 13;

			//        cData.checkTaxInvoice(biz_id, doc_date, const_name, amt1, amt2);
			
			cData.checkTaxInvoice(biz_id, doc_date, const_name, amt1, amt2, amt3,lenFlag);   

			Object[] object = (Object[]) compHt.get(biz_id);
			TaxCompanyVO comp = null;
			TaxPersonVO person = null;

			if (object != null) {
				comp = (TaxCompanyVO) object[0];
				person = (TaxPersonVO) object[1];
			} else {
				comp = new TaxCompanyVO();
				person = new TaxPersonVO();
			}

			vo.setUuid(uuid);
			vo.getMeta().setConstruct_no(const_no);
			vo.getMeta().setUuid(uuid);
			vo.getMeta().setDoc_state("WM");
			vo.getMeta().setWriter_type("K");
			vo.getMeta().setContractor_id(financeVO.getId());
			vo.getMeta().setInspector_id(vo.getMain().getBuyer_id());
			vo.getMeta().setExt_process_status_code("1");
			vo.getMeta().setExt_system_type("300");
			vo.getMeta().setExt_revenue_construct_no(headerVO.getProf_cons_no());
			vo.getMeta().setExt_revenue_construct_amount(Long.toString(totProfAmt));

			vo.getMain().setUuid(uuid);

			if (cData.isDocDate())
				vo.getMain().setDoc_date(doc_date); //문서작성일 년

			vo.getMain().setDoc_desc(doc_desc); //비고
			vo.getMain().setVolum_id(""); //권번호
			vo.getMain().setIssue_id(""); //호번호
			vo.getMain().setSeq_id("-"); //일련번호
			vo.getMain().setDoc_type_code("01"); 	// (01: 세금계산서, 02 : 계산서)

			//공급자 정보
			//vo.getMain().setSupplier_biz_id(comp.getBiz_id()); //공급자 사업자 등록 번호
			vo.getMain().setSupplier_biz_id(biz_id); //공급자 사업자 등록 번호
			vo.getMain().setSupplier_president_name(comp.getPresident_name()); //공급자 성명
			vo.getMain().setSupplier_name(comp.getName()); //상호
			vo.getMain().setSupplier_biz_type(comp.getBiz_type()); //업태
			vo.getMain().setSupplier_biz_class(comp.getBiz_class()); //종목
			
		// 공급자 주소길이를  바이트 크기만큼 만 받아서 셋팅하기 시작  2011.11.09 윤민호
			//vo.getMain().setSupplier_addr(comp.getAddr().trim().length() > 30 ? (comp.getAddr().trim()).substring(0, 30) : comp.getAddr().trim()); //공급자 주소
			
			String str=comp.getAddr(); //입력정보
			int limit=145;              //Byte 제한
		
			
			String[] ary = null;

			byte[] rawBytes = str.getBytes("EUC-KR");
			int rawLength = rawBytes.length;

			int index = 0;
			int minus_byte_num = 0;
			int offset = 0;
			int hangul_byte_num = 2;

			if(rawLength > limit){
			int aryLength = (rawLength / limit) + (rawLength % limit != 0 ? 1 : 0);
			ary = new String[aryLength];

			for(int i=0; i<aryLength; i++){
			minus_byte_num = 0;
			offset = limit;

			if(index + offset > rawBytes.length){
			offset = rawBytes.length - index;
			}

			for(int j=0; j<offset; j++){
			if(((int)rawBytes[index + j] & 0x80) != 0){
			minus_byte_num ++;
			}
			}  //for

			if(minus_byte_num % hangul_byte_num != 0){
			offset -= minus_byte_num % hangul_byte_num;
			}

			ary[i] = new String(rawBytes, index, offset, "EUC-KR");
			index += offset ;

			} //aryLength for

			} else {  //rawLength if
			ary = new String[]{str};
			}

		System.out.println(" ■■■■■■■■■■■■■■■ MakeTaxInvoice 공급자 주소 결과: "+ ary[0]);			
		vo.getMain().setSupplier_addr(ary[0]); //저장
		
	// 공급자 주소길이를  바이트 크기만큼 만 받아서 셋팅하기 끝
			
			vo.getMain().setSupplier_id(person.getId()); //공급자 id
			vo.getMain().setSupplier_contactor_name(person.getName());
			vo.getMain().setSupplier_contactor_email(person.getEmail());
			vo.getMain().setSupplier_contactor_tel(person.getTel());

			//공급받는자 정보
			vo.getMain().setBuyer_president_name(vo.getMain().getBuyer_president_name()); //수요자 성명
			vo.getMain().setBuyer_biz_id(vo.getMain().getBuyer_biz_id()); //수요자 사업자 등록 번호
			vo.getMain().setBuyer_name(vo.getMain().getBuyer_name()); //상호
			vo.getMain().setBuyer_biz_type(vo.getMain().getBuyer_biz_type()); //업태
			vo.getMain().setBuyer_biz_class(vo.getMain().getBuyer_biz_class()); //종목
			
	// 공급받는자 주소길이를  바이트 크기만큼 만 받아서 셋팅하기 시작  2011.11.09 윤민호
			//vo.getMain().setBuyer_addr((vo.getMain().getBuyer_addr().trim()).length() > 30 ? (vo.getMain().getBuyer_addr().trim()).substring(0, 30) : vo.getMain().getBuyer_addr().trim()); //수요자 주소
			
			String str1=vo.getMain().getBuyer_addr(); //입력정보
			int limit1=97;              //Byte 제한
		
			String[] ary1 = null;

			byte[] rawBytes1 = str1.getBytes("EUC-KR");
			int rawLength1 = rawBytes1.length;

			int index1 = 0;
			int minus_byte_num1 = 0;
			int offset1 = 0;
			int hangul_byte_num1 = 2;

			if(rawLength1 > limit1){
			int aryLength1 = (rawLength1 / limit1) + (rawLength1 % limit1 != 0 ? 1 : 0);
			ary1 = new String[aryLength1];

			for(int i=0; i<aryLength1; i++){
			minus_byte_num1 = 0;
			offset1 = limit1;

			if(index1 + offset1 > rawBytes1.length){
			offset1 = rawBytes1.length - index1;
			}

			for(int j=0; j<offset1; j++){
			if(((int)rawBytes1[index1 + j] & 0x80) != 0){
			minus_byte_num1 ++;
			}
			}  //for

			if(minus_byte_num1 % hangul_byte_num1 != 0){
			offset1 -= minus_byte_num1 % hangul_byte_num1;
			}

			ary1[i] = new String(rawBytes1, index1, offset1, "EUC-KR");
			index1 += offset1 ;

			} //aryLength for

			} else {  //rawLength if
			ary1 = new String[]{str1};
			}

	//	System.out.println(" ■■■■■■■■■■■■■■■ MakeTaxInvoice 공급받는자 주소 결과: "+ ary1[0]);			
		vo.getMain().setBuyer_addr(ary1[0]); //저장
		
	// 공급받는자 주소길이를  바이트 크기만큼 만 받아서 셋팅하기 끝
		
			
			
			vo.getMain().setBuyer_id(vo.getMain().getBuyer_id()); //수요자 id
			vo.getMain().setBuyer_contactor_name(vo.getMain().getBuyer_contactor_name());
			vo.getMain().setBuyer_contactor_email(vo.getMain().getBuyer_contactor_email());
			vo.getMain().setBuyer_contactor_tel(vo.getMain().getBuyer_contactor_tel());
			vo.getMain().setBuyer_biz_cd(vo.getMain().getBuyer_biz_cd()); // 종사업장번호 추가변경 200912

			//ERP 연계 추가 데이터 항목
			vo.getMeta().setContractor_id(financeVO.getId());
			vo.getMeta().setInspector_id(vo.getMain().getBuyer_id());
			vo.getMeta().setExt_process_status_code("1");
			//18:청구
			vo.getMain().setDemand_type("18");

			// 2006.08.10 내선기기 구분 값
			vo.getMain().setImport_reg_id("CX2");
			vo.getMeta().setExt_status_code("N");

			//현금, 수표 등등..(셋팅안함)
			vo.getMain().setPayment_cash_dc_amt("");
			vo.getMain().setPayment_check_dc_amt("");
			vo.getMain().setPayment_bill_dc_amt("");
			vo.getMain().setPayment_credit_dc_amt("");


			if (!CommonUtil.nullToZero(amt1).equals("0"))	//계기
				vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name+" 계기공사비", amt1));
			if (!CommonUtil.nullToZero(amt2).equals("0"))	//인입
				vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name+" 인입선공사비", amt2));
			
			//if (!CommonUtil.nullToZero(amt3).equals("0"))	//계기함
			//	vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name+" 계기함부설비", amt3));
			

			long totAmt = Long.parseLong(CommonUtil.nullToZero(amt1)) + Long.parseLong(CommonUtil.nullToZero(amt2)); //

			if (cData.isAmt()) {
				int blank = 11 - Long.toString(totAmt).length();
				vo.getMain().setBlank_num(blank + ""); //공란수
			}

			if (totAmt<0) {
				vo.getMain().setBusiness_type_code("2"); // (1: 일반세금계산서, 2: 마이너스세금계산서)
			} else {
				vo.getMain().setBusiness_type_code("1"); // (1: 일반세금계산서, 2: 마이너스세금계산서)
			}

			long totTax = getTotTax(vo.getLine());

			vo.getMain().setCharge_amt(totAmt + "");//단가합계
			vo.getMain().setTot_tax_amt(totTax + "");//세액합계

			//        vo.getMain().setTot_amt((totAmt+totTax)+"");//공급가액+세액합계
			vo.getMain().setTot_amt((totAmt + totTax) + "");//공급가액+세액합계 // 2006.07.27 이제중

			//MSH value
			vo.getMeta().setSender_id(vo.getMain().getBuyer_id());
			vo.getMeta().setReceiver_id(vo.getMain().getSupplier_id());
			vo.getMeta().setSender_comp_id(vo.getMain().getBuyer_biz_id());
			vo.getMeta().setReceiver_comp_id(vo.getMain().getSupplier_biz_id());
			vo.getMeta().setExt_buyer_sabun(vo.getMain().getBuyer_id());

			result = cData.getResult();
			if (result.equals("오류")) {
				result = "ERR";
				resultMsg = cData.getResultMsg();
			} else {
				resultMsg = "";
			}
		}
	}

	/*    private int getTotTax(ArrayList line) {
	 int totTax = 0;
	 for(int i=0; i<line.size(); i++){
	 TaxLineVO lineVO = (TaxLineVO)line.get(i);
	 totTax = totTax + Integer.parseInt(CommonUtil.nullToZero(lineVO.getTax_amt()));
	 }
	 return totTax;
	 }
	 */
	// 2006.07.27 이제중 Long 형으로 변경
	private long getTotTax(ArrayList line) {
		long totTax = 0;
		for (int i = 0; i < line.size(); i++) {
			TaxLineVO lineVO = (TaxLineVO) line.get(i);
			totTax = totTax + Long.parseLong(CommonUtil.nullToZero(lineVO.getTax_amt()));
		}
		return totTax;
	}
	
	private long getPPATotTax(ArrayList line) {
		long totTax = 0;
		for (int i = 0; i < line.size(); i++) {
			TaxLineVO lineVO = (TaxLineVO) line.get(i);
			totTax = totTax + Long.parseLong(CommonUtil.nullToZero(lineVO.getTax_amt()));
		}
		return totTax;
	}

	private TaxLineVO setLineVO(CheckExcelLoadData cData, ArrayList list, String biz_id, String doc_date, String const_name, String amt) {
		TaxLineVO lineVO = new TaxLineVO();
		// 2006.07.27 이제중 Long 형으로 바꿈
		//        int nAmt = Integer.parseInt(CommonUtil.nullToZero(amt));//공급가액
		//        int nTax = 0;//세액

		long nAmt = Long.parseLong(CommonUtil.nullToZero(amt));//공급가액
		long nTax = 0;//세액

		lineVO.setLine_num((list.size() + 1) + ""); //line1개
		lineVO.setName(const_name);//품목명
		lineVO.setDefine_txt("공사");//규격
		if (cData.isDocDate())
			lineVO.setTrans_date(doc_date);//날짜

		lineVO.setQuantity("1");//수량

		if (cData.isAmt()) {
			// nTax = (int)Math.floor( ((nAmt-(nAmt%10))/10)/10 )*10; //원단위 절삭
			nTax = (nAmt - (nAmt % 10)) / 10; //화면과같이
			lineVO.setBasis_amt(amt + "");//단가
			lineVO.setAmt(amt + "");//공급가액
			lineVO.setTax_amt(nTax + "");//세액
		}
		lineVO.setLine_desc("");
		return lineVO;
	}

//2015.08.20 세액합계(PPA시스템만 사용)	
/*	private TaxLineVO setLinePPAVO(CheckExcelLoadData cData, ArrayList list, String biz_id, String doc_date, String const_name, String amt, long totVatAmt) {
		TaxLineVO lineVO = new TaxLineVO();

		lineVO.setLine_num((list.size() + 1) + ""); //line1개
		lineVO.setName(const_name);//품목명
		lineVO.setDefine_txt("공사");//규격
		if (cData.isDocDate())
			lineVO.setTrans_date(doc_date);//날짜

		lineVO.setQuantity("1");//수량

		if (cData.isAmt()) {
			lineVO.setBasis_amt(amt + "");//단가
			lineVO.setAmt(amt + "");//공급가액
			lineVO.setTax_amt(totVatAmt + "");//세액
		}
		lineVO.setLine_desc("");
		return lineVO;
	}
*/
	
/*
	public static void main(String arg[]) {
		ReadTaxInvoiceExcel read = new ReadTaxInvoiceExcel();
		File file = new File("E:\\psy\\세금계산서\\test\\세금계산서연계자료_sample.xls");

		try {
			read.getExcelData(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TaxInvoiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	
	public void makeVONcisFromExcel(TaxHeaderVO headerVO, ArrayList detailArray, Hashtable compHt) throws IOException, SQLException, TaxInvoiceException {

		String biz_id = headerVO.getBiz_no();
		String doc_date = headerVO.getPub_ymd();
		String const_no = headerVO.getCons_no();
		String doc_desc = headerVO.getComp_no();							//공사업체코드
		String const_name = headerVO.getCons_nm();							//공사명

		long totMeterAmt = 0;
		long totInbAmt = 0;
		long totProfAmt = 0;
//2015.08.20 세액합계(PPA시스템만 사용)
		long totVatAmt = 0;
		TaxDetailVO dVO = new TaxDetailVO();

		String uuid = CommonUtil.getUUID();

		try {

	//		금액 누적 , 상세정보저장
			for (int i = 0; i < detailArray.size(); i++) {
				dVO = (TaxDetailVO)detailArray.get(i);

				TaxReceiptVO ReceiptVO = new TaxReceiptVO();
				
				ReceiptVO.setBiz_id(headerVO.getBiz_no());						//공사업체사업자번호
				ReceiptVO.setConst_no(headerVO.getCons_no());					//공사번호
				ReceiptVO.setProf_cons_no(headerVO.getProf_cons_no());			//수익적공사번호
				ReceiptVO.setReceipt_no(dVO.getAcptno());						//접수번호
				ReceiptVO.setReceipt_kind(dVO.getAcpt_knd_nm());				//접수종류명
				ReceiptVO.setCust_name(dVO.getCustnm());						//고객명
				ReceiptVO.setAddress(dVO.getAddress());							//주소
				ReceiptVO.setConst_date(dVO.getOper_ymd());						//시공일자
				ReceiptVO.setMatrbill_comp_amt(dVO.getMatrbill_comp_amt());		//도급재료비
				ReceiptVO.setComp_amt(dVO.getComp_amt());						//도급공사비
				ReceiptVO.setProf_comp_amt(dVO.getProf_comp_amt());				//수익적공사비
				
				// 도급재료비+도급공사비 = 단가
				long tempAmt = Long.parseLong(CommonUtil.nullToZero(dVO.getMatrbill_comp_amt())) + Long.parseLong(CommonUtil.nullToZero(dVO.getComp_amt()));
				
				// 한로우 합금액
				//if ( dVO.getCons_knd_cd().equals("01") ) {			//계기부설비
					totMeterAmt += tempAmt;
					ReceiptVO.setAmt1(Long.toString(tempAmt));		// 단가 금액 입력함
				//} else if ( dVO.getCons_knd_cd().equals("02") ) {	//인입선공사비
					
					//totInbAmt += tempAmt;
				//	ReceiptVO.setAmt2(Long.toString(tempAmt));
				//}

				//totProfAmt += Long.parseLong(CommonUtil.nullToZero(dVO.getProf_comp_amt()));
				
				//System.out.println("totProfAmt = "+totProfAmt);
//2015.08.20 세액합계(PPA시스템만 사용)					
				if("K1PPAS1000".equals(headerVO.getRel_system_id())){
					totVatAmt += Long.parseLong(CommonUtil.nullToZero(dVO.getVat_amt()));
				}
					
				ReceiptVO.setUuid(uuid);
				details.add(ReceiptVO);
			}

		} catch (Exception e) {
    		result = "ERR";
			resultMsg =  "세금계산서 금액 처리 중 오류 발생";
    		System.out.println("makeVONcisFromExcel Error.. " + e);
        }

		if (!result.equals("ERR")) {

			String amt1 = Long.toString(totMeterAmt);
			String amt2 = Long.toString(totInbAmt); // 값이 없으므로 의미없음
			String amt3 = Long.toString(0); // 값이 없으므로 의미없음
			
			//validation
			CheckExcelLoadData cData = new CheckExcelLoadData();

			byte checkLength[] = const_name.getBytes();
			int lenFlag = checkLength.length + 13;

			//        cData.checkTaxInvoice(biz_id, doc_date, const_name, amt1, amt2);

			cData.checkTaxInvoice(biz_id, doc_date, const_name, amt1, amt2, amt3, lenFlag);  
			Object[] object = (Object[]) compHt.get(biz_id);
			TaxCompanyVO comp = null;
			TaxPersonVO person = null;

			if (object != null) {
				comp = (TaxCompanyVO) object[0];
				person = (TaxPersonVO) object[1];
			} else {
				comp = new TaxCompanyVO();
				person = new TaxPersonVO();
			}

			vo.setUuid(uuid);
			vo.getMeta().setConstruct_no(const_no);
			vo.getMeta().setUuid(uuid);
			vo.getMeta().setDoc_state("WM");
			vo.getMeta().setWriter_type("K");
			vo.getMeta().setContractor_id(financeVO.getId());
			vo.getMeta().setInspector_id(vo.getMain().getBuyer_id());
			vo.getMeta().setExt_process_status_code("1");
//2015.08.20 세액합계(PPA시스템만 사용)					
			if("K1PPAS1000".equals(headerVO.getRel_system_id())){			
				vo.getMeta().setExt_system_type("701");//PPA시스템(701)
			}else{
				vo.getMeta().setExt_system_type("700");//300 ET1 700 영업
			}
			vo.getMeta().setExt_revenue_construct_no(headerVO.getProf_cons_no());
			vo.getMeta().setExt_revenue_construct_amount(Long.toString(totProfAmt));

			vo.getMain().setUuid(uuid);

			if (cData.isDocDate())
				vo.getMain().setDoc_date(doc_date); //문서작성일 년

			vo.getMain().setDoc_desc(doc_desc); //비고
			vo.getMain().setVolum_id(""); //권번호
			vo.getMain().setIssue_id(""); //호번호
			vo.getMain().setSeq_id("-"); //일련번호
			vo.getMain().setDoc_type_code("01"); 	// (01: 세금계산서, 02 : 계산서)

			//공급자 정보
			//vo.getMain().setSupplier_biz_id(comp.getBiz_id()); //공급자 사업자 등록 번호
			vo.getMain().setSupplier_biz_id(biz_id); //공급자 사업자 등록 번호
			vo.getMain().setSupplier_president_name(comp.getPresident_name()); //공급자 성명
			vo.getMain().setSupplier_name(comp.getName()); //상호
			vo.getMain().setSupplier_biz_type(comp.getBiz_type()); //업태
			vo.getMain().setSupplier_biz_class(comp.getBiz_class()); //종목
			
		// 공급자 주소길이를  바이트 크기만큼 만 받아서 셋팅하기 시작  2011.11.09 윤민호
			//vo.getMain().setSupplier_addr(comp.getAddr().trim().length() > 30 ? (comp.getAddr().trim()).substring(0, 30) : comp.getAddr().trim()); //공급자 주소
			
			String str=comp.getAddr(); //입력정보
			int limit=145;              //Byte 제한
		
			String[] ary = null;

			byte[] rawBytes = str.getBytes("EUC-KR");
			int rawLength = rawBytes.length;

			int index = 0;
			int minus_byte_num = 0;
			int offset = 0;
			int hangul_byte_num = 2;

			if(rawLength > limit){
			int aryLength = (rawLength / limit) + (rawLength % limit != 0 ? 1 : 0);
			ary = new String[aryLength];

			for(int i=0; i<aryLength; i++){
			minus_byte_num = 0;
			offset = limit;

			if(index + offset > rawBytes.length){
			offset = rawBytes.length - index;
			}

			for(int j=0; j<offset; j++){
			if(((int)rawBytes[index + j] & 0x80) != 0){
			minus_byte_num ++;
			}
			}  //for

			if(minus_byte_num % hangul_byte_num != 0){
			offset -= minus_byte_num % hangul_byte_num;
			}

			ary[i] = new String(rawBytes, index, offset, "EUC-KR");
			index += offset ;

			} //aryLength for

			} else {  //rawLength if
			ary = new String[]{str};
			}

		System.out.println(" ■■■■■■■■■■■■■■■ MakeTaxInvoice 공급자 주소 결과: "+ ary[0]);			
		vo.getMain().setSupplier_addr(ary[0]); //저장
		
	// 공급자 주소길이를  바이트 크기만큼 만 받아서 셋팅하기 끝
			
			vo.getMain().setSupplier_id(person.getId()); //공급자 id
			vo.getMain().setSupplier_contactor_name(person.getName());
			vo.getMain().setSupplier_contactor_email(person.getEmail());
			vo.getMain().setSupplier_contactor_tel(person.getTel());

			//공급받는자 정보
			vo.getMain().setBuyer_president_name(vo.getMain().getBuyer_president_name()); //수요자 성명
			vo.getMain().setBuyer_biz_id(vo.getMain().getBuyer_biz_id()); //수요자 사업자 등록 번호
			vo.getMain().setBuyer_name(vo.getMain().getBuyer_name()); //상호
			vo.getMain().setBuyer_biz_type(vo.getMain().getBuyer_biz_type()); //업태
			vo.getMain().setBuyer_biz_class(vo.getMain().getBuyer_biz_class()); //종목
			
	// 공급받는자 주소길이를  바이트 크기만큼 만 받아서 셋팅하기 시작  2011.11.09 윤민호		
			//		vo.getMain().setBuyer_addr((vo.getMain().getBuyer_addr().trim()).length() > 30 ? (vo.getMain().getBuyer_addr().trim()).substring(0, 30) : vo.getMain().getBuyer_addr().trim()); //수요자 주소
			
					String str1=vo.getMain().getBuyer_addr(); //입력정보
					int limit1=97;              //Byte 제한
					
						String[] ary1 = null;
						byte[] rawBytes1 = str1.getBytes("EUC-KR");
						int rawLength1 = rawBytes1.length;

						int index1 = 0;
						int minus_byte_num1 = 0;
						int offset1 = 0;
						int hangul_byte_num1 = 2;

						if(rawLength1 > limit1){
						int aryLength = (rawLength1 / limit1) + (rawLength1 % limit1 != 0 ? 1 : 0);
						ary1 = new String[aryLength];

						for(int i=0; i<aryLength; i++){
						minus_byte_num1 = 0;
						offset1 = limit1;

						if(index1 + offset1 > rawBytes1.length){
						offset1 = rawBytes1.length - index1;
						}

						for(int j=0; j<offset1; j++){
						if(((int)rawBytes1[index1 + j] & 0x80) != 0){
						minus_byte_num1 ++;
						}
						}  //for

						if(minus_byte_num1 % hangul_byte_num1 != 0){
						offset1 -= minus_byte_num1 % hangul_byte_num1;
						}

						ary1[i] = new String(rawBytes1, index1, offset1, "EUC-KR");
						index1 += offset1 ;

						} //aryLength for

						} else {  //rawLength if
						ary1 = new String[]{str1};
						}

			//		System.out.println(" ■■■■■■■■■■■■■■■ MakeTaxInvoice 공급받는자 주소 결과: "+ ary1[0]);			
					vo.getMain().setBuyer_addr(ary1[0]); //입력			
					
		// 공급받는자 주소길이를  바이트 크기만큼 만 받아서 셋팅하기 끝
					
			
			vo.getMain().setBuyer_id(vo.getMain().getBuyer_id()); //수요자 id
			vo.getMain().setBuyer_contactor_name(vo.getMain().getBuyer_contactor_name());
			vo.getMain().setBuyer_contactor_email(vo.getMain().getBuyer_contactor_email());
			vo.getMain().setBuyer_contactor_tel(vo.getMain().getBuyer_contactor_tel());
			vo.getMain().setBuyer_biz_cd(vo.getMain().getBuyer_biz_cd()); // 종사업장번호 추가변경 200912

			//ERP 연계 추가 데이터 항목
			vo.getMeta().setContractor_id(financeVO.getId());
			vo.getMeta().setInspector_id(vo.getMain().getBuyer_id());
			vo.getMeta().setExt_process_status_code("1");
			//18:청구
			vo.getMain().setDemand_type("18");

			// 2006.08.10 내선기기 구분 값 넣지 않음
			vo.getMain().setImport_reg_id(""); //CX2
			vo.getMeta().setExt_status_code("C"); //N  //ncis

			//현금, 수표 등등..(셋팅안함)
			vo.getMain().setPayment_cash_dc_amt("");
			vo.getMain().setPayment_check_dc_amt("");
			vo.getMain().setPayment_bill_dc_amt("");
			vo.getMain().setPayment_credit_dc_amt("");


			//
			if (!CommonUtil.nullToZero(amt1).equals("0")){	//  -- //계기
//2015.08.20 세액합계(PPA시스템만 사용)					
/*
  				if("K1PPAS1000".equals(headerVO.getRel_system_id())){
 					vo.getLine().add(setLinePPAVO(cData, vo.getLine(), biz_id, doc_date, const_name, amt1,totVatAmt));
				}else{
					vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name, amt1));
				}
*/
				TaxLineVO lineVOtmp = new TaxLineVO();
				lineVOtmp = setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name, amt1);
				if("K1PPAS1000".equals(headerVO.getRel_system_id())){
					lineVOtmp.setTax_amt(totVatAmt+"");
				}
				vo.getLine().add(lineVOtmp);
			}
			//if (!CommonUtil.nullToZero(amt2).equals("0"))	//  -- //인입
			//	vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name+" 인입선공사비", amt2));
			//
			
			
			long totAmt = Long.parseLong(CommonUtil.nullToZero(amt1)) + Long.parseLong(CommonUtil.nullToZero(amt2));

			if (cData.isAmt()) {
				int blank = 11 - Long.toString(totAmt).length();
				vo.getMain().setBlank_num(blank + ""); //공란수
			}

			if (totAmt<0) {
				vo.getMain().setBusiness_type_code("2"); // (1: 일반세금계산서, 2: 마이너스세금계산서)
			} else {
				vo.getMain().setBusiness_type_code("1"); // (1: 일반세금계산서, 2: 마이너스세금계산서)
			}
//이부분 수정하기
			long totTax = getTotTax(vo.getLine());

			vo.getMain().setCharge_amt(totAmt + "");//단가합계
			vo.getMain().setTot_tax_amt(totTax + "");//세액합계

			//        vo.getMain().setTot_amt((totAmt+totTax)+"");//공급가액+세액합계
			vo.getMain().setTot_amt((totAmt + totTax) + "");//공급가액+세액합계 // 2006.07.27 이제중

			//MSH value
			vo.getMeta().setSender_id(vo.getMain().getBuyer_id());
			vo.getMeta().setReceiver_id(vo.getMain().getSupplier_id());
			vo.getMeta().setSender_comp_id(vo.getMain().getBuyer_biz_id());
			vo.getMeta().setReceiver_comp_id(vo.getMain().getSupplier_biz_id());
			vo.getMeta().setExt_buyer_sabun(vo.getMain().getBuyer_id());

			result = cData.getResult();
			if (result.equals("오류")) {
				result = "ERR";
				resultMsg = cData.getResultMsg();
			} else {
				resultMsg = "";
			}
		}
	}
	
	/**
	 * 
	 * @param headerVO
	 * @param ncis_detailArray
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws TaxInvoiceException
	public Hashtable makeNcisTaxinvoice(TaxHeaderVO headerVO, ArrayList ncis_detailArray) throws IOException, SQLException, TaxInvoiceException {

		Hashtable finalData = new Hashtable();
		Hashtable compHt = new Hashtable();
		
		Object[] object = new Object[2];
		object[0] = new TaxCompanyVO();
		object[1] = new TaxPersonVO();
		
		compHt.put(headerVO.getBiz_no(), object);

		if (ncis_detailArray.size()<1) { result="ERR"; resultMsg="DETAIL TABLE에 상세정보가 없습니다."; }

		if (!result.equals("ERR")) setBuyerInfo(headerVO.getBuyer_biz_cd()); 				// 종사업장번호로 검색
		
		if (!result.equals("ERR")) setInspectorInfo(headerVO.getInspector_id()); 			// 한전 담당자 검색
		
		if (!result.equals("ERR")) setFinanceInfo(headerVO.getContractor_id()); 			// 한전 담당자 검색
		
		if (!result.equals("ERR")) compHt = settingCompInfo(headerVO.getBiz_no(), compHt); 	// 업체정보 검색
		
		if (!result.equals("ERR")) makeVOFromExcel(headerVO, ncis_detailArray, compHt);

		if ("".equals(headerVO.getDoc_type_detail())){
			result="ERR"; resultMsg="세금계산서 종류 가 없습니다.";
		}else{
			vo.getMain().setDoc_type_detail(headerVO.getDoc_type_detail());
		}

		finalData.put("taxVO", vo);
		finalData.put("financeVO", financeVO);
		finalData.put("detailArry", details);
		finalData.put("result", result);
		finalData.put("resultMsg", resultMsg);

		System.out.println("세금계산서 vo생성 완료 [makeTaxinvoice]");
		return finalData;
	}
	 */
}
