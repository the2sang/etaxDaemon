package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxCompanyNewManagerDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxLineDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxMailManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxPersonNewManagerDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxSMSManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxStatusInfoManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxBpVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLineVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMailVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxPersonVO;
import kr.co.bizframe.ebxml.ebms.message.handler.MSHException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;


public class ChangeStatus {
	
	
	
	/*
	 * 저장된 세금계산서조회
	 */
	

	/*
	 * 세금계산서 상태 상태값 설명 CFR 확인요청 CFS 확인전성 CFC 확인취소 DEL 폐기 END 정상완료 REJ 취소완료
	 */
	
	
//20180619 사용하지 않는 method라서 comment 처리 
/*
	public String sendPublishBySupplier(TaxInvoiceVO taxVO) throws TaxInvoiceException, SQLException, MSHException {
		String mailTitle = "";
		String retMsg = "";
		if (taxVO.getMeta().getDoc_state().equals("CFR")) {
			taxVO.getMeta().setDoc_state("CFR");
			mailTitle = "세금계산서 확인요청 입니다.";
			this.sendToBuyer(taxVO, mailTitle);
//			 kdn
			sendToKDN(taxVO, "CFR");
			retMsg = "세금계산서 확인요청이 완료 되었습니다.";
		}

		if (taxVO.getMeta().getDoc_state().equals("CFS")) {
			mailTitle = "세금계산서 확인전송 입니다.";
			taxVO.getMeta().setDoc_state("SED");
			this.sendToSupplier(taxVO, mailTitle);
			retMsg = "세금계산서 송신대기중 입니다.";
		}

		if (taxVO.getMeta().getDoc_state().equals("CFC")) {
			taxVO.getMeta().setDoc_state("CFC");
			mailTitle = "세금계산서  취소요청  입니다.";
			this.sendToSupplier(taxVO, mailTitle);
			retMsg = "세금계산서 취소요청이 완료 되었습니다.";
		}

		if (taxVO.getMeta().getDoc_state().equals("DEL")) {
			taxVO.getMeta().setDoc_state("DEL");
			mailTitle = "세금계산서가 폐기 되었습니다.";
			this.sendToSupplier(taxVO, mailTitle);
//			 kdn
			sendToKDN(taxVO, "DEL");
			retMsg = "세금계산서가 폐기 요청이 완료 되었습니다.";
		}

		if (taxVO.getMeta().getDoc_state().equals("WM")) {
			taxVO.getMeta().setDoc_state("WM");
			mailTitle = "세금계산서  확인취소 입니다.(세금계산서는 작성중인 상태로 변경되었습니다.)";
			this.sendToSupplier(taxVO, mailTitle);
			retMsg = "세금계산서 취소확인이 완료 되었습니다.";
		}
		return retMsg;
	}
*/
	public String sendPublishByBuyer(TaxInvoiceVO taxVO) throws TaxInvoiceException, SQLException, MSHException {
		String mailTitle = "";
		String retMsg = "";
		if (taxVO.getMeta().getDoc_state().equals("CFR")) {
			taxVO.getMeta().setDoc_state("CFR");
			System.out.println("sendPublishByBuyer::: CFR:::" + taxVO.getMeta().getDoc_state());
			mailTitle = "세금계산서 확인요청 입니다.";
			this.sendToSupplier(taxVO, mailTitle);
//			 kdn
			sendToKDN(taxVO, "CFR");
			retMsg = "세금계산서 확인요청이 완료 되었습니다.";
		}

		if (taxVO.getMeta().getDoc_state().equals("CFS")) {
			taxVO.getMeta().setDoc_state("SED");
			mailTitle = "세금계산서 확인전송 입니다.";
			this.sendToBuyer(taxVO, mailTitle);
			retMsg = "세금계산서 송신대기중 입니다.";
		}

		if (taxVO.getMeta().getDoc_state().equals("CFC")) {
			taxVO.getMeta().setDoc_state("CFC");
			mailTitle = "세금계산서  취소요청  입니다.";
			this.sendToBuyer(taxVO, mailTitle);
			retMsg = "세금계산서 취소요청이 완료 되었습니다.";
		}

		if (taxVO.getMeta().getDoc_state().equals("DEL")) {
			taxVO.getMeta().setDoc_state("DEL");
			mailTitle = "세금계산서가 폐기 되었습니다.";
			this.sendToBuyer(taxVO, mailTitle);
//			 kdn
			sendToKDN(taxVO, "DEL");
			retMsg = "세금계산서가 폐기 요청이 완료 되었습니다.";
		}
		if (taxVO.getMeta().getDoc_state().equals("WM")) {
			taxVO.getMeta().setDoc_state("WM");
			mailTitle = "세금계산서  확인취소 입니다.(세금계산서는 작성중인 상태로 변경되었습니다.)";
			this.sendToBuyer(taxVO, mailTitle);
			retMsg = "세금계산서 취소확인이 완료 되었습니다.";
		}
		return retMsg;
	}

