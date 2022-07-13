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
	
	static final int DEFAULT_PORT = 1234; // ������ bind�ϴ� �ʱ� ��Ʈ ��

	public ConnectionManager cm = null; // ������ �����ϴ� ������

	public static PollerAdapter demon = null;

	public Startup(int port, PollerAdapter demon) {
		logger.debug("Server is initializing to port " + port);
		cm = new ConnectionManager(port, demon);
		
		// ������ �����ϴ� ������ ����.
		cm.start();
		// ConnectionManager�� run() ���� ȣ��
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
			logger.debug("shutdown port ������ �߸� �Ǿ����ϴ�.");
		}
		Startup sc = new Startup(_port, poller);
        
	}
	
	
}
/*
 * ConnectionManager�� ������ ��ٸ��ٰ� Ŭ���̾�Ʈ�� �����ؿ��� �ش� Ŭ���̾�Ʈ�� ó���� ServerConnection �����带 �����Ѵ�. ������ �������� ��Ƽ�÷����̴�.
 */

class ConnectionManager extends Thread {
	private static int _port;

	private static Vector _my_threads = new Vector(5, 2);

	private volatile boolean _work = false;

	private PollerAdapter _demon = null;

	/*
	 * �ʱ�ġ 5, ����ġ 2�� ���͸� �����Ѵ�. �� ���Ϳ��� ������ ����Ǹ� �����Ǵ� ServerConnection ���� �� �ν��Ͻ����� ����ȴ�.
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
		// ���� ������ �����Ͽ� ���� ������ ���ٰ� Ŭ���̾�Ʈ���� �����ؿ��� ������ �����Ͽ�
		// ������ ���ῡ �ϳ��� �����带 ������ִ� ��ƾ�̴�.
	}

	private void serverRequests() {
		System.out.println("start serverRequests");
		try {
			System.out.println("port::::" + _port);
			_main_socket = new ServerSocket(_port); // ���� ���� ����
			System.out.println("_main_socket::::" + _main_socket);
		} catch (Exception e) {
			System.err.println(e);
			// System.exit(1);
		}
		ServerConnection temp_sc = null;
		while (_work) // ���� ����. �� ������ ��Ʈ�� ������ ������ ��Ʈ�̴�.
		{
			System.out.println(_work);
			System.out.println(1);
			try {
				System.out.println(2);
				Socket this_connection = _main_socket.accept();
				System.out.println(1);
				// ���� ������ Ŭ���̾�Ʈ�� ��ٷȴٰ� ������ �����Ѵ�.
				// accept()�� ��ȯ���� Socket �ν��Ͻ��̰� ������ ����� Ŭ���̾�Ʈ��
				// ��ȭ�� �� �ν��Ͻ��� ���ؼ� �Ѵ�.
				// ������ �����Ǹ� �� Socket �ν��Ͻ��� ServerConnection �����带 ����
				temp_sc = new ServerConnection(this_connection, _demon);
				temp_sc.start();
				
				// ServerConnection �ν��Ͻ��� run() ȣ��
				_my_threads.addElement(temp_sc);
				// ServerConnection �ν��Ͻ��� ���Ϳ� �߰�
				/*
				 * ������ ũ�⸸ŭ ������ �ݺ��Ѵ�. ���Ϳ� ����Ǿ� �ִ� �� ServerConnection �����尡 ���� ������ �˻��ϰ� �����尡 �����Ͽ����� ���Ϳ��� �����Ѵ�. ServerConnection ������� ������ �����ϸ� ������ �ߴ��Ѵ�.
				 */
				for (int i = 0; i < ConnectionManager._my_threads.size(); i++)
					if (!((ServerConnection) (_my_threads.elementAt(i))).isAlive())
						_my_threads.removeElementAt(i);
				System.out.println(4);
			} catch (Exception e) {
				System.out.println("���μ����� �̹� ������Դϴ�. ������� ���μ����� kill �� �ּ���!");
				System.exit(-1);
			}
		}
	}
}

class ServerConnection extends Thread // ������ ������ �� ���� Ŭ���̾�Ʈ�� ��ȭ�� �ϴ� ������
{
	private Socket _mysocket;

	private PrintStream _output;

	private InputStream _input;

	private PollerAdapter _demon = null;

	public ServerConnection(Socket s, PollerAdapter demon) {
		_mysocket = s; // Ŭ���̾�Ʈ�� ������ �� ���� �ν��Ͻ��� �Ѱ� �޴´�.
		_demon = demon;
	}

	private void doServerWork() {
		/*
		 * ���� ������ �ϴ� �ϵ��� ���⿡ ����. �� ���� ������ �� ������ �����Ѵ�. �� ���� �����带 �����Ѵ�.(��, stop()�� ȣ��) ���⼭�� �����ϰ� �޽����� PrintStream���� 10�� ��������.
		 */
		try {
			for (int i = 0; i < 10; i++) {
				_output.println("This is a message from the server");
				sleep((int) (Math.random() * 4000));
				// Math.random()�� 0.0�� 1.0 ������ ���� ���� �߻�
			}
		} catch (Exception e) {
		}
	}

	public void run() {
		System.out.println("Connected to: " + _mysocket.getInetAddress() + ":" + _mysocket.getPort());
		// ����� ������ ���ͳ� �ּҿ� ��Ʈ�� ǥ�� ������� ����Ѵ�.
		try {

			byte[] temp = new byte[1024];
			byte[] buffer = null;
			int saved = 0;
			int len;
			_output = new PrintStream(_mysocket.getOutputStream());
			/*
			 * ����� ������ ��� ��Ʈ������ PrintStream�� �����Ѵ�. ������ ��� ��Ʈ���� �������� Ŭ���̾�Ʈ�� ���޵Ǵ� ��Ʈ���̴�. �ݴ�� �Է� ��Ʈ���� Ŭ���̾�Ʈ���� ������ ���޵Ǵ� ��Ʈ���̴�.
			 */
			_input = _mysocket.getInputStream(); // �Է� ��Ʈ���� ��´�.
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
		stop(); // �����带 �ߴ��Ѵ�.
		this.notifyAll();
		/*
		 * �������� stop() ������ ThreadDeath ��ü �ν��Ͻ��� ���� ��ü���� ���� �ش�. ThreadDeath ��ü�� Exception Ŭ�������� �Ļ��� ���� �ƴ϶� Error Ŭ�������� �Ļ��Ǿ���. Ư���� ���� ���ٸ� catch�� �� �ʿ䰡 ����. catch�� ���� ������ �ߴܵ� �����尡 ������ ����ȴ�.
		 */
	}

}