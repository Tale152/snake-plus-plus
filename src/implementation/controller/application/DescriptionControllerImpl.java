package implementation.controller.application;


import java.awt.Button;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DescriptionControllerImpl implements Initializable {

  private final static String PATH = "res" + File.separator + "descriptions" + File.separator;
  private final static String PATH_ITEMS = "res" + File.separator + "resources" + File.separator + "Default_Pack" + File.separator + "Items" + File.separator;
  
  @FXML private MenuButton selectItem;
  @FXML private Label itemDescription;
  @FXML private AnchorPane imageSpot;

  private final Map<String, String> descriptionButtonMap = new HashMap<>();
  String labelStr;
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    final File folder = new File("res" + File.separator + "descriptions");
    listFiles(folder);
    initializeMenuItem();
    this.itemDescription.setWrapText(true);
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
                
                try {
                  FileInputStream inputStream = new FileInputStream(PATH_ITEMS + m.getText().replace(" ", "") + ".png");
                  Image item = new Image(inputStream);
                  ImageView itemImage = new ImageView(item);
                  imageSpot.getChildren().clear();
                  imageSpot.getChildren().add(itemImage);
                  itemImage.setPreserveRatio(true);
                 
     

                } catch (FileNotFoundException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                }                
              } 
          }; 
      m.setOnAction(event);
      this.selectItem.getItems().add(m);
    } 
  }
  

}
