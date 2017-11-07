package CLIENT;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Thread_Client extends Thread {
	private DatagramPacket pkt;
	private byte[] buf = new byte[256];
	private DatagramSocket s;
	public void run(){
		while(true){
			String smg_arrivo = receiveMessage();
			System.out.println(smg_arrivo);
		}
	}
	
	public Thread_Client() {
		try {
			s = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.start();
	}

	private String receiveMessage(){
			
			pkt = new DatagramPacket(buf, buf.length);
			
			try {
				s.receive(pkt);
				//System.out.println("" + pkt.getData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String msg = new String(pkt.getData(), 0, pkt.getLength());
			System.out.println(msg);
			return msg;
		}
}
