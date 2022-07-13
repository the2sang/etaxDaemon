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
//	private ArrayList results = new ArrayList(); //result (���� or ����)
//	private ArrayList resultMsgs = new ArrayList(); //result message

	public void setBuyerInfo(String bizNO) {

		TaxCompanyNewManagerDao dao = new TaxCompanyNewManagerDao();
		ArrayList complist = new ArrayList();
		try {
			//��������ȣ�� �˻� (����)
			if("".equals(bizNO)){ // �߰� ��������ȣ�� ������
				result = "ERR"; resultMsg = "���޹޴���  ��������ȣ�� �����ϴ�.";
			}else{
				complist = dao.selectCompanyList(bizNO,"","K");

				//if (complist.size()<1) { result = "ERR"; resultMsg = "���޹޴���  ����ڹ�ȣ��  �˻��Ǵ� ����������� �����ϴ�.";}
				if (complist.size()<1) { result = "ERR"; resultMsg = "���޹޴���  ��������ȣ�� �˻��Ǵ� ����������� �����ϴ�.";}
				else {
					TaxCompanyVO CompanyVO = new TaxCompanyVO();
					CompanyVO = (TaxCompanyVO)complist.get(0);

					//���޹޴��� ����
					vo.getMain().setBuyer_biz_id(CommonUtil.nullToBlank(CompanyVO.getBiz_id()));         //������ ����� ��� ��ȣ
					vo.getMain().setBuyer_president_name(CommonUtil.nullToBlank(CompanyVO.getPresident_name())); //������ ����
					vo.getMain().setBuyer_name(CommonUtil.nullToBlank(CompanyVO.getName()));       		//��ȣ
					vo.getMain().setBuyer_biz_type(CommonUtil.nullToBlank(CompanyVO.getBiz_type()));       //����
					vo.getMain().setBuyer_biz_class(CommonUtil.nullToBlank(CompanyVO.getBiz_class()));      //����
					vo.getMain().setBuyer_addr(CommonUtil.nullToBlank(CompanyVO.getAddr())); //�ּ�
					vo.getMeta().setComp_code(CommonUtil.nullToBlank(CompanyVO.getComp_code()));
					vo.getMain().setBuyer_biz_cd(CommonUtil.nullToBlank(CompanyVO.getBuy_regist_id())); //��������ȣ �߰�
					vo.getMain().setZbran_name(CommonUtil.nullToBlank(CompanyVO.getZbran_name())); // ������ 20100204
					// �߰����� 200912
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
			if (personlist.size()<1) { result = "ERR"; resultMsg = "�ش� �������  �˻��Ǵ� ����������� �����ϴ�.";}
			else {
				TaxPersonVO PersonVO = new TaxPersonVO();
				PersonVO = (TaxPersonVO)personlist.get(0);

				//���޹޴��� ����� ����
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
			if (personlist.size()<1) { result = "ERR"; resultMsg = "�ش� �������  �˻��Ǵ� ����������� �����ϴ�.";}
			else {
				TaxPersonVO PersonVO = new TaxPersonVO();
				PersonVO = (TaxPersonVO)personlist.get(0);

				financeVO.setId(CommonUtil.nullToBlank(PersonVO.getId()));
				financeVO.setName(CommonUtil.nullToBlank(PersonVO.getName()));
				financeVO.setEmail(CommonUtil.nullToBlank(PersonVO.getEmail()));
				financeVO.setTel(CommonUtil.nullToBlank(PersonVO.getTel()));
				//2015.12.07 ������� ���Ϻ����̸� ����ڷ� ���� CDH
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
//		ArrayList results = new ArrayList(); //result (���� or ����)
//		ArrayList resultMsgs = new ArrayList(); //result message
 
		Hashtable compHt = new Hashtable(); //comp info from excel
		Object[] object = new Object[2];
		object[0] = new TaxCompanyVO();
		object[1] = new TaxPersonVO();
		compHt.put(headerVO.getBiz_no(), object);

		if (detailArray.size()<1) { result="ERR"; resultMsg="DETAIL TABLE�� �������� �����ϴ�."; }

		//if (!result.equals("ERR")) setBuyerInfo(headerVO.getBuyer_biz_id());
		if (!result.equals("ERR")) setBuyerInfo(headerVO.getBuyer_biz_cd()); 				// ��������ȣ�� �˻�
		if (!result.equals("ERR")) setInspectorInfo(headerVO.getInspector_id()); 			// ���� ����� �˻�
		if (!result.equals("ERR")) setFinanceInfo(headerVO.getContractor_id()); 			// ���� ����� �˻�
		if (!result.equals("ERR")) compHt = settingCompInfo(headerVO.getBiz_no(), compHt); 	// ��ü���� �˻�
		
		// ���� �߰�����
		// K1ETAX1022 = �������    K1NCIS1000 = ���� ��ħ�� �뿪 ������,   K1NDIS1000 = ���� ��������Ȱ����
		//2015.08.20 �����߰� 
		//K1PPAS1000 = PPA�ý���
		if("K1NCIS1000".equals(headerVO.getRel_system_id()) || "K1NDIS1000".equals(headerVO.getRel_system_id()) || "K1PPAS1000".equals(headerVO.getRel_system_id())){
			if (!result.equals("ERR")) makeVONcisFromExcel(headerVO, detailArray, compHt); // 201009 Add
		}else if("K1ETAX1022".equals(headerVO.getRel_system_id())){//�������
			if (!result.equals("ERR")) makeVOFromExcel(headerVO, detailArray, compHt); // ��������
		}else{
//			System.out.println("CDH id�� �������ͼ� ����");
			result="ERR"; resultMsg="System ID�� �������� �ʽ��ϴ�.";
		}

		if ("".equals(headerVO.getDoc_type_detail())){
			result="ERR"; resultMsg="���ݰ�꼭 ���� �� �����ϴ�.";
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

		System.out.println("���ݰ�꼭 vo���� �Ϸ� [makeTaxinvoice]");
		return finalData;
	}

	private Hashtable settingCompInfo(String bizNO, Hashtable compHt) throws SQLException, TaxInvoiceException {
//		ArrayList bizList = getBizIdList(compHt);
		TaxPersonNewManagerDao dao = new TaxPersonNewManagerDao();
		compHt = dao.getOutterInfoForExcelPublish(bizNO, compHt);

//		System.out.println("compHt.size()=="+((TaxCompanyVO)(((Object[])(compHt.get(bizNO)))[0])).getBiz_id()   );
//		System.out.println("compHt.size()=="+((TaxCompanyVO)(((Object[])(compHt.get(bizNO)))[0])).getName()   );

		if (((TaxCompanyVO)(((Object[])(compHt.get(bizNO)))[0])).getBiz_id().equals("")) 
		{ result = "ERR"; resultMsg = "������ ����ڹ�ȣ��  �˻��Ǵ� ��ü������ �����ϴ�.";}

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
		String doc_desc = headerVO.getComp_no();							//�����ü�ڵ�
		String const_name = headerVO.getCons_nm();							//�����

		long totMeterAmt = 0;
		long totInbAmt = 0;
		long totIdfmAmt = 0;	//����Ժμ��� 20101004 �߰�
		long totProfAmt = 0;

		TaxDetailVO dVO = new TaxDetailVO();

		String uuid = CommonUtil.getUUID();

		try {

	//		�ݾ� ���� , ����������
			for (int i = 0; i < detailArray.size(); i++) {
				dVO = (TaxDetailVO)detailArray.get(i);

				TaxReceiptVO ReceiptVO = new TaxReceiptVO();
				ReceiptVO.setBiz_id(headerVO.getBiz_no());						//�����ü����ڹ�ȣ
				ReceiptVO.setConst_no(headerVO.getCons_no());					//�����ȣ
				ReceiptVO.setProf_cons_no(headerVO.getProf_cons_no());			//�����������ȣ
				ReceiptVO.setReceipt_no(dVO.getAcptno());						//������ȣ
				ReceiptVO.setReceipt_kind(dVO.getAcpt_knd_nm());				//����������
				ReceiptVO.setCust_name(dVO.getCustnm());						//����
				ReceiptVO.setAddress(dVO.getAddress());							//�ּ�
				ReceiptVO.setConst_date(dVO.getOper_ymd());						//�ð�����
				ReceiptVO.setMatrbill_comp_amt(dVO.getMatrbill_comp_amt());		//��������
				ReceiptVO.setComp_amt(dVO.getComp_amt());						//���ް����
				ReceiptVO.setProf_comp_amt(dVO.getProf_comp_amt());				//�����������

				long tempAmt = Long.parseLong(CommonUtil.nullToZero(dVO.getMatrbill_comp_amt())) + Long.parseLong(CommonUtil.nullToZero(dVO.getComp_amt()));

				if ( dVO.getCons_knd_cd().equals("01") || dVO.getCons_knd_cd().equals("03")) {			//���μ���
					totMeterAmt += tempAmt;
					ReceiptVO.setAmt1(Long.toString(tempAmt));
					
					if(dVO.getCons_knd_cd().equals("03")){
						ReceiptVO.setAmt3(Long.toString(tempAmt));
					}
					
				} else if ( dVO.getCons_knd_cd().equals("02") ) {	//���Լ������
					totInbAmt += tempAmt;
					ReceiptVO.setAmt2(Long.toString(tempAmt));
				}
				
				//} else if ( dVO.getCons_knd_cd().equals("03") ) { 	//����Ժμ��� 20101004 �߰�
				//	totIdfmAmt += tempAmt;
				//	ReceiptVO.setAmt3(Long.toString(tempAmt));		//�����ؾ���.setAmt3

				totProfAmt += Long.parseLong(CommonUtil.nullToZero(dVO.getProf_comp_amt()));

				ReceiptVO.setUuid(uuid);
				details.add(ReceiptVO);
			}

		} catch (Exception e) {
    		result = "ERR";
			resultMsg =  "���ݰ�꼭 �ݾ� ó�� �� ���� �߻�";
    		logger.debug("makeVOFromExcel Error.. " + e);
        }

		if (!result.equals("ERR")) {

			String amt1 = Long.toString(totMeterAmt);
			String amt2 = Long.toString(totInbAmt);
			String amt3 = Long.toString(totIdfmAmt);		//����Ժμ��� 20101004 �߰�

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
				vo.getMain().setDoc_date(doc_date); //�����ۼ��� ��

			vo.getMain().setDoc_desc(doc_desc); //���
			vo.getMain().setVolum_id(""); //�ǹ�ȣ
			vo.getMain().setIssue_id(""); //ȣ��ȣ
			vo.getMain().setSeq_id("-"); //�Ϸù�ȣ
			vo.getMain().setDoc_type_code("01"); 	// (01: ���ݰ�꼭, 02 : ��꼭)

			//������ ����
			//vo.getMain().setSupplier_biz_id(comp.getBiz_id()); //������ ����� ��� ��ȣ
			vo.getMain().setSupplier_biz_id(biz_id); //������ ����� ��� ��ȣ
			vo.getMain().setSupplier_president_name(comp.getPresident_name()); //������ ����
			vo.getMain().setSupplier_name(comp.getName()); //��ȣ
			vo.getMain().setSupplier_biz_type(comp.getBiz_type()); //����
			vo.getMain().setSupplier_biz_class(comp.getBiz_class()); //����
			
		// ������ �ּұ��̸�  ����Ʈ ũ�⸸ŭ �� �޾Ƽ� �����ϱ� ����  2011.11.09 ����ȣ
			//vo.getMain().setSupplier_addr(comp.getAddr().trim().length() > 30 ? (comp.getAddr().trim()).substring(0, 30) : comp.getAddr().trim()); //������ �ּ�
			
			String str=comp.getAddr(); //�Է�����
			int limit=145;              //Byte ����
		
			
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

		System.out.println(" ���������������� MakeTaxInvoice ������ �ּ� ���: "+ ary[0]);			
		vo.getMain().setSupplier_addr(ary[0]); //����
		
	// ������ �ּұ��̸�  ����Ʈ ũ�⸸ŭ �� �޾Ƽ� �����ϱ� ��
			
			vo.getMain().setSupplier_id(person.getId()); //������ id
			vo.getMain().setSupplier_contactor_name(person.getName());
			vo.getMain().setSupplier_contactor_email(person.getEmail());
			vo.getMain().setSupplier_contactor_tel(person.getTel());

			//���޹޴��� ����
			vo.getMain().setBuyer_president_name(vo.getMain().getBuyer_president_name()); //������ ����
			vo.getMain().setBuyer_biz_id(vo.getMain().getBuyer_biz_id()); //������ ����� ��� ��ȣ
			vo.getMain().setBuyer_name(vo.getMain().getBuyer_name()); //��ȣ
			vo.getMain().setBuyer_biz_type(vo.getMain().getBuyer_biz_type()); //����
			vo.getMain().setBuyer_biz_class(vo.getMain().getBuyer_biz_class()); //����
			
	// ���޹޴��� �ּұ��̸�  ����Ʈ ũ�⸸ŭ �� �޾Ƽ� �����ϱ� ����  2011.11.09 ����ȣ
			//vo.getMain().setBuyer_addr((vo.getMain().getBuyer_addr().trim()).length() > 30 ? (vo.getMain().getBuyer_addr().trim()).substring(0, 30) : vo.getMain().getBuyer_addr().trim()); //������ �ּ�
			
			String str1=vo.getMain().getBuyer_addr(); //�Է�����
			int limit1=97;              //Byte ����
		
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

	//	System.out.println(" ���������������� MakeTaxInvoice ���޹޴��� �ּ� ���: "+ ary1[0]);			
		vo.getMain().setBuyer_addr(ary1[0]); //����
		
	// ���޹޴��� �ּұ��̸�  ����Ʈ ũ�⸸ŭ �� �޾Ƽ� �����ϱ� ��
		
			
			
			vo.getMain().setBuyer_id(vo.getMain().getBuyer_id()); //������ id
			vo.getMain().setBuyer_contactor_name(vo.getMain().getBuyer_contactor_name());
			vo.getMain().setBuyer_contactor_email(vo.getMain().getBuyer_contactor_email());
			vo.getMain().setBuyer_contactor_tel(vo.getMain().getBuyer_contactor_tel());
			vo.getMain().setBuyer_biz_cd(vo.getMain().getBuyer_biz_cd()); // ��������ȣ �߰����� 200912

			//ERP ���� �߰� ������ �׸�
			vo.getMeta().setContractor_id(financeVO.getId());
			vo.getMeta().setInspector_id(vo.getMain().getBuyer_id());
			vo.getMeta().setExt_process_status_code("1");
			//18:û��
			vo.getMain().setDemand_type("18");

			// 2006.08.10 ������� ���� ��
			vo.getMain().setImport_reg_id("CX2");
			vo.getMeta().setExt_status_code("N");

			//����, ��ǥ ���..(���þ���)
			vo.getMain().setPayment_cash_dc_amt("");
			vo.getMain().setPayment_check_dc_amt("");
			vo.getMain().setPayment_bill_dc_amt("");
			vo.getMain().setPayment_credit_dc_amt("");


			if (!CommonUtil.nullToZero(amt1).equals("0"))	//���
				vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name+" �������", amt1));
			if (!CommonUtil.nullToZero(amt2).equals("0"))	//����
				vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name+" ���Լ������", amt2));
			
			//if (!CommonUtil.nullToZero(amt3).equals("0"))	//�����
			//	vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name+" ����Ժμ���", amt3));
			

			long totAmt = Long.parseLong(CommonUtil.nullToZero(amt1)) + Long.parseLong(CommonUtil.nullToZero(amt2)); //

			if (cData.isAmt()) {
				int blank = 11 - Long.toString(totAmt).length();
				vo.getMain().setBlank_num(blank + ""); //������
			}

			if (totAmt<0) {
				vo.getMain().setBusiness_type_code("2"); // (1: �Ϲݼ��ݰ�꼭, 2: ���̳ʽ����ݰ�꼭)
			} else {
				vo.getMain().setBusiness_type_code("1"); // (1: �Ϲݼ��ݰ�꼭, 2: ���̳ʽ����ݰ�꼭)
			}

			long totTax = getTotTax(vo.getLine());

			vo.getMain().setCharge_amt(totAmt + "");//�ܰ��հ�
			vo.getMain().setTot_tax_amt(totTax + "");//�����հ�

			//        vo.getMain().setTot_amt((totAmt+totTax)+"");//���ް���+�����հ�
			vo.getMain().setTot_amt((totAmt + totTax) + "");//���ް���+�����հ� // 2006.07.27 ������

			//MSH value
			vo.getMeta().setSender_id(vo.getMain().getBuyer_id());
			vo.getMeta().setReceiver_id(vo.getMain().getSupplier_id());
			vo.getMeta().setSender_comp_id(vo.getMain().getBuyer_biz_id());
			vo.getMeta().setReceiver_comp_id(vo.getMain().getSupplier_biz_id());
			vo.getMeta().setExt_buyer_sabun(vo.getMain().getBuyer_id());

			result = cData.getResult();
			if (result.equals("����")) {
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
	// 2006.07.27 ������ Long ������ ����
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
		// 2006.07.27 ������ Long ������ �ٲ�
		//        int nAmt = Integer.parseInt(CommonUtil.nullToZero(amt));//���ް���
		//        int nTax = 0;//����

		long nAmt = Long.parseLong(CommonUtil.nullToZero(amt));//���ް���
		long nTax = 0;//����

		lineVO.setLine_num((list.size() + 1) + ""); //line1��
		lineVO.setName(const_name);//ǰ���
		lineVO.setDefine_txt("����");//�԰�
		if (cData.isDocDate())
			lineVO.setTrans_date(doc_date);//��¥

		lineVO.setQuantity("1");//����

		if (cData.isAmt()) {
			// nTax = (int)Math.floor( ((nAmt-(nAmt%10))/10)/10 )*10; //������ ����
			nTax = (nAmt - (nAmt % 10)) / 10; //ȭ�������
			lineVO.setBasis_amt(amt + "");//�ܰ�
			lineVO.setAmt(amt + "");//���ް���
			lineVO.setTax_amt(nTax + "");//����
		}
		lineVO.setLine_desc("");
		return lineVO;
	}

//2015.08.20 �����հ�(PPA�ý��۸� ���)	
/*	private TaxLineVO setLinePPAVO(CheckExcelLoadData cData, ArrayList list, String biz_id, String doc_date, String const_name, String amt, long totVatAmt) {
		TaxLineVO lineVO = new TaxLineVO();

		lineVO.setLine_num((list.size() + 1) + ""); //line1��
		lineVO.setName(const_name);//ǰ���
		lineVO.setDefine_txt("����");//�԰�
		if (cData.isDocDate())
			lineVO.setTrans_date(doc_date);//��¥

		lineVO.setQuantity("1");//����

		if (cData.isAmt()) {
			lineVO.setBasis_amt(amt + "");//�ܰ�
			lineVO.setAmt(amt + "");//���ް���
			lineVO.setTax_amt(totVatAmt + "");//����
		}
		lineVO.setLine_desc("");
		return lineVO;
	}
*/
	
/*
	public static void main(String arg[]) {
		ReadTaxInvoiceExcel read = new ReadTaxInvoiceExcel();
		File file = new File("E:\\psy\\���ݰ�꼭\\test\\���ݰ�꼭�����ڷ�_sample.xls");

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
		String doc_desc = headerVO.getComp_no();							//�����ü�ڵ�
		String const_name = headerVO.getCons_nm();							//�����

		long totMeterAmt = 0;
		long totInbAmt = 0;
		long totProfAmt = 0;
//2015.08.20 �����հ�(PPA�ý��۸� ���)
		long totVatAmt = 0;
		TaxDetailVO dVO = new TaxDetailVO();

		String uuid = CommonUtil.getUUID();

		try {

	//		�ݾ� ���� , ����������
			for (int i = 0; i < detailArray.size(); i++) {
				dVO = (TaxDetailVO)detailArray.get(i);

				TaxReceiptVO ReceiptVO = new TaxReceiptVO();
				
				ReceiptVO.setBiz_id(headerVO.getBiz_no());						//�����ü����ڹ�ȣ
				ReceiptVO.setConst_no(headerVO.getCons_no());					//�����ȣ
				ReceiptVO.setProf_cons_no(headerVO.getProf_cons_no());			//�����������ȣ
				ReceiptVO.setReceipt_no(dVO.getAcptno());						//������ȣ
				ReceiptVO.setReceipt_kind(dVO.getAcpt_knd_nm());				//����������
				ReceiptVO.setCust_name(dVO.getCustnm());						//����
				ReceiptVO.setAddress(dVO.getAddress());							//�ּ�
				ReceiptVO.setConst_date(dVO.getOper_ymd());						//�ð�����
				ReceiptVO.setMatrbill_comp_amt(dVO.getMatrbill_comp_amt());		//��������
				ReceiptVO.setComp_amt(dVO.getComp_amt());						//���ް����
				ReceiptVO.setProf_comp_amt(dVO.getProf_comp_amt());				//�����������
				
				// ��������+���ް���� = �ܰ�
				long tempAmt = Long.parseLong(CommonUtil.nullToZero(dVO.getMatrbill_comp_amt())) + Long.parseLong(CommonUtil.nullToZero(dVO.getComp_amt()));
				
				// �ѷο� �ձݾ�
				//if ( dVO.getCons_knd_cd().equals("01") ) {			//���μ���
					totMeterAmt += tempAmt;
					ReceiptVO.setAmt1(Long.toString(tempAmt));		// �ܰ� �ݾ� �Է���
				//} else if ( dVO.getCons_knd_cd().equals("02") ) {	//���Լ������
					
					//totInbAmt += tempAmt;
				//	ReceiptVO.setAmt2(Long.toString(tempAmt));
				//}

				//totProfAmt += Long.parseLong(CommonUtil.nullToZero(dVO.getProf_comp_amt()));
				
				//System.out.println("totProfAmt = "+totProfAmt);
//2015.08.20 �����հ�(PPA�ý��۸� ���)					
				if("K1PPAS1000".equals(headerVO.getRel_system_id())){
					totVatAmt += Long.parseLong(CommonUtil.nullToZero(dVO.getVat_amt()));
				}
					
				ReceiptVO.setUuid(uuid);
				details.add(ReceiptVO);
			}

		} catch (Exception e) {
    		result = "ERR";
			resultMsg =  "���ݰ�꼭 �ݾ� ó�� �� ���� �߻�";
    		System.out.println("makeVONcisFromExcel Error.. " + e);
        }

		if (!result.equals("ERR")) {

			String amt1 = Long.toString(totMeterAmt);
			String amt2 = Long.toString(totInbAmt); // ���� �����Ƿ� �ǹ̾���
			String amt3 = Long.toString(0); // ���� �����Ƿ� �ǹ̾���
			
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
//2015.08.20 �����հ�(PPA�ý��۸� ���)					
			if("K1PPAS1000".equals(headerVO.getRel_system_id())){			
				vo.getMeta().setExt_system_type("701");//PPA�ý���(701)
			}else{
				vo.getMeta().setExt_system_type("700");//300 ET1 700 ����
			}
			vo.getMeta().setExt_revenue_construct_no(headerVO.getProf_cons_no());
			vo.getMeta().setExt_revenue_construct_amount(Long.toString(totProfAmt));

			vo.getMain().setUuid(uuid);

			if (cData.isDocDate())
				vo.getMain().setDoc_date(doc_date); //�����ۼ��� ��

			vo.getMain().setDoc_desc(doc_desc); //���
			vo.getMain().setVolum_id(""); //�ǹ�ȣ
			vo.getMain().setIssue_id(""); //ȣ��ȣ
			vo.getMain().setSeq_id("-"); //�Ϸù�ȣ
			vo.getMain().setDoc_type_code("01"); 	// (01: ���ݰ�꼭, 02 : ��꼭)

			//������ ����
			//vo.getMain().setSupplier_biz_id(comp.getBiz_id()); //������ ����� ��� ��ȣ
			vo.getMain().setSupplier_biz_id(biz_id); //������ ����� ��� ��ȣ
			vo.getMain().setSupplier_president_name(comp.getPresident_name()); //������ ����
			vo.getMain().setSupplier_name(comp.getName()); //��ȣ
			vo.getMain().setSupplier_biz_type(comp.getBiz_type()); //����
			vo.getMain().setSupplier_biz_class(comp.getBiz_class()); //����
			
		// ������ �ּұ��̸�  ����Ʈ ũ�⸸ŭ �� �޾Ƽ� �����ϱ� ����  2011.11.09 ����ȣ
			//vo.getMain().setSupplier_addr(comp.getAddr().trim().length() > 30 ? (comp.getAddr().trim()).substring(0, 30) : comp.getAddr().trim()); //������ �ּ�
			
			String str=comp.getAddr(); //�Է�����
			int limit=145;              //Byte ����
		
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

		System.out.println(" ���������������� MakeTaxInvoice ������ �ּ� ���: "+ ary[0]);			
		vo.getMain().setSupplier_addr(ary[0]); //����
		
	// ������ �ּұ��̸�  ����Ʈ ũ�⸸ŭ �� �޾Ƽ� �����ϱ� ��
			
			vo.getMain().setSupplier_id(person.getId()); //������ id
			vo.getMain().setSupplier_contactor_name(person.getName());
			vo.getMain().setSupplier_contactor_email(person.getEmail());
			vo.getMain().setSupplier_contactor_tel(person.getTel());

			//���޹޴��� ����
			vo.getMain().setBuyer_president_name(vo.getMain().getBuyer_president_name()); //������ ����
			vo.getMain().setBuyer_biz_id(vo.getMain().getBuyer_biz_id()); //������ ����� ��� ��ȣ
			vo.getMain().setBuyer_name(vo.getMain().getBuyer_name()); //��ȣ
			vo.getMain().setBuyer_biz_type(vo.getMain().getBuyer_biz_type()); //����
			vo.getMain().setBuyer_biz_class(vo.getMain().getBuyer_biz_class()); //����
			
	// ���޹޴��� �ּұ��̸�  ����Ʈ ũ�⸸ŭ �� �޾Ƽ� �����ϱ� ����  2011.11.09 ����ȣ		
			//		vo.getMain().setBuyer_addr((vo.getMain().getBuyer_addr().trim()).length() > 30 ? (vo.getMain().getBuyer_addr().trim()).substring(0, 30) : vo.getMain().getBuyer_addr().trim()); //������ �ּ�
			
					String str1=vo.getMain().getBuyer_addr(); //�Է�����
					int limit1=97;              //Byte ����
					
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

			//		System.out.println(" ���������������� MakeTaxInvoice ���޹޴��� �ּ� ���: "+ ary1[0]);			
					vo.getMain().setBuyer_addr(ary1[0]); //�Է�			
					
		// ���޹޴��� �ּұ��̸�  ����Ʈ ũ�⸸ŭ �� �޾Ƽ� �����ϱ� ��
					
			
			vo.getMain().setBuyer_id(vo.getMain().getBuyer_id()); //������ id
			vo.getMain().setBuyer_contactor_name(vo.getMain().getBuyer_contactor_name());
			vo.getMain().setBuyer_contactor_email(vo.getMain().getBuyer_contactor_email());
			vo.getMain().setBuyer_contactor_tel(vo.getMain().getBuyer_contactor_tel());
			vo.getMain().setBuyer_biz_cd(vo.getMain().getBuyer_biz_cd()); // ��������ȣ �߰����� 200912

			//ERP ���� �߰� ������ �׸�
			vo.getMeta().setContractor_id(financeVO.getId());
			vo.getMeta().setInspector_id(vo.getMain().getBuyer_id());
			vo.getMeta().setExt_process_status_code("1");
			//18:û��
			vo.getMain().setDemand_type("18");

			// 2006.08.10 ������� ���� �� ���� ����
			vo.getMain().setImport_reg_id(""); //CX2
			vo.getMeta().setExt_status_code("C"); //N  //ncis

			//����, ��ǥ ���..(���þ���)
			vo.getMain().setPayment_cash_dc_amt("");
			vo.getMain().setPayment_check_dc_amt("");
			vo.getMain().setPayment_bill_dc_amt("");
			vo.getMain().setPayment_credit_dc_amt("");


			//
			if (!CommonUtil.nullToZero(amt1).equals("0")){	//  -- //���
//2015.08.20 �����հ�(PPA�ý��۸� ���)					
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
			//if (!CommonUtil.nullToZero(amt2).equals("0"))	//  -- //����
			//	vo.getLine().add(setLineVO(cData, vo.getLine(), biz_id, doc_date, const_name+" ���Լ������", amt2));
			//
			
			
			long totAmt = Long.parseLong(CommonUtil.nullToZero(amt1)) + Long.parseLong(CommonUtil.nullToZero(amt2));

			if (cData.isAmt()) {
				int blank = 11 - Long.toString(totAmt).length();
				vo.getMain().setBlank_num(blank + ""); //������
			}

			if (totAmt<0) {
				vo.getMain().setBusiness_type_code("2"); // (1: �Ϲݼ��ݰ�꼭, 2: ���̳ʽ����ݰ�꼭)
			} else {
				vo.getMain().setBusiness_type_code("1"); // (1: �Ϲݼ��ݰ�꼭, 2: ���̳ʽ����ݰ�꼭)
			}
//�̺κ� �����ϱ�
			long totTax = getTotTax(vo.getLine());

			vo.getMain().setCharge_amt(totAmt + "");//�ܰ��հ�
			vo.getMain().setTot_tax_amt(totTax + "");//�����հ�

			//        vo.getMain().setTot_amt((totAmt+totTax)+"");//���ް���+�����հ�
			vo.getMain().setTot_amt((totAmt + totTax) + "");//���ް���+�����հ� // 2006.07.27 ������

			//MSH value
			vo.getMeta().setSender_id(vo.getMain().getBuyer_id());
			vo.getMeta().setReceiver_id(vo.getMain().getSupplier_id());
			vo.getMeta().setSender_comp_id(vo.getMain().getBuyer_biz_id());
			vo.getMeta().setReceiver_comp_id(vo.getMain().getSupplier_biz_id());
			vo.getMeta().setExt_buyer_sabun(vo.getMain().getBuyer_id());

			result = cData.getResult();
			if (result.equals("����")) {
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

		if (ncis_detailArray.size()<1) { result="ERR"; resultMsg="DETAIL TABLE�� �������� �����ϴ�."; }

		if (!result.equals("ERR")) setBuyerInfo(headerVO.getBuyer_biz_cd()); 				// ��������ȣ�� �˻�
		
		if (!result.equals("ERR")) setInspectorInfo(headerVO.getInspector_id()); 			// ���� ����� �˻�
		
		if (!result.equals("ERR")) setFinanceInfo(headerVO.getContractor_id()); 			// ���� ����� �˻�
		
		if (!result.equals("ERR")) compHt = settingCompInfo(headerVO.getBiz_no(), compHt); 	// ��ü���� �˻�
		
		if (!result.equals("ERR")) makeVOFromExcel(headerVO, ncis_detailArray, compHt);

		if ("".equals(headerVO.getDoc_type_detail())){
			result="ERR"; resultMsg="���ݰ�꼭 ���� �� �����ϴ�.";
		}else{
			vo.getMain().setDoc_type_detail(headerVO.getDoc_type_detail());
		}

		finalData.put("taxVO", vo);
		finalData.put("financeVO", financeVO);
		finalData.put("detailArry", details);
		finalData.put("result", result);
		finalData.put("resultMsg", resultMsg);

		System.out.println("���ݰ�꼭 vo���� �Ϸ� [makeTaxinvoice]");
		return finalData;
	}
	 */
}
