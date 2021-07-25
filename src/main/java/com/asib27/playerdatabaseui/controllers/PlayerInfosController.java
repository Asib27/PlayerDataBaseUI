/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabaseui.PlayerInfoCard;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class PlayerInfosController implements Initializable {
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private BorderPane playerCard;
    
    @FXML
    private Button leftButton, rightButton;
    
    private Player player;
    private PlayerInfoCard playerInfoCard = new PlayerInfoCard();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        playerCard.setCenter(playerInfoCard);
        
    }    

    public void setPlayer(Player player) {
        this.player = player;
        playerInfoCard.setPlayer(player);
        playerCard.setCenter(playerInfoCard);
    }

    public Player getPlayer() {
        return player;
    }
    
    @FXML
    private void closeButtonPressed(){
        
    }

    public Button getLeftButton() {
        return leftButton;
    }

    public void setLeftButton(Button leftButton) {
        this.leftButton = leftButton;
    }

    public Button getRightButton() {
        return rightButton;
    }

    public void setRightButton(Button rightButton) {
        this.rightButton = rightButton;
    }

    
}
