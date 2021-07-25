/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.ControllerHelper.MainControlDriverInt;
import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.Drivers.SellMenuDriver;
import com.asib27.playerdatabaseui.MyTab;
import com.asib27.playerdatabaseui.NotificationBox;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.Notification;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    
    @FXML
    private Button backButton, forwardButton;
    
    @FXML
    private VBox menuLableBox;
    
    @FXML
    private AnchorPane saticMessageBox;

    @FXML
    private AnchorPane slidingMessageBox;
    
    private ObservableList<Node> menuLabels;
    private ObservableList<Node> notificationLabels;
    private MainControlDriverInt driver;
    DatabaseManager databaseManager;
    String currentSliderContent = "menu label";
    private boolean isSliderOut = false;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyTab tab = new MyTab("New Tab");
        tabPane.getTabs().remove(0);
        tabPane.getTabs().add(0, tab);
        tabPane.getSelectionModel().select(tab);
        
        bindButtons(tab);
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
            if(nv instanceof MyTab){
                MyTab myTab = (MyTab) nv;
                bindButtons(myTab);
                myTab.update(databaseManager);
            }
        });
        
        menuLableBox.addEventHandler(EventType.ROOT, (t) -> {
            t.consume();
        });
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
    private void openBuyMenu(ActionEvent av){
        menuOpenHelper("Buy");
    }
    
    @FXML
    private void openSellMenu(){
        menuOpenHelper("Sell");
    }
    
    @FXML
    private void slideInOut(MouseEvent me){
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), slider);
        
        if(isSliderOut){ //slide out
            isSliderOut = false;
            
            tt.setToX(-300);
            sideMenu.setStyle("-fx-background-color : white;");
            sliderMain.setMouseTransparent(true);
            tabPane.setOpacity(1);
            
            if(!currentSliderContent.equals("menu label")){
                tt.setOnFinished((t) -> {
                    menuLableBox.getChildren().removeAll(notificationLabels);
                    menuLableBox.getChildren().addAll(menuLabels);   
                    currentSliderContent = "menu label";
                });
            }
        }
        else{ //slide in
            isSliderOut = true;
            
            tt.setToX(0);
            //sideMenu.setStyle("-fx-background-color : yellow;");
            sliderMain.setMouseTransparent(false);
            tabPane.setOpacity(.7);
        }
        
        tt.play();
    }
    
    @FXML
    private void homeButtonClicked(MouseEvent me){
        setContent(driver.GuiDriverFactory("Intro"));
    }
    
    private void menuOpenHelper(String name){
        Task<Driver> guiDriver = this.driver.taskFactory(name);
        
        guiDriver.setOnSucceeded((t) -> {
            setContent(guiDriver.getValue());
        });
        
        setProgressBoxTask(guiDriver);
    }

    public void setMainDriver(MainControlDriverInt mainDriver) {
        this.driver = mainDriver;
        
        MyTab myTab = (MyTab) tabPane.getTabs().get(0);
        myTab.setContent(driver.GuiDriverFactory("Intro"));
        
        newTab.setOnSelectionChanged((t) -> {
            ObservableList<Tab> tabs = tabPane.getTabs();
            MyTab tab = new MyTab("New Tab");
            
            tab.setContent(mainDriver.GuiDriverFactory("Intro"));
            
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
    
    public void setContent(Driver driver){
        MyTab selected = (MyTab) tabPane.getSelectionModel().getSelectedItem();
        selected.setText(driver.getDriverName());
        selected.setContent(driver);;
    }
    
    public void setContent(int index, Driver driver){
        ObservableList<Tab> tabs = tabPane.getTabs();
        
        if(index < tabs.size() -1){
            MyTab tab = (MyTab) tabs.get(index);
            tab.setText(driver.getDriverName());
            tab.setContent(driver);
        }
    }
    
    public void clearListener(){
        progressBar.progressProperty().unbind();
        taskNameLabel.textProperty().unbind();
        progressTextLabel.textProperty().unbind();
    }

    public TabPane getTabPane() {
        return tabPane;
    }
    
    @FXML
    public void backButtonPressed(ActionEvent e){
        MyTab selected = (MyTab)tabPane.getSelectionModel().getSelectedItem();
        selected.back();
    }
    
    @FXML
    public void forwardButtonPressed(ActionEvent e){
        MyTab selected = (MyTab)tabPane.getSelectionModel().getSelectedItem();
        selected.forward();
    }
    
    
    private void bindButtons(MyTab tab){
        backButton.disableProperty().bind(tab.BackExistsProperty().not());
        forwardButton.disableProperty().bind(tab.ForwardExistsProperty().not());
    }
    
    public void update(DatabaseManager manager){
        databaseManager = manager;
        
        Tab selectedItem = tabPane.getSelectionModel().getSelectedItem();
        if(selectedItem instanceof MyTab){
            MyTab myTab = (MyTab) selectedItem;
            myTab.update(manager);
        }
    }
    
    @FXML
    private void notificationsButtonCLicked(){
        if(!currentSliderContent.equals("Notifications")){
            ArrayList<Notification> notifications = driver.getNotifications();
            
            Label label = new Label("Notifications");
            notificationLabels = FXCollections.observableArrayList(label);

            notifications.forEach((t) -> {
                NotificationBox box = new NotificationBox(t, driver.getService());
                notificationLabels.add(box);
            });

            menuLabels = FXCollections.observableArrayList(menuLableBox.getChildren());
        }
        
        if(!currentSliderContent.equals("Notifications")){
            menuLableBox.getChildren().removeAll(menuLabels);
            menuLableBox.getChildren().addAll(notificationLabels);
            currentSliderContent = "Notifications";
        }
        
        if(!isSliderOut)
            slideInOut(null);
    }
    
    public void setSlidingMessagePane(Pane pane){
        slidingMessageBox.setTranslateX(-300.0);
        slidingMessageBox.getChildren().add(pane);
        
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        
        slideIn(slidingMessageBox);;
    }
    
    public void slideIn(AnchorPane ap){
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), ap);
        
        tt.setToX(0);
        tt.play();
    }
}
