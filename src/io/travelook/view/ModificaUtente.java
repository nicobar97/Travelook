package io.travelook.view;		
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.rdp.RichiesteObservableController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModificaUtente extends Application {
	private Stage primaryStage;
	@FXML
    private FlowPane rootLayout;
    @FXML
    private Button back;
    @FXML
    private Button salva;
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
    private Button addImage;
    @FXML
    private Text username;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private Text email;
    @FXML
    private TextArea bio;
    @FXML
    private Text interessi;
    @FXML
    private Button annulla;
    @FXML
    private Button addInteresse;
    @FXML
    private ImageView userImage;
    @FXML
    private ListView<Interessi> interessiView;
    private FileChooser imgChooser;
    private File newImg = null;
    private Utente user;
    private UtenteController uc;
    private FXMLLoader loader;
    private double average;
	public ModificaUtente(Utente user, double average, List<Interessi> interessi) {
        this.user = user;
        this.average = average;
        uc = new UtenteController(user);
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
            loader = new FXMLLoader(getClass().getResource("ModificaUtente.fxml"));
            loader.setController(this);
            loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            back.setOnMouseClicked(event -> {
            	new HomeUtente(user).start(primaryStage);
            });
            annulla.setOnMouseClicked(event -> {
            	new ModificaUtente(user, average, user.getInteressi()).start(primaryStage);
            });
            imgChooser = new FileChooser();
            newImg = null;
            bio.setEditable(true);
            username.setText("@"+user.getUsername());
            nome.setText(user.getNome());
            cognome.setText(user.getCognome());
            email.setText(user.getEmail());
            if(user.getImmagineProfilo() != null && !user.getImmagineProfilo().trim().equals("") && new File("src/"+user.getImmagineProfilo().trim()).exists())
        		userImage.setImage(new Image(user.getImmagineProfilo().trim()));
            if(user.getInteressi() != null && user.getInteressi().size() > 0)
            	interessi.setText(formatInteressi(user.getInteressi()));
            else 
            	interessi.setText("L'utente non ha interessi");
            bio.setText(user.getBio());
            if(user.getImmagineProfilo() != null && !user.getImmagineProfilo().trim().equals("") && new File("src/"+user.getImmagineProfilo().trim()).exists())
        		userImage.setImage(new Image(user.getImmagineProfilo().trim()));
            initStars();
            salva.setOnMouseClicked(event -> {
            	
            });
            addImage.setOnMouseClicked(event -> {
            	newImg = imgChooser.showOpenDialog(primaryStage);
            	if(newImg != null)
            		userImage.setImage(new Image(newImg.getName()));
            });
            addInteresse.setOnMouseClicked(event -> {
            	Interessi selected = interessiView.getSelectionModel().getSelectedItem();
            	if(selected == null) {
            		new Alert(AlertType.WARNING, "Nessun interesse selezionato").show();
            	}
            	else {
            		uc.aggiungiInteressi(selected);
            		user.getInteressi().add(selected);
            		initInteressi();
            	}
            });
            salva.setOnMouseClicked(event -> {
            	user.setNome(nome.getText());
            	user.setCognome(cognome.getText());
            	user.setBio(bio.getText());
            	if(newImg != null)
            		user.setImmagineProfilo(newImg.getName());
            	uc.modificaProfilo(user);
            	new HomeUtente(user).start(primaryStage);
            });
            initInteressi();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private void initInteressi() {
		List<Interessi> chenonha = new ArrayList<>();
		boolean first = true;
		for(Interessi i : Interessi.values()) {
			if(!first) {
				boolean trovato = false;
				for(Interessi u : user.getInteressi())
					if(u.compareTo(i) == 0)
						trovato = true;
				if(!trovato)
					chenonha.add(i);
			}
			first = false;
		}
        ObservableList<Interessi> obsv = FXCollections.observableArrayList(chenonha);
        interessiView.setItems(obsv);
	}
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	private void initStars() {
		if(average >= 0.5 && average < 1.5) {
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
}

