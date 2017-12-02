package SERVER;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle.Control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ServerMain {
	public static Scene scene;
	public static Stage copia_stage;
	
	public static void main(String[] args) {
	
		ServerModel sModel = new ServerModel();
		
		try {
			int i = 0;
			ServerSocket ss = new ServerSocket(9898);	
			System.out.println("*** SERVER AVVIATO ***");
			
			while(true) {
				Socket s = ss.accept();
				MyThread t = new MyThread(s);
				sModel.clientConnected.add(t);
				System.out.println("Client connessi dopo una connessione: " + ServerModel.clientConnected.size());
				i++;
				System.out.println();
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
