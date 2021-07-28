/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.CustomControls;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabaseui.Drivers.Service;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class MessageBoxUtil {
    public static void playerSellMessageBox(Player player, Service service){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "PLayer will be updated in sell dashboard, Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> showAndWait = alert.showAndWait();
            
        if(showAndWait.isPresent() && showAndWait.get() == ButtonType.YES){
            PlayerTransaction pt = new PlayerTransaction(player);
            pt.setSeller(service.getClubName());

            service.sendSellRequest(pt);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Player is updated on sell dashboard");
            alert2.setHeaderText("Information");
            alert2.setContentText("Player is updated on sell dashboard");
            alert2.showAndWait();
        }
        
    }
    
    public static void playerBuyRequestMessageBox(Player player, Service service){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to buy"
                + " this player? This task is not reversible", ButtonType.YES, ButtonType.NO);
        
        alert.showAndWait().ifPresent((t) -> {
            if(t == ButtonType.YES){
                PlayerTransaction pt = new PlayerTransaction(player);
                
                pt.setBuyer(service.getClubName());
                service.sendBuyRequest(pt);
                
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Request Succesfully sent to owner club");
                alert2.setHeaderText("Information");
                alert2.showAndWait();
            }
        });
    }
    
    /**
     * Not implemented yet
     * @param player
     * @param service 
     */
    public static void playerSellRequestCancel(Player player, Service service){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to cancel selling?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> showAndWait = alert.showAndWait();
            
        if(showAndWait.isPresent() && showAndWait.get() == ButtonType.YES){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Sell cancelled");
            alert2.setHeaderText("Information");
            alert2.showAndWait();
        }
        
    }
    
    public static void buyRequestApproval(PlayerTransaction pt, Service service){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to sell this player permanantly?"
                , ButtonType.YES, ButtonType.NO);
        
        alert.showAndWait().ifPresent((t) -> {
            if(t == ButtonType.YES){
                pt.setSeller(service.getClubName());
                service.sendBuyRequestAproval(pt);
                
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Request Succesfully sent to server");
                alert2.setHeaderText("Information");
                alert2.showAndWait();
            }
        });
    }
    
    public static void buyRequestDecline(PlayerTransaction pt, Service service){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to decline request?"
                , ButtonType.YES, ButtonType.NO);
        
        alert.showAndWait().ifPresent((t) -> {
            if(t == ButtonType.YES){
                pt.setSeller(service.getClubName());
                service.sendBuyRequestDenial(pt);
                
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Request Succesfully sent to server");
                alert2.setHeaderText("Information");
                alert2.showAndWait();
            }
        });
    }
}
