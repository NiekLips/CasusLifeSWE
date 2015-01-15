/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Roy
 */
public class MainGuiController implements Initializable {
    
    @FXML private Pane ViewPane;
    
    
    /**
     * actie bij klikken op New World
     * @param event 
     */
    @FXML private void handleButtonNewWorldAction(ActionEvent event) 
    {
        
    }
    
    /**
     * actie bij klikken op Save World
     * @param event 
     */
    @FXML private void handleButtonSaveWorldAction(ActionEvent event) 
    {
    }
    
    /**
     * actie bij klikken op Load World
     * @param event 
     */
    @FXML private void handleButtonLoadWorldAction(ActionEvent event) 
    {
    }
                
    /**
     * actie bij klikken op Add World
     * @param event 
     */
    @FXML private void handleButtonAddWorldAction(ActionEvent event) 
    {
    }
    
    
    /**
     * actie bij klikken op LogOut
     * @param event 
     */
    @FXML private void handleButtonLogOutAction(ActionEvent event) 
    {
    }
    
    /**
     * actie bij klikken op Change Password
     * @param event 
     */
    @FXML private void handleButtonChangePasswordAction(ActionEvent event) throws IOException 
    {
        ViewPane.getChildren().clear();
        ViewPane.getChildren().add(FXMLLoader.load(getClass().getResource("ChangePassword.fxml")));
    }
    
    /**
     * actie bij klikken op Add User
     * @param event 
     */
    @FXML private void handleButtonAddUserAction(ActionEvent event) 
    {
        
    }
    
    /**
     * actie bij klikken op Exit
     * @param event 
     */
    @FXML private void handleButtonExitAction(ActionEvent event) 
    {
        Platform.exit();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    
    
}
