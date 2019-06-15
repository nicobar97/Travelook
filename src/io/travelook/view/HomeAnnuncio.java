package io.travelook.view;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.text.DateFormatter;

import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeAnnuncio extends Application {
	private Stage primaryStage;
    private FlowPane rootLayout;
    private Text destinazione;
    private Text lingua;
    private Text dataInizio;
    private Text dataFine;
    private Text luogoPartenza;
    private Text budget;
    private Text titolo;
    private ListView chatView;
    private TextField newMessage;
    private Button backButton;
    private Button sendButton;
    private Button modificaAnnuncio;
    private TextArea descrizione;
    private int count;
    private Viaggio viaggio;
    private SimpleDateFormat formatter;
    private Utente user;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Annuncio");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	public HomeAnnuncio(Viaggio viaggio, Utente user) {
		this.viaggio = viaggio;
		this.user= user;
	}
	public void initRootLayout() {
        try {
        	count = 0;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeAnnuncio.class.getResource("HomeAnnuncio.fxml"));
            rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            formatter = new SimpleDateFormat("yyyy-mm-dd");
            titolo = (Text) scene.lookup("#titolo");
            titolo.setText(viaggio.getTitolo().trim());
            destinazione = (Text) scene.lookup("#destinazione");
            destinazione.setText(viaggio.getDestinazione().trim());
            lingua = (Text) scene.lookup("#lingua");
            lingua.setText(viaggio.getLingua().trim());
            dataInizio = (Text) scene.lookup("#datainizio");
            dataInizio.setText(formatter.format(viaggio.getDatainizio()));
            dataFine = (Text) scene.lookup("#datafine");
            dataFine.setText(formatter.format(viaggio.getDatafine()));
            luogoPartenza = (Text) scene.lookup("#luogopartenza");
            luogoPartenza.setText(viaggio.getLuogopartenza().trim());
            budget = (Text) scene.lookup("#budget");
            budget.setText(budgetFormat(viaggio.getBudget()));
            descrizione = (TextArea) scene.lookup("#descrizione");
            descrizione.setText(viaggio.getDescrizione().trim());
            backButton = (Button) scene.lookup("#back");
            modificaAnnuncio = (Button) scene.lookup("#modifica");
            modificaAnnuncio.setOnMouseClicked(event -> {
        			new CreaAnnuncio(viaggio, 0, user).start(primaryStage);
            });
            backButton.setOnMouseClicked(event -> {
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
