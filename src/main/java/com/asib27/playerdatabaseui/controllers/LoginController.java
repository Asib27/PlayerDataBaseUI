/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

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
    private Label nameLabel;

    @FXML
    private TextField clubnameBox;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private Button loginButton;

    @FXML
    private VBox vboxContainer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubnameBox.prefWidthProperty().bind(vboxContainer.widthProperty().divide(2));
        passwordBox.prefWidthProperty().bind(vboxContainer.widthProperty().divide(2));
    }

    public void initialize(){
        clubnameBox.prefWidthProperty().bind(vboxContainer.widthProperty().divide(2));
    }
    
    @FXML
    void loginButtonPressed(ActionEvent event) {
        
    }
}
