/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.asib27.playerdatabasesystem.Player;
/**
 * FXML Controller class
 *
 * @author USER
 */
public class PlayerInfoController implements Initializable {
    Player p1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        p1 = new Player();
        p1.setAge(28);
    }    
    
}
