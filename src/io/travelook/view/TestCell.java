package io.travelook.view;

import java.io.IOException;

import io.travelook.model.Utente;
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

public class TestCell extends ListCell<Utente> {
	@FXML
    private Label lblTitle;
	@FXML
    private Label lblOverview;
	@FXML
    private GridPane gridPane;
	@FXML
	private ImageView poster;

    private FXMLLoader mLLoader;
protected void updateItem(Utente user, boolean empty) {
    super.updateItem(user, empty);
    if (empty || user == null) {
        setText(null);
        setGraphic(null);
    } else {
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("TestCell.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("title");
                alert.setContentText("error");
            }
        }
        lblTitle.setText(user.getNome() + "" + user.getCognome());
        
        lblTitle.setStyle("-fx-font: 20 arial;");
        lblOverview.setText("nickname: " + user.getUsername() + " + email: " + user.getEmail());
        poster.setImage(new Image("test.png"));
        setText(null);
        setGraphic(gridPane);
    }
}
}
