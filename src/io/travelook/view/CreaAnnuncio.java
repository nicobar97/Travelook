package io.travelook.view;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.swing.text.DateFormatter;

import io.travelook.controller.ListaAnnunciController;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        this.primaryStage.setTitle("Modifica Annuncio");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	public CreaAnnuncio(Viaggio viaggio, int type, Utente user) {
		this.viaggio = viaggio;
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
            titolo.setText(viaggio.getTitolo().trim());
            destinazione = (TextField) scene.lookup("#destinazione");
            destinazione.setText(viaggio.getDestinazione().trim());
            lingua = (TextField) scene.lookup("#lingua");
            lingua.setText(viaggio.getLingua().trim());
            dataInizio = (TextField) scene.lookup("#datainizio");
            dataFine = (TextField) scene.lookup("#datafine");
            if(type == 0) {
            	dataInizio.setText(formatter.format(viaggio.getDatainizio()));
            	dataFine.setText(formatter.format(viaggio.getDatafine()));
            }
            luogoPartenza = (TextField) scene.lookup("#luogopartenza");
            luogoPartenza.setText(viaggio.getLuogopartenza().trim());
            budget = (TextField) scene.lookup("#budget");
            if(type == 1)
            	budget.setText("$..$");
            else
            	budget.setText(budgetFormat(viaggio.getBudget()));
            backButton = (Button) scene.lookup("#back");
            descrizione = (TextArea) scene.lookup("#descrizione");
            descrizione.setText(viaggio.getDescrizione());
            immagine = (ImageView) scene.lookup("#immagine");
            if(type == 0)
            	if(viaggio.getImmaginiProfilo() != null && new File("src/"+viaggio.getImmaginiProfilo().trim()).exists())
            		immagine.setImage(new Image(viaggio.getImmaginiProfilo()));
            saveButton = (Button) scene.lookup("#salva");
            annullaButton = (Button) scene.lookup("#annulla");
            backButton.setOnMouseClicked(event -> {
            		if(type==0)
            			new HomeAnnuncio(viaggio, user).start(primaryStage);
            		else
            			new HomeListaAnnunci().start(primaryStage);
            });
            controller = new ListaAnnunciController();
            Viaggio nv = new Viaggio();
            saveButton.setOnMouseClicked(event -> {
            	nv.setTitolo(titolo.getText());
				nv.setDestinazione(destinazione.getText());
				nv.setDescrizione(descrizione.getText());
				nv.setBudget(budget.getText().length());
				nv.setLuogopartenza(luogoPartenza.getText());
				nv.setLingua(lingua.getText());
				//nv.setImmaginiProfilo(immagine.getImage().toString() == null ? "" : immagine.getImage().toString());
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
				if(type == 0)
					nv.setCreatore(viaggio.getCreatore());
				else 
					nv.setCreatore(user);
				controller.creaAnnuncio(nv);
				if(type==0)
        			new HomeAnnuncio(nv, user).start(primaryStage);
        		else
        			new HomeListaAnnunci().start(primaryStage);
            });
            annullaButton.setOnMouseClicked(event -> {
            	if(type==0)
        			new HomeAnnuncio(nv, user).start(primaryStage);
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
