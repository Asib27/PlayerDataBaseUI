/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.Driver;
import com.asib27.playerdatabaseui.MainDriver;
import com.asib27.playerdatabaseui.Service;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MainController implements Initializable {
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab newTab;
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private Label taskNameLabel;
    
    @FXML
    private Label progressTextLabel;
    
    @FXML
    private HBox progressBox;
    
    @FXML
    private AnchorPane slider, sideMenu, sliderMain;
    
    private MainControlDriverInt driver;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    
            
    @FXML
    private void openSearchMenu(ActionEvent av){
        menuOpenHelper("Search");
    }
    
    @FXML
    private void openStatMenu(ActionEvent av){
        menuOpenHelper("Stat");
    }
    
    @FXML
    private void slideInOut(MouseEvent me){
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), slider);
        
        if(slider.getTranslateX() == 0){ //slide out
            tt.setByX(-300);
            sideMenu.setStyle("-fx-background-color : white;");
            sliderMain.setMouseTransparent(true);
            tabPane.setOpacity(1);
        }
        else{ //slide in
            tt.setByX(300);
            //sideMenu.setStyle("-fx-background-color : yellow;");
            sliderMain.setMouseTransparent(false);
            tabPane.setOpacity(.7);
        }
        
        tt.play();
    }
    
    private void menuOpenHelper(String name){
        Task<Driver> guiDriver = this.driver.taskFactory(name);
        
        guiDriver.setOnSucceeded((t) -> {
            setContent(guiDriver.getValue().getGuiPane());
        });
        
        setProgressBoxTask(guiDriver);
    }

    public void setMainDriver(MainControlDriverInt mainDriver) {
        this.driver = mainDriver;
        
        tabPane.getTabs().get(0).setContent(mainDriver.GuiDriverFactory("Intro").getGuiPane());
        
        newTab.setOnSelectionChanged((t) -> {
            ObservableList<Tab> tabs = tabPane.getTabs();
            Tab tab = new Tab("New Tab");
            tab.setContent(mainDriver.GuiDriverFactory("Intro").getGuiPane());
            int index = tabs.size()-1;
            tabs.add(index, tab);
            tabPane.getSelectionModel().selectPrevious();
            
        });
    }
    
    public void setProgressBoxTask(Task worker){
        
        progressBar.progressProperty().bind(worker.progressProperty());
        taskNameLabel.textProperty().bind(worker.titleProperty());
        progressTextLabel.textProperty().bind(worker.messageProperty());
        progressBox.visibleProperty().bind(worker.runningProperty());
        
        Thread bgThread = new Thread(worker);
        bgThread.start();
    }
    
    public void setContent(Pane pane){
        tabPane.getSelectionModel().getSelectedItem().setContent(pane);
    }
    
    public void setContent(int index, Pane pane){
        ObservableList<Tab> tabs = tabPane.getTabs();
        
        if(index < tabs.size() -1){
            tabs.get(index).setContent(pane);
        }
    }
    
    public void clearListener(){
        progressBar.progressProperty().unbind();
        taskNameLabel.textProperty().unbind();
        progressTextLabel.textProperty().unbind();
    }
}
