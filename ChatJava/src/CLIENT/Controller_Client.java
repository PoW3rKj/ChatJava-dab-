package CLIENT;

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
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.html.ListView;

public class Controller_Client  extends Thread implements javafx.fxml.Initializable{
		
		private DatagramSocket s;
		@FXML
		private Button invia;
		@FXML
		private TextField messaggio;
		@FXML
		private TextField ip_dest;
//		@FXML
//		private ListView vista_messaggio;
		
		private DatagramPacket pkt;
		private byte[] buf = new byte[256];
		private DatagramSocket s2;
		

		public Controller_Client() {
			try {
				s2 = new DatagramSocket(9897);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.start();
		}
		
		public void run() {
			while(true){
				String smg_arrivo = receiveMessage();
				System.out.println(smg_arrivo);
				messaggio.setText("");
				//vista_messaggio
				// DA MODIFICARE COMPONENTE GRAFICO, LISTVIEW NON è QUELLO GIUSTO
			}
		}
		
		
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

		private String receiveMessage(){
			
			pkt = new DatagramPacket(buf, buf.length);
			
			try {
				s2.receive(pkt);
				//System.out.println("" + pkt.getData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String msg = new String(pkt.getData(), 0, pkt.getLength());
			System.out.println("msg ricevuto: " + msg);
			return msg;
		}
		
}	

