package CLIENT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class  Login_Controller implements Initializable {
	
	@FXML
	private TextField serverip_fxml;
	@FXML
	private TextField porta_fxml;
	@FXML
	private TextField nickname_fxml;
	@FXML
	private ColorPicker chat_color_fxml = new ColorPicker();
	@FXML
	private Button but0;
	
	
	private String serverip;
	private String port;
	private String nickname;
	private Color chat_color;
	

	@FXML
	 private void login(ActionEvent event) {
		serverip = serverip_fxml.getText();
		port = porta_fxml.getText();
		nickname = nickname_fxml.getText(); 
		chat_color = chat_color_fxml.getValue();
		Nickname.color = chat_color;
		Nickname.serverip = serverip;
		Nickname.port = Integer.parseInt(port);
		Nickname.nickname = nickname;
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client_UI.fxml"));
        Parent root1;
		try {
			root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initStyle(StageStyle.UTILITY);
			stage.resizableProperty().setValue(Boolean.FALSE);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle("Chat");
	        stage.setScene(new Scene(root1));  
	        stage.show();
	        Client.copia_stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@FXML
	private void closeButtonAction(){
		Platform.exit();
		System.exit(0);
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Color getChat_color() {
		return chat_color;
	}

	public void setChat_color(Color chat_color) {
		this.chat_color = chat_color;
	}

}
