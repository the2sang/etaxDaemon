package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

public class ChangeTest {


	public static String checkTime (String t_time){
		String time = "";

		// 자리 수 체크  (14자리로 들어 올때에 변경)
		if(t_time.length() == 14){

			//시간만 잘라서 체크
			String t_hh  =   t_time.substring(8, 10);

			String s_dt  = t_time.substring(0, 8);
			String e_dt  = t_time.substring(10, 14);

			//System.out.println("====t_hh====="+t_hh);
			//System.out.println("====s_dt====="+s_dt);
			//System.out.println("====e_dt====="+e_dt);

			if(t_hh.equals("24")){
				time = s_dt+"00"+e_dt;
			}else{
				time = t_time;  // 시간이 24시가 아니면 들어온 그래도 리턴
			}

		}else{
			time = t_time;   // 자리수가 이상하게 들어와도 들어온 그래도 리턴
		}
		return time;
	}



	public static void main(String[] args){

		System.out.println("===test====");

		String  t_modifydt = "20180123212312321231233141116";

		System.out.println("기 존   = >" + t_modifydt);
		String modifydt  = checkTime(t_modifydt);
		System.out.println("변경후 = >" + modifydt);



	}
}
