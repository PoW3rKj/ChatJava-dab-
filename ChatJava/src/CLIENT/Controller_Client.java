package CLIENT;

import GUI.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Client implements javafx.fxml.Initializable {
		
		private DatagramSocket s;
		@FXML
		private Button invia;
		@FXML
		private TextField messaggio;
		@FXML
		private TextField ip_dest;

		@FXML
		 private void handleButtonAction(ActionEvent event) {
		     // Button was clicked, do something...
		     String msg = messaggio.getText() + "|"+ ip_dest.getText() + "|";
		     try {
				s= new DatagramSocket();
		    	s.send(new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("localhost"), 9898));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}	
		
		
}	

