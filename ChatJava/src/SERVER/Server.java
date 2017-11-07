package SERVER;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server extends Thread {

	private final DatagramSocket s;
	private DatagramPacket pkt;
	private byte[] buf = new byte[256];

	public Server() throws Exception{
		s = new DatagramSocket(9898);
		this.start();
	}
	
	public void run(){
		while(true){
		String m = receiveMessage();
		
		String[] m2 = new String[2];
		m2 = divMessage(m);
		
		sendMessage(m2[0], m2[1]);

		}
	}
	
	private String[] divMessage(String m){
		
		String[] s = new String[2];
		s[1] = "";
		s[0] = "";
		boolean trovato = false;
		int i = 0;
		while(true && !trovato){
			
			if(m.charAt(i) == '|'){
				i++;
				while(m.charAt(i) != '|'){
					s[1] = s[1] + m.charAt(i);
					
					i++;
				}
				trovato = true;
			}else{
				s[0] = s[0] + m.charAt(i);
			}
			i++;
		}
		System.out.println("Destinatario: " + s[1] + " Msg: " + s[0]);
		return s;
	}
	
	
	private void sendMessage(String msg, String dest){
		
		try {
			s.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(dest), 9898));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
