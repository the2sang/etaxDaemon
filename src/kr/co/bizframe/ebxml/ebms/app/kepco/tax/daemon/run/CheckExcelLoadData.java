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
    
    private boolean isBizId 		= false; //��ȿ�� ����ڹ�ȣ ����
    private boolean isDocDate 	    = false; //��¥
    private boolean isName			= false; //�̸�
    private boolean isAmt 			= false; //���ް���
    private boolean isTerm			= false;//�Ⱓ���� ��꼭�ΰ�
    private boolean isInTime		= false;//2006�� 10�� 01�� ���� ���� ���� ��꼭 �ΰ�. 
    
    private boolean isTotAmt 		= false; //�Ѿ�

    private boolean isValidBizId		 = false; //����ڹ�ȣ�� ������� ���� �������ΰ�
    private boolean isSupplierPerson = false; //��ü ����� ����

    private TaxCompanyVO comp	= new TaxCompanyVO();
    private TaxPersonVO person	= new TaxPersonVO();
    
    private String token = "|$|";
    private String result = "";
    private String resultMsg = "";
    
	//load excel data cheking
	  public void checkTaxInvoice(String biz_id, String doc_date, String name, String amt1, String amt2 , String amt3)  throws SQLException {
	  	checkTaxInvoice(biz_id, doc_date, name, amt1,amt2,amt3, 0);//amt3 �߰�
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
		    		    
        isAmt		= checkAmt(amt1) || checkAmt(amt2) || checkAmt(amt3) ;//amt3 �߰�
        
        isTotAmt = checkTotAmt(amt1, amt2, amt3);//amt3 �߰�
         
		 if(isDocDate) {
			 isTerm = checkDocDateTerm(doc_date);
			 isTerm = true;
			 isInTime = checkIssueDateInTime(doc_date);
		 }
		    
        if(isBizId&&isDocDate&&isName&&isAmt&&isTerm&&isTotAmt&&isInTime&&( flagLength <= 40 )){
            result = "����";
            resultMsg = "���������� �Էµ� �������Դϴ�.";
        }
        
        if(!isBizId){
            result = "����";
            resultMsg += "����ڵ�Ϲ�ȣ ������ �߸��Ǿ����ϴ�. ���� : 10�ڸ� ���ڷ� �Է��ϼ���.)"+token;
        }
        if(!isDocDate){
            result = "����";
            resultMsg += "�����ۼ��������� �߸��Ǿ����ϴ�. ���� : YYYYMMDD�������� �Է��ϼ���."+token;
        } else {
			if (!isInTime) {
				result = "����";
				resultMsg += "�����ۼ����� 2006�� 10�� 01�� ���Ŀ� �ۼ��Ǿ�� �մϴ�." + token;
			}
			if (!isTerm) {
				result = "����";
				resultMsg += "�����ۼ����� 15���̳��� �ۼ��Ǿ�� �մϴ�." + token;
			}
		}
        
        if(!isName){
            result = "����";
            resultMsg += "ǰ����� �߸��Ǿ����ϴ�."+token;
        }
        
        if(!isAmt){
            result = "����";
            resultMsg += "���ް����� �߸��Ǿ����ϴ�. ���� : ���ڷθ� �Է��ϼ���."+token;            
        }
        
        if(!isTotAmt){
            result = "����";
            resultMsg += "�Է��Ͻ� �Ѿ��� ���Ǵ� �ݾ׺��� Ů�ϴ�."+token;            
        }
        
        if ( flagLength > 40 ) {
        	  result = "����";
            resultMsg += "ǰ��� ���� ���̴� 40byte�Դϴ�.(���� "+flagLength+" byte) �ѱ� �ѱ���:2byte ���� ����:1btye �Դϴ�."+token;
        }        
   } 
    
	//TaxInvoiceVO checking(MakeTaxInvoiceByExcel.jsp���� �����)
    public void checkTaxInvoice(TaxInvoiceVO vo) throws SQLException{
        isBizId = checkBizId(vo.getMain().getSupplier_biz_id());
        if(isBizId){
            result = "����";
            resultMsg = "���������� �Էµ� �������Դϴ�.";
        }
        if(!isBizId){
            result = "����";
            resultMsg += "������������ �߸��Ǿ����ϴ�."+token;
        }
    }

	
////////////////////////////////input���ڿ� ��ȿ�� �˻�////////////////////////////////

	//����ڵ�Ϲ�ȣ ��ȿ�� üũ
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

	
	 //2006�� 10�� 1�� ���� ���� ���� ��꼭 ���� üũ ��¥����üũ
    public boolean checkIssueDateInTime(String doc_date){
        boolean flag = true;
       
        if(doc_date.length()==8 && Integer.parseInt(doc_date) >= 20061001){
			flag = true;
        }else{
        	flag = false;
        }
        return flag;
    }
    
    //��¥����üũ
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

	//�Ⱓüũ
	public boolean checkDocDateTerm(String doc_date){
		boolean flag = true;
		String today = CommonUtil.getCurrentTimeFormat("yyyyMMdd");
		flag = CommonUtil.isDateTermCheck(today,doc_date,"-15");                    
		
		return flag;
	}
    
    //ǰ���
    public boolean checkName(String name){
        boolean flag = true;
        
        if("".equals(name)) flag = false;
        else if(name.length()==0) flag = false;
                        
        return flag;
    }
    
    //���޴ܰ�üũ
    public boolean checkAmt(String amt){
        boolean flag = true;
        
        if(amt.length()>12) {
            flag = false; //100���̻�
        } else{
            try{
                long nAmt = Long.parseLong(amt);                
            }catch(NumberFormatException e){
                flag = false;
            }
        }
        return flag;
    }
    
    // 2006.07.27 ������ �ѱݾ� üũ 
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
