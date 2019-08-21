package implementation.controller.application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
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
    private static final String MAIN_MENU_VIEW = "/implementation/view/application/MainMenuView.fxml";

    @FXML private MenuButton selectItem;
    @FXML private Label itemDescription;
    @FXML private AnchorPane imageSpot;
    @FXML private MenuButton skinPacks;

    private final Map<String, String> descriptionButtonMap = new HashMap<>();
    private final Map<String, Path> itemButtonMap = new HashMap<>();
    private Path packName;
    private boolean isSelected;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        final Path folderDescriptions = PathUtils.getResourcePath("descriptions");
        final Path folderPack = PathUtils.getResourcePath("resources");
        this.isSelected = false;
        listFiles(folderDescriptions);
        listDirectory(folderPack);
        initializeMenuPack();
        initializeMenuItem();
    }

    @Override
    public final void goToMainMenu() {
        final FXMLLoader root = new FXMLLoader(getClass().getResource(MAIN_MENU_VIEW));
        try {
            Main.getScene().setRoot(root.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //used to list all the files contained in a folder, which are the file of the item's descriptions
    private void listFiles(final Path folder) {
        try {
            Files.walk(folder, 1).filter(p -> !p.equals(folder)).forEach(p -> {
                try {
                    final Path fileName = p.getFileName();
                    if (fileName == null) {
                        return;
                    }
                    this.descriptionButtonMap.put(fileName.toString().replace("_", " "),
                            new String(Files.readAllBytes(p)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeMenuItem() {
        for (final String s : this.descriptionButtonMap.keySet()) {
            final MenuItem m = new MenuItem(s);
            //set the action for each menu item, when selected
            final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
                public void handle(final ActionEvent e) { 
                    isSelected = true; //an item has been selected
                    //string that contains all the item description
                    String descStr = descriptionButtonMap.get(s);
                    //change the text of the menu button and the label
                    selectItem.setText(m.getText());
                    itemDescription.setText(descStr);
                    //change the image in the image view
                    printImage(packName, m.getText().replace(" ", ""));
                }
            };
            m.setOnAction(event);
            this.selectItem.getItems().add(m);
        } 
    }

    //used to print a new image in the image view
    private void printImage(final Path packName, final String itemName) {
      //path where there are all the items images
        try {
            final Image item = new Image(
                    packName.resolve(ITEMS).resolve(itemName + PathUtils.IMAGE_TYPE).toUri().toURL().toString(),
                    100, 100, true, true);
            //set the image in the image view
            final ImageView itemImage = new ImageView(item);
            itemImage.setPreserveRatio(true);
            imageSpot.getChildren().clear();
            imageSpot.getChildren().add(itemImage);
            itemImage.fitWidthProperty().bind(imageSpot.widthProperty());
            itemImage.fitHeightProperty().bind(imageSpot.heightProperty());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //used to find all the directories that contains the skin pack
    private void listDirectory(final Path folder) {
        Path randomPack = null;
        try {
            for (final Iterator<Path> iterator = Files.walk(folder,  1).filter(p -> !p.equals(folder)).iterator(); iterator.hasNext();) {
                final Path item = iterator.next();
                final Path itemFileName = item.getFileName();
                if (Files.isDirectory(item) && itemFileName != null) {
                    final String packName = itemFileName.toString().replace("_", " ").replace("/", "");
                    this.itemButtonMap.put(packName, item);
                    if (randomPack == null) {
                        randomPack = item;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("There are problems with directory " + folder);
        }
        if (this.itemButtonMap.isEmpty()) {
            throw new RuntimeException("no skin paks found");
        } else if (this.itemButtonMap.containsKey(DEFAULT_PACK)) {
            this.packName = this.itemButtonMap.get(DEFAULT_PACK);
        } else {
            this.packName = randomPack;
        } 
    }

    //used to initialize the menu item of the pack
   private void initializeMenuPack() {
       for (final String s : this.itemButtonMap.keySet()) {
           final MenuItem m = new MenuItem(s);
           m.setUserData(this.itemButtonMap.get(s));
           final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
               public void handle(final ActionEvent e) { 
                   skinPacks.setText(m.getText());
                   packName = (Path) m.getUserData();
                   //if an item has been selected and I change pack, the image changes
                   if (isSelected) {
                       printImage(packName, selectItem.getText().replace(" ", ""));
                   }
               } 
           }; 
           m.setOnAction(event);
           this.skinPacks.getItems().add(m);
       }
   }

}
