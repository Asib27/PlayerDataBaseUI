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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class PlayerInfosController implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField ageField;
    
    @FXML
    private TextField clubField;
    
    @FXML
    private TextField countryField;
    
    @FXML
    private TextField posField;
    
    @FXML
    private TextField salaryField;
    
    @FXML
    private TextField jurseyField;
    
    private Player player;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Player p1 = new Player();
        p1.setName("Asib Rahman");
        nameField.setText(p1.getName());
    }    

    public void setPlayer(Player player) {
        this.player = player;
        
        nameField.setText(player.getName());
        ageField.setText(String.valueOf(player.getAge()));
        clubField.setText(player.getClub());
        countryField.setText(player.getCountry());
        posField.setText(String.valueOf(player.getPosition()));
        jurseyField.setText(String.valueOf(player.getJurseyNumber()));
        salaryField.setText(String.valueOf(player.getSalary()));
    }

    public Player getPlayer() {
        return player;
    }
    
    
}
