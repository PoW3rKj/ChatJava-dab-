package CLIENT;

import java.io.IOException;
import java.util.ResourceBundle.Control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Client extends Application {
	public static Scene scene;
	public static Stage copia_stage;
	@Override
	public void start(Stage primaryStage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("client_Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		copia_stage = primaryStage;
		scene = new Scene(root);
		
//		primaryStage.initStyle(StageStyle.UNDECORATED);
//		primaryStage.initStyle(StageStyle.UTILITY);
//		primaryStage.resizableProperty().setValue(Boolean.FALSE);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	public Stage getCopia_stage() {
		return copia_stage;
	}

	public void setCopia_stage(Stage copia_stage) {
		this.copia_stage = copia_stage;
	}

	public static void main(String[] args) {
		//Controller_Client t = new Controller_Client();
		//Thread_Client receiver = new Thread_Client(s);
		try {
			Sql sql = new Sql();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void Close(){
		
	}

}
