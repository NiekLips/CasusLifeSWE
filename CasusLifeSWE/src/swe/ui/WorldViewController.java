/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import swe.life.Statistics;
import swe.life.WildLife;
import swe.life.World;
import swe.life.objects.Object;

/**
 * FXML Controller class
 *
 * @author Medische Technologie
 */
public class WorldViewController implements Initializable {
   
    public static WorldViewController instance;
    private boolean pauze = false;

    @FXML private Canvas cvWorld;
    @FXML Label LblOmniNR, LblVegiNR, LblCarniNR, LblHerbiNR;
    @FXML Label LblOmniEnergy, LblVegiEnergy, LblCarniEnergy, LblHerbiEnergy;
    @FXML Label LblSpeed;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }    
    
    /**
     * Draws the current world in the canvas.
     */
    public void draw() 
    { 
        World world = World.instance;
        
        GraphicsContext g = cvWorld.getGraphicsContext2D();
        
        double canvasDrawWidth = cvWorld.getWidth() / world.getWidth();
        double canvasDrawHeight = cvWorld.getHeight() / world.getHeight();
        
        List<Object> objects;
        Object lastObject;
        
        for (int i = 0; i < world.getWidth(); i++) 
        {
            for (int j = 0; j < world.getHeight(); j++) 
            {
                objects = world.getObjectsForXY(i, j);
                lastObject = objects.get(objects.size()-1);
                g.setFill(lastObject.getColor());
                g.fillRect(i*canvasDrawWidth, j*canvasDrawHeight, canvasDrawWidth, canvasDrawHeight);
            }
        }
        
        try
        {
        Statistics Stats = World.instance.getCurrentStatistics();
        //update labels 
        LblOmniNR.setText(String.valueOf(Stats.getTotalCount(WildLife.Omnivore)));
        LblVegiNR.setText(String.valueOf(Stats.getTotalCount(WildLife.Vegetation)));
        LblCarniNR.setText(String.valueOf(Stats.getTotalCount(WildLife.Carnivore)));
        LblHerbiNR.setText(String.valueOf(Stats.getTotalCount(WildLife.Herbivore)));
        
        LblOmniEnergy.setText(String.valueOf(Stats.getTotalEnergy(WildLife.Omnivore)));
        LblVegiEnergy.setText(String.valueOf(Stats.getTotalEnergy(WildLife.Vegetation)));
        LblCarniEnergy.setText(String.valueOf(Stats.getTotalEnergy(WildLife.Carnivore)));
        LblHerbiEnergy.setText(String.valueOf(Stats.getTotalEnergy(WildLife.Herbivore)));
        }
        catch(Exception e)
        {
            MainGuiController.instance.LblOutput.setText(e.getMessage());
        }
        
        LblSpeed.setText(String.valueOf(World.instance.getSimulator().getSpeed()));
    }
    
    /**
     * Clears the canvas.
     */
    public void clear() {
        cvWorld.getGraphicsContext2D().clearRect(0, 0, cvWorld.getWidth(), cvWorld.getHeight());
    }
    
    /**
     * actie bij klikken op Play
     * @param event 
     */
    @FXML private void handleButtonPlayAction(ActionEvent event) 
    {
        if (!pauze)
        {
            World.instance.getSimulator().startSimulation(10);
        }
        else
        {
            World.instance.getSimulator().continueSimulation();
            pauze= false;
        }
    }
    
    /**
     * actie bij klikken op Stop
     * @param event 
     */
    @FXML private void handleButtonStopAction(ActionEvent event) 
    {
        World.instance.getSimulator().stopSimulation();
    }
    
    /**
     * actie bij klikken op Pauze
     * @param event 
     */
    @FXML private void handleButtonPauzeAction(ActionEvent event) 
    {
        World.instance.getSimulator().pauseSimulation();
        pauze = true;
    }
    
    /**
     * actie bij klikken op Slow
     * @param event 
     */
    @FXML private void handleButtonSlowAction(ActionEvent event) 
    {
        try
        {
            World.instance.getSimulator().setSpeed(World.instance.getSimulator().getSpeed()-1);
        }
        catch(Exception e)
        {
            MainGuiController.instance.LblOutput.setText(e.getMessage());
        }
        
    }
    
    /**
     * actie bij klikken op Fast
     * @param event 
     */
    @FXML private void handleButtonFastAction(ActionEvent event) 
    {
        try
        {
            World.instance.getSimulator().setSpeed(World.instance.getSimulator().getSpeed()+ 1);
        }
        catch(Exception e)
        {
            MainGuiController.instance.LblOutput.setText(e.getMessage());
        }
    }
}
