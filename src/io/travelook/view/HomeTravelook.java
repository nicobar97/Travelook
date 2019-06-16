package io.travelook.view;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.autenticazione.LoginController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.utils.BCrypt;
import io.travelook.utils.SHA256;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HomeTravelook extends Application {
	private Stage primaryStage;
    private AnchorPane rootLayout;
    private ListView<Viaggio> listView;
    private Button login;
    private Button register;
    private Dialog<String> dialog;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Travelook");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeTravelook.class.getResource("HomeTravelook.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            initLoginDialog();
           
            login = (Button) scene.lookup("#login");
            register = (Button) scene.lookup("#register");
            
            login.setOnMouseClicked(event -> {
            	Optional<String> username = dialog.showAndWait();
            	if(username.isPresent()) {
            		new HomeListaAnnunci(username.get()).start(primaryStage);
            	}
            	else {
            		new Alert(AlertType.ERROR, "Username o password errati").showAndWait();
            	}
            });

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	private void initLoginDialog() {
		dialog = new Dialog<String>();
    	dialog.setTitle("Login");
    	dialog.setHeaderText("Inserisci le credenziali per autenticarti:\nNon rivelare la password a nessuno.");
    	dialog.setResizable(false);
    	 
    	Label label1 = new Label("Username: ");
    	Label label2 = new Label("Password: ");
    	TextField usernameField = new TextField();
    	TextField pswField = new TextField();
    	         
    	GridPane grid = new GridPane();
    	grid.add(label1, 1, 1);
    	grid.add(usernameField, 2, 1);
    	grid.add(label2, 1, 2);
    	grid.add(pswField, 2, 2);
    	dialog.getDialogPane().setContent(grid);
    	         
    	ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	 
    	dialog.setResultConverter(new Callback<ButtonType, String>() {
    	    @Override
    	    public String call(ButtonType b) {
    	    	if(new LoginController().verificaCredenziali(usernameField.getText(), SHA256.encrypt(pswField.getText())))
    	    		return usernameField.getText();
    	    	else
    	    		return null;
    	    }
    	});
	}
}

