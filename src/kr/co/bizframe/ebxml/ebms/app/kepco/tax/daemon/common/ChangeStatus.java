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
	 * ����� ���ݰ�꼭��ȸ
	 */
	

	/*
	 * ���ݰ�꼭 ���� ���°� ���� CFR Ȯ�ο�û CFS Ȯ������ CFC Ȯ����� DEL ��� END ����Ϸ� REJ ��ҿϷ�
	 */
	
	
//20180619 ������� �ʴ� method�� comment ó�� 
/*
	public String sendPublishBySupplier(TaxInvoiceVO taxVO) throws TaxInvoiceException, SQLException, MSHException {
		String mailTitle = "";
		String retMsg = "";
		if (taxVO.getMeta().getDoc_state().equals("CFR")) {
			taxVO.getMeta().setDoc_state("CFR");
			mailTitle = "���ݰ�꼭 Ȯ�ο�û �Դϴ�.";
			this.sendToBuyer(taxVO, mailTitle);
//			 kdn
			sendToKDN(taxVO, "CFR");
			retMsg = "���ݰ�꼭 Ȯ�ο�û�� �Ϸ� �Ǿ����ϴ�.";
		}

		if (taxVO.getMeta().getDoc_state().equals("CFS")) {
			mailTitle = "���ݰ�꼭 Ȯ������ �Դϴ�.";
			taxVO.getMeta().setDoc_state("SED");
			this.sendToSupplier(taxVO, mailTitle);
			retMsg = "���ݰ�꼭 �۽Ŵ���� �Դϴ�.";
		}

		if (taxVO.getMeta().getDoc_state().equals("CFC")) {
			taxVO.getMeta().setDoc_state("CFC");
			mailTitle = "���ݰ�꼭  ��ҿ�û  �Դϴ�.";
			this.sendToSupplier(taxVO, mailTitle);
			retMsg = "���ݰ�꼭 ��ҿ�û�� �Ϸ� �Ǿ����ϴ�.";
		}

		if (taxVO.getMeta().getDoc_state().equals("DEL")) {
			taxVO.getMeta().setDoc_state("DEL");
			mailTitle = "���ݰ�꼭�� ��� �Ǿ����ϴ�.";
			this.sendToSupplier(taxVO, mailTitle);
//			 kdn
			sendToKDN(taxVO, "DEL");
			retMsg = "���ݰ�꼭�� ��� ��û�� �Ϸ� �Ǿ����ϴ�.";
		}

		if (taxVO.getMeta().getDoc_state().equals("WM")) {
			taxVO.getMeta().setDoc_state("WM");
			mailTitle = "���ݰ�꼭  Ȯ����� �Դϴ�.(���ݰ�꼭�� �ۼ����� ���·� ����Ǿ����ϴ�.)";
			this.sendToSupplier(taxVO, mailTitle);
			retMsg = "���ݰ�꼭 ���Ȯ���� �Ϸ� �Ǿ����ϴ�.";
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
			mailTitle = "���ݰ�꼭 Ȯ�ο�û �Դϴ�.";
			this.sendToSupplier(taxVO, mailTitle);
//			 kdn
			sendToKDN(taxVO, "CFR");
			retMsg = "���ݰ�꼭 Ȯ�ο�û�� �Ϸ� �Ǿ����ϴ�.";
		}

		if (taxVO.getMeta().getDoc_state().equals("CFS")) {
			taxVO.getMeta().setDoc_state("SED");
			mailTitle = "���ݰ�꼭 Ȯ������ �Դϴ�.";
			this.sendToBuyer(taxVO, mailTitle);
			retMsg = "���ݰ�꼭 �۽Ŵ���� �Դϴ�.";
		}

		if (taxVO.getMeta().getDoc_state().equals("CFC")) {
			taxVO.getMeta().setDoc_state("CFC");
			mailTitle = "���ݰ�꼭  ��ҿ�û  �Դϴ�.";
			this.sendToBuyer(taxVO, mailTitle);
			retMsg = "���ݰ�꼭 ��ҿ�û�� �Ϸ� �Ǿ����ϴ�.";
		}

		if (taxVO.getMeta().getDoc_state().equals("DEL")) {
			taxVO.getMeta().setDoc_state("DEL");
			mailTitle = "���ݰ�꼭�� ��� �Ǿ����ϴ�.";
			this.sendToBuyer(taxVO, mailTitle);
//			 kdn
			sendToKDN(taxVO, "DEL");
			retMsg = "���ݰ�꼭�� ��� ��û�� �Ϸ� �Ǿ����ϴ�.";
		}
		if (taxVO.getMeta().getDoc_state().equals("WM")) {
			taxVO.getMeta().setDoc_state("WM");
			mailTitle = "���ݰ�꼭  Ȯ����� �Դϴ�.(���ݰ�꼭�� �ۼ����� ���·� ����Ǿ����ϴ�.)";
			this.sendToBuyer(taxVO, mailTitle);
			retMsg = "���ݰ�꼭 ���Ȯ���� �Ϸ� �Ǿ����ϴ�.";
		}
		return retMsg;
	}

	public void sendToSupplier(TaxInvoiceVO taxVO, String title) throws TaxInvoiceException {
		//2015.12.07 ������� ���Ϻ����̸� ����ڷ� ���� CDH
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
//2015.12.07 ������� ���Ϻ����̸� ����ڷ� ���� CDH
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

//			����,���ں�����
			mailDao.sendMail(mailVO);
			//smsDao.sendSMS_new(personVO.getHp(),taxVO.getUuid()); 
			
			//20180212 ���Թ� sms->lms
			System.out.println("==================LMS Start===========================");
			//20180619 �ڻ��� 
			// poller.properies LMS.SEND.LEVEL ���� ����
			//         Y - EAI_TAX_HEADER_INFO_TB.SMS_YN = Y  �� �ۺ�
            //         YorNULL - EAI_TAX_HEADER_INFO_TB.SMS_YN = Y or null �ۺ�
            //         YorNorNULL - EAI_TAX_HEADER_INFO_TB.SMS_YN = Y or N or null �ۺ�
			// KEY_POWEREDI.EAI_TAX_HEADER_INFO_TB.SMS_YN ���� Y�� ��츸 LMS �۽�
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
			   System.out.println("==================LMS �۽� �Ϸ� ");
            }else{
			   System.out.println("==================LMS �۽� ����ó��");
            }							   
			System.out.println("==================LMS End===========================");
			
			TaxManagementDao taxDao = new TaxManagementDao();
			taxDao.setStatus(taxVO.getUuid(), taxVO.getMeta().getDoc_state());
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "���� ���� ���� ���μ��� ó���߿� ���� �߻� �Ͽ����ϴ�.");
		}
	}

		//2015.12.07 ������� ���Ϻ����̸� ����ڷ� ���� CDH
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
//2015.12.07 ������� ���Ϻ����̸� ����ڷ� ���� CDH			
//			mailVO.setToName(personVO.getName());
//			mailVO.setToMail(personVO.getEmail());
			mailVO.setToName(taxVO.getMeta().getContractor_name());
			mailVO.setToMail(taxVO.getMeta().getContractor_email());

			mailVO.setFromName(taxVO.getMain().getSupplier_contactor_name());
			mailVO.setFromMail(taxVO.getMain().getSupplier_contactor_email());
			mailVO.setDocUuid(taxVO.getUuid());

//			���Ϻ�����
			mailDao.sendMail(mailVO);

			// smsDao.sendSMS(personVO.getHp()); ��������ڴ� ���� �ȹ���

			TaxManagementDao taxDao = new TaxManagementDao();
			taxDao.setStatus(taxVO.getUuid(), taxVO.getMeta().getDoc_state());

		} catch (Exception e) {
			e.printStackTrace();   
			throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "���� ���� ���� ���μ��� ó���߿� ���� �߻� �Ͽ����ϴ�.");
		}
	}  

	public void sendToKDN(TaxInvoiceVO taxVO, String status) throws TaxInvoiceException {

		try {
			if ( Integer.parseInt(taxVO.getMain().getDoc_date()) > 20090131 ) {
	//			KDN ���� üũ
				TaxCompanyNewManagerDao comDao = new TaxCompanyNewManagerDao();
				boolean YN = comDao.getBiznumInfo(taxVO.getMain().getSupplier_biz_id(), "KDN");

				if (YN) {
	//				KDN ������� ���̺� �μ�Ʈ
					TaxStatusInfoManagementDao statusDao = new TaxStatusInfoManagementDao();
					statusDao.insertStatusInfo(taxVO.getUuid(),status,"KDN");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "KDN ����������� �Է� ó���߿� ������ �߻� �Ͽ����ϴ�.");
		}
	}

}
