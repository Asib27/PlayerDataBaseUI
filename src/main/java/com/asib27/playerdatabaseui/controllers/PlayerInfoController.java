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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author USER
 */
public class PlayerInfoController implements Initializable {
     @FXML
    private ImageView playerImage;

    @FXML
    private Label nameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private ImageView countryImage;

    @FXML
    private Label countryLabel;

    @FXML
    private ImageView clubImage;

    @FXML
    private Label clubName;

    @FXML
    private TextField ageField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField jurseyNumberField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    public ImageView getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String imageName) {
        Image image = playerImage.getImage();
        image = new Image(getClass().getResourceAsStream(imageName));
        playerImage.setImage(image);
    }

    public ImageView getCountryImage() {
        return countryImage;
    }

    public void setCountryImage(String imageName) {
        Image image = this.countryImage.getImage();
        image = new Image(getClass().getResourceAsStream(imageName));
        countryImage.setImage(image);
    }

    public ImageView getClubImage() {
        return clubImage;
    }

    public void setClubImage(String imageName) {
        Image image = clubImage.getImage();
        image = new Image(getClass().getResourceAsStream(imageName));
        clubImage.setImage(image);
    }
    
    public void setName(String name){
        nameLabel.setText(name);
    }
    
    public void setClub(String name){
        clubName.setText(name);
    }
    
    public void setCountry(String name){
        countryLabel.setText(name);
    }
    
    public void setPosition(String position){
        positionLabel.setText(position);
    }
    
    public void setAge(String age){
        ageField.setText(age);
    }
    
    public void setHeight(String age){
        heightField.setText(age);
    }
    
    public void setJurseyNumber(String number){
        jurseyNumberField.setText(number);
    }
    
    public void setSalary(String number){
        salaryField.setText(number);
    }
}
