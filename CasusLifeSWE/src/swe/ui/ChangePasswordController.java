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
public class ChangePasswordController implements Initializable {

    
    @FXML TextField TfdOld, TfdNew, TfdNewCheck;
    @FXML Label LblErrorMes;
    
    @FXML private void handleButtonAddUserAction(ActionEvent event)
    {
        try
        {
            String passwordOld = TfdOld.getText();
            String passwordNew = TfdNew.getText();
            String passwordCheck = TfdNewCheck.getText();

            if(passwordNew.matches(passwordCheck))
            {
                changePassword(passwordOld, passwordNew);
            }
            else
            {
                LblErrorMes.setText("The new passwords do not match!");
            }
        }
        catch(Exception e)
        {
            LblErrorMes.setText(e.getMessage());
        }
    }
    
        
    @FXML private void handleButtonBackAction(ActionEvent event)
    {
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
