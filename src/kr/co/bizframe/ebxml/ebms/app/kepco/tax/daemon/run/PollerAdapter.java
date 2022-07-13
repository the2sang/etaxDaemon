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
	
	private final String tax_bill_sms_day = "-3"; // 매출세금계산서 

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
			
			/* 매출 sms , mail 전송 */
			String lastday_Sms_tax_bill = "";
			boolean tax_bill_sms_sandOK = false; // 매출관련

			while(true) {

				logger.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		        try {
		          	//// 내선계기시스템 연계 역발행 세금계산서 발행
		            TaxManagementDao dao = new TaxManagementDao();
		            ArrayList data = dao.getReceiveHeaderList(); // Header 정보 가져옴 WRM (요청)인것

					for (int idx = 0; idx < data.size(); idx++) { // 해당 가져온 만큼 돌림

						TaxHeaderVO headerVO = new TaxHeaderVO();
			            headerVO = (TaxHeaderVO)data.get(idx); // 헤더정보 하나씩 가져옴
			            
			            System.out.println(" 시스템ID = "+headerVO.getRel_system_id());
						//logger.debug(idx+" New bizno="+headerVO.getBiz_no()); // 해더정보에 업체 사업자번호를 가져옴

						try {
							String errMsg = "";
							//logger.debug("::::::::::::::::::>>>>>>>>>>>>"+headerVO.getDoc_type_detail());
							ArrayList detailArray = dao.getReceiveDetailList(headerVO); // 업체 사업자번호로 상세정보 가져옴
							
							Hashtable finalData	= new Hashtable();
							MakeTaxInvoice makeTax = new MakeTaxInvoice();
							
							finalData = makeTax.makeTaxinvoice(headerVO, detailArray); // 헤더 정보와 그 해당없체 상세정보를 넘김 세금계산서 VO 생성

							if (finalData.get("result").equals("ERR")) { errMsg = (String)finalData.get("resultMsg"); }
							else {
								PublishTaxinvoice publishTax = new PublishTaxinvoice();
								
								TaxInvoiceVO vo1 = (TaxInvoiceVO)finalData.get("taxVO");
								errMsg = publishTax.publishData(finalData); // 에러 아닌 정상처리 되면 작성
							}

							dao.setStatusInfo(headerVO,((TaxInvoiceVO)finalData.get("taxVO")).getUuid(),errMsg);//연계 테이블 해당건  업데이트
 
						} catch (Exception e) {
							logger.debug("세금계산서 생성 중 오류 발생 :"+headerVO.getBiz_no()+":::"+headerVO.getCons_no()+":::"+headerVO.getReq_no());
							e.printStackTrace();
							//stop();
							Shutdown sdc = new Shutdown(prop_path);
					        sdc.start();
				        }

						Thread.sleep(1000 * 2);
					}
					/*
					////////////////////////////////////////////////////////////////////////////////////////
					System.out.println("// 영업정보세금계산서 발행 체크 //");
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
					            ArrayList ncis_detailArray = dao.getNcisReceiveDetailList(headerVO); // 업체 사업자번호로 상세정보 가져옴
					            
					            Hashtable finalData	= new Hashtable();
								MakeTaxInvoice makeTax = new MakeTaxInvoice();
								finalData = makeTax.makeNcisTaxinvoice(headerVO, ncis_detailArray); // 헤더 정보와 그 해당없체 상세정보를 넘김 세금계산서 VO 생성

							}
						}
					}catch(Exception e1){
						e1.printStackTrace();
						Shutdown sdc = new Shutdown(prop_path);
				        sdc.start();
					}
					////////////////////////////////////////////////////////////////////////////////////////
					*/
					//// ERP시스템 계약담당자 변경 정보 연계처리
		            ArrayList contractorData = dao.getReceiveContractorList();

		            for (int idx2 = 0; idx2 < contractorData.size(); idx2++) {

		            	TaxFinanceContectVO financeVO = new TaxFinanceContectVO();
		            	financeVO = (TaxFinanceContectVO)contractorData.get(idx2);
		            	logger.debug(idx2+" New contractor="+financeVO.getId()+" :: New Uuid="+financeVO.getUuid());

						try {

							dao.updateContractorInfo(financeVO);

						} catch (Exception e) {
							logger.debug("계약담당자 변경처리 중 오류 발생 : Uuid="+financeVO.getUuid()+":::contractor="+financeVO.getId());
							e.printStackTrace();
//							Shutdown sdc = new Shutdown(prop_path);
//					        sdc.start();
				        }

						Thread.sleep(1000 * 1);

		            }

					//// 15일 지난 역발행 세금계산서 폐기처리

					String today = CommonUtil.getCurrentTimeFormat("yyyyMMdd"); //System 현재날자
					String currentTime = CommonUtil.getCurrentTimeFormat("kkmm"); // System 시분

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
					// 현재날에 7일전 날자가 작성일자과 같은것 = 폐기전 7일전 역발행 세금계산서 SMS 발송
					// 하루에 한번 검색 일괄 전송 시간 오전 10:15 ~ 10:45
					/* sms_sand 2009.08.06 */
					if (!lastday_Sms.equals(today) && sms_SandOK) sms_SandOK = false;
					
					
					
					if (
							!lastday_Sms.equals(today)
							&& !sms_SandOK
							&& ( Integer.parseInt(currentTime) > 1015
							&& Integer.parseInt(currentTime) < 1045 )){

						logger.debug("today_sms : "+today);
						logger.debug("currentTime_sms : "+currentTime);

						String smsDate = CommonUtil.getPastDay("yyyyMMdd", sms_Sand); // -7일전날자를 구한다.
						logger.debug("smsDate : "+smsDate);

						//SMS 발송한다.
						dao.sandBySmsDate(smsDate);
						logger.debug(" SMS 발송_END ");

						lastday_Sms = today;
						sms_SandOK = true;
					}
					
					/* 한전매출 sms , mail */
					/* 2012년 1월 1일 이후로 batch2에서 sms와 mail을 보내기 때문에 주석처리함 2013.03.18 장지호.
					if(!lastday_Sms_tax_bill.equals(today) && tax_bill_sms_sandOK) tax_bill_sms_sandOK = false;
					if(!lastday_Sms_tax_bill.equals(today)
							&& !tax_bill_sms_sandOK
							&& ( Integer.parseInt(currentTime) > 1045
							&& Integer.parseInt(currentTime) < 1115 )
						){
						
						String tax_Date = CommonUtil.getPastDay("yyyyMMdd", tax_bill_sms_day); // -3일전날자를 구한다.
						System.out.println("한전매출 SMS,MAIL 발행일자: "+tax_Date);
						// sms,mail 발송
						dao.tax_sms_mail(tax_Date);
						
						lastday_Sms_tax_bill = today;
						tax_bill_sms_sandOK = true;	
						System.out.println();
					}
					*/
					
					/*
					 * 상태정보 -> FI 전송 
					 * */
					logger.debug(" FI transmet Start ==>>> ");
					System.out.println("//////////////// f /////////////////☆");
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
