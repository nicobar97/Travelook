package io.travelook.view.copy;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.sql.Date;

import javax.swing.text.DateFormatter;

import org.apache.commons.io.FileUtils;

import io.travelook.broker.ClientProxy;
import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.controller.annuncio.ListaAnnunciController;
import io.travelook.controller.autenticazione.RegistrazioneController;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.utils.SHA256;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreaAnnuncio extends Application {
	private ClientProxy c;
	
	private Stage primaryStage;
    private FlowPane rootLayout;
    private TextField destinazione;
    private TextField lingua;
    private DatePicker dataInizio;
    private DatePicker dataFine;
    private Label windowtitle;
    private TextField luogoPartenza;
    private TextField budget;
    private TextField titolo;
    private Button backButton;
    private Button sendButton;
    private Button annullaButton;
    private Button saveButton;
    private TextArea descrizione;
    private Viaggio viaggio;
    private SimpleDateFormat formatter;
    private ImageView immagine;
    private ListaAnnunciController controller;
    private FileChooser imgChooser;
    private File newImg = null;
    private Button load;
    private int type;
    private Utente user;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        if(type == 0)
        	this.primaryStage.setTitle("Modifica Annuncio");
        else
        	this.primaryStage.setTitle("Crea Annuncio");
        try {
			c = new ClientProxy();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	public CreaAnnuncio(Viaggio viaggio, int type, Utente user) { // TYPE = 0 ? MODIFICA ANNUNCIO
		if(viaggio != null)
			this.viaggio = viaggio;
		else
			this.viaggio = new Viaggio();
		this.type = type;
		this.user = user;
	}
	@SuppressWarnings("deprecation")
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CreaAnnuncio.class.getResource("CreaAnnuncio.fxml"));
            
            rootLayout = (FlowPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            formatter = new SimpleDateFormat("yyyy-mm-dd");
            titolo = (TextField) scene.lookup("#titolo");          
            windowtitle = (Label) scene.lookup("#windowtitle");         
            destinazione = (TextField) scene.lookup("#destinazione");
            lingua = (TextField) scene.lookup("#lingua");
            dataInizio = (DatePicker) scene.lookup("#dateInizio");
            dataFine = (DatePicker) scene.lookup("#dateFine");
            luogoPartenza = (TextField) scene.lookup("#luogopartenza");
            budget = (TextField) scene.lookup("#budget");
            backButton = (Button) scene.lookup("#back");
            descrizione = (TextArea) scene.lookup("#descrizione");
            immagine = (ImageView) scene.lookup("#immagine");	
            saveButton = (Button) scene.lookup("#salva");
            annullaButton = (Button) scene.lookup("#annulla");
            load = (Button) scene.lookup("#load");
            imgChooser = new FileChooser();
            newImg = null;
            Viaggio nv = new Viaggio();
            load.setText("L\nO\nA\nD");
            load.setTextAlignment(TextAlignment.CENTER);
            if(type == 0) {
            	titolo.setText(viaggio.getTitolo().trim());
            	destinazione.setText(viaggio.getDestinazione().trim());
            	dataInizio.setValue(viaggio.getDatainizio().toLocalDate());
            	dataFine.setValue(viaggio.getDatafine().toLocalDate());
            	luogoPartenza.setText(viaggio.getLuogopartenza().trim());
            	budget.setText(budgetFormat(viaggio.getBudget()));
            	descrizione.setText(viaggio.getDescrizione().trim());
                lingua.setText(viaggio.getLingua().trim());
            	if(viaggio.getImmaginiProfilo() != null && !viaggio.getImmaginiProfilo().trim().equals("") && new File("src/"+viaggio.getImmaginiProfilo().trim()).exists())
            		immagine.setImage(new Image(viaggio.getImmaginiProfilo().trim()));
            }
            if(type == 1) {
            	windowtitle.setText("Travelook - Crea Annuncio");
            	budget.setText("$..$");
            	titolo.setText("");
            	destinazione.setText("");
            	luogoPartenza.setText("");
            	budget.setText("");
            	descrizione.setText("");
                lingua.setText("");
            }   
            controller = new ListaAnnunciController();
            load.setOnMouseClicked(event -> {
            	newImg = imgChooser.showOpenDialog(primaryStage);
            	//copyImage(newImg);
            	if(newImg != null)
            	//immagine.setImage(new Image("viaggio"+viaggio.getIdViaggio()+newImg.getName().substring(newImg.getName().length()-4, newImg.getName().length())));
            		immagine.setImage(new Image(newImg.getName()));
            });
            backButton.setOnMouseClicked(event -> {
            		if(type==0)
						try {
							new HomeAnnuncio(viaggio, user, "lista").start(primaryStage);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else
						try {
							new HomeListaAnnunci(user).start(primaryStage);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            });
           
            saveButton.setOnMouseClicked(event -> {
            	nv.setTitolo(titolo.getText());
				nv.setDestinazione(destinazione.getText());
				nv.setDescrizione(descrizione.getText());
				nv.setBudget(budget.getText().length());
				nv.setLuogopartenza(luogoPartenza.getText());
				nv.setLingua(lingua.getText());
				nv.setStato(viaggio.getStato());
				if(newImg != null) {
					nv.setImmaginiProfilo(newImg.getName());
					//nv.setImmaginiProfilo("viaggio"+viaggio.getIdViaggio()+newImg.getName().substring(newImg.getName().length()-4, newImg.getName().length()));
				}
				else
					nv.setImmaginiProfilo(immagine.getImage() == null ? "" : immagine.getImage().toString().trim());

				LocalDate tmp = dataInizio.getValue();
				
				nv.setDatainizio(Date.valueOf(tmp));
				tmp = dataFine.getValue();
				nv.setDatafine(Date.valueOf(tmp));
				nv.setIdViaggio(viaggio.getIdViaggio());
				if(type == 0) {
					nv.setCreatore(viaggio.getCreatore());
					AnnuncioController ac = new AnnuncioController(viaggio);
					ac.modificaAnnuncio(nv);
				}
				else {
					nv.setStato(Stato.INIZIO);
					nv.setCreatore(user);
					try {
						new ClientProxy().creaAnnuncio(nv);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(type==0)
					try {
						new HomeAnnuncio(nv, user, "lista").start(primaryStage);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else
					try {
						new HomeListaAnnunci(user).start(primaryStage);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            });
            annullaButton.setOnMouseClicked(event -> {
            	if(type==0)
					try {
						new HomeAnnuncio(viaggio, user, "lista").start(primaryStage);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else
					try {
						new HomeListaAnnunci(user).start(primaryStage);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            });
            backButton.setOnMouseClicked(event -> {
            	if(type==0)
					try {
						new HomeAnnuncio(viaggio, user, "lista").start(primaryStage);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				else
					try {
						new HomeListaAnnunci(user).start(primaryStage);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	private String budgetFormat(int budget) {
		if(budget == 1)
			return "$";
		if(budget == 2)
			return "$$";
		if(budget == 3)
			return "$$$";
		if(budget == 4)
			return "$$$$";
		if(budget == 5)
			return "$$$$";
		return "";
	}
	private void copyImage(File newImg) {
		try {
    		if(new File("src/"+newImg.getName()).exists()) {
    			FileUtils.copyFile(newImg, new File("src/viaggio"+viaggio.getIdViaggio()+newImg.getName().substring(newImg.getName().length()-4, newImg.getName().length())));
    		}
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
