package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import org.apache.commons.lang.builder.ToStringBuilder;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxManagementDao;

public class CommonUtil {
	
	public static String SMS_SENDER_NAME = "관리자";
	public static String SMS_SENDER_TEL1 = "0619317583";
	public static String SMS_SENDER_TEL2 = "0613451249";
	
	public static void viewObj(Object obj) {
		System.out.println(ToStringBuilder.reflectionToString(obj));
	}

	public static String getConversationId() {
		UUIDFactory uuidF = UUIDFactory.getInstance();
		UUID uuid = uuidF.newUUID();
		return uuid.toString();
	}

	public static String getMoney(int i, String value) {
		String seqValue = "";

		if (value != null && !value.equals("") && i <= value.length()) {
			seqValue = value.substring(value.length() - i, value.length() - i + 1);
		}
		return seqValue;
	}

	public static String getCompCodeToString(String value) {
		String ret = "";
		if (value == null || value.equals(""))
			ret = "한전";
		else if (value.equals("00"))
			ret = "한전";
		else if (value.equals("01"))
			ret = "남동";
		else if (value.equals("99"))
			ret = "업체";
		else 
			ret = "업체";
		return ret;
	}

	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 kk:mm:ss SS");
		Date date = new Date(System.currentTimeMillis());
		String m_date = "";
		try {
			m_date = sdf.format(date);
		} catch (Exception e) {
			System.out.println("error in dateToString:" + e);
		}
		return m_date;
	}

	//////////////////////////////////////////////////////////////  
	//지정된 형식으로 날짜 가져오기
	public static String getCurrentTimeFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(System.currentTimeMillis());
		String m_date = "";
		try {
			m_date = sdf.format(date);
		} catch (Exception e) {
			System.out.println("error in dateToString:" + e);
		}
		return m_date;
	}

	//지난날짜구하기
	public static String getPastDay(String format, String term) {
		String gDate = "";
		Calendar cal = Calendar.getInstance(Locale.KOREAN);
		cal.add(Calendar.DATE, Integer.parseInt(term));
		Date currTime = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREAN);
		gDate = formatter.format(currTime);

		return gDate;
	}

	//날짜범위가 맞는지 체크(발송 및 승인시)
	public static boolean isDateTermCheck(String today, String docDate, String fixed) {
		boolean isok = false;
		long diff = 0;
		long nFixed = Long.parseLong(fixed);
		nFixed = 24 * 60 * nFixed; //24시간*60분*15일
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date beginDate = formatter.parse(today);
			Date endDate = formatter.parse(docDate);
			diff = (endDate.getTime() - beginDate.getTime()) / (60 * 1000);
			System.out.println(diff + ":" + nFixed);
			if (diff < nFixed)
				isok = false;
			else
				isok = true;

		} catch (Exception e) {
			isok = false;
		}

		return isok;
	}

	//////////////////////////////////////////////////////////////

	public static String getSystemId() throws TaxInvoiceException {
		//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
		//        Date date = new Date(System.currentTimeMillis());
		//        String m_date = "";
		TaxManagementDao dao = new TaxManagementDao();
		//        m_date = sdf.format(date);
		//        m_date = m_date + dao.getSeqId();
		return dao.getSeqId();
	}

	public static String getCurrentTimeWithSecond() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
		Date date = new Date(System.currentTimeMillis());
		String m_date = "";
		try {
			m_date = sdf.format(date);
		} catch (Exception e) {
			System.out.println("error in dateToString:" + e);
		}
		return m_date;
	}

	public static String getProperty(String key) {
		String value = "";
		System.out.println(key);
		try {
			if (key != null){
				if(ServerProperties.getInstance().getProperty(key) == null){
					value = "";
				}else{
					value = new String(ServerProperties.getInstance().getProperty(key).getBytes("8859_1"), "KSC5601");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	
	
	public static String getDotFormatDate(String date) {
		String value = "";
		System.out.println(date);
		if (date.length() >= 8) {
			value = date.substring(0, 4);
			value = value + "." + date.substring(4, 6);
			value = value + "." + date.substring(6, 8);
		}
		return value;
	}

	public static String getKorFormatDate(String date) {
		String value = "";
		if (date.length() >= 8) {
			value = date.substring(0, 4);
			value = value + "년 " + date.substring(4, 6);
			value = value + "월 " + date.substring(6, 8) + "일";
		}
		return value;
	}

	public static String getDate(String value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return value.substring(0, 10);
		}
	}

	public static String getTime(String value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return value.substring(11, value.length());
		}
	}

	public static Object nullToZero(Object obj) {
		if (obj == null)
			return "0";
		else
			return obj;
	}

	public static String setBoldS(Object obj) {
		if (obj == null)
			return "";
		else
			return "<b>";
	}

	public static String setBoldE(Object obj) {
		if (obj == null)
			return "";
		else
			return "</b>";
	}

	public static String getCastorUpNumber(Double ddd) {
		try {
			if (ddd != null) {
				DecimalFormat form = new DecimalFormat("#.#");
				String up = form.format((double) ddd.doubleValue() * 10).toString();
				return up;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	public static String getStringIndex(Double ddd) {
		try {
			if (ddd != null) {
				DecimalFormat form = new DecimalFormat("#.#");
				String up = form.format((double) ddd.doubleValue() * 10).toString();
				return up;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	public static String setTextOnSigature(String value) {
		try {
			if (value.length() > 50) {
				String temp = value.substring(0, 90);
				value = temp.substring(0, 15) + "<br>" + temp.substring(16, 30) + "<br>" + temp.substring(31, 45) + "<br>" + temp.substring(45, 60) + "<br>" + temp.substring(61, 75) + "<br>"
						+ temp.substring(75, 90);
			} else {
				value = "";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return value;
	}

	public static String getCastorNumber(Double ddd) {
		try {
			if (ddd != null) {
				DecimalFormat form = new DecimalFormat("#.#");
				String up = form.format(ddd.doubleValue()).toString();
				return up;
			} else
				;
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	public static Double getHiberDownNumber(String sss) {
		try {
			if (sss != null && !sss.equals("")) {
				DecimalFormat form = new DecimalFormat("#.#");
				double d = Double.parseDouble(sss);
				String down = form.format((double) d / 10).toString();
				return new Double(down);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static Double getHiberNumber(String sss) {
		try {
			if (sss != null && !sss.equals("")) {
				DecimalFormat form = new DecimalFormat("#.#");
				double d = Double.parseDouble(sss);
				String down = form.format(d).toString();
				return new Double(down);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static String nullToZero(String val) {
		if (val == null) {
			return "0";
		} else if (val.equals("")) {
			return "0";
		} else {
			return val;
		}
	}

	public static String nullToBar(String val) {
		if (val == null) {
			return "-";
		} else {
			return val;
		}
	}

	public static String nullToBlank(String value) {
		return value == null ? "" : value;
	}

	public static String justNullToBlank(String value) {
		return value == null ? "" : value;
	}

	public static String encodeKor(String value) {
		try {
			value = value == null ? "" : new String(value.getBytes("8859_1"), "euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public static String zeroToBlank(String value) {
		if (value == null) {
			return "";
		} else if (value.equals("0")) {
			return "";
		} else {
			return value;
		}
	}

	public static String getIndexChar(String value, int i) {
		String data = "";
		try {
			data = String.valueOf(value.charAt(value.length() - i));
		} catch (Exception e) {
			return "";
		}
		return data;

	}

	public static String getStrDateGet(String value, String type) {
		String data = "";
		try {
			if (type.equals("Y"))
				data = value.substring(0, 4);
			if (type.equals("M"))
				data = value.substring(4, 6);
			if (type.equals("D"))
				data = value.substring(6, 8);
		} catch (Exception e) {
			return "";
		}
		return data;

	}

	public static String moneyFormat(String value) {
		System.out.println(value);
		if (value == null || value.equals("")) {
			return "";
		} else {
			DecimalFormat dcf = new DecimalFormat("###,###,###,###,###,###");
			return dcf.format(Long.parseLong(value));
		}
	}
	
	
	public static String moneyFormatInt(int value) {
		System.out.println(value);
		if (value==0) {
			return "";
		} else {
			DecimalFormat dcf = new DecimalFormat("###,###,###,###,###,###");
			return dcf.format(value);
		}
	}
	
	public static String moneyFormatLong(long value) {
		System.out.println(value);
		if (value==0) {
			return "";
		} else {
			DecimalFormat dcf = new DecimalFormat("###,###,###,###,###,###");
			return dcf.format(value);
		}
	}
	

	public static String generateDocId(String docType) {
		String head = docType.substring(0, 1);
		String tail = docType.substring(3, docType.length());
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddkkmmss");
		Date date = new Date(System.currentTimeMillis());
		String m_date = "";
		try {
			m_date = sdf.format(date);
		} catch (Exception e) {
			System.out.println("error in dateToString:" + e);
		}
		Random ran = new Random();
		String random = new Integer(Math.abs(ran.nextInt() % 10000)).toString();
		return head + tail + m_date + random;
	}

	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String m_date = null;
		try {
			m_date = sdf.format(date);
		} catch (Exception e) {
			System.out.println("error in dateToString:" + e);
		}
		return m_date;
	}

	public static Date stringToDate(String value) {
		System.out.println("yyyymmdd" + value);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(value);
		} catch (Exception e) {
			System.out.println("error in stringToDate:" + e);
		}
		return date;
	}

	public static String getYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String m_date = null;
		try {
			m_date = sdf.format(date);
		} catch (Exception e) {
			System.out.println("error in dateToString:" + e);
		}
		return m_date;
	}

	public static String getMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String m_date = null;
		try {
			m_date = sdf.format(date);
		} catch (Exception e) {
			System.out.println("error in dateToString:" + e);
		}
		return m_date;
	}

	public static String getDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String m_date = null;
		try {
			m_date = sdf.format(date);
		} catch (Exception e) {
			System.out.println("error in dateToString:" + e);
		}
		return m_date;
	}

	public static Date stringToDate(String year, String month, String day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(year + month + day);
		} catch (Exception e) {
			System.out.println("error in stringToDate:" + e);
		}
		return date;
	}

	public static String getUUID() throws TaxInvoiceException {
		return getSystemId();
	}

	public static String incodeUrlParam(String value) {
		byte data[] = value.getBytes();
		String outParam = "";
		for (int i = 0; i < data.length; i++) {
			outParam = outParam + "|" + data[i];
		}
		return outParam;
	}

	public static String decodeUrlParam(String value) {
		StringTokenizer st = new StringTokenizer(value, "|");
		String out = "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (st.hasMoreTokens()) {
			String intValue = st.nextToken();
			Integer intData = new Integer(intValue);
			byte bdata = intData.byteValue();
			baos.write(bdata);
		}
		return baos.toString();
	}

	public static String substrParam(String value, int cut) {
		String str = "";
		int len = value.length();

		if (len != 0 && cut != 0 && len > cut) {
			str = value.substring(0, cut) + "...";
		} else
			str = value;

		return str;
	}

	/**
	 * 2005-03-08 pka
	 * TextArea에서 입력받은 캐리지 리턴값을 <BR>태그로 변환
	 */
	public static String nl2br(String saComment) {
		StringBuffer slBuffer = new StringBuffer();
		String slReturn = null;

		for (int i = 0; i < saComment.length(); ++i) {
			String slComp = saComment.substring(i, i + 1);
			if ("\r".compareTo(slComp) == 0) {
				slComp = saComment.substring(++i, i + 1);
				if ("\n".compareTo(slComp) == 0)
					slBuffer.append("<BR>");
			} else {
				slBuffer.append(slComp);
			}
		}
		slReturn = slBuffer.toString();

		return slReturn;

	}
	/**
	 * 일반 스트링을 암호화 String으로 변환하는 함수
	 * @param String TempString
	 * @return String
	 */
	public static String StringCipher(String TempString){
		String ReturnString = "";
		try{
			byte[] byteTmp = TempString.getBytes("KSC5601");
			ReturnString = BytesToHexString(byteTmp);
		}catch (Exception e){}
		return ReturnString;
	}
	/**
	 * 암호화 스트링을  일반 스트링으로 복원하는 함수
	 * @param String TempString
	 * @return String
	 */
	public static String StringDecipher(String TempString){
		String newString = "";
		try{
			byte[] byteStr = HexStringToBytes(TempString);
			newString = new String(byteStr, "KSC5601");
		}catch(Exception e){}
		return newString;
	}

	/**
	 * 바이트 배열을 Hex String으로...by 정명수 2005.3.8
	 * @param byte[] TempByte
	 * @return String
	 */
	private static String BytesToHexString(byte[] TempByte) throws Exception {
        String ReturnString = "";
        for (int i=0; i<TempByte.length; i++){
            if( (TempByte[i] > 15) || (TempByte[i] < 0) ){
                ReturnString += java.lang.Integer.toHexString(TempByte[i] & 0xff).toUpperCase();
            }else{
                ReturnString += "0"+java.lang.Integer.toHexString(TempByte[i] & 0xff).toUpperCase();
            }
        }
        return ReturnString;
    }

	/**
	 * Hex String 을 바이트 배열로...by 정명수 2005.3.8
	 * @param String TempString
	 * @return byte[]
	 */
	private static byte[] HexStringToBytes(String TempString) throws Exception {

		byte[] ReturnByte = new byte[TempString.length()/2];
        int ReturnBytePointer=0;

        for (int i=0; i<TempString.length(); i++){
            if(TempString.charAt(i)=='0'){
                i++;
                if((TempString.charAt(i)>='0') && (TempString.charAt(i)<='9')){
                    ReturnByte[ReturnBytePointer]=(byte)(TempString.charAt(i)-'0');
                }
                else if((TempString.charAt(i)>='A') && (TempString.charAt(i)<='F')){
                    ReturnByte[ReturnBytePointer]=(byte)(10+TempString.charAt(i)-'A');
                }
                ReturnBytePointer++;
            }else{
                byte TempUpper;
                byte TempLower;
                TempUpper=0;
                if((TempString.charAt(i)>='0') && (TempString.charAt(i)<='9')){
                    TempUpper=(byte)(TempString.charAt(i)-'0');
                }else if((TempString.charAt(i)>='A') && (TempString.charAt(i)<='F')){
                    TempUpper=(byte)(10+TempString.charAt(i)-'A');
                }

                TempUpper=(byte)(TempUpper*16);
                i++;
                TempLower=0;

                if((TempString.charAt(i)>='0') && (TempString.charAt(i)<='9')){
                    TempLower=(byte)(TempString.charAt(i)-'0');
                }else if((TempString.charAt(i)>='A') && (TempString.charAt(i)<='F')){
                    TempLower=(byte)(10+TempString.charAt(i)-'A');
                }
                ReturnByte[ReturnBytePointer]=(byte)(TempUpper+TempLower);
                ReturnBytePointer++;
            }
        }
        return ReturnByte;
    }
//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH	
	  /**
     * <PRE>
     * 지정된 위치(/{WEB-ROOT}/WEB-INF/classes)의 properties파일을 읽어 key에 대한 value리턴
     *
     * </PRE>
     * @param   String Key
     * @return  String Value         
     */
    public static String getString(String key)
    {
        Properties props   = null;
		//InputStream  in = null;
		FileInputStream in = null;
		String strReturn = "";
		
 		/**
		 * 프로퍼티 파일명
		 */
		final String CONFIG_FILE = "ap.property";
//		final String CONFIG_FILE = "kr/co/kepco/etax30/selling/util/ap.property";
        
		try {
			//File f = new File(CommProperties.class.getResource(CONFIG_FILE).getPath());
			File f = new File("/data5/ebxml/kepcobill2/kepcobill2/WEB-INF/batch/ap.property");
			//File f = new File("C:/ap.property");
			//System.out.println(CommProperties.class.getResource(CONFIG_FILE).getPath());
			in = new FileInputStream(f);
		}
		catch (Exception e) {
			System.out.println(CONFIG_FILE+" 화일이 없습니다.");
			e.printStackTrace();
		}
		
            
        if (in == null)
        {
			System.out.println(CONFIG_FILE+" 화일이 없습니다.");
			return strReturn;
        }
        else
        {
	        try
	        {
				props = new Properties();     
	            props.load(in);

				strReturn = props.getProperty(key);
	        }
	        catch (IOException ioe)
	        {
	                System.out.println("Exception 발생(CommProperties.java) : " + ioe.getMessage());
					ioe.printStackTrace();                
	        }
	        finally
	        {
	            try 
	            { 
	                if(in != null) in.close(); 
	            } 
	            catch (Exception e)                 
	            { 
	                System.out.println("[CommProperties.java] 스트림 해제 에러");
	                e.printStackTrace(); 
	            }
	        }
	        return strReturn;
        }
    }//END METHOD 	
	public static void main(String args[]) {
		//       int i=0; 
		//       while(i<50){
		//           System.out.println(getSystemId());
		//           i++;
		//           try {
		//            Thread.sleep(100);
		//        } catch (InterruptedException e) {
		//            // TODO Auto-generated catch block
		//            e.printStackTrace();
		//        }
		//       }

	}

}
