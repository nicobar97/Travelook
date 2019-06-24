package io.travelook.client.user;		
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.travelook.broker.ClientProxy;
import io.travelook.model.Interessi;
import io.travelook.model.Recensione;
import io.travelook.model.RichiestaDiPartecipazione;
import io.travelook.model.Storico;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeUtente extends Application {
	private Stage primaryStage;
	@FXML
    private FlowPane rootLayout;
    @FXML
    private ListView<Viaggio> listViaggio;
    @FXML
    private ListView<Viaggio> listStorico;
    @FXML
    private ListView<Recensione> listRecensioni;
    @FXML
    private ListView<RichiestaDiPartecipazione> listRichieste;
    @FXML
    private Button back;
    @FXML
    private ImageView logoi;
    @FXML
    private Button modifica;
    @FXML
    private Text username;
    @FXML
    private Text nomeCognome;
    @FXML
    private Text email;
    @FXML
    private TextArea bio;
    @FXML
    private Text interessi;
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
    @FXML
    private Button showStorico;
    @FXML
    private Button showRichieste;
    @FXML
    private Button showViaggi;
    @FXML
    private Button showRecensioni;
    @FXML
    private ImageView userImage;
    private Utente user;
    private List<Recensione> listaRecensioni;
    private List<Viaggio> listaViaggio;
    private Storico storico;
    private Double average;
    private List<RichiestaDiPartecipazione> listaRichieste;
    private FXMLLoader loader;
    private ClientProxy c;
	public HomeUtente(ClientProxy c, Utente user) {
        this.c=c;
        try {
			this.user = new ClientProxy().attachInteressiToUser(user);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(user.getInteressi() == null) {
        	user.setInteressi(new ArrayList<>());
        }
	}
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Travelook");

        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout() {
        try {
            loader = new FXMLLoader(getClass().getResource("HomeUtente.fxml"));
            loader.setController(this);
            loader.load();
            average = new Double(0);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            back.setOnMouseClicked(event -> {
            	try {
					new HomeListaAnnunci(user, c).start(primaryStage);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            });
            bio.setEditable(false);
            username.setText("@"+user.getUsername());
            nomeCognome.setText(user.getNome() + " " + user.getCognome());
            email.setText(user.getEmail());
            logoi.setImage(new Image("http://travelook.altervista.org/logo.png"));
            if(user.getInteressi() != null)
            	interessi.setText(formatInteressi(user.getInteressi()));
            bio.setText(user.getBio());
            if(user.getImmagineProfilo() != null && !user.getImmagineProfilo().trim().equals(""))
        		userImage.setImage(new Image(user.getImmagineProfilo().trim()));
            refreshStorico();
            refreshViaggiAttivi();
            refreshRichieste();
            refreshRecensioni();
            initStars();
            listRichieste.setVisible(false);
        	listViaggio.setVisible(false);
        	listRecensioni.setVisible(true);
        	listStorico.setVisible(false);
            showViaggi.setOnMouseClicked(event -> {
            	listRichieste.setVisible(false);
            	listViaggio.setVisible(true);
            	listRecensioni.setVisible(false);
            	listStorico.setVisible(false);
            });
            showRichieste.setOnMouseClicked(event -> {
            	listRichieste.setVisible(true);
            	listViaggio.setVisible(false);
            	listRecensioni.setVisible(false);
            	listStorico.setVisible(false);
            });
            showStorico.setOnMouseClicked(event -> {
            	listRichieste.setVisible(false);
            	listViaggio.setVisible(false);
            	listRecensioni.setVisible(false);
            	listStorico.setVisible(true);
            });
            showRecensioni.setOnMouseClicked(event -> {
            	listRichieste.setVisible(false);
            	listViaggio.setVisible(false);
            	listRecensioni.setVisible(true);
            	listStorico.setVisible(false);
            });
            listViaggio.setOnMouseClicked(event ->{
            	MouseEvent me = (MouseEvent) event;
            	if(me.getClickCount() == 2) {
            		Viaggio open = null;
					try {
						open = c.getViaggioById(listViaggio.getSelectionModel().getSelectedItem().getIdViaggio());
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            		try {
						new HomeAnnuncio(c, open, user, "utente").start(primaryStage);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            });
            modifica.setOnMouseClicked(event -> {
            	new ModificaUtente(c, user, average, user.getInteressi()).start(primaryStage);
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	private void initStars() {
		int sum = 0;
		for(Recensione r : listaRecensioni) {
			sum += r.getVoto();
		}
		if(listaRecensioni.size() > 0)
			average = (double) (sum/listaRecensioni.size());
		
		//average = 4.6;
		if(average < 0.5) {
			
		}
		else if(average >= 0.5 && average < 1.5) {
			 star1.setFill(Paint.valueOf("orange"));
		}
		else if(average >= 1.5 && average < 2.5) {
			 star1.setFill(Paint.valueOf("orange"));
			 star2.setFill(Paint.valueOf("orange"));
		}
		else if(average >= 2.5 && average < 3.5) {
			 star1.setFill(Paint.valueOf("orange"));
			 star2.setFill(Paint.valueOf("orange"));
			 star3.setFill(Paint.valueOf("orange"));
		}
		else if(average >= 3.5 && average < 4.5) {
			 star1.setFill(Paint.valueOf("orange"));
			 star2.setFill(Paint.valueOf("orange"));
			 star3.setFill(Paint.valueOf("orange"));
			 star4.setFill(Paint.valueOf("orange"));
		}
		else if(average >= 4.5) {
			 star1.setFill(Paint.valueOf("orange"));
			 star2.setFill(Paint.valueOf("orange"));
			 star3.setFill(Paint.valueOf("orange"));
			 star4.setFill(Paint.valueOf("orange"));
			 star5.setFill(Paint.valueOf("orange"));
		}
	}

	private String formatInteressi(List<Interessi> inte) { 
		String out = "";
		boolean nl = false;
		for(Interessi i : inte) {
			out += i.name();
			if(nl) {
				out += "\n";
				nl=false;
			}
			else {
				out += " ";
				nl=true;
			}
		}
		return out;
	}
	private void refreshViaggiAttivi() {
		try {
			listaViaggio = c.getViaggiInPartecipazione(user);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ObservableList<Viaggio> obsv = FXCollections.observableArrayList(listaViaggio);
        if(!listaViaggio.isEmpty() && listaViaggio != null) {
        	listViaggio.setItems(obsv);
        	listViaggio.setCellFactory(userCell -> new ViaggioCellUtente());
        }
	}
	private void refreshStorico() {
		try {
			//c.setUtente(user);
			storico = c.visualizzaStorico(user);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ObservableList<Viaggio> obsv = FXCollections.observableArrayList(storico.getStorico());
        if(!storico.getStorico().isEmpty() && storico.getStorico() != null) {
        	listStorico.setItems(obsv);
        	listStorico.setCellFactory(userCell -> new ViaggioCellUtente());
        }
	}
	private void refreshRecensioni() {
		try {
			//c.setUtente(user);
			listaRecensioni = c.visualizzaRecensioni(user);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ObservableList<Recensione> obsv = FXCollections.observableArrayList(listaRecensioni);
        if(!listaRecensioni.isEmpty() && listaRecensioni != null) {
        	listRecensioni.setItems(obsv);
        	listRecensioni.setCellFactory(recensioneCell -> new RecensioneCell());
        }
	}
	private void refreshRichieste() {
        try {
			listaRichieste = c.getRichiesteForCreatore(user);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ObservableList<RichiestaDiPartecipazione> obsrdp = FXCollections.observableArrayList(listaRichieste);
        if(!listaRichieste.isEmpty() && listaRichieste != null) {
        	listRichieste.setItems(obsrdp);
        	listRichieste.setCellFactory(userCell -> new RDPCellUtente(c, listRichieste));
        }
	}
}

