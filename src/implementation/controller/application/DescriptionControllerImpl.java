package implementation.controller.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import design.controller.application.DescriptionController;
import implementation.controller.PathUtils;
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

/**
 * The description controller define the behaviors of the description view,
 * where there are all the item's descriptions, that can be selected from every 
 * skin pack.
 */
public class DescriptionControllerImpl implements DescriptionController, Initializable {

    private static final String ITEMS = "Items" + File.separator;
    private static final String DEFAULT_PACK = "Default Pack";

    @FXML private MenuButton selectItem;
    @FXML private Label itemDescription;
    @FXML private AnchorPane imageSpot;
    @FXML private MenuButton skinPacks;

    private final Map<String, String> descriptionButtonMap = new HashMap<>();
    private final Map<String, String> itemButtonMap = new HashMap<>();
    private String packName;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        final File folderDescriptions = new File("res" + File.separator + "descriptions");
        final File folderPack = new File("res" + File.separator + "resources");
        listFiles(folderDescriptions);
        listDirectory(folderPack);
        initializeMenuPack();
        initializeMenuItem();
        this.itemDescription.setWrapText(true);
    }

    @Override
    public final void goToMainMenu() {
        final FXMLLoader root = new FXMLLoader(getClass().getResource("/implementation/view/application/MainMenuView.fxml"));
        try {
            Main.getScene().setRoot(root.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //used to list all the files contained in a folder, which are the file of the item's descriptions
    private void listFiles(final File folder) {
        final File[] files = folder.listFiles();
        if (files != null) {
            final List<File> filesList = new ArrayList<>(Arrays.asList(files));
            for (final File fileEntry : filesList) {
                if (!fileEntry.isDirectory()) {
                    this.descriptionButtonMap.put(fileEntry.getName().replace("_", " "), PathUtils.DESCRIPTIONS + fileEntry.getName());
                }
            }
        }
    }

    private void initializeMenuItem() {
        for (final String s : this.descriptionButtonMap.keySet()) {
            final MenuItem m = new MenuItem(s);
            //set the action for each menu item, when selected
            final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
                public void handle(final ActionEvent e) { 
                    //string that contains all the item description
                    String labelStr = "";
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(descriptionButtonMap.get(s)));
                        //read item description
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
                    //change the text of the menu button and the label
                    selectItem.setText(m.getText());
                    itemDescription.setText(labelStr);

                    //path where there are all the items images
                    Image item = new Image(
                            new File(PathUtils.RESPACKS + packName + File.separator + ITEMS + m.getText().replace(" ", "") 
                                    + PathUtils.IMAGE_TYPE).toURI().toString(), 100, 100, true, true);
                    //set the image in the image view
                    ImageView itemImage = new ImageView(item);
                    itemImage.setPreserveRatio(true);
                    imageSpot.getChildren().clear();
                    imageSpot.getChildren().add(itemImage);
                    itemImage.fitWidthProperty().bind(imageSpot.widthProperty());
                    itemImage.fitHeightProperty().bind(imageSpot.heightProperty());
                }
            };
            m.setOnAction(event);
            this.selectItem.getItems().add(m);
        } 
    }

    //used to find all the directories that contains the skin pack
    private void listDirectory(final File folder) {
        //random pack is used if the default pack does not exist
        String randomPack = "";
        final File[] files = folder.listFiles();
        if (files != null) {
            final List<File> filesList = new ArrayList<>(Arrays.asList(files));
            for (final File fileEntry : filesList) {
                if (fileEntry.isDirectory()) {
                    this.itemButtonMap.put(fileEntry.getName().replace("_", " "), fileEntry.getName());
                    if (randomPack.isEmpty()) {
                        randomPack = fileEntry.getName().replace(" ", "_");
                    }
                }
            }
            //set the default skin pack if the user does not select one
            if (this.itemButtonMap.isEmpty()) {
                throw new RuntimeException("no skin paks found");
            } else if (this.itemButtonMap.containsKey(DEFAULT_PACK)) {
                this.packName = this.itemButtonMap.get(DEFAULT_PACK);
            } else {
                this.packName = randomPack;
            }
        } else {
            throw new RuntimeException("there are problems with directory " + folder.getAbsolutePath());
        }
    }

    //used to initialize the menu item of the pack
   private void initializeMenuPack() {
       for (final String s : this.itemButtonMap.keySet()) {
           final MenuItem m = new MenuItem(s);
           final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
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
