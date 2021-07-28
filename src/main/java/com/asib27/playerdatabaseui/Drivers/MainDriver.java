/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Drivers;

import com.asib27.playerdatabaseui.App;
import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.controllers.IntroPageController;
import com.asib27.playerdatabaseui.ControllerHelper.MainControlDriverInt;
import com.asib27.playerdatabaseui.CustomControls.NotificationBox;
import com.asib27.playerdatabaseui.controllers.AboutPageController;
import com.asib27.playerdatabaseui.controllers.FeedbackPageController;
import com.asib27.playerdatabaseui.controllers.MainController;
import com.asib27.playerdatabaseui.util.Notification;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author USER
 */
public class MainDriver implements Driver, MainControlDriverInt, MainDriverInt {
    private Service service;
    private DatabaseManager databaseManager;
    
    final String driverName = "Main Menu";
    
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
    public String getDriverName() {
        return driverName;
    }

    @Override
    public void showNotification(Notification notification) {
        NotificationBox box = new NotificationBox(notification, service);
        
        box.setStyle("-fx-border-width : 1;"
                + "-fx-border-color : black;");
        
        Platform.runLater(() -> {
            mainController.setSlidingMessagePane(box);
        });
    }

    @Override
    public void showMessage(String message) {
        TextArea ta = new TextArea(message);
        
        ta.setStyle("-fx-border-width : 1;"
                + "-fx-border-color : black;");
        
        BorderPane bp = new BorderPane(ta);
        Platform.runLater(() -> {
            mainController.setSlidingMessagePane(bp);
        });
    }
    
    

    @Override
    public Service getService() {
        return service;
    }
    
    
    @Override
    public Driver GuiDriverFactory(String name){
        if(name.equals("Intro")){
            IntroPageController controller = loader("IntroPage.fxml");
            controller.addSearchListener(mainController);
            return controller;
        }
        else if(name.equals("About")){
            AboutPageController controller = loader("aboutPage.fxml");
            return controller;
        }
        else if(name.equals("Feedback")){
            FeedbackPageController controller = loader("feedbackPage.fxml");
            controller.setService(service);
            return controller;
        }
        return switch (name) {
            case "Search" -> new SearchMenuDriver(service);
            case "Stat" -> new StatMenuDriver(service);
            case "Buy" -> new BuyMenuDriver(service);
            case "Sell" ->  new SellMenuDriver(service);
            
            default -> null;
        };
    }
    
    private <T>T loader(String name){
        FXMLLoader fXMLLoader = App.getFXMLLoader(name);
        try {
            fXMLLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            
        return fXMLLoader.getController();
    }
    
    @Override
    public Task<Driver> taskFactory(String name){
        return switch (name) {
            case "Search" -> SearchMenuDriver.getLoader(service);
            case "Stat" -> StatMenuDriver.getLoader(service);
            case "Buy" -> BuyMenuDriver.getLoader(service);
            case "Sell" -> SellMenuDriver.getLoader(service);
            
            default -> null;
        };
    }
    
    @Override
    public void update(DatabaseManager database){
        mainController.update(database);
    }

    @Override
    public ArrayList<Notification> getNotifications() {
        ArrayList<Notification> notifications = service.getNotification();
        
        notifications.sort((Notification o1,Notification o2) -> {
            return (-1) * o1.getTime().compareTo(o2.getTime());
        });
        
        return notifications;
    }
}
