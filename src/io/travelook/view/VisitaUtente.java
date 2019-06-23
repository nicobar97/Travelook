package io.travelook.view;		
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.rdp.RichiesteObservableController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VisitaUtente extends Application {
	private Stage primaryStage;
	@FXML
    private FlowPane rootLayout;
    @FXML
    private ListView<Recensione> listRecensioni;
    @FXML
    private Button back;
    @FXML
    private TextArea recArea;
    @FXML
    private Button segnala;
    @FXML
    private Button cancButton;
    @FXML
    private Button recButton;
    @FXML
    private Text username;
    @FXML
    private Text nomeCognome;
    @FXML
    private Text email;
    @FXML
    private TextArea bio;
    @FXML
    private Text interessi;
    @FXML
    private SVGPath star1;
    @FXML
    private SVGPath star2;
    @FXML
    private SVGPath star3;
    @FXML
    private SVGPath star4;
    @FXML
    private SVGPath star5;
    @FXML
    private Button showRecensioni;
    @FXML
    private ImageView userImage;
    @FXML
    private ImageView logoi;
    private Utente user;
    private Utente userOspite;
    private boolean toggleRec;
    private Viaggio viaggio;
    private int votoRec;
    private UtenteController uc;
    private List<Recensione> listaRecensioni;
    private Double average;
    private FXMLLoader loader;
	public VisitaUtente(Utente user, Utente userOspite, Viaggio viaggio) {
        uc = new UtenteController(user);
        this.user = uc.attachInteressiToUser(user);
        this.userOspite = userOspite;
        this.viaggio = viaggio;
        if(user.getInteressi() == null) {
        	user.setInteressi(new ArrayList<>());
        }
	}
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Travelook");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout() {
        try {
            loader = new FXMLLoader(getClass().getResource("VisitaUtente.fxml"));
            loader.setController(this);
            loader.load();
            toggleRec = false;
            average = new Double(0);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            back.setOnMouseClicked(event -> {
            	try {
					new HomeAnnuncio(viaggio, userOspite, "lista").start(primaryStage);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            });
            bio.setEditable(false);
            logoi.setImage(new Image("http://travelook.altervista.org/logo.png"));
            username.setText("@"+user.getUsername());
            nomeCognome.setText(user.getNome() + " " + user.getCognome());
            email.setText(user.getEmail());  
            if(user.getInteressi() != null)
            	interessi.setText(formatInteressi(user.getInteressi()));
            bio.setText(user.getBio());
            if(user.getImmagineProfilo() != null && !user.getImmagineProfilo().trim().equals("") && new File("src/"+user.getImmagineProfilo().trim()).exists())
        		userImage.setImage(new Image(user.getImmagineProfilo().trim()));
            refreshRecensioni();
            recArea.setOnMouseClicked(event -> {
            	toggleRec = true;
            	star1.setFill(Paint.valueOf("orange"));
				star2.setFill(Paint.valueOf("transparent"));
				star3.setFill(Paint.valueOf("transparent"));
				star4.setFill(Paint.valueOf("transparent"));
				star5.setFill(Paint.valueOf("transparent"));
				votoRec = 1;
            });
            cancButton.setOnMouseClicked(EventHandler -> {
            	toggleRec = false;
            	initStars();
            	votoRec = 0;
            	recArea.setText("");
            });
            initStars();
            star1.setOnMouseClicked(event -> {
            	if(toggleRec) {
            		star1.setFill(Paint.valueOf("orange"));
            		star2.setFill(Paint.valueOf("transparent"));
    				star3.setFill(Paint.valueOf("transparent"));
    				star4.setFill(Paint.valueOf("transparent"));
    				star5.setFill(Paint.valueOf("transparent"));
            		votoRec = 1;
            	}
            });
			star2.setOnMouseClicked(event -> {
				if(toggleRec) {
					 star1.setFill(Paint.valueOf("orange"));
					 star2.setFill(Paint.valueOf("orange"));
					 star3.setFill(Paint.valueOf("transparent"));
				     star4.setFill(Paint.valueOf("transparent"));
					 star5.setFill(Paint.valueOf("transparent"));
					 votoRec = 2;
            	}   	
			});
			star3.setOnMouseClicked(event -> {
				if(toggleRec) {
					 star1.setFill(Paint.valueOf("orange"));
					 star2.setFill(Paint.valueOf("orange"));
					 star3.setFill(Paint.valueOf("orange"));
				   	 star4.setFill(Paint.valueOf("transparent"));
					 star5.setFill(Paint.valueOf("transparent"));
					 votoRec = 3;
            	}
			});
			star4.setOnMouseClicked(event -> {
				if(toggleRec) {
					 star1.setFill(Paint.valueOf("orange"));
					 star2.setFill(Paint.valueOf("orange"));
					 star3.setFill(Paint.valueOf("orange"));
					 star4.setFill(Paint.valueOf("orange"));
					 star5.setFill(Paint.valueOf("transparent"));
					 votoRec = 4;
            	}
			});
			star5.setOnMouseClicked(event -> {
				if(toggleRec) {
					 star1.setFill(Paint.valueOf("orange"));
					 star2.setFill(Paint.valueOf("orange"));
					 star3.setFill(Paint.valueOf("orange"));
					 star4.setFill(Paint.valueOf("orange"));
					 star5.setFill(Paint.valueOf("orange"));
					 votoRec = 5;
            	}
			});

            listRecensioni.setVisible(true); 
            segnala.setOnMouseClicked(event -> {
            	//TODO
            	new Alert(AlertType.INFORMATION, "TODO").show();
            });
            recButton.setOnMouseClicked(event -> {
            	if(recArea.getText().length() < 50)
            		new Alert(AlertType.ERROR, "Devi inserire almeno 50 caratteri per recensire un utente.").show();
            	else {
            		Optional<String> mexRec = new TextInputDialog("Titolo Recensione").showAndWait();
    	        	if(mexRec.isPresent()) {
    	        		uc.lasciaRecensione(new Recensione(user.getId(), votoRec, mexRec.get(), recArea.getText() + " - @" + userOspite.getUsername(), userOspite.getId()));
    	        		new Alert(AlertType.INFORMATION, "Utente " + user.getUsername() + " recensito!").show();
    	        	}
    	        	else {
    	        		new Alert(AlertType.ERROR, "Devi inserire un titolo.").show();
//    	        		toggleRec = false;
//    	            	initStars();
//    	            	votoRec = 0;
//    	            	recArea.setText("");
    	        	}
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
	private void initStars() {
		int sum = 0;
		for(Recensione r : listaRecensioni) {
			sum += r.getVoto();
		}
		if(listaRecensioni.size() > 0)
			average = (double) (sum/listaRecensioni.size());
		
		//average = 4.6;
		if(average < 0.5) {
			
		}
		else if(average >= 0.5 && average < 1.5) {
			 star1.setFill(Paint.valueOf("orange"));
		}
		else if(average >= 1.5 && average < 2.5) {
			 star1.setFill(Paint.valueOf("orange"));
			 star2.setFill(Paint.valueOf("orange"));
		}
		else if(average >= 2.5 && average < 3.5) {
			 star1.setFill(Paint.valueOf("orange"));
			 star2.setFill(Paint.valueOf("orange"));
			 star3.setFill(Paint.valueOf("orange"));
		}
		else if(average >= 3.5 && average < 4.5) {
			 star1.setFill(Paint.valueOf("orange"));
			 star2.setFill(Paint.valueOf("orange"));
			 star3.setFill(Paint.valueOf("orange"));
			 star4.setFill(Paint.valueOf("orange"));
		}
		else if(average >= 4.5) {
			 star1.setFill(Paint.valueOf("orange"));
			 star2.setFill(Paint.valueOf("orange"));
			 star3.setFill(Paint.valueOf("orange"));
			 star4.setFill(Paint.valueOf("orange"));
			 star5.setFill(Paint.valueOf("orange"));
		}
	}

	private String formatInteressi(List<Interessi> inte) { 
		String out = "";
		boolean nl = false;
		for(Interessi i : inte) {
			out += i.name();
			if(nl) {
				out += "\n";
				nl=false;
			}
			else {
				out += " ";
				nl=true;
			}
		}
		return out;
	}
	private void refreshRecensioni() {
		listaRecensioni = uc.visualizzaRecensioni();
        ObservableList<Recensione> obsv = FXCollections.observableArrayList(listaRecensioni);
        if(!listaRecensioni.isEmpty() && listaRecensioni != null) {
        	listRecensioni.setItems(obsv);
        	listRecensioni.setCellFactory(userCell -> new RecensioneCell());
        }
	}
}

