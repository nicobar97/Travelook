package io.travelook.view;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.text.DateFormatter;

import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private int count;
    private Viaggio viaggio;
    private SimpleDateFormat formatter;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Annuncio");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	public HomeAnnuncio(Viaggio viaggio) {
		this.viaggio = viaggio;
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
            formatter = new SimpleDateFormat("dd-mm-yyyy");
            titolo = (Text) scene.lookup("#titolo");
            titolo.setText(viaggio.getTitolo());
            destinazione = (Text) scene.lookup("#destinazione");
            destinazione.setText(viaggio.getDestinazione());
            lingua = (Text) scene.lookup("#lingua");
            lingua.setText(viaggio.getLingua());
            dataInizio = (Text) scene.lookup("#datainizio");
            dataInizio.setText(formatter.format(viaggio.getDatainizio()));
            dataFine = (Text) scene.lookup("#datafine");
            dataFine.setText(formatter.format(viaggio.getDatafine()));
            luogoPartenza = (Text) scene.lookup("#luogopartenza");
            luogoPartenza.setText(viaggio.getLuogopartenza());
            budget = (Text) scene.lookup("#budget");
            budget.setText(budgetFormat(viaggio.getBudget()));
            backButton = (Button) scene.lookup("#back");
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
