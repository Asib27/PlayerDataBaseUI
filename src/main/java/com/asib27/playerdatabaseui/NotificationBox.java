/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabaseui.Drivers.Service;
import com.asib27.playerdatabaseui.util.Notification;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author USER
 */
public class NotificationBox extends HBox{
    Notification notification;
    Service service;

    public NotificationBox(Notification notification, Service service) {
        super();
        this.notification = notification;
        this.service = service;
        addContent();
    }
    
    private void addContent(){
        
        TextArea text = new TextArea(notification.getMessage());
        text.setPrefRowCount(2);
        text.setPrefColumnCount(25);
        text.setWrapText(true);
        text.setEditable(false);
        text.setId("text");
        //text.lookup("#text .content").setCursor(Cursor.DEFAULT);
        
        text.setOnMouseClicked(this::mouseClickHandler);
        
        super.getChildren().addAll(text);
        super.setAlignment(Pos.CENTER);
    }
    
    private void mouseClickHandler(MouseEvent event){
        Stage stage = new Stage(StageStyle.UTILITY);
        
        PlayerTransaction pt = notification.getData();
        Player player = pt.getPlayer();
        PlayerInfoCard infoCard = new PlayerInfoCard(player);
        
        Label label = new Label(notification.getMessage());
        infoCard.setTop(label);
        BorderPane.setAlignment(label, Pos.CENTER);
        
        Button b1 = new Button("Cancel");
        b1.setOnAction((t) -> { 
            stage.close();
        });
        
        b1.setCancelButton(true);
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        addNotificationSpecifics(hb, stage);
        hb.getChildren().add(b1);
        infoCard.setBottom(hb);
        BorderPane.setAlignment(hb, Pos.CENTER);
        
        Scene scene = new Scene(infoCard);
        
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
    }

    private void addNotificationSpecifics(HBox hb, Stage stage) {
        Button b2 = new Button();
        Button b3 = new Button();
        
        PlayerTransaction pt = notification.getData();
        Player player = pt.getPlayer();
        switch(notification.getType()){
            case SELL_REQUEST->{
                b2.setText("Buy Player");
                hb.getChildren().add(b2);
                b2.setOnAction((t) -> {
                    MessageBoxUtil.playerBuyRequestMessageBox(player, service);
                    stage.close();
                });
                if(player.getClub().equals(service.getClubName()))
                    b2.setDisable(true);
            }
                
            case BUY_REQUEST->{
                b2.setText("Approve");
                b2.setOnAction((t) -> {
                    MessageBoxUtil.buyRequestApproval(pt, service);
                    stage.close();
                });
                b3.setText("Decline");
                b3.setOnAction((t) -> {
                    MessageBoxUtil.buyRequestDecline(pt, service);
                    stage.close();
                });
                hb.getChildren().addAll(b2, b3);
                
            }
        }
        
    }
}
