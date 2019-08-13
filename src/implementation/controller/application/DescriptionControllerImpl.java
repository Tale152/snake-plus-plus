package implementation.controller.application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class DescriptionControllerImpl implements Initializable {

  private final static String PATH = "res" + File.separator + "descriptions" + File.separator;
  
  @FXML private MenuButton selectItem;
  @FXML private Label itemDescription;
  @FXML private AnchorPane root;
  
  private final Map<String, String> descriptionButtonMap = new HashMap<>();
  String labelStr;
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    final File folder = new File("res" + File.separator + "descriptions");
    listFiles(folder);
    initializeMenuItem();
  }

  private void listFiles(final File folder) {
      for (final File fileEntry : folder.listFiles()) {
          if (!fileEntry.isDirectory()) {
            this.descriptionButtonMap.put(fileEntry.getName().replace("_", " "), PATH + fileEntry.getName());
          }
      }  
  }
  
  private void initializeMenuItem() {
    for(String s : this.descriptionButtonMap.keySet()) {
      MenuItem m = new MenuItem(s);
      EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
              public void handle(ActionEvent e) { 
                  try {
                    BufferedReader br = new BufferedReader(new FileReader(descriptionButtonMap.get(s)));
                    labelStr = "";
                    String nextStr;
                    nextStr = br.readLine();
                    while (nextStr != null) {
                      labelStr += nextStr + "\n";
                      nextStr = br.readLine();
                    }
                    br.close();
                    
                  } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                  } catch (IOException e1) {
                    e1.printStackTrace();
                  }
                  
                selectItem.setText(m.getText());
                itemDescription.setText(labelStr);
              } 
          }; 
      m.setOnAction(event);
      this.selectItem.getItems().add(m);
    } 
  }
  

}
