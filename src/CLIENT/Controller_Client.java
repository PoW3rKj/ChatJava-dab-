package CLIENT;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ListView;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Date;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
//import java.awt.Label;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.swing.JComboBox;


public class Controller_Client  extends Thread implements javafx.fxml.Initializable, Serializable{
		
		private String server_ip;
		private String nickname;
		private int port;
		
		@FXML
		private Button invia;
		@FXML
		private TextField messaggio;
		@FXML
		private TextField ip_dest;
		@FXML
		private ListView lista;
		@FXML
		private Button pulisci;
		@FXML
		private TextFlow view;
		@FXML
		private ComboBox dest = new ComboBox();
		@FXML
		private ListView notifiche;
		@FXML
		private Label online_users;
		@FXML
		private Label nickname_show = new Label();
		@FXML
		private CheckBox notifiche_systema;
		
		private Scene scene;
		private Socket s;
		private ObjectInputStream ois = null;
	    private ObjectOutputStream oos = null;
	    private ArrayList<Chat> chats;
	    private Color colore_here;
	    private Color colore_ext;

		public Controller_Client() {
			nickname = Nickname.nickname;
			server_ip = Nickname.serverip;
			port = Nickname.port;
			colore_here = Nickname.color;
			
			
			if (!SystemTray.isSupported()) {
				System.err.println("System tray not supported!");
	        }
			try {
				view = new TextFlow();

				s = new Socket(server_ip, port);
				
				System.out.println("Connesso con il server ip: " + s.getInetAddress());
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
				chats = new ArrayList<Chat>();
			    
			    System.out.println("pacchetto inviato");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Date d = new Date();
			String ora = "[" + d.getHours() + ":" + d.getMinutes() + "]";
			Pacchetto pkg = new Pacchetto("nickname", "", nickname, nickname, ora, false);
		    try {
				oos.writeObject(pkg);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.start();
		}
	
		public void run() {
			scene = Client.scene;
			System.out.println(dest);
		    while(true) {
		    		
					Pacchetto pkg2;
					try {
						Platform.runLater(() -> {
							nickname_show.setText(nickname);
						});
						pkg2 = (Pacchetto)ois.readObject();
						if(pkg2.getType().equals("msg")){
							System.out.println("Ricevuto:" + pkg2);
						} else if(pkg2.getType().equals("UPDATE_USERS")) {
							Platform.runLater(() -> {
								online_users.setText("Online: " + pkg2.getData().size());
								System.out.println(pkg2);
								dest.getItems().clear();
								for(int cont=0; cont < pkg2.getData().size(); cont++) {
									dest.getItems().add(pkg2.getData().get(cont));
								}
								for(int cont=0; cont < dest.getItems().size(); cont++){
									if(dest.getItems().get(cont).toString().equals(nickname)){
										dest.getSelectionModel().select(cont);
									}
								}
								Text text = new Text(">> Nuova chat con: " + nickname + "\n");
			     				text.setFont(Font.font("Helvetica", FontWeight.THIN,  15));
			     				text.setFill(Color.WHITESMOKE);
			     				view.getChildren().addAll(text);
								
								if(pkg2.isFlag() == true){
									boolean flag = false;
									for(int cont=0; cont < pkg2.getData().size(); cont++){
										int cont2 = 0;
										while(flag == false && cont2 < chats.size()){
											if(chats.get(cont2).equals(pkg2.getData().get(cont))){
												flag = true;
											}
					
											cont2++;
											
										}
										if (flag == false){
											ArrayList<Pacchetto> msgs = new ArrayList<Pacchetto>();
											Chat chat = new Chat(pkg2.getData().get(cont), msgs, false);
											chats.add(chat);
										}
									}
									
									if (flag)
									for(int cont=0; cont < pkg2.getData().size(); cont++){
										for(int cont1=0; cont < chats.size(); cont++){
											if(!pkg2.getData().get(cont).equals(chats.get(cont1).getName())){
												ArrayList<Pacchetto> msgs = new ArrayList<Pacchetto>();
												Chat chat = new Chat(pkg2.getData().get(cont), msgs, false);
												chats.add(chat);
											}
										}
									}
									System.out.println(chats.size());
								}
							});
						}
						insertMessage(pkg2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
						System.exit(1);
					}
					
		    }
		    

		}
		
		
		@FXML
		 private void invia(ActionEvent event) {
			 if((dest.getSelectionModel().getSelectedIndex() >= 0 ) && (!messaggio.getText().equals(""))){
				 Date d = new Date();
				 String ora = "[" + d.getHours() + ":" + d.getMinutes() + "]";
				 Pacchetto pkg = new Pacchetto("msg", dest.getSelectionModel().getSelectedItem().toString(), nickname, messaggio.getText(), ora, false);
			     Platform.runLater(() -> {
	                 try {
	     				for(int cont=0; cont< chats.size(); cont++){
	     					if(chats.get(cont).getName().equals(pkg.getDestinatario())){
	     						chats.get(cont).getChat().add(pkg);
	     					}
	     				}
	     				if(dest.getSelectionModel().getSelectedItem().toString().equals(pkg.getDestinatario())){
		     				Text text = new Text(ora + " " + pkg.getMittente() + ": " + pkg.getMsg() + "\n");
		     				text.setFont(Font.font("Helvetica", 15));
		     				text.setFill(Color.WHITE);
		     				view.getChildren().addAll(text);
		     				System.out.println("pkg");
		                	messaggio.setText("");
		                	oos.writeObject(pkg);
	     				}
	                
	                 } catch (Exception ex) {
	                    //Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
	                 }
	                 
             });
			 }
		     
		 }
		
		
		
		@FXML
		 private void clear(ActionEvent event) {
			lista.getItems().clear();
		}
		
		@FXML
		public void onEnter(ActionEvent ae){
		
			System.err.println(dest);
			System.out.println("Cambio chat a " + dest.getSelectionModel().getSelectedItem().toString());
			view.getChildren().clear();
			String chatname = dest.getSelectionModel().getSelectedItem().toString();
			for(int cont=0; cont< chats.size(); cont++){
					if(chats.get(cont).getName().equals(chatname)){
						for(int cont1=0; cont1<chats.get(cont).getChat().size(); cont1++){
							System.out.println("Stampo messaggio: " + chats.get(cont).getChat().get(cont1).getMsg());
							String msg = chats.get(cont).getChat().get(cont1).getMsg();
							String ora = chats.get(cont).getChat().get(cont1).getOra();
							String mittente = chats.get(cont).getChat().get(cont1).getMittente();
							Text text = new Text(ora + " " + mittente + ": " + msg + "\n");
//							colore_ext = chats.get(cont).getChat().get(cont1).getColore();
//							text.setFill(colore_ext);
							text.setFont(Font.font("Helvetica", 15));
							if(!nickname.equals(mittente)){
								text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
							}
							//lista.getItems().add(pkg.getMittente() + ": " + pkg.getMsg());
							view.getChildren().addAll(text);
						}
					}
					for(int cont4=0; cont<notifiche.getItems().size(); cont++){
						if(notifiche.getItems().get(cont4).equals("- " +dest.getSelectionModel().getSelectedItem().toString())){
							notifiche.getItems().remove(cont4);
						}
					}
				}
		}
		
		@FXML
		public void comboboxChanged(ActionEvent event){
			System.err.println(dest);
			System.out.println("Cambio chat a " + dest.getSelectionModel().getSelectedItem().toString());
			view.getChildren().clear();
			String chatname = dest.getSelectionModel().getSelectedItem().toString();
			for(int cont=0; cont< chats.size(); cont++){
					if(chats.get(cont).getName().equals(chatname)){
						for(int cont1=0; cont1<chats.get(cont).getChat().size(); cont1++){
							System.out.println("Stampo messaggio: " + chats.get(cont).getChat().get(cont1).getMsg());
							String msg = chats.get(cont).getChat().get(cont1).getMsg();
							String ora = chats.get(cont).getChat().get(cont1).getOra();
							String mittente = chats.get(cont).getChat().get(cont1).getMittente();
							Text text = new Text(ora + " " + mittente + ": " + msg + "\n");
//							text.setFill(chats.get(cont).getChat().get(cont1).getColore());
							text.setFill(Color.WHITE);
							text.setFont(Font.font("Helvetica", 15));
							if(!nickname.equals(mittente)){
								text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
							}
							//lista.getItems().add(pkg.getMittente() + ": " + pkg.getMsg());
							view.getChildren().addAll(text);
						}
					}
					for(int cont4=0; cont<notifiche.getItems().size(); cont++){
						if(notifiche.getItems().get(cont4).equals("- " +dest.getSelectionModel().getSelectedItem().toString())){
							notifiche.getItems().remove(cont4);
						}
					}
				}
		}
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
		

		@FXML
		private void closeButtonAction(){
			Platform.exit();
			System.exit(MIN_PRIORITY);
		}
		
		public void insertMessage(Pacchetto pkg){
			if(pkg.getType().equals("msg")){
				Platform.runLater(() -> {
				Date d = new Date();
				String date = "[" + d.getHours() + ":" + d.getMinutes() + "]";	
				
				for(int cont=0; cont< chats.size(); cont++){
					System.out.println(chats.size());
					System.out.println("Nickname chat: " + chats.get(cont).getName());
					System.out.println("Mittente msg: " + pkg.getMittente());
 					if(chats.get(cont).getName().equals(pkg.getMittente())){
 						chats.get(cont).getChat().add(pkg);
 						System.out.println("Aggiungo messaggio: " + pkg.getMsg() + " Alla chat: " + chats.get(cont).getName());
 					}
 				}
				
				if(dest.getSelectionModel().getSelectedItem().toString().equals(pkg.getMittente())){
					Text text = new Text(date + " " + pkg.getMittente() + ": " + pkg.getMsg() + "\n");
					colore_ext = pkg.getColore();
//					text.setFill(colore_ext);
					text.setFill(Color.WHITE);
					text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
					//lista.getItems().add(pkg.getMittente() + ": " + pkg.getMsg());
					view.getChildren().addAll(text);
				}
				if(notifiche_systema.isSelected()){
					//Obtain only one instance of the SystemTray object
			        SystemTray tray = SystemTray.getSystemTray();

			        //If the icon is a file
			        
			        //Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
			        //Alternative (if the icon is on the classpath):
			        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("logo_ext.png"));
			        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
			        //Let the system resizes the image if needed
			        trayIcon.setImageAutoSize(true);
			        //Set tooltip text for the tray icon
			        trayIcon.setToolTip("System tray icon demo");
			        try {
						tray.add(trayIcon);
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        trayIcon.displayMessage(pkg.getMittente(), pkg.getMsg(), MessageType.INFO);
				}
				
				if(!dest.getSelectionModel().getSelectedItem().toString().equals(pkg.getMittente())){
					notifiche.getItems().add("- " + pkg.getMittente());
				}
				});
			}
				
		}
}	

