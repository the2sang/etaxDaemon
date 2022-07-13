package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.run;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.PropertyUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.ServerProperties;
import org.apache.log4j.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Startup {
	
	private static Logger logger = Logger.getLogger(Startup.class);
	
	static final int DEFAULT_PORT = 1234; // 서버가 bind하는 초기 포트 값

	public ConnectionManager cm = null; // 연결을 관리하는 쓰레드

	public static PollerAdapter demon = null;

	public Startup(int port, PollerAdapter demon) {
		logger.debug("Server is initializing to port " + port);
		cm = new ConnectionManager(port, demon);
		
		// 접속을 관리하는 쓰레드 생성.
		cm.start();
		// ConnectionManager의 run() 도구 호출
	}

	public void shutDownServer() {
		System.exit(-1);
		cm = null;
	}
	
	public static void main(String[] args) 
	{	
		
        String prop_path  = args[0];
       
        PollerAdapter poller = new PollerAdapter(prop_path);
        poller.loadProperty();		
        poller.start();
        
//        logger.debug("poller.tax_ref_cnt:"+poller.tax_ref_cnt);
//        if (poller.tax_ref_cnt<1) {
//        	poller.setStatus(true);
//        	poller.start();
//		}	
        
        ServerProperties props = ServerProperties.getInstance();
        PropertyUtil.loadProperty(prop_path);
		
        String str_prot = props.getProperty("demon.shutdown.port");
        
        logger.debug("demon.shutdown.port::" + str_prot);
		int _port = 0;
		try {
			_port = Integer.parseInt(str_prot);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
			logger.debug("shutdown port 설정이 잘못 되었습니다.");
		}
		Startup sc = new Startup(_port, poller);
        
	}
	
	
}
/*
 * ConnectionManager는 접속을 기다리다가 클라이언트가 연결해오면 해당 클라이언트를 처리할 ServerConnection 쓰레드를 생성한다. 일종의 동기적인 멀티플렉싱이다.
 */

class ConnectionManager extends Thread {
	private static int _port;

	private static Vector _my_threads = new Vector(5, 2);

	private volatile boolean _work = false;

	private PollerAdapter _demon = null;

	/*
	 * 초기치 5, 증가치 2인 벡터를 생성한다. 이 벡터에는 소켓이 연결되면 생성되는 ServerConnection 쓰레 드 인스턴스들이 저장된다.
	 */
	private ServerSocket _main_socket = null;

	public ConnectionManager(int port, PollerAdapter demon) {
		_port = port;
		_work = true;
		_demon = demon;
	}

	public synchronized void stopThread() {
		// for (int i = 0; i < ConnectionManager._my_threads.size(); i++){
		// Thread currentThread = (Thread) _my_threads.elementAt(i);
		// if (((ServerConnection)currentThread).isAlive()){
		// currentThread.stop();
		// currentThread = null;
		//              
		// }
		// }
		_work = false;
		this.notify();
		this.notifyAll();
	}

	public void run() {
		serverRequests();
		// 서버 소켓을 생성하여 무한 루프를 돌다가 클라이언트들이 접속해오면 연결을 생성하여
		// 각각의 연결에 하나의 쓰레드를 만들어주는 루틴이다.
	}

	private void serverRequests() {
		System.out.println("start serverRequests");
		try {
			System.out.println("port::::" + _port);
			_main_socket = new ServerSocket(_port); // 서버 소켓 생성
			System.out.println("_main_socket::::" + _main_socket);
		} catch (Exception e) {
			System.err.println(e);
			// System.exit(1);
		}
		ServerConnection temp_sc = null;
		while (_work) // 무한 루프. 이 루프의 포트는 지정한 서버의 포트이다.
		{
			System.out.println(_work);
			System.out.println(1);
			try {
				System.out.println(2);
				Socket this_connection = _main_socket.accept();
				System.out.println(1);
				// 서버 소켓이 클라이언트를 기다렸다가 연결을 생성한다.
				// accept()의 반환값은 Socket 인스턴스이고 앞으로 연결된 클라이언트와
				// 대화는 이 인스턴스를 통해서 한다.
				// 연결이 생성되면 이 Socket 인스턴스로 ServerConnection 쓰레드를 생성
				temp_sc = new ServerConnection(this_connection, _demon);
				temp_sc.start();
				
				// ServerConnection 인스턴스의 run() 호출
				_my_threads.addElement(temp_sc);
				// ServerConnection 인스턴스를 벡터에 추가
				/*
				 * 벡터의 크기만큼 루프를 반복한다. 벡터에 저장되어 있는 각 ServerConnection 쓰레드가 실행 중인지 검사하고 쓰레드가 종료하였으면 벡터에서 제거한다. ServerConnection 쓰레드는 소켓을 종료하면 실행을 중단한다.
				 */
				for (int i = 0; i < ConnectionManager._my_threads.size(); i++)
					if (!((ServerConnection) (_my_threads.elementAt(i))).isAlive())
						_my_threads.removeElementAt(i);
				System.out.println(4);
			} catch (Exception e) {
				System.out.println("프로세스가 이미 사용중입니다. 사용중인 프로세스를 kill 해 주세요!");
				System.exit(-1);
			}
		}
	}
}

