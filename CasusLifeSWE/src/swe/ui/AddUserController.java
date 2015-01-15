/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import swe.user.*;
import static swe.user.Users.*;

/**
 * FXML Controller class
 *
 * @author Medische Technologie
 */
public class AddUserController implements Initializable {

    
    @FXML ComboBox CoBXRights;
    @FXML TextField TfdUserName, TfdPassword;
    @FXML Label LblErrorMes;
       
      
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CoBXRights.getItems().addAll("Viewer", "Controller", "Admin");
    }    
    
    
    @FXML private void handleButtonAddUserAction(ActionEvent event)
    {
        try
        {
            String name = TfdUserName.getText();
            String password = TfdPassword.getText();
            UserRights rights = null;
            if(CoBXRights.getValue().toString().matches("Viewer"))
            {
                rights = UserRights.fromInteger(0);
            }
            else if(CoBXRights.getValue().toString().matches("Controller"))
            {
                rights = UserRights.fromInteger(1);
            }
            else if(CoBXRights.getValue().toString().matches("Admin"))
            {
                rights = UserRights.fromInteger(2);
            }
            createUser(name, password,rights); 
        }
        catch(Exception e)
        {
            LblErrorMes.setText(e.getMessage());
        }
    }
    
    
    @FXML private void handleButtonBackAction(ActionEvent event)
    {
        
    }
}