	public void sendToSupplier(TaxInvoiceVO taxVO, String title) throws TaxInvoiceException {
		//2015.12.07 내선계기 메일보낸이를 계약자로 변경 CDH
		//TaxMailVO mailVO = new TaxMailVO(taxVO.getUuid(), "supplier",taxVO.getMain().getDoc_date());
		TaxMailVO mailVO = new TaxMailVO(taxVO.getUuid(), "supplier",taxVO.getMain().getDoc_date(),taxVO.getMeta().getContractor_name(),taxVO.getMeta().getContractor_email(),taxVO.getMeta().getContractor_phone());
		mailVO.setTitile(title);
		mailVO.setToName(taxVO.getMain().getSupplier_contactor_name());
		mailVO.setToMail(taxVO.getMain().getSupplier_contactor_email());
		
//		 for(int i=0; i<taxVO.getLine().size(); i++){
//		        System.out.println("size of line:"+taxVO.getLine().size());
//	             lineVO = (TaxLineVO)taxVO.getLine().get(i);
//	            lineVO.setUuid(taxVO.getUuid());
//	            lineVO.setName(taxVO.getLine().get(1).toString());
//	            System.out.println("linevo.getname"+lineVO.getName());
//	            
//	        }
//2015.12.07 내선계기 메일보낸이를 계약자로 변경 CDH
//		mailVO.setFromMail(taxVO.getMain().getBuyer_contactor_email());
//		mailVO.setFromName(taxVO.getMain().getBuyer_contactor_name());
		mailVO.setFromMail(taxVO.getMeta().getContractor_email());
		mailVO.setFromName(taxVO.getMeta().getContractor_name());
	
		
		
		TaxMailManagementDao mailDao = new TaxMailManagementDao();
		TaxSMSManagementDao smsDao = new TaxSMSManagementDao();
		try {
			TaxPersonNewManagerDao personDao = new TaxPersonNewManagerDao();
//			TaxPersonVO personVO = personDao.selectPersonById(taxVO.getMeta().getReceiver_id(), "N");
			TaxPersonVO personVO = personDao.selectPersonByIdnCompId(taxVO.getMeta().getReceiver_comp_id(), taxVO.getMeta().getReceiver_id(), "N");

//			메일,문자보내기
			mailDao.sendMail(mailVO);
			//smsDao.sendSMS_new(personVO.getHp(),taxVO.getUuid()); 
			
			//20180212 윤규미 sms->lms
			System.out.println("==================LMS Start===========================");
			//20180619 박상종 
			// poller.properies LMS.SEND.LEVEL 값을 참조
			//         Y - EAI_TAX_HEADER_INFO_TB.SMS_YN = Y  만 송부
            //         YorNULL - EAI_TAX_HEADER_INFO_TB.SMS_YN = Y or null 송부
            //         YorNorNULL - EAI_TAX_HEADER_INFO_TB.SMS_YN = Y or N or null 송부
			// KEY_POWEREDI.EAI_TAX_HEADER_INFO_TB.SMS_YN 값이 Y인 경우만 LMS 송신
			String LMS_SEND_LEVEL = CommonUtil.getProperty("LMS.SEND.LEVEL");
			String sms_yn = taxVO.getSms_yn();
			boolean LMS = true;
			if(LMS_SEND_LEVEL.equals("Y")){
               LMS = (sms_yn.equals("Y")) ? true : false;
			}else if(LMS_SEND_LEVEL.equals("YorNULL")){
               LMS = (sms_yn.equals("Y") || sms_yn.equals("") || sms_yn == null) ? true : false;
			}else if(LMS_SEND_LEVEL.equals("YorNorNULL")){
               LMS = (sms_yn.equals("Y") || sms_yn.equals("") || sms_yn == null || sms_yn.equals("N") ) ? true : false;
		    }
			
            if(LMS){
	   			smsDao.sendLMS(personVO.getHp(),
							   taxVO.getUuid(),
							   taxVO.getMain().getDoc_date(),
							   taxVO.getMain().getSupplier_name(),
							   taxVO.getMain().getCharge_amt(),
							   taxVO.getMain().getTot_tax_amt(),
							   taxVO.getMain().getTot_amt(),
							   taxVO.getMain().getSupplier_contactor_email(),
							   mailVO.getLineName(),
							   "F" , taxVO
							   );
			   System.out.println("==================LMS 송신 완료 ");
            }else{
			   System.out.println("==================LMS 송신 제외처리");
            }							   
			System.out.println("==================LMS End===========================");
			
			TaxManagementDao taxDao = new TaxManagementDao();
			taxDao.setStatus(taxVO.getUuid(), taxVO.getMeta().getDoc_state());
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "문서 상태 변경 프로세스 처리중에 에러 발생 하였습니다.");
		}
	}

		//2015.12.07 내선계기 메일보낸이를 계약자로 변경 CDH
	public void sendToBuyer(TaxInvoiceVO taxVO, String title) throws TaxInvoiceException {
//		TaxMailVO mailVO = new TaxMailVO(taxVO.getUuid(), "buyer",taxVO.getMain().getDoc_date());
		TaxMailVO mailVO = new TaxMailVO(taxVO.getUuid(), "buyer",taxVO.getMain().getDoc_date(),"","","");
		TaxMailManagementDao mailDao = new TaxMailManagementDao();
		TaxSMSManagementDao smsDao = new TaxSMSManagementDao();
		String comp_code = taxVO.getMeta().getComp_code();
		System.out.println("sendToBuyer.comp_code ::: "+comp_code);
		try {
			TaxPersonNewManagerDao personDao = new TaxPersonNewManagerDao();
			TaxPersonVO personVO = new TaxPersonVO();

			if (taxVO.getMeta().getDoc_state().equals("SED")) {
				personVO = personDao.selectPersonById(taxVO.getMeta().getContractor_id(), comp_code);
			} else {
				personVO = personDao.selectPersonById(taxVO.getMeta().getReceiver_id(), comp_code);
			}

			mailVO.setTitile(title);
//2015.12.07 내선계기 메일보낸이를 계약자로 변경 CDH			
//			mailVO.setToName(personVO.getName());
//			mailVO.setToMail(personVO.getEmail());
			mailVO.setToName(taxVO.getMeta().getContractor_name());
			mailVO.setToMail(taxVO.getMeta().getContractor_email());

			mailVO.setFromName(taxVO.getMain().getSupplier_contactor_name());
			mailVO.setFromMail(taxVO.getMain().getSupplier_contactor_email());
			mailVO.setDocUuid(taxVO.getUuid());

//			메일보내기
			mailDao.sendMail(mailVO);

			// smsDao.sendSMS(personVO.getHp()); 한전담당자는 문자 안받음

			TaxManagementDao taxDao = new TaxManagementDao();
			taxDao.setStatus(taxVO.getUuid(), taxVO.getMeta().getDoc_state());

		} catch (Exception e) {
			e.printStackTrace();   
			throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "문서 상태 변경 프로세스 처리중에 에러 발생 하였습니다.");
		}
	}  

	public void sendToKDN(TaxInvoiceVO taxVO, String status) throws TaxInvoiceException {

		try {
			if ( Integer.parseInt(taxVO.getMain().getDoc_date()) > 20090131 ) {
	//			KDN 인지 체크
				TaxCompanyNewManagerDao comDao = new TaxCompanyNewManagerDao();
				boolean YN = comDao.getBiznumInfo(taxVO.getMain().getSupplier_biz_id(), "KDN");

				if (YN) {
	//				KDN 연계상태 테이블 인서트
					TaxStatusInfoManagementDao statusDao = new TaxStatusInfoManagementDao();
					statusDao.insertStatusInfo(taxVO.getUuid(),status,"KDN");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "KDN 연계상태정보 입력 처리중에 에러가 발생 하였습니다.");
		}
	}

}
