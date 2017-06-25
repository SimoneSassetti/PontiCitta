package it.polito.tdp.radio;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.radio.bean.Citta;
import it.polito.tdp.radio.bean.Model;
import it.polito.tdp.radio.bean.Ponte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class RadioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Citta> boxCItta1;

    @FXML
    private ComboBox<Citta> boxCitta2;

    @FXML
    private ComboBox<Citta> boxCitta3;

    @FXML
    private Button btnCercaPonti;

    @FXML
    private Button btnCoperturaOttima;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCercaPonti(ActionEvent event) {
    	Citta c1=boxCItta1.getValue();
    	Citta c2=boxCitta2.getValue();
    	if(c1==null || c2==null){
    		txtResult.appendText("Selezionare due città per la ricerca.\n");
    		return;
    	}
    	if(c1.equals(c2)){
    		txtResult.appendText("Selezionare due città diverse.\n");
    		return;
    	}
    	
    	List<Ponte> temp= model.cercaPonti(c1,c2);
    	if(temp.isEmpty()){
    		txtResult.appendText("Non sono stati trovati ponti in comune.\n");
    	}else{
    		txtResult.appendText("Elenco dei ponti in comune:\n");
    		for(Ponte p: temp){
    			txtResult.appendText(p.toString()+"\n");
    		}
    	}
    	
    	List<Citta> listaCittaPulita=model.getCitta();
    	listaCittaPulita.remove(c1);
    	listaCittaPulita.remove(c2);
    	boxCitta3.getItems().addAll(listaCittaPulita);
    }

    @FXML
    void doCoperturaOttima(ActionEvent event) {
    	Citta c1=boxCItta1.getValue();
    	Citta c2=boxCitta2.getValue();
    	Citta c3=boxCitta3.getValue();
    	if(c1==null || c2==null || c3==null){
    		txtResult.appendText("Selezionare due città per la ricerca.\n");
    		return;
    	}
    	
    	List<Ponte> temp=model.trovaCopertura(c1,c2,c3);
    	txtResult.appendText("Ponte migliore per "+c1.toString()+" - "+c2.toString()+" - "+c3.toString()+"\n");
    	txtResult.appendText(temp.toString());
    }

    @FXML
    void initialize() {
        assert boxCItta1 != null : "fx:id=\"boxCItta1\" was not injected: check your FXML file 'Radio.fxml'.";
        assert boxCitta2 != null : "fx:id=\"boxCitta2\" was not injected: check your FXML file 'Radio.fxml'.";
        assert boxCitta3 != null : "fx:id=\"boxCitta3\" was not injected: check your FXML file 'Radio.fxml'.";
        assert btnCercaPonti != null : "fx:id=\"btnCercaPonti\" was not injected: check your FXML file 'Radio.fxml'.";
        assert btnCoperturaOttima != null : "fx:id=\"btnCoperturaOttima\" was not injected: check your FXML file 'Radio.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Radio.fxml'.";

    }
    Model model;
	public void setModel(Model model) {
		this.model=model;
		
		boxCItta1.getItems().addAll(model.getCitta());
		boxCitta2.getItems().addAll(model.getCitta());
	}
}

