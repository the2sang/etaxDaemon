/*
 * Created on 2005. 11. 22.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.run;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;    

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxCompanyVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxPersonVO;

import java.io.UnsupportedEncodingException;


/**
 * @author pka
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckExcelLoadData {
    
    private boolean isBizId 		= false; //유효한 사업자번호 형식
    private boolean isDocDate 	    = false; //날짜
    private boolean isName			= false; //이름
    private boolean isAmt 			= false; //공급가액
    private boolean isTerm			= false;//기간내의 계산서인가
    private boolean isInTime		= false;//2006년 10월 01일 이후 발행 세금 계산서 인가. 
    
    private boolean isTotAmt 		= false; //총액

    private boolean isValidBizId		 = false; //사업자번호가 있을경우 정상 데이터인가
    private boolean isSupplierPerson = false; //업체 담당자 여부

    private TaxCompanyVO comp	= new TaxCompanyVO();
    private TaxPersonVO person	= new TaxPersonVO();
    
    private String token = "|$|";
    private String result = "";
    private String resultMsg = "";
    
	//load excel data cheking
	  public void checkTaxInvoice(String biz_id, String doc_date, String name, String amt1, String amt2 , String amt3)  throws SQLException {
	  	checkTaxInvoice(biz_id, doc_date, name, amt1,amt2,amt3, 0);//amt3 추가
	  }
    public void checkTaxInvoice(
            String biz_id, 
            String doc_date, 
            String name, 
            String amt1,
            String amt2,
            String amt3,
            int flagLength
            ) throws SQLException
    {
        biz_id		= CommonUtil.nullToBlank(biz_id);
        doc_date  = CommonUtil.nullToBlank(doc_date);
        name		  = CommonUtil.nullToBlank(name); 
        amt1	    = CommonUtil.nullToBlank(amt1);
        amt2      = CommonUtil.nullToBlank(amt2);
        amt3	  = CommonUtil.nullToBlank(amt3);
        
        isBizId		= checkBizId(biz_id);
        isDocDate	= checkDocDate(doc_date);
		    isName		= checkName(name);
		    		    
        isAmt		= checkAmt(amt1) || checkAmt(amt2) || checkAmt(amt3) ;//amt3 추가
        
        isTotAmt = checkTotAmt(amt1, amt2, amt3);//amt3 추가
         
		 if(isDocDate) {
			 isTerm = checkDocDateTerm(doc_date);
			 isTerm = true;
			 isInTime = checkIssueDateInTime(doc_date);
		 }
		    
        if(isBizId&&isDocDate&&isName&&isAmt&&isTerm&&isTotAmt&&isInTime&&( flagLength <= 40 )){
            result = "정상";
            resultMsg = "정상적으로 입력된 데이터입니다.";
        }
        
        if(!isBizId){
            result = "오류";
            resultMsg += "사업자등록번호 형식이 잘못되었습니다. 형식 : 10자리 숫자로 입력하세요.)"+token;
        }
        if(!isDocDate){
            result = "오류";
            resultMsg += "문서작성일형식이 잘못되었습니다. 형식 : YYYYMMDD형식으로 입력하세요."+token;
        } else {
			if (!isInTime) {
				result = "오류";
				resultMsg += "문서작성일은 2006년 10월 01일 이후에 작성되어야 합니다." + token;
			}
			if (!isTerm) {
				result = "오류";
				resultMsg += "문서작성일은 15일이내로 작성되어야 합니다." + token;
			}
		}
        
        if(!isName){
            result = "오류";
            resultMsg += "품목명이 잘못되었습니다."+token;
        }
        
        if(!isAmt){
            result = "오류";
            resultMsg += "공급가액이 잘못되었습니다. 형식 : 숫자로만 입력하세요."+token;            
        }
        
        if(!isTotAmt){
            result = "오류";
            resultMsg += "입력하신 총액을 허용되는 금액보다 큽니다."+token;            
        }
        
        if ( flagLength > 40 ) {
        	  result = "오류";
            resultMsg += "품목명 문자 길이는 40byte입니다.(현재 "+flagLength+" byte) 한글 한글자:2byte 영문 숫자:1btye 입니다."+token;
        }        
   } 
    
	//TaxInvoiceVO checking(MakeTaxInvoiceByExcel.jsp에서 사용중)
    public void checkTaxInvoice(TaxInvoiceVO vo) throws SQLException{
        isBizId = checkBizId(vo.getMain().getSupplier_biz_id());
        if(isBizId){
            result = "정상";
            resultMsg = "정상적으로 입력된 데이터입니다.";
        }
        if(!isBizId){
            result = "오류";
            resultMsg += "공급자정보가 잘못되었습니다."+token;
        }
    }

	
////////////////////////////////input문자열 유효성 검사////////////////////////////////

	//사업자등록번호 유효성 체크
	public boolean checkBizId(String biz_id){
        boolean flag = true;
        
        if("".equals(biz_id)) flag = false;
        else if(biz_id.length()!=10) flag = false;

		if(flag){
			try{
                long tmp = Long.parseLong(biz_id);                
            }catch(NumberFormatException e){
                flag = false;
            }
		}
        return flag;
	}

	
	 //2006년 10월 1일 이전 발행 세금 계산서 인지 체크 날짜형식체크
    public boolean checkIssueDateInTime(String doc_date){
        boolean flag = true;
       
        if(doc_date.length()==8 && Integer.parseInt(doc_date) >= 20061001){
			flag = true;
        }else{
        	flag = false;
        }
        return flag;
    }
    
    //날짜형식체크
    public boolean checkDocDate(String doc_date){
        boolean flag = true;
       
        if(doc_date.length()!=8)
			flag = false;
        else{
            try{                
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                Date date = formatter.parse(doc_date);
                String tmp = formatter.format(date);
                
                if(!doc_date.equals(tmp)){
                    flag = false;
                }else{
                    flag = true;
                }	            
            }catch(NumberFormatException e){
                flag = false;
            }catch(Exception e){
                flag = false;
            }
        }
        return flag;
    }

	//기간체크
	public boolean checkDocDateTerm(String doc_date){
		boolean flag = true;
		String today = CommonUtil.getCurrentTimeFormat("yyyyMMdd");
		flag = CommonUtil.isDateTermCheck(today,doc_date,"-15");                    
		
		return flag;
	}
    
    //품목명
    public boolean checkName(String name){
        boolean flag = true;
        
        if("".equals(name)) flag = false;
        else if(name.length()==0) flag = false;
                        
        return flag;
    }
    
    //공급단가체크
    public boolean checkAmt(String amt){
        boolean flag = true;
        
        if(amt.length()>12) {
            flag = false; //100억이상
        } else{
            try{
                long nAmt = Long.parseLong(amt);                
            }catch(NumberFormatException e){
                flag = false;
            }
        }
        return flag;
    }
    
    // 2006.07.27 이제중 총금액 체크 
    public boolean checkTotAmt(String amt1, String amt2,String amt3){
        boolean flag = true;
        try {
            long double1 = Long.parseLong(amt1) + Long.parseLong(amt2) + Long.parseLong(amt3);
                    
            if( double1 > 99999999999L ) {
                flag = false;
            }
        } catch (NumberFormatException ne) {
        	  flag = false;
        }
        return flag;
    }


///////////////////////////////////getter/setter//////////////////////////////////
    public TaxCompanyVO getComp() {
        return comp;
    }
    public TaxPersonVO getPerson() {
        return person;
    } 
    public boolean isAmt() {
        return isAmt;
    }
    public boolean isBizId() {
        return isBizId;
    }
    public boolean isValidBizId() {
        return isValidBizId;
    }
    public boolean isDocDate() {
        return isDocDate;
    }
    public boolean isName() {
        return isName;
    }
    public boolean isSupplierPerson() {
        return isSupplierPerson;
    }
    public String getResult() {
        return result;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public boolean isTerm() {
        return isTerm;
    }    
}
