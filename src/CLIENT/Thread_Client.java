package CLIENT;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class Thread_Client extends Thread {
	
	private ObjectInputStream ois;
	
	public void run(){
		System.out.println("Thread avviato (revicer)");
		while(true){
			try {
				Pacchetto pkg = (Pacchetto)ois.readObject();
				if(pkg.getType().equals("msg")){
					System.out.println(pkg);
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Thread_Client(Socket s, ObjectInputStream ois) {
		this.ois = ois;
		System.out.println("costruttore threadClient");
		//try {
			//System.out.println("dfseds");
			//ois = new ObjectInputStream(s.getInputStream());
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		System.out.println("prima di start");
		this.start();
	}

}
