/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class SlidingScreenController implements Initializable , SplitedScreenInt{
    @FXML
    private AnchorPane paneLeft;

    @FXML
    private AnchorPane paneRight;

    @FXML
    private AnchorPane FLoatingPane;
    
    @FXML
    private AnchorPane floatingPaneContent;

    @FXML
    private HBox upperControl;

    @FXML
    private Button UpButton;
    private boolean isUp = true;
    
    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListener();
    }
    
    public void addListener(){
        UpButton.setOnAction((t) -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(300), FLoatingPane);
            
            if(isUp){
                tt.setByY(FLoatingPane.getHeight() -  upperControl.getHeight());
                isUp = false;
            }
            else{
                tt.setByY(-(FLoatingPane.getHeight() -  upperControl.getHeight()));
                isUp = true;
            }
            
            System.out.println(FLoatingPane.getMaxHeight() + " " + FLoatingPane.getMaxWidth());
            System.out.println(FLoatingPane.getHeight() + " " + FLoatingPane.getWidth());
            System.out.println(stackPane.getHeight() + " " + stackPane.getWidth());
            tt.play();
        });
        
        FLoatingPane.maxHeightProperty().bind(stackPane.heightProperty().divide(2));
        FLoatingPane.maxWidthProperty().bind(stackPane.widthProperty().divide(2));
        
        upperControl.maxWidthProperty().bind(floatingPaneContent.widthProperty());
        upperControl.minWidthProperty().bind(floatingPaneContent.widthProperty());
    }

    @Override
    public void setLeftPane(Pane pane) {
        paneLeft.getChildren().add(pane);
        
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
    }

    @Override
    public void setRightPane(Pane pane) {
        paneRight.getChildren().add(pane);
        
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
    }

    @Override
    public void setFloatingPane(Pane pane) {
        floatingPaneContent.getChildren().add(pane);
        
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
    }

    public void clearListener(){
        FLoatingPane.maxHeightProperty().unbind();
        FLoatingPane.maxWidthProperty().unbind();
    }
}