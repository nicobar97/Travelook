package io.travelook.view;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.swing.text.DateFormatter;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreaAnnuncio extends Application {
	private Stage primaryStage;
    private FlowPane rootLayout;
    private TextField destinazione;
    private TextField lingua;
    private TextField dataInizio;
    private TextField dataFine;
    private Label windowtitle;
    private TextField luogoPartenza;
    private TextField budget;
    private TextField titolo;
    private Button backButton;
    private Button sendButton;
    private Button annullaButton;
    private Button saveButton;
    private TextArea descrizione;
    private Viaggio viaggio;
    private SimpleDateFormat formatter;
    private ImageView immagine;
    private ListaAnnunciController controller;
    private int type;
    private Utente user;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        if(type == 0)
        	this.primaryStage.setTitle("Modifica Annuncio");
        else
        	this.primaryStage.setTitle("Crea Annuncio");
        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	public CreaAnnuncio(Viaggio viaggio, int type, Utente user) {
		if(viaggio != null)
			this.viaggio = viaggio;
		else
			this.viaggio = new Viaggio();
		this.type = type;
		this.user = user;
	}
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CreaAnnuncio.class.getResource("CreaAnnuncio.fxml"));
            
            rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            formatter = new SimpleDateFormat("yyyy-mm-dd");
            titolo = (TextField) scene.lookup("#titolo");          
            windowtitle = (Label) scene.lookup("#windowtitle");         
            destinazione = (TextField) scene.lookup("#destinazione");
            lingua = (TextField) scene.lookup("#lingua");
            dataInizio = (TextField) scene.lookup("#datainizio");
            dataFine = (TextField) scene.lookup("#datafine");
            luogoPartenza = (TextField) scene.lookup("#luogopartenza");
            budget = (TextField) scene.lookup("#budget");
            backButton = (Button) scene.lookup("#back");
            descrizione = (TextArea) scene.lookup("#descrizione");
            immagine = (ImageView) scene.lookup("#immagine");	
            saveButton = (Button) scene.lookup("#salva");
            annullaButton = (Button) scene.lookup("#annulla");
            if(type == 0) {
            	titolo.setText(viaggio.getTitolo().trim());
            	destinazione.setText(viaggio.getDestinazione().trim());
            	dataInizio.setText(formatter.format(viaggio.getDatainizio()));
            	dataFine.setText(formatter.format(viaggio.getDatafine()));
            	luogoPartenza.setText(viaggio.getLuogopartenza().trim());
            	budget.setText(budgetFormat(viaggio.getBudget()));
            	descrizione.setText(viaggio.getDescrizione().trim());
                lingua.setText(viaggio.getLingua().trim());
            	if(viaggio.getImmaginiProfilo() != null && new File("src/"+viaggio.getImmaginiProfilo().trim()).exists())
            		immagine.setImage(new Image(viaggio.getImmaginiProfilo().trim()));
            }
            if(type == 1) {
            	windowtitle.setText("Travelook - Crea Annuncio");
            	budget.setText("$..$");
            	titolo.setText("");
            	destinazione.setText("");
            	dataInizio.setText("");
            	dataFine.setText("");
            	luogoPartenza.setText("");
            	budget.setText("");
            	descrizione.setText("");
                lingua.setText("");
            }   
            controller = new ListaAnnunciController();
            Viaggio nv = new Viaggio();
            backButton.setOnMouseClicked(event -> {
            		if(type==0)
            			new HomeAnnuncio(viaggio, user).start(primaryStage);
            		else
            			new HomeListaAnnunci().start(primaryStage);
            });
           
            saveButton.setOnMouseClicked(event -> {
            	nv.setTitolo(titolo.getText());
				nv.setDestinazione(destinazione.getText());
				nv.setDescrizione(descrizione.getText());
				nv.setBudget(budget.getText().length());
				nv.setLuogopartenza(luogoPartenza.getText());
				nv.setLingua(lingua.getText());
				nv.setImmaginiProfilo(immagine.getImage().toString() == null ? "" : immagine.getImage().toString().trim());
				try {
					nv.setDatainizio(new Date(formatter.parse(dataInizio.getText()).getTime()));
					nv.setDatafine(new Date(formatter.parse(dataFine.getText()).getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(type == 0) {
					controller.eliminaAnnuncio(viaggio.getIdViaggio());
				}
				nv.setIdViaggio(viaggio.getIdViaggio());
				if(type == 0) {
					nv.setCreatore(viaggio.getCreatore());
					AnnuncioController ac = new AnnuncioController();
					ac.modificaAnnuncio(nv);
				}
				else {
					nv.setCreatore(user);
					controller.creaAnnuncio(nv);
				}
				
				if(type==0)
        			new HomeAnnuncio(nv, user).start(primaryStage);
        		else
        			new HomeListaAnnunci().start(primaryStage);
            });
            annullaButton.setOnMouseClicked(event -> {
            	if(type==0)
        			new HomeAnnuncio(viaggio, user).start(primaryStage);
        		else
        			new HomeListaAnnunci().start(primaryStage);
            });
            backButton.setOnMouseClicked(event -> {
            	if(type==0)
        			new HomeAnnuncio(viaggio, user).start(primaryStage);
        		else
        			new HomeListaAnnunci().start(primaryStage);
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	private String budgetFormat(int budget) {
		if(budget == 1)
			return "$";
		if(budget == 2)
			return "$$";
		if(budget == 3)
			return "$$$";
		if(budget == 4)
			return "$$$$";
		if(budget == 5)
			return "$$$$";
		return "";
	}
}
