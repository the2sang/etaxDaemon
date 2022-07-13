package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.run;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.PropertyUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.ServerProperties;
import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Shutdown extends Thread {
	
	private static Logger logger = Logger.getLogger(Shutdown.class);
	
    byte[] _sendData = new String("stop").getBytes();
    private String _ip = "localhost";
    private int _port = 0;
    private boolean isSafe = true;
    private ServerProperties props = null;
    
    public Shutdown(String prop_path){
        try{
            loadProperty(prop_path);
            System.out.println(props.getProperty("demon.shutdown.port"));
            _port = Integer.parseInt((String)props.getProperty("demon.shutdown.port"));
            
        }catch(NumberFormatException e){
            System.out.println("CRON PROPERTY의  CRON.SHUTDONW.PORT를 정확히 지정해 주세요.\n");
            isSafe = false;
            System.exit(-1);
            e.printStackTrace();
        }
    }
    
    public boolean isSafe(){
        return isSafe;
    }
    
    public void loadProperty(String prop_path) {      
        try {
            props = ServerProperties.getInstance();
            PropertyUtil.loadProperty(prop_path);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }   
    public void run(){
        InputStream input = null;
        PrintStream output = null;
        byte bytes[] = new byte[4096];
        Socket server = null;
        int c;
        try {
            server = new Socket(_ip, _port);
            
// 지정된 주소와 포트를 이용하여 소켓을 생성한 후 서버에 연결까지 한다.
            input = server.getInputStream();
            
// 연결된 소켓에서 입력 스트림을 얻는다.
            output = new PrintStream(server.getOutputStream());
            output.write(_sendData,0,_sendData.length);
            server.close();
            output.close();
            input.close();
        } catch (Exception e) {
            System.err.println("Exception in sending data to server" + e);
            e.printStackTrace();
            try {
                server.close(); // 예외가 발생하면 연결된 소켓을 닫는다.
            } catch (Exception e1) {
                logger.debug("Exception in sending data to server" + e1);
            }
            return;
        }
    }
    
    public static void main(String arg[]){
    	String prop_path = arg[0];
		logger.debug("prop_path:"+prop_path);
        Shutdown sdc = new Shutdown(prop_path);
        sdc.start();
    }
	
	
	
	
//	public static void main(String[] args) throws InterruptedException 
//	{	
//        String prop_path  = args[0];
//        PollerAdapter poller = new PollerAdapter(prop_path);
//        poller.loadProperty();		
//        logger.debug("poller.getStatus():"+poller.getStatus());
////        poller.stop();
////        poller.start();
//        poller.setStatus(false);
//        
//        logger.debug("poller.getStatus():"+poller.getStatus());
//        poller.loadProperty();		
//        
//	}
}