class ServerConnection extends Thread // 연결이 생성된 후 실제 클라이언트와 대화를 하는 쓰레드
{
	private Socket _mysocket;

	private PrintStream _output;

	private InputStream _input;

	private PollerAdapter _demon = null;

	public ServerConnection(Socket s, PollerAdapter demon) {
		_mysocket = s; // 클라이언트와 연결이 된 소켓 인스턴스를 넘겨 받는다.
		_demon = demon;
	}

	private void doServerWork() {
		/*
		 * 실제 서버가 하는 일들이 여기에 들어간다. 이 일을 수행한 후 소켓을 종료한다. 그 다음 쓰레드를 종료한다.(즉, stop()을 호출) 여기서는 간단하게 메시지를 PrintStream으로 10번 내보낸다.
		 */
		try {
			for (int i = 0; i < 10; i++) {
				_output.println("This is a message from the server");
				sleep((int) (Math.random() * 4000));
				// Math.random()은 0.0과 1.0 사이의 수를 난수 발생
			}
		} catch (Exception e) {
		}
	}

	public void run() {
		System.out.println("Connected to: " + _mysocket.getInetAddress() + ":" + _mysocket.getPort());
		// 연결된 소켓의 인터넷 주소와 포트를 표준 출력으로 출력한다.
		try {

			byte[] temp = new byte[1024];
			byte[] buffer = null;
			int saved = 0;
			int len;
			_output = new PrintStream(_mysocket.getOutputStream());
			/*
			 * 연결된 소켓의 출력 스트림으로 PrintStream을 생성한다. 소켓의 출력 스트림은 서버에서 클라이언트로 전달되는 스트림이다. 반대로 입력 스트림은 클라이언트에서 서버로 전달되는 스트림이다.
			 */
			_input = _mysocket.getInputStream(); // 입력 스트림을 얻는다.
			int b = 0;
			ByteArrayOutputStream bufferByteArray = new ByteArrayOutputStream();
			while ((b = _input.read(temp)) != -1) {
				bufferByteArray.write(temp, 0, b);
			}
			buffer = bufferByteArray.toByteArray();
			// StringBuffer stringBuffer = new StringBuffer(new String(buffer));

			System.out.println(buffer.length);
			int length = buffer.length;
			if (buffer.length > 200) {
				length = 200;

			}

			byte[] comp = new byte[length];
			for (int i = 0; i < length; i++) {
				comp[i] = buffer[i];
			}
			String data = new String(comp);

			if (data != null && data.equals("stop")) {
				System.out.println("===============================================");
				System.out.println("Producer demon stop");
				System.out.println("===============================================");
				System.exit(-1);
			}
			System.out.println("received data:" + data);
			_mysocket.close();
		} catch (Exception e) {
			try {
				_mysocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Exception in getting data from monitering client:\n" + e);
		}
		System.out.println("Disconnecting: " + _mysocket.getInetAddress() + ":" + _mysocket.getPort());
		stop(); // 쓰레드를 중단한다.
		this.notifyAll();
		/*
		 * 쓰레드의 stop() 도구는 ThreadDeath 객체 인스턴스를 목적 객체에게 던져 준다. ThreadDeath 객체는 Exception 클래스에서 파생된 것이 아니라 Error 클래스에서 파생되었다. 특별한 일이 없다면 catch를 할 필요가 없다. catch를 하지 않으면 중단된 쓰레드가 실제로 종료된다.
		 */
	}

}