/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Colton Thompson 
 */
public class MainMenu extends Application {
       @Override
        public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(MainMenu.class.getResource("MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("XML Comparator");
        
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String [] args)
    {
        launch(args); 
    }

    
}
