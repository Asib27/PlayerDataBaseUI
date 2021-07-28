/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.Drivers.Service;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.Feedback;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FeedbackPageController implements Initializable, Driver{
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField titleField;
    
    @FXML
    private TextArea detailsField;
    
    private final String driverName = "Feedback Page";
    private Service service;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
    
    
    
    @Override
    public void clearListener() {
    
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public Pane getGuiPane() {
        return anchorPane;
    }

    @Override
    public void update(DatabaseManager databaseManager) {
        
    }
    
    @FXML
    private void sendFeedback(){
        service.sendFeedback(new Feedback(titleField.getText(), detailsField.getText()));
    }
}
