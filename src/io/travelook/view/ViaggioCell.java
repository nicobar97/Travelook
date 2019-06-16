package io.travelook.view;

import java.io.File;
import java.io.IOException;

import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ViaggioCell extends ListCell<Viaggio> {
	@FXML
    private Label lblTitle;
	@FXML
    private Label lblOverview;
	@FXML
    private GridPane gridPane;
	@FXML
	private ImageView poster;

    private FXMLLoader mLLoader;
protected void updateItem(Viaggio v, boolean empty) {
    super.updateItem(v, empty);
    
    if (empty || v == null) {
        setText(null);
        setGraphic(null);
    } else {
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("ViaggioCell.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("title");
                alert.setContentText("error");
            }
        }
        String logo = v.getImmaginiProfilo();
        lblTitle.setText(v.getTitolo());
        
        lblTitle.setStyle("-fx-font: 20 arial;");
        lblOverview.setText("Destinazione: " + v.getDestinazione() + "\tLingua: " + v.getLingua() + 
        					"\n" + v.getDescrizione());
        if(logo != null && !logo.trim().equals("") && new File("src/"+logo.trim()).exists())
        		poster.setImage(new Image(logo.trim()));
        setText(null);
        setGraphic(gridPane);
    }
}
}
