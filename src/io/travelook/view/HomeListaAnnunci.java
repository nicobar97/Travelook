package io.travelook.view;

import java.io.IOException;

import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class HomeListaAnnunci extends Application {
	private Stage primaryStage;
    private FlowPane rootLayout;
    private ListView<Viaggio> listView;
    private ImageView logo;
    private Button refresh;
    private Button creaAnnuncio;
    private Utente user;
    private int count;
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
        	count = 0;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeListaAnnunci.class.getResource("HomeListaAnnunci.fxml"));
            rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            UtenteController controlleru = new UtenteController();
            this.user = controlleru.getUtenteById(1);
            listView = (ListView<Viaggio>) scene.lookup("#lista");

            ListaAnnunciController controller = new ListaAnnunciController();
            ObservableList<Viaggio> items = FXCollections.observableArrayList(controller.getAnnunci());
            listView.setItems(items);
            listView.setCellFactory(userCell -> new ViaggioCell());
            listView.setOnMouseClicked(event -> { 
            	MouseEvent me = (MouseEvent) event;
            	if(me.getClickCount() == 2)
            		new HomeAnnuncio(listView.getSelectionModel().getSelectedItem(), user).start(primaryStage);
            });
            creaAnnuncio = (Button) scene.lookup("#crea");
            creaAnnuncio.setOnAction(event -> {
            	new CreaAnnuncio(null, 1, user).start(primaryStage);
            });
            refresh = (Button) scene.lookup("#refresh");
            refresh.setOnAction(event -> {
            	ObservableList<Viaggio> refresh = FXCollections.observableArrayList(controller.getAnnunci());
                listView.setItems(refresh);
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public Stage getPrimaryStage() {
        return primaryStage;
    }
}

