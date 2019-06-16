package io.travelook.view;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.DateFormatter;

import io.travelook.controller.chat.ChatController;
import io.travelook.model.Chat;
import io.travelook.model.Messaggio;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.shape.Rectangle;
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
    private ListView<Messaggio> chatView;
    private TextField newMessage;
    private ImageView immagine;
    private Button backButton;
    private Button sendButton;
    private Button modificaAnnuncio;
    private Text descrizione;
    private ChatController chatCont;
    private Viaggio viaggio;
    private SimpleDateFormat formatter;
    private Rectangle chatShape;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeAnnuncio.class.getResource("HomeAnnuncio.fxml"));
            rootLayout = (FlowPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            formatter = new SimpleDateFormat("yyyy-mm-dd");
            chatView = (ListView) scene.lookup("#chatView");
            newMessage = (TextField) scene.lookup("#messageBox");
            sendButton = (Button) scene.lookup("#send");
            
            titolo = (Text) scene.lookup("#titolo");
            immagine = (ImageView) scene.lookup("#immagine");
            chatShape = (Rectangle) scene.lookup("#chatShape");
           	if(viaggio.getImmaginiProfilo() != null && !viaggio.getImmaginiProfilo().trim().equals("") && new File("src/"+viaggio.getImmaginiProfilo().trim()).exists())
            	immagine.setImage(new Image(viaggio.getImmaginiProfilo().trim()));
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
            descrizione = (Text) scene.lookup("#descrizione");
            descrizione.setText(viaggio.getDescrizione().trim());
            backButton = (Button) scene.lookup("#back");
            
            backButton.setOnMouseClicked(event -> {
        		new HomeListaAnnunci(user).start(primaryStage);
            });
            if(user.getId() == viaggio.getCreatore().getId() && false) {
            	//CREATORE
                modificaAnnuncio = (Button) scene.lookup("#modifica");
                modificaAnnuncio.setOnMouseClicked(event -> {
            			new CreaAnnuncio(viaggio, 0, user).start(primaryStage);
                });
            }
            else {
            	List<Utente> listUserViaggio = new ArrayList<Utente>();
            	boolean trovato = false;/*
            	for(Utente u : listUserViaggio)
            		if(u.getId() == user.getId())
            			trovato = true;*/
            	if(trovato) {
            		//PARTECIPANTE
            		//CHAT
                    chatCont = new ChatController();
                    refreshChat();
                    sendButton.setOnMouseClicked(event -> {
                    	Messaggio m = new Messaggio();
                    	m.setMessaggio(newMessage.getText());
                    	m.setTimestamp(new Timestamp(System.currentTimeMillis()));
                    	m.setUtente(user);
                    	chatCont.inviaMessaggio(m, viaggio);
                    	refreshChat();
                    	newMessage.setText("");
                    });
                    newMessage.setOnKeyReleased(event -> {
                    	if(newMessage.getText().trim().equals(""))
                    		sendButton.setDisable(true);
                    	else
                    		sendButton.setDisable(false);
                    });
                    sendButton.setDisable(true);
            	}
            	else {
            		//GUEST
            		chatView.setVisible(false);
            		sendButton.setVisible(false);
            		newMessage.setVisible(false);
            	}
            }
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private void refreshChat() {
		Chat chat = chatCont.getChat(viaggio);
        ObservableList<Messaggio> messaggi = FXCollections.observableArrayList(chat.getChat());
        if(!chat.getChat().isEmpty() && messaggi != null) {
        	chatView.setItems(messaggi);
        	chatView.setCellFactory(userCell -> new ChatCell(user));
        	chatView.scrollTo(chat.getChat().size());
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
