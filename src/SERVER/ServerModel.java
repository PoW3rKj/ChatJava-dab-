package SERVER;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import CLIENT.AppendObjectOutputStream;
import CLIENT.Pacchetto;

public class ServerModel {

	private static ObjectOutputStream oos = null;
	public static ArrayList<MyThread> clientConnected = new ArrayList<MyThread>();
	
	public static void sendMessage(Pacchetto p) {
		
		for(int i = 0; i < clientConnected.size(); i++){
			
			if(p.getDestinatario().equals(clientConnected.get(i).getName())){
					
					clientConnected.get(i).sendMessage(p);

				
			}
			
		}
		
			
	}
	
	public static void InviaTutti(Pacchetto p) {
		for(int i = 0; i < clientConnected.size(); i++){	
			clientConnected.get(i).sendMessage(p);	
		}
	}
}
