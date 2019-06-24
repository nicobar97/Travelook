package io.travelook.view;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.travelook.broker.ClientProxy;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.autenticazione.RegistrazioneController;
import io.travelook.controller.filtro.FiltraViaggioBudget;
import io.travelook.controller.filtro.FiltraViaggioData;
import io.travelook.controller.filtro.FiltraViaggioDestinazione;
import io.travelook.controller.filtro.FiltraViaggioLingua;
import io.travelook.controller.filtro.Filtro;
import io.travelook.controller.utente.UtenteController;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.utils.SHA256;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

public class HomeListaAnnunci extends Application {
	private Stage primaryStage;
    private FlowPane rootLayout;
    private ListView<Viaggio> listView;
    private ImageView logo;
    private Button logout;
    private Button filtra;
    private Button rem;
    private Text currentUser;
    private ImageView userImg;
    private Button refresh;
    private Button creaAnnuncio;
    private Utente user;
    private Dialog<List<Viaggio>> dialogFilter;
    private UtenteController controlleru;
    private List<Viaggio> lista;
    private List<Viaggio> listaTutti;
	public HomeListaAnnunci(String username) {
		controlleru = new UtenteController();
		int iduser = controlleru.getIdUtenteFromUsername(username);
        this.user = controlleru.getUtenteById(iduser);
        controlleru.setU(user);
	}
	public HomeListaAnnunci(Utente user) {
        this.user = user;
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
	
	@SuppressWarnings("unchecked")
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeListaAnnunci.class.getResource("HomeListaAnnunci.fxml"));
            rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            listView = (ListView<Viaggio>) scene.lookup("#lista");
            userImg = (ImageView) scene.lookup("#imgUtente");
            logo = (ImageView) scene.lookup("#logoi");
            logo.setImage(new Image("http://travelook.altervista.org/logo.png"));
            logout = (Button) scene.lookup("#logout");
            filtra = (Button) scene.lookup("#filtra");
            rem = (Button) scene.lookup("#rem");
            currentUser = (Text) scene.lookup("#currentUser");
            if(user.getImmagineProfilo() != null && !user.getImmagineProfilo().trim().equals(""))
        		userImg.setImage(new Image(user.getImmagineProfilo().trim()));
            currentUser.setText("Current user: " + user.getUsername());
            currentUser.setOnMouseClicked(event -> {
            	//open profilo utente
            	//new Alert(AlertType.INFORMATION, "todo").show();
            	new HomeUtente(user).start(primaryStage);
            });
            userImg.setOnMouseClicked(event -> {
            	//open profilo utente
            	//new Alert(AlertType.INFORMATION, "todo").show();
            	new HomeUtente(user).start(primaryStage);
            });
            logout.setOnMouseClicked(event -> {
            	new HomeTravelook().start(primaryStage);
            	new Alert(AlertType.INFORMATION, "Logout effettuato con successo").show();
            });
            ListaAnnunciController controller = new ListaAnnunciController();
            lista = new ArrayList<>();
            listaTutti = controller.getAnnunci();
            for(Viaggio v: listaTutti)
            	if(v.getStato().equals(Stato.INIZIO))
            		lista.add(v);
            ObservableList<Viaggio> items = FXCollections.observableArrayList(lista);
            if(lista != null || lista.size() > 0)
            	listView.setItems(items);
            listView.setCellFactory(userCell -> new ViaggioCell());
            listView.setOnMouseClicked(event -> { 
            	MouseEvent me = (MouseEvent) event;
            	if(me.getClickCount() == 2)
					try {
						new HomeAnnuncio(listView.getSelectionModel().getSelectedItem(), user, "lista").start(primaryStage);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            });
            initFilterDialog();
            filtra.setOnMouseClicked(event -> {
            	Optional<List<Viaggio>> fil = dialogFilter.showAndWait();
            	if(fil.isPresent() && fil.get().size() > 0) {
            		ObservableList<Viaggio> refresh = FXCollections.observableArrayList(fil.get());
                    listView.setItems(refresh);
            	}
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
            rem.setOnMouseClicked(event -> {
            	ObservableList<Viaggio> refresh = FXCollections.observableArrayList(lista);
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
	private void initFilterDialog() {
		dialogFilter = new Dialog<List<Viaggio>>();
    	dialogFilter.setTitle("Filtra");
    	dialogFilter.setHeaderText("Scegli un filtro:");
    	dialogFilter.setResizable(false);
    	RadioButton f1 = new RadioButton("Lingua:");
    	RadioButton f2 = new RadioButton("Data:");
    	RadioButton f3 = new RadioButton("Budget:");
    	RadioButton f4 = new RadioButton("Destinazione:");
    	TextField tf1 = new TextField();
    	TextField tf2 = new TextField();
    	TextField tf3 = new TextField();
    	TextField tf4 = new TextField();
    	ToggleGroup tg = new ToggleGroup();
    	f1.setToggleGroup(tg);
    	f2.setToggleGroup(tg);
    	f3.setToggleGroup(tg);
    	f4.setToggleGroup(tg);
    	tf1.setDisable(true);
		tf2.setDisable(true);
		tf3.setDisable(true);
		tf4.setDisable(true);
    	f1.setOnMouseClicked(event -> {
    		tf1.setDisable(false);
    		tf2.setDisable(true);
    		tf3.setDisable(true);
    		tf4.setDisable(true);
    	});
    	f2.setOnMouseClicked(event -> {
    		tf1.setDisable(true);
    		tf2.setDisable(false);
    		tf3.setDisable(true);
    		tf4.setDisable(true);
    	});
    	f3.setOnMouseClicked(event -> {
    		tf1.setDisable(true);
    		tf2.setDisable(true);
    		tf3.setDisable(false);
    		tf4.setDisable(true);
    	});
    	f4.setOnMouseClicked(event -> {
    		tf1.setDisable(true);
    		tf2.setDisable(true);
    		tf3.setDisable(true);
    		tf4.setDisable(false);
    	});
    	GridPane grid = new GridPane();
    	grid.add(f1, 1, 1);
    	grid.add(f2, 1, 2);
    	grid.add(f3, 1, 3);
    	grid.add(f4, 1, 4);
    	grid.add(tf1, 2, 1);
    	grid.add(tf2, 2, 2);
    	grid.add(tf3, 2, 3);
    	grid.add(tf4, 2, 4);
    	dialogFilter.getDialogPane().setContent(grid);
    	ButtonType buttonTypeOk = new ButtonType("Filtra", ButtonData.OK_DONE);
    	dialogFilter.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	
    	dialogFilter.setResultConverter(new Callback<ButtonType, List<Viaggio>>() {
    	    @Override
    	    public List<Viaggio> call(ButtonType b) {
    	    	Filtro filtro = null;
    	    	Object[] args = new Object[1];
    	    	if(f1.isSelected()) {
    	    		filtro = new FiltraViaggioLingua();
    	    		args[0] = tf1.getText();
    	    	}
    	    	else if(f2.isSelected()) {
    	    		filtro = new FiltraViaggioData();
    	    		args[0] = tf2.getText();
    	    	}
    	    	else if(f3.isSelected()) {
    	    		filtro = new FiltraViaggioBudget();
    	    		args[0] = tf3.getText().length();
    	    	}
    	    	else if(f4.isSelected()) {
    	    		filtro = new FiltraViaggioDestinazione();
    	    		args[0] = tf4.getText();
    	    	}
    	    	return deschif(filtro.filtra(args, schif(lista)));
    	    }	
    	});
	}
	private List<Object> schif(List<Viaggio> list) {
		List<Object> res = new ArrayList<>();
		for(Viaggio v : list)
			res.add(v);
		return res;
	}
	public List<Viaggio> deschif(List<Object> oggetti){
		List<Viaggio> res= new ArrayList<Viaggio>();
		if(oggetti.size() > 0)
			for(Object o :oggetti) {
				if(o instanceof Viaggio) {
					Viaggio v=(Viaggio)o;
					res.add(v);
				}
			}
		return res;
	}
}

