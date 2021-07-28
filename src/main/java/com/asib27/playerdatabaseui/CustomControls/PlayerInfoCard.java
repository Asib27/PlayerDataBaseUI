/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.CustomControls;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabaseui.App;
import com.asib27.playerdatabaseui.controllers.PlayerInfoController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author USER
 */
public class PlayerInfoCard extends BorderPane{
    String placeHolderText = "No info to view";
    String path = "src/main/resources";
    String logoDirPath = "/image/ClubLogo/";
    String playerDirPath = "/image/Players/";
    String flagDirPath = "/image/Flags/";
    Player player;
    
    PlayerInfoController playerInfoController;
    AnchorPane anchorPane ;

    public PlayerInfoCard() {
        super.setCenter(new Label(placeHolderText));
        
        FXMLLoader fxmlLoader = App.getFXMLLoader("PlayerInfo.fxml");
        try {
            anchorPane = fxmlLoader.load();
            playerInfoController = fxmlLoader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public PlayerInfoCard(Player player) {
        this();
        setPlayer(player);
    }
    
    

    public String getPlaceHolderText() {
        return placeHolderText;
    }

    public void setPlaceHolderText(String placeHolderText) {
        this.placeHolderText = placeHolderText;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        
        playerInfoController.setName(player.getName());
        playerInfoController.setCountry(player.getCountry());
        playerInfoController.setClub(player.getClub());
        playerInfoController.setPosition(player.getPosition());
        playerInfoController.setAge(String.valueOf(player.getAge()));
        playerInfoController.setSalary(String.valueOf(player.getSalary()));
        playerInfoController.setHeight(String.valueOf(player.getHeight()));
        playerInfoController.setJurseyNumber(String.valueOf(player.getJursey()));
        
        playerInfoController.setClubImage(nameToFileName(player.getClub() , "logo"));
        playerInfoController.setCountryImage(nameToFileName(player.getCountry(), "flag"));
        playerInfoController.setPlayerImage(nameToFileName(player.getName(), "player"));
        
        super.setCenter(anchorPane);
    }
    
    private String nameToFileName(String name, String imageType){
        StringBuilder sb = new StringBuilder(name.toLowerCase());
        
        for (int i = 0; i < sb.length(); i++) {
            if(sb.charAt(i) == ' ')
                sb.setCharAt(i, '_');
        }
        
        if(imageType.equals("logo")){
            name = logoDirPath.concat(sb.toString());
        }else if(imageType.equals("player")){
            name = playerDirPath.concat(sb.toString());
        }else if(imageType.equals("flag")){
            name = flagDirPath.concat(sb.toString());
        }
        
        File f = new File(path + name + ".png");
        if(f.exists()){
            return name + ".png";
        }else{
            return name + ".jpg";
        }
    }
}
