/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import swe.life.World;
import swe.life.objects.*;
import static swe.life.objects.enumerations.Digestion.*;
import static swe.life.objects.enumerations.Sex.*;

/**
 * FXML Controller class
 *
 * @author Medische Technologie
 */
public class PresetController implements Initializable {

    
        /**
     * actie bij klikken op Preset1
     * @param event 
     */
    @FXML private void handleButtonPreset1Action(ActionEvent event) throws IOException
    {
        MainGuiController.instance.ViewPane.getChildren().clear();
        MainGuiController.instance.ViewPane.getChildren().add(FXMLLoader.load(getClass().getResource("WorldView.fxml")));
    
        World world = new World(50,50,"");
        int width = world.getWidth();
        int height = world.getHeight();
        int borderW = (width/10)-1;
        int borderH = (height/10) -1;

        Random Temp = new Random();

        for(int i =0;i < width;i++)
        {
            for(int j =0;j < height;j++)
            {
                int choise = Temp.nextInt(36);
                int legs = 1+Temp.nextInt(6);
                int stamina = (legs*150);
                if(i > borderW && i < (width - borderW) && j > borderH && j < (height - borderH)
                    //&& (((i > width/2) && (j > height/2)&& j-i <= width /2)
                   // || ((i < width/2) && (j < height/2)&& i+j >= width / 2)
                   // || ((j > height / 2) && (i > width / 2) && ((i-25)+(j-25)) <= width / 2) 
                   // || ((j < height / 2) && (i > width / 2) && i-j <= width / 2))
                    )
                {
                    if(choise < 18)
                    {
                        world.addObject( new Land(i,j, world));
                    }
                    else if(choise < 22)
                    {
                        world.addObject(new Land(i,j, world));                        
                        if(choise%2==0)
                        {
                            world.addObject(new Animal(i,j, world,Herbivorous, 
                                Male,stamina, legs, stamina-30, legs*4, legs*3, stamina, legs*2, legs*40));   
                        }
                        else
                        {
                            world.addObject(new Animal(i,j, world,Herbivorous, 
                                Female,stamina, legs, stamina-30, legs*4, legs*3, stamina, legs*2, legs*40));                            
                        }
                    }
                    else if(choise < 26)
                    {
                        world.addObject(new Land(i,j, world));                        
                        if(choise%2==0)
                        {
                            world.addObject(new Animal(i,j, world,Carnivorous, 
                                Male,stamina, legs, stamina-30, legs*4, legs*3, stamina, legs*2, legs*40));   
                        }
                        else
                        {
                            world.addObject(new Animal(i,j, world,Carnivorous, 
                                Female,stamina, legs, stamina-30, legs*4, legs*3, stamina, legs*2, legs*40));                            
                        }
                    }
                    else if(choise < 30)
                    {
                        world.addObject(new Land(i,j, world));                        
                        if(choise ==26)
                        {
                            world.addObject(new Animal(i,j, world,OmnivorousPreferMeat, 
                                Male,stamina, legs, stamina-30, legs*4, legs*3, stamina, legs*2, legs*40));   
                        }
                        else if(choise == 27)
                        {
                            world.addObject(new Animal(i,j, world,OmnivorousPreferMeat, 
                                Female,stamina, legs, stamina-30, legs*4, legs*3, stamina, legs*2, legs*40));                            
                        }
                        if(choise ==28)
                        {
                            world.addObject(new Animal(i,j, world,OmnivorousPreferVegetation, 
                                Male,stamina, legs, stamina-30, legs*4, legs*3, stamina, legs*2, legs*40));   
                        }
                        else if(choise == 29)
                        {
                            world.addObject(new Animal(i,j, world,OmnivorousPreferVegetation, 
                                Female,stamina, legs, stamina-30, legs*4, legs*3, stamina, legs*2, legs*40));                            
                        }
                    }
                    else if(choise < 34)
                    {
                        world.addObject(new Land(i,j, world));
                        world.addObject(new Vegetation(i,j, world, choise, 0));
                    }
                    else
                    {
                        world.addObject(new Land(i,j, world));
                        world.addObject(new Obstacle(i,j));
                    }
                }
                else
                {
                    world.addObject(new Water(i,j, world));                    
                }
                
            }
        }
        
        WorldViewController.instance.draw();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
