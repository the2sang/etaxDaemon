package kr.co.kepco.etax30.buyw;

public class ChangeTest {
	
	
	public static String checkTime (String t_time){
		String time = "";
		
		// �ڸ� �� üũ  (14�ڸ��� ��� �ö��� ����)  
		if(t_time.length() == 14){
			
			//�ð��� �߶� üũ 
			String t_hh  =   t_time.substring(8, 10);
			
			String s_dt  = t_time.substring(0, 8);
			String e_dt  = t_time.substring(10, 14);
			
			//System.out.println("====t_hh====="+t_hh);
			//System.out.println("====s_dt====="+s_dt);
			//System.out.println("====e_dt====="+e_dt);
			
			if(t_hh.equals("24")){
				time = s_dt+"00"+e_dt;
			}else{
				time = t_time;  // �ð��� 24�ð� �ƴϸ� ���� �׷��� ����
			}

		}else{
			time = t_time;   // �ڸ����� �̻��ϰ� ���͵� ���� �׷��� ����
		}
		return time; 
	}
	
	
	
	public static void main(String[] args){
		
		System.out.println("===test====");
		
		String  t_modifydt = "20180123212312321231233141116";
		
		System.out.println("�� ��   = >" + t_modifydt);
		String modifydt  = checkTime(t_modifydt);
		System.out.println("������ = >" + modifydt);
		
		
		
	}
}
