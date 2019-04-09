package ch.lohmes.Views;

import java.net.URL;
import java.util.ResourceBundle;

import ch.lohmes.model.APICaller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane displayPanel;

    @FXML
    private TextField fromTxtField;

    @FXML
    private TextField toTxtField;

    @FXML
    private Button goBtn;

    private APICaller apiCaller;
    
    @FXML
    void searchConnections(ActionEvent event) {
    	String zugInfo = apiCaller.getConnections(fromTxtField.getText(), toTxtField.getText());
    	System.out.println(zugInfo);
    }

    @FXML
    void initialize() {
    	apiCaller = new APICaller();
    	
        assert displayPanel != null : "fx:id=\"displayPanel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert fromTxtField != null : "fx:id=\"fromTxtField\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert toTxtField != null : "fx:id=\"toTxtField\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert goBtn != null : "fx:id=\"goBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";

    }
}
