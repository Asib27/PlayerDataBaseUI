/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.controllers.IntroPageController;
import com.asib27.playerdatabaseui.controllers.MainControlDriverInt;
import com.asib27.playerdatabaseui.controllers.MainController;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author USER
 */
public class MainDriver implements Driver, MainControlDriverInt {
    private Service service;
    private DatabaseManager databaseManager;
    
    private BorderPane mainPane;
    
    private MainController mainController;

    public MainDriver(Service service) {
        this.service = service;
        databaseManager = this.service.getDatabase();
        
        FXMLLoader loader = App.getFXMLLoader("Main.fxml");
        try {
            mainPane = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mainController = loader.getController();
        mainController.setMainDriver(this);
    }
    
    
    
    @Override
    public Pane getGuiPane() {
        return mainPane;
    }

    @Override
    public void clearListener() {
        
    }
    
    @Override
    public Driver GuiDriverFactory(String name){
        if(name.equals("Intro")){
            FXMLLoader fXMLLoader = App.getFXMLLoader("IntroPage.fxml");
            try {
                fXMLLoader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return fXMLLoader.getController();
        }
        return switch (name) {
            case "Search" -> new SearchMenuDriver(service);
            case "Stat" -> new StatMenuDriver(service);
            
            default -> null;
        };
    }
    
    @Override
    public Task<Driver> taskFactory(String name){
        return switch (name) {
            case "Search" -> SearchMenuDriver.getLoader(service);
            case "Stat" -> StatMenuDriver.getLoader(service);
            
            default -> null;
        };
    }
}
