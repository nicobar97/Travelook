package io.travelook.view;

import java.io.IOException;

import io.travelook.model.Messaggio;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ChatCell extends ListCell<Messaggio> {
	@FXML
    private Text user;
	@FXML
    private Text body;
	@FXML
    private Text timestamp;
	@FXML
    private FlowPane flowPane;
	private Utente u;
    private FXMLLoader mLLoader;
public ChatCell(Utente u) {
		this.u = u;
	}
protected void updateItem(Messaggio mex, boolean empty) {
    super.updateItem(mex, empty);
    if (empty || mex == null) {
        setText(null);
        setGraphic(null);
    } else {
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("ChatCell.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("title");
                alert.setContentText("error");
                alert.show();
            }
        }
        
        user.setText(mex.getUtente().getUsername().trim());
        body.setText(mex.getMessaggio().trim());
        timestamp.setText(mex.getTimeStr().trim());
        if(mex.getUtente().getId() == u.getId()) {
        	user.setTextAlignment(TextAlignment.RIGHT);
        	body.setTextAlignment(TextAlignment.RIGHT);
        	timestamp.setTextAlignment(TextAlignment.RIGHT);
        }
        else {
        	user.setTextAlignment(TextAlignment.LEFT);
        	body.setTextAlignment(TextAlignment.LEFT);
        	timestamp.setTextAlignment(TextAlignment.LEFT);
        }
        setText(null);
        setGraphic(flowPane);
    }
}
}
