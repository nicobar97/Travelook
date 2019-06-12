package io.travelook.view;

import java.io.IOException;

import io.travelook.controller.UtenteController;
import io.travelook.model.Utente;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ListaTest extends Application {
	private Stage primaryStage;
    private FlowPane rootLayout;
    private ListView<Utente> listView;
    private Button button;
    private int count;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Annuncio");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout() {
        try {
        	count = 0;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ListaTest.class.getResource("ListaTest.fxml"));
            rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            listView = null;
            listView = (ListView<Utente>) scene.lookup("#lista");
            UtenteController controller = new UtenteController();
            ObservableList<Utente> items = FXCollections.observableArrayList(controller.getListaUtenti());
            listView.setItems(items);
            listView.setCellFactory(userCell -> new TestCell());
            /*button = (Button) scene.lookup("#button1");
            button.setOnAction(event -> {
            	
            });*/
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public Stage getPrimaryStage() {
        return primaryStage;
    }
}

