/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Drivers;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabaseui.MessageBoxUtil;
import com.asib27.playerdatabaseui.PlayerInfoCard;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class SellMenuDriver implements Driver{
    ScrollPane scrollPane = new ScrollPane();
    BorderPane borderPane = new BorderPane();
    TilePane tilePane = new TilePane(Orientation.HORIZONTAL);
    
    final String driverName = "My Club";
    
    String clubName;
    Service service;

    private SellMenuDriver() {
        tilePane.setPrefColumns(2);
        scrollPane.setContent(tilePane);
        borderPane.setCenter(scrollPane);
    }
    
    

    public SellMenuDriver(Service service) {
        this();
        this.clubName = service.getClubName();
        this.service = service;
        intialize();
    }
    
    static Task getLoader(Service service){
        return new MainLoader(service);
    }
    
    @Override
    public Pane getGuiPane() {
        return borderPane;
    }

    @Override
    public void clearListener() {
        
    }

    @Override
    public String getDriverName() {
        return driverName;
    }
    
    

    @Override
    public void update(DatabaseManager databaseManager) {
        //System.out.println("Called");
        Platform.runLater(() -> {
            //ObservableList<Node> children = tilePane.getChildren();
            tilePane.getChildren().removeIf((t) -> {
                return true;
            });
            intialize();
        });
    }

    private void intialize() {
        Player[] query = service.getDatabase().getDataBase().query(PlayerAttribute.CLUB, clubName);


        ArrayList<PlayerInfoCard> infoCards =  new ArrayList<>();
        for (int i = 0; i < query.length; i++) {
            Player player = query[i];

            PlayerInfoCard card = tileInitialize(player);
            infoCards.add(card);

        }

        tilePane.getChildren().addAll(infoCards);
    }
    
    private PlayerInfoCard tileInitialize(Player player){
        PlayerInfoCard card = new PlayerInfoCard(player);

        Button sellButton = new Button("Sell Player");
        BorderPane.setAlignment(sellButton, Pos.CENTER);
        card.setBottom(sellButton);
        
        card.setStyle("-fx-border-color : black;"
                + "-fx-border-width : 2;"
                + "-fx-border-insets : 10;"
                + "-fx-border-radius : 2;");
        
        BorderPane.setMargin(sellButton, new Insets(5));

        
        sellButton.setOnAction((t) -> {
            MessageBoxUtil.playerSellMessageBox(player, service);
        });
        
        
        return card;
    }
    
    public static class MainLoader extends Task<SellMenuDriver>{
        Service service;
        String clubName;

        public MainLoader(Service service) {
            this.service = service;
            this.clubName = service.getClubName();
            updateTitle("loading sell Menu");
        }
        
        @Override
        protected SellMenuDriver call() throws Exception {
            SellMenuDriver result = new SellMenuDriver();
            result.service = service;
            result.clubName = clubName;
            
            Player[] query = service.getDatabase().getDataBase().query(PlayerAttribute.CLUB, clubName);
        
            updateMessage("loading..");
            ArrayList<PlayerInfoCard> infoCards =  new ArrayList<>();
            for (int i = 0; i < query.length; i++) {
                Player player = query[i];

                PlayerInfoCard infoCard = result.tileInitialize(player);
                infoCards.add(infoCard);
                updateProgress(i, query.length);
            }
        
            result.tilePane.getChildren().addAll(infoCards);
            
            updateMessage("Done");
            updateProgress(1, 1);
            return result;
        }
        
    }
}
