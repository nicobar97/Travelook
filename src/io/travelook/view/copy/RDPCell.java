package io.travelook.view.copy;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import io.travelook.controller.rdp.RichiesteObservableController;
import io.travelook.model.Messaggio;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class RDPCell extends ListCell<RichiestaDiPartecipazione> {
	@FXML
    private Text user;
	@FXML
    private Text body;
	@FXML
    private Text stato;
	@FXML
    private FlowPane flowPane;
	@FXML
	private Button accept;
	@FXML
	private Button reject;
	private Utente u;
    private FXMLLoader mLLoader;
    private RichiesteObservableController rdpc;
    private ListView<RichiestaDiPartecipazione> rdpview;
    public RDPCell(RichiesteObservableController rdpc, ListView<RichiestaDiPartecipazione> rdpview) {
    	this.rdpc = rdpc;
    	this.rdpview = rdpview;
    }
	protected void updateItem(RichiestaDiPartecipazione rdp, boolean empty) {
	    super.updateItem(rdp, empty);
	    if (empty || rdp == null) {
	        setText(null);
	        setGraphic(null);
	    } else {
	        if (mLLoader == null) {
	            mLLoader = new FXMLLoader(getClass().getResource("RDPCell.fxml"));
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
	        
	        user.setText(rdp.getUtente().getUsername().trim());
	        body.setText(rdp.getMessaggioRichiesta().trim());
	        stato.setText(rdp.getStato().name());
	        if(rdp.getStato().compareTo(Stato.NONVISTA) != 0) {
	        	accept.setVisible(false);
	        	reject.setVisible(false);
	        }
	        
	        accept.setOnMouseClicked(event -> {
	        	Optional<String> mexRisp = new TextInputDialog("Inserisci il messaggio di risposta:").showAndWait();
	        	if(mexRisp.isPresent()) {
	        		rdp.setRisposta(Stato.ACCETTATA, mexRisp.get());
	        		System.out.println(rdpc.rispondiRichiesta(rdp));
	        		refreshRdp(rdp);
	        	}
	        });
	        
	        reject.setOnMouseClicked(event -> {
	        	Optional<String> mexRisp = new TextInputDialog("Inserisci il messaggio di risposta:").showAndWait();
	        	if(mexRisp.isPresent()) {
	        		rdp.setRisposta(Stato.NONACCETTATA, mexRisp.get());
	        		rdpc.rispondiRichiesta(rdp);
	        		refreshRdp(rdp);
	        	}
	        });
	        setText(null);
	        setGraphic(flowPane);
	    }
	}
	private void refreshRdp(RichiestaDiPartecipazione rdp) {
		List<RichiestaDiPartecipazione> rdpList = rdpc.getRichiesteForCreatoreViaggio(rdp.getViaggio().getCreatore(), rdp.getViaggio() );
        ObservableList<RichiestaDiPartecipazione> obsrdp = FXCollections.observableArrayList(rdpList);
        if(!rdpList.isEmpty() && rdpList != null) {
        	rdpview.setItems(obsrdp);
        }
	}
}
