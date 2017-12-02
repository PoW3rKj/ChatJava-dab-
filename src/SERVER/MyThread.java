package SERVER;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import CLIENT.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.TextFlow;


//import jdk.internal.org.xml.sax.InputSource;

public class MyThread extends Thread{
	@FXML
	private TextFlow view;
	
	private Scene scene;
	private Socket client;
	private int id;
	private ServerModel sModel = new ServerModel();
	
//	private Scanner ins;
//	private BufferedReader in;
//	private PrintWriter out;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
		
	public MyThread(Socket s) {
		client = s;
		
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.start();
	}
	
	public void run() {
		scene = ServerMain.scene;
		while(true){
			
			try {
				Pacchetto pkg = (Pacchetto)ois.readObject();
				//ois.reset();
				
				if(pkg.getType().equals("nickname")){
					this.setName(pkg.getMsg());
					
					ArrayList<String> data = new ArrayList<String>();
					for(int cont = 0; cont < ServerModel.clientConnected.size(); cont ++) {
						data.add(ServerModel.clientConnected.get(cont).getName());
					}
					Pacchetto pacchetto = new Pacchetto("UPDATE_USERS", data, pkg.getMsg(), "",true);
					ServerModel.InviaTutti(pacchetto);
					System.out.println("Invio UPDATE USERS2");
				}
				
				if(pkg.getType().equals("msg")){
					sModel.sendMessage(pkg);
				}
				
				
				System.out.println(pkg);
				
			} catch (Exception e) {			
				try {
					//System.out.println(ServerModel.clientConnected.size());
					boolean trovato = false;
					for(int i = 0; i < ServerModel.clientConnected.size() && !trovato; i++) {
						//System.out.println(i);
						//System.out.println(this.getName());						
						String name = ServerModel.clientConnected.get(i).getName();
						//System.out.println(name);
						if(this.getName().equals(name)) {
							trovato = true;
							ServerModel.clientConnected.remove(i);
							System.out.println(ServerModel.clientConnected);
							ArrayList<String> data = new ArrayList<String>();
							for(int cont = 0; cont < ServerModel.clientConnected.size(); cont ++) {
								data.add(ServerModel.clientConnected.get(cont).getName());
							}
							Pacchetto p = new Pacchetto("UPDATE_USERS", data, this.getName(), "",false);
							System.out.println("Invio UPDATE_USERS2");
							ServerModel.InviaTutti(p);
						}
					}
					client.close();
					System.out.println("Client connessi dopo rimozione: " + ServerModel.clientConnected.size());
					break;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				
				//e.printStackTrace();
			}
			
		}
		
	}
	
	public Socket getSocket(){
		return client;
	}

	public void sendMessage(Pacchetto pkg){
		try {
			
			oos.writeObject(pkg);
			oos.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
