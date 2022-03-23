package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Dictionary model;
	private List<String> inputTextList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> txtCombo;

    @FXML
    private Label txtError;

    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Label txtSeconds;


    @FXML
    void doClearText(ActionEvent event) {
    	
    	txtOutput.clear();
    	txtInput.clear();
    	txtError.setText("Number of errors:");
    	txtSeconds.setText("Spell check status:");

    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	txtOutput.clear();
    	inputTextList = new LinkedList<String>();
    	
    	if (txtCombo.getValue()==null) {
    		txtOutput.setText("Seleziona una lingua !!");
    		return;
    	}
    	
    	if (!model.loadDictionary(txtCombo.getValue())) {
    		txtInput.setText("Errore nel caricamento del dizionario");
    		return;
    	}
    	
    	String inputText = txtInput.getText().toLowerCase();
    	
    	if(inputText.isEmpty()) {
    	txtInput.setText("Inserire un testo da correggere!");
    	return;	
    	}
    	
    	inputText = inputText.replaceAll("\n", " ");
    	//inputText = inputText.replaceAll(“[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]", "");
    	
    	StringTokenizer st = new StringTokenizer(inputText, " ");
    	
    	while(st.hasMoreTokens()) {
    		inputTextList.add(st.nextToken());
    	}
    	
    	List <RichWord> outputTextList = model.spellCheckText(inputTextList);
    	
    	int numErrori =0;
    	StringBuilder richText = new StringBuilder();
    	
    	for (RichWord r: outputTextList) {
    		if(!r.isCorrect()) {
    			numErrori++;
    			richText.append(r.getWord()+"\n");
    		}
    	}
    	
    	txtOutput.setText(richText.toString());
    	txtError.setText("The text contains" +numErrori+" errors");
    		
    	

    }

    @FXML
    void initialize() {
        assert txtCombo != null : "fx:id=\"txtCombo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtError != null : "fx:id=\"txtError\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSeconds != null : "fx:id=\"txtSeconds\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   
    public void setModel(Dictionary model) {
    	
    	txtInput.setText("Selezionare una lingua");
    	
    	txtCombo.getItems().addAll("English","Italian");

    	this.model=model;
    	
    	
    }

}

