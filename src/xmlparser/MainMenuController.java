/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Colton Thompson
 */
public class MainMenuController implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private FileChooser fc2 = new FileChooser(); 
    /**
     * Initializes the controller class.
     */
    @FXML
    Parent root;
    @FXML
    private Hyperlink fileLink;
    @FXML
    private Hyperlink fileLink2;
    @FXML
    private Button cmpButton;
    File file, file2; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileLink.setOnAction(e -> {
            file = fileChooser.showOpenDialog((Stage) root.getScene().getWindow());
            if (file != null) {
                fileLink.setText(file.getName());
            }
        });

        fileLink2.setOnAction(e -> {
            file2 = fc2.showOpenDialog((Stage) root.getScene().getWindow());
            if (file2 != null) {
                fileLink2.setText(file2.getName());
            }
        });
        cmpButton.setOnAction(e -> {
            try {
                XMLParserMain.compareXML(file, file2);
            } catch (Exception ex) {
                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
