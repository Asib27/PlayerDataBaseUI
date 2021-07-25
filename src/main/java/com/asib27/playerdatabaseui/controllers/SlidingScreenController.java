/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.ControllerHelper.SplitedScreenInt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
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
    
    @FXML
    private SplitPane splitPane;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addListener();
    }
    
    public void addListener(){        
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

    public SplitPane getSplitPane() {
        return splitPane;
    }
    
    @FXML
    private void collapseFloatingPane(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), FLoatingPane);
        RotateTransition rt = new RotateTransition(Duration.millis(500), UpButton);

        rt.setByAngle(180);
        if(isUp){
            tt.setByY(FLoatingPane.getHeight() -  upperControl.getHeight());
            isUp = false;

        }
        else{
            tt.setByY(-(FLoatingPane.getHeight() -  upperControl.getHeight()));
            isUp = true;
        }

        ParallelTransition pt = new ParallelTransition(tt, rt);
        pt.play();
    }

    public boolean isFlotingPaneCollapsed() {
        return isUp;
    }

    public void setFloatingPointCollapsed(boolean isUp) {
        if(this.isUp != isUp){
//            if(isUp){
//                FLoatingPane.setTranslateY(FLoatingPane.getTranslateY() + FLoatingPane.getHeight() -  upperControl.getHeight());
//                this.isUp = false;
//            }
//            else{
//                System.out.println("Here");
//                FLoatingPane.setTranslateY(( FLoatingPane.getHeight() -  upperControl.getHeight()) );
//                this.isUp = false;
//            }
            //collapseFloatingPane();
        }
    }
    
    
}