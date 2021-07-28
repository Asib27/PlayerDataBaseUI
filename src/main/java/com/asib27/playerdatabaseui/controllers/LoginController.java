/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.Client.LoginDriver;
import com.asib27.playerdatabaseui.Drivers.Service;
import com.asib27.playerdatabaseui.util.PasswordManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
 * @author USER
 */
public class LoginController implements Initializable {
    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField clubnameBox;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private Button loginButton;
    
    @FXML
    private Label statusLabel;
    
    LoginDriver loginDriver;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addListener();
    }
    
    public void addListener(){
        loginButton.disableProperty().bind(clubnameBox.textProperty().isEmpty().or(passwordBox.textProperty().isEmpty()));
    }
    
    @FXML
    void loginButtonPressed(ActionEvent event) {
        PasswordManager pm = new PasswordManager(clubnameBox.getText(), passwordBox.getText());
        
        if(loginDriver == null){
            statusLabel.setText("Error occured : Login Driver is not set");
        }
        else{
            String msg = loginDriver.sendLoginInfo(pm);
            if(msg != null) statusLabel.setText(msg);
            else statusLabel.setText("Waiting for server response");
        }
    }
    
    public void setStatus(String msg){
        statusLabel.setText(msg);
    }

    public void setLoginDriver(LoginDriver loginDriver) {
        this.loginDriver = loginDriver;
    }
    
    
}
