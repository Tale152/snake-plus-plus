package implementation.controller.application;

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

import design.controller.application.DescriptionController;
import implementation.view.application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DescriptionControllerImpl implements DescriptionController, Initializable {

  private final static String PATH = "res" + File.separator + "descriptions" + File.separator;
  private final static String PATH_ITEMS = "res" + File.separator + "resources" + File.separator;
  private final static String ITEMS = "Items" + File.separator;
  private final static String FILE_TYPE = ".png"; 
  private static final String DEFAULT_PACK = "Default Pack";
  
  @FXML private MenuButton selectItem;
  @FXML private Label itemDescription;
  @FXML private AnchorPane imageSpot;
  @FXML private MenuButton skinPacks;
  
  private final Map<String, String> descriptionButtonMap = new HashMap<>();
  private final Map<String, String> itemButtonMap = new HashMap<>();
  private String labelStr;
  private String packName;
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    final File folderDescriptions = new File("res" + File.separator + "descriptions");
    final File folderPack = new File("res" + File.separator + "resources");
    listFiles(folderDescriptions);
    listDirectory(folderPack);
    initializeMenuPack();
    initializeMenuItem();
    this.itemDescription.setWrapText(true);
    
  }
  
  public void goToMainMenu() {
    FXMLLoader root = new FXMLLoader(getClass().getResource("/implementation/view/application/MainMenuView.fxml"));
    try {
      Main.getScene().setRoot(root.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
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
                  FileInputStream inputStream = new FileInputStream(PATH_ITEMS + packName + File.separator + ITEMS + m.getText().replace(" ", "") + FILE_TYPE);
                  Image item = new Image(inputStream, 100, 100, true, true);
                  ImageView itemImage = new ImageView(item);
                  itemImage.setPreserveRatio(true);
                  imageSpot.getChildren().clear();
                  imageSpot.getChildren().add(itemImage);
                  itemImage.fitWidthProperty().bind(imageSpot.widthProperty());
                  itemImage.fitHeightProperty().bind(imageSpot.heightProperty()); 
                } catch (FileNotFoundException e1) {
                  e1.printStackTrace();
                }                
              } 
          }; 
      m.setOnAction(event);
      this.selectItem.getItems().add(m);
    } 
  }
  
   private void listDirectory(final File folder) {
       String randomPack = "";
       for (final File fileEntry : folder.listFiles()) {
           if (fileEntry.isDirectory()) {
             this.itemButtonMap.put(fileEntry.getName().replace("_", " "), fileEntry.getName());
             if (randomPack.isEmpty()) {
                  randomPack = fileEntry.getName().replace(" ", "_");
              }
          }
       }
       if (this.itemButtonMap.isEmpty()) {
           System.out.println("There are no skin packs");
           System.exit(1);
       } else if (this.itemButtonMap.containsKey(DEFAULT_PACK)) {
         this.packName = this.itemButtonMap.get(DEFAULT_PACK);
       } else {
         this.packName = randomPack;
       }
   }

   
   private void initializeMenuPack() {
       for (String s : this.itemButtonMap.keySet()) {
           MenuItem m = new MenuItem(s);
           EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
               public void handle(final ActionEvent e) { 
                   skinPacks.setText(m.getText());
                   packName = m.getText().replace(" ", "_");
               } 
           }; 
           m.setOnAction(event);
           this.skinPacks.getItems().add(m);
       }
   }

}
