package io.travelook.client.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import io.travelook.broker.ClientProxy;
import io.travelook.model.Utente;
import io.travelook.utils.SHA256;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HomeTravelook extends Application {
	private ClientProxy c;
	private Stage primaryStage;
    private AnchorPane rootLayout;
    private String newimg;
    private Button login;
    private Button register;
    private Dialog<String> dialog;
    private Dialog<String> dialogRegister;
    private ImageView logo;
    private String nametmp;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Travelook");
        try {
			c = new ClientProxy();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			c = new ClientProxy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
		
	}
	
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
            initRegisterDialog();
            nametmp = null;
            login = (Button) scene.lookup("#login");
            register = (Button) scene.lookup("#register");
            logo = (ImageView) scene.lookup("#logo");
            logo.setImage(new Image("http://travelook.altervista.org/logo.png"));
            login.setOnMouseClicked(event -> {
            	Optional<String> username = dialog.showAndWait();
            	if(username.isPresent()) {
            		try {
						new HomeListaAnnunci(username.get(), c).start(primaryStage);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	else {
            		new Alert(AlertType.ERROR, "Username o password errati").showAndWait();
            	}
            });
            register.setOnMouseClicked(event -> {
            	Optional<String> username = dialogRegister.showAndWait();
            	if(username.isPresent()) {
            		new Alert(AlertType.INFORMATION, "Registrazione avvenuta con successo.").show();
            	}
            	else {
            		new Alert(AlertType.ERROR, "Registrazione fallita, ricontrolla i campi inseriti.").show();
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
    	PasswordField pswField = new PasswordField();
    	GridPane grid = new GridPane();
    	grid.add(label1, 1, 1);
    	grid.add(usernameField, 2, 1);
    	grid.add(label2, 1, 2);
    	grid.add(pswField, 2, 2);
    	dialog.getDialogPane().setContent(grid);
    	         
    	ButtonType buttonTypeOk = new ButtonType("Login", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	 
    	dialog.setResultConverter(new Callback<ButtonType, String>() {
    	    @Override
    	    public String call(ButtonType b) {
    	    	if(usernameField.getText() != null && !usernameField.getText().trim().contentEquals("") && 
    	    			pswField.getText() != null && !pswField.getText().trim().contentEquals("")) {
    	    		try {
						if(c.verificaCredenziali(usernameField.getText(), SHA256.encrypt(pswField.getText())))
							return usernameField.getText();
						else
							return null;
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	    	}
    	    	else return null;
				return null;
    	    }
    	});
	}
	private void initRegisterDialog() {
		dialogRegister = new Dialog<String>();
    	dialogRegister.setTitle("Registrazione");
    	dialogRegister.setHeaderText("Username e password minimo 8 caratteri.\nNon riveleremo i tuoi dati a nessuno.");
    	dialogRegister.setResizable(false);
    	 
    	Label label1 = new Label("Username: ");
    	Label label2 = new Label("Password: ");
    	Label label21 = new Label("Conferma psw: ");
    	Label label3 = new Label("Email: ");
    	Label label4 = new Label("Nome: ");
    	Label label5 = new Label("Cognome: ");
    	Label label6 = new Label("Data di nascita: ");
    	Label label7 = new Label("Immagine Profilo: ");
    	TextField usernameField = new TextField();
    	PasswordField pswField = new PasswordField();
    	PasswordField pswField2 = new PasswordField();
    	TextField emailField = new TextField();
    	TextField nomeField = new TextField();
    	TextField cognomeField = new TextField();
    	DatePicker ddnPicker = new DatePicker();
    	FileChooser imgField = new FileChooser();
    	Button openChooser = new Button("Carica..");
    	GridPane grid = new GridPane();
    	grid.add(label1, 1, 1);
    	grid.add(label2, 1, 2);
    	grid.add(label21, 1, 3);
    	grid.add(label3, 1, 4);
    	grid.add(label4, 1, 5);
    	grid.add(label5, 1, 6);
    	grid.add(label6, 1, 7);
    	grid.add(label7, 1, 8);
    	grid.add(usernameField, 2, 1);
    	grid.add(pswField, 2, 2);
    	grid.add(pswField2, 2, 3);
    	grid.add(emailField, 2, 4);
    	grid.add(nomeField, 2, 5);
    	grid.add(cognomeField, 2, 6);
    	grid.add(ddnPicker, 2, 7);
    	grid.add(openChooser, 2, 8);
    	imgField.setSelectedExtensionFilter(new ExtensionFilter(".png", ".jpg"));
    	newimg = null;
    	openChooser.setOnMouseClicked(event -> {
    		newimg = uploadFile(imgField.showOpenDialog(primaryStage), "User");
    		label7.setText(label7.getText() + "\n(" + nametmp + ")");
    		label7.setStyle("-fx-font-size: 8pt;");
    	});
    	dialogRegister.getDialogPane().setContent(grid);
    	         
    	ButtonType buttonTypeOk = new ButtonType("Registrati", ButtonData.OK_DONE);
    	dialogRegister.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	
    	dialogRegister.setResultConverter(new Callback<ButtonType, String>() {
    	    @Override
    	    public String call(ButtonType b) {
    	    	if(	usernameField.getText().trim().equals("") || usernameField.getText().length() < 6 ||
    	    		pswField.getText().trim().equals("") || pswField.getText().length() < 8 ||	
    	    		nomeField.getText().trim().equals("") || cognomeField.getText().trim().equals("") ||
    	    		emailField.getText().indexOf("@") == -1 || emailField.getText().indexOf(".") == -1 ||
    	    		ddnPicker.getValue() == null || newimg == null || !pswField.getText().equals(pswField2.getText())) 
    	    		return null;
    	    	else {
    	    		LocalDate tmp = ddnPicker.getValue();
    	    	
					try {
						c.registraUtente(new Utente(usernameField.getText(), emailField.getText(), nomeField.getText(), 
								cognomeField.getText(),Date.valueOf(tmp), newimg.trim()), SHA256.encrypt(pswField.getText()));
					} catch (ClassNotFoundException | IllegalArgumentException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(pswField.getText());			
    	    		return usernameField.getText();
    	    	}
    	    }	
    	});
	}
	private String uploadFile(File toUpload, String folder) {
		FTPClient client = new FTPClient();
		String addr = "travelook.altervista.org";
		int id = -1;
	    try {
	      client.connect("ftp."+addr);
	      client.login("travelook", "mBb6686QbHxk");
	      client.enterLocalPassiveMode();
	      client.setFileType(FTP.BINARY_FILE_TYPE);
	      client.listFiles("Images/" + folder);
	      id = client.listFiles("Images/" + folder).length;
	      client.storeFile("Images/" + folder + "/" + folder + id + 
	      toUpload.getName().substring(toUpload.getName().length()-4, toUpload.getName().length()), new FileInputStream(toUpload.getAbsoluteFile()));
	    } catch(IOException ioe) {
	      ioe.printStackTrace();
	      System.out.println( "Error communicating with FTP server." );
	    } finally {
	      try {
	        client.disconnect( );
	      } catch (IOException e) {
	        System.out.println( "Problem disconnecting from FTP server" );
	      }
	    }
	    nametmp = toUpload.getName();
		return "http://" + addr + "/Images/" + folder + "/" + folder + id + 
		toUpload.getName().substring(toUpload.getName().length()-4, toUpload.getName().length());
	}
}

