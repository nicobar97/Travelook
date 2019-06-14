package io.travelook.view;

import java.io.IOException;

import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class HomeAnnuncio extends Application {
	private Stage primaryStage;
    private FlowPane rootLayout;
    private TextField textField;
    private Button backButton;
    private int count;
    private Viaggio viaggio;
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
            loader.setLocation(HomeAnnuncio.class.getResource("HomeAnnuncio.fxml"));
            rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
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
}
