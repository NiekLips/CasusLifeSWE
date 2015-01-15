/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import swe.life.World;
import swe.life.objects.Object;

/**
 * FXML Controller class
 *
 * @author Medische Technologie
 */
public class WorldViewController implements Initializable {
   
    public static WorldViewController instance;

    @FXML private Canvas cvWorld;
    
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
    public void draw() { 
        World world = World.instance;
        
        GraphicsContext g = cvWorld.getGraphicsContext2D();
        
        double canvasDrawWidth = cvWorld.getWidth() / world.getWidth();
        double canvasDrawHeight = cvWorld.getHeight() / world.getHeight();
        
        List<Object> objects;
        Object lastObject;
        
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                objects = world.getObjectsForXY(i, j);
                lastObject = objects.get(objects.size()-1);
                g.setFill(lastObject.getColor());
                g.fillRect(i, j, canvasDrawWidth, canvasDrawHeight);
                
            }
        }
    }
    
    /**
     * Clears the canvas.
     */
    public void clear() {
        cvWorld.getGraphicsContext2D().clearRect(0, 0, cvWorld.getWidth(), cvWorld.getHeight());
    }
}
