package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.run;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.PropertyUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.ServerProperties;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxFinanceContectVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxHeaderVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;

import org.apache.log4j.Logger;

public class PollerAdapter extends Thread {

	private static Logger logger = Logger.getLogger(PollerAdapter.class);

	private final String term = "-15";

	public static String prop_path  = "";

	/* add 2009.08.06 */
	private final String sms_Sand	= "-7";//
	
	private final String tax_bill_sms_day = "-3"; // ���⼼�ݰ�꼭 

    public ServerProperties props = ServerProperties.getInstance();

    public PollerAdapter(String path) {
        this.prop_path = path ;
    }

    public PollerAdapter() {

    }

    public void loadProperty() {
        try {
         //   props.load(new FileInputStream(prop_path+"poller.properties"));
            PropertyUtil.loadProperty(prop_path);
//            System.out.println(props.getProperty("CRON.JDBC.USERNAME"));

        }catch(Exception e) {
            e.printStackTrace();
        }
    }


	public void run() {

		try {

			String lastday = "";
			boolean updateOK = false;

			/* add 2009.08.06 */
			String lastday_Sms = "";
			boolean sms_SandOK = false;
			
			/* ���� sms , mail ���� */
			String lastday_Sms_tax_bill = "";
			boolean tax_bill_sms_sandOK = false; // �������

			while(true) {

				logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		        try {
		          	//// �������ý��� ���� ������ ���ݰ�꼭 ����
		            TaxManagementDao dao = new TaxManagementDao();
		            ArrayList data = dao.getReceiveHeaderList(); // Header ���� ������ WRM (��û)�ΰ�

					for (int idx = 0; idx < data.size(); idx++) { // �ش� ������ ��ŭ ����

						TaxHeaderVO headerVO = new TaxHeaderVO();
			            headerVO = (TaxHeaderVO)data.get(idx); // ������� �ϳ��� ������
			            
			            System.out.println(" �ý���ID = "+headerVO.getRel_system_id());
						//logger.debug(idx+" New bizno="+headerVO.getBiz_no()); // �ش������� ��ü ����ڹ�ȣ�� ������

						try {
							String errMsg = "";
							//logger.debug("::::::::::::::::::>>>>>>>>>>>>"+headerVO.getDoc_type_detail());
							ArrayList detailArray = dao.getReceiveDetailList(headerVO); // ��ü ����ڹ�ȣ�� ������ ������
							
							Hashtable finalData	= new Hashtable();
							MakeTaxInvoice makeTax = new MakeTaxInvoice();
							
							finalData = makeTax.makeTaxinvoice(headerVO, detailArray); // ��� ������ �� �ش��ü �������� �ѱ� ���ݰ�꼭 VO ����

							if (finalData.get("result").equals("ERR")) { errMsg = (String)finalData.get("resultMsg"); }
							else {
								PublishTaxinvoice publishTax = new PublishTaxinvoice();
								
								TaxInvoiceVO vo1 = (TaxInvoiceVO)finalData.get("taxVO");
								errMsg = publishTax.publishData(finalData); // ���� �ƴ� ����ó�� �Ǹ� �ۼ�
							}

							dao.setStatusInfo(headerVO,((TaxInvoiceVO)finalData.get("taxVO")).getUuid(),errMsg);//���� ���̺� �ش��  ������Ʈ
 
						} catch (Exception e) {
							logger.debug("���ݰ�꼭 ���� �� ���� �߻� :"+headerVO.getBiz_no()+":::"+headerVO.getCons_no()+":::"+headerVO.getReq_no());
							e.printStackTrace();
							//stop();
							Shutdown sdc = new Shutdown(prop_path);
					        sdc.start();
				        }

						Thread.sleep(1000 * 2);
					}
					/*
					////////////////////////////////////////////////////////////////////////////////////////
					System.out.println("// �����������ݰ�꼭 ���� üũ //");
					////////////////////////////////////////////////////////////////////////////////////////
					ArrayList ncis_data = dao.getNcisReceiveHeaderList();
					try{
						if(ncis_data.size() != 0 ){
							for(int ncis_idx=0; ncis_idx < ncis_data.size(); ncis_idx++){
								TaxHeaderVO headerVO = new TaxHeaderVO();
					            headerVO = (TaxHeaderVO)data.get(ncis_idx);
					            ////////////////////////////////////////////////////////////////////////////////////////
					            System.out.println(" ");
					            ////////////////////////////////////////////////////////////////////////////////////////
					            ArrayList ncis_detailArray = dao.getNcisReceiveDetailList(headerVO); // ��ü ����ڹ�ȣ�� ������ ������
					            
					            Hashtable finalData	= new Hashtable();
								MakeTaxInvoice makeTax = new MakeTaxInvoice();
								finalData = makeTax.makeNcisTaxinvoice(headerVO, ncis_detailArray); // ��� ������ �� �ش��ü �������� �ѱ� ���ݰ�꼭 VO ����

							}
						}
					}catch(Exception e1){
						e1.printStackTrace();
						Shutdown sdc = new Shutdown(prop_path);
				        sdc.start();
					}
					////////////////////////////////////////////////////////////////////////////////////////
					*/
					//// ERP�ý��� ������� ���� ���� ����ó��
		            ArrayList contractorData = dao.getReceiveContractorList();

		            for (int idx2 = 0; idx2 < contractorData.size(); idx2++) {

		            	TaxFinanceContectVO financeVO = new TaxFinanceContectVO();
		            	financeVO = (TaxFinanceContectVO)contractorData.get(idx2);
		            	logger.debug(idx2+" New contractor="+financeVO.getId()+" :: New Uuid="+financeVO.getUuid());

						try {

							dao.updateContractorInfo(financeVO);

						} catch (Exception e) {
							logger.debug("������� ����ó�� �� ���� �߻� : Uuid="+financeVO.getUuid()+":::contractor="+financeVO.getId());
							e.printStackTrace();
//							Shutdown sdc = new Shutdown(prop_path);
//					        sdc.start();
				        }

						Thread.sleep(1000 * 1);

		            }

					//// 15�� ���� ������ ���ݰ�꼭 ���ó��

					String today = CommonUtil.getCurrentTimeFormat("yyyyMMdd"); //System ���糯��
					String currentTime = CommonUtil.getCurrentTimeFormat("kkmm"); // System �ú�

					if (!lastday.equals(today) && updateOK) updateOK = false;

					if (!lastday.equals(today)
							&& !updateOK
							&& ( Integer.parseInt(currentTime) > 2401
									&& Integer.parseInt(currentTime) < 2430 )  ) {

						logger.debug("today="+today);
						logger.debug("currentTime="+currentTime);

	//					boolean isTerm = CommonUtil.isDateTermCheck(today,CommonUtil.nullToBlank(vo.getMain().getDoc_date()),term);
						String basicDate  = CommonUtil.getPastDay("yyyyMMdd", term);
						logger.debug("basicDate="+basicDate);
						dao.updateStatusByBasicDate(basicDate);

						lastday = today;
						updateOK = true;
					}
					// ���糯�� 7���� ���ڰ� �ۼ����ڰ� ������ = ����� 7���� ������ ���ݰ�꼭 SMS �߼�
					// �Ϸ翡 �ѹ� �˻� �ϰ� ���� �ð� ���� 10:15 ~ 10:45
					/* sms_sand 2009.08.06 */
					if (!lastday_Sms.equals(today) && sms_SandOK) sms_SandOK = false;
					
					
					
					if (
							!lastday_Sms.equals(today)
							&& !sms_SandOK
							&& ( Integer.parseInt(currentTime) > 1015
							&& Integer.parseInt(currentTime) < 1045 )){

						logger.debug("today_sms : "+today);
						logger.debug("currentTime_sms : "+currentTime);

						String smsDate = CommonUtil.getPastDay("yyyyMMdd", sms_Sand); // -7�������ڸ� ���Ѵ�.
						logger.debug("smsDate : "+smsDate);

						//SMS �߼��Ѵ�.
						dao.sandBySmsDate(smsDate);
						logger.debug(" SMS �߼�_END ");

						lastday_Sms = today;
						sms_SandOK = true;
					}
					
					/* �������� sms , mail */
					/* 2012�� 1�� 1�� ���ķ� batch2���� sms�� mail�� ������ ������ �ּ�ó���� 2013.03.18 ����ȣ.
					if(!lastday_Sms_tax_bill.equals(today) && tax_bill_sms_sandOK) tax_bill_sms_sandOK = false;
					if(!lastday_Sms_tax_bill.equals(today)
							&& !tax_bill_sms_sandOK
							&& ( Integer.parseInt(currentTime) > 1045
							&& Integer.parseInt(currentTime) < 1115 )
						){
						
						String tax_Date = CommonUtil.getPastDay("yyyyMMdd", tax_bill_sms_day); // -3�������ڸ� ���Ѵ�.
						System.out.println("�������� SMS,MAIL ��������: "+tax_Date);
						// sms,mail �߼�
						dao.tax_sms_mail(tax_Date);
						
						lastday_Sms_tax_bill = today;
						tax_bill_sms_sandOK = true;	
						System.out.println();
					}
					*/
					
					/*
					 * �������� -> FI ���� 
					 * */
					logger.debug(" FI transmet Start ==>>> ");
					System.out.println("//////////////// f /////////////////��");
					dao.transmitList_F();

					System.out.println("//////////////// y /////////////////");
					dao.transmitList_Y();
					logger.debug(" FI transmet End <<<== ");
					

		        } catch (Exception e) {
		            e.printStackTrace();
		            logger.debug("Error.. thread stop " + e);
//		            stop();
		            Shutdown sdc = new Shutdown(prop_path);
			        sdc.start();
		        }

		        Thread.sleep(1000 * Integer.parseInt(props.getProperty("demon.poll.interver")));
			}

		} catch(InterruptedException ie) {
			ie.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			try {
				logger.debug("Exception.. thread is stop !!!");
//				stop();
				Shutdown sdc = new Shutdown(prop_path);
		        sdc.start();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
	}

//	public static void main(String[] args)
//	{
//
//        prop_path  = args[0];
//
//        PollerAdapter poller = new PollerAdapter(prop_path);
////        logger.debug("poller.getStatus()2:"+poller.getStatus());
//
////        this._thread = new Thread();
//        poller.loadProperty();
//
////        logger.debug("poller.getStatus()3:"+poller.getStatus());
////        logger.debug("poller.tax_ref_cnt:"+poller.tax_ref_cnt);
//        if (poller.tax_ref_cnt<1) {
//        	poller.setStatus(true);
//        	poller.start();
//		}
//
//	}

}
