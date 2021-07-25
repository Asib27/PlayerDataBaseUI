/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.ControllerHelper.SplitedScreenInt;
import com.asib27.playerdatabaseui.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class SearchScreenController implements Initializable, SplitedScreenInt {
    @FXML
    private AnchorPane paneLeftUp;

    @FXML
    private AnchorPane paneLeftDown;

    @FXML
    private AnchorPane paneRight;
    
    @FXML
    private SplitPane mainSplitPane;
    
    @FXML
    private SplitPane leftSplitPane;
    
    
    @FXML
    private TitledPane leftDowntitlePane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        leftDowntitlePane.expandedProperty().addListener(this::divisionChangeListener);
    }    

    @Override
    public void setLeftPane(Pane paneLeft) {
        this.paneLeftUp.getChildren().add(paneLeft);
        
        AnchorPane.setRightAnchor(paneLeft, 0.0);
        AnchorPane.setLeftAnchor(paneLeft, 0.0);
        AnchorPane.setTopAnchor(paneLeft, 0.0);
        AnchorPane.setBottomAnchor(paneLeft, 0.0);
        
        
    }

    @Override
     public void setFloatingPane(Pane pane) {
        leftDowntitlePane.setContent(pane);
    }

    @Override
    public void setRightPane(Pane pane) {
        this.paneRight.getChildren().add(pane);
        
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
    }

    
    private void divisionChangeListener(ObservableValue<? extends Boolean> cl, Boolean ov, Boolean nv){
        if(nv){
            leftSplitPane.setDividerPosition(0, .5);
        }else{
            leftSplitPane.setDividerPosition(0, .95);
        }
    }
    
    public void clearListener(){
        leftDowntitlePane.expandedProperty().removeListener(this::divisionChangeListener);
    }

    @Override
    public SplitPane getSplitPane() {
        return mainSplitPane;
    }

    @Override
    public void setFloatingPointCollapsed(boolean isUp) {
        leftDowntitlePane.setExpanded(isUp);
    }
    
    
}
