package ch.lohmes.Views;

import java.io.IOException;

import ch.lohmes.model.APICaller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainWindowController {
	
    @FXML
    private AnchorPane displayPanel;
    
    @FXML
    private ListView<String> trainList;

    @FXML
    private TextField fromTxtField;

    @FXML
    private TextField toTxtField;

    @FXML
    private Button goBtn;

    private APICaller apiCaller;
    
    @FXML
    void searchConnections(ActionEvent event) throws IOException {
    	var zugInfo = apiCaller.getConnections(fromTxtField.getText(), toTxtField.getText());
    	
    	if(zugInfo.size() == 0) {
    		Alert alert = new Alert(AlertType.ERROR, "The locations you entered were not found.", ButtonType.OK);
    		alert.showAndWait();
    	} else {
    		ObservableList<String> observableTrains = FXCollections.observableArrayList(zugInfo);
    	
	    	for(var train : zugInfo) {
	    		System.out.println(train);
	    	}
	    	
	    	trainList.setItems(observableTrains);
    	}
    }

    @FXML
    void initialize() {
    	apiCaller = new APICaller();
    	
    	trainList.setCellFactory(cell -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                        setFont(Font.font(16));
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: red;"
                        		+ "-fx-font-weight: bold;");
                    }
                }
            };
        });
    	
        assert displayPanel != null : "fx:id=\"displayPanel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert fromTxtField != null : "fx:id=\"fromTxtField\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert toTxtField != null : "fx:id=\"toTxtField\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert goBtn != null : "fx:id=\"goBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";

    }
}
