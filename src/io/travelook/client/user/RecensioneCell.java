package io.travelook.client.user;

import java.io.IOException;
import io.travelook.model.Recensione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

public class RecensioneCell extends ListCell<Recensione> {
	@FXML
    private Text body;
	@FXML
    private Text title;
	@FXML
    private FlowPane flowPane;
	@FXML
    private SVGPath star1;
    @FXML
    private SVGPath star2;
    @FXML
    private SVGPath star3;
    @FXML
    private SVGPath star4;
    @FXML
    private SVGPath star5;
	private int voto;

    private FXMLLoader mLLoader;
protected void updateItem(Recensione r, boolean empty) {
    super.updateItem(r, empty);
    
    if (empty || r == null) {
        setText(null);
        setGraphic(null);
        System.out.println("ENHHDDNAK");
    } else {
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("RecensioneCell.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("title");
                alert.setContentText("error");
            }
        }
        System.out.println("DENTRO VERO");
        body.setText(r.getCorpo());
        title.setText(r.getTitolo());
        voto = r.getVoto();
        initStars();
        setGraphic(flowPane);
    }
}
private void initStars() {
	if(voto == 1) {
		 star1.setFill(Paint.valueOf("orange"));
	}
	else if(voto == 2) {
		 star1.setFill(Paint.valueOf("orange"));
		 star2.setFill(Paint.valueOf("orange"));
	}
	else if(voto == 3) {
		 star1.setFill(Paint.valueOf("orange"));
		 star2.setFill(Paint.valueOf("orange"));
		 star3.setFill(Paint.valueOf("orange"));
	}
	else if(voto == 4) {
		 star1.setFill(Paint.valueOf("orange"));
		 star2.setFill(Paint.valueOf("orange"));
		 star3.setFill(Paint.valueOf("orange"));
		 star4.setFill(Paint.valueOf("orange"));
	}
	else if(voto == 5) {
		 star1.setFill(Paint.valueOf("orange"));
		 star2.setFill(Paint.valueOf("orange"));
		 star3.setFill(Paint.valueOf("orange"));
		 star4.setFill(Paint.valueOf("orange"));
		 star5.setFill(Paint.valueOf("orange"));
	}
}

}
