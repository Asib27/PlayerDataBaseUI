/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Drivers;

import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabaseui.App;
import com.asib27.playerdatabaseui.ControllerHelper.SplitedScreenInt;
import com.asib27.playerdatabaseui.controllers.*;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 *
 * @author USER
 */
public class TableSearchInfoDriverUtil {
    protected SearchMenuController searchMenuController;
    protected SplitedScreenInt searchScreenController;
    protected PlayerInfosController playerInfoController;
    protected PlayerTableController playerTableController;
    
    protected BorderPane searchScreenPane;
    protected BorderPane searchMenuPane;
    protected BorderPane playerTablePane;
    protected BorderPane playerInfoPane;
    
    protected void loadSearchScreen(String fileName){
        try {
            FXMLLoader loader = paneFactory(fileName);
            searchScreenPane = loader.load();
            searchScreenController = loader.getController();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    protected void loadLeftScreen(String fileName){
        try {
            FXMLLoader loader = paneFactory(fileName);
            playerTablePane = loader.load();
            playerTableController = loader.getController();
            searchScreenController.setLeftPane(playerTablePane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    protected void loadRightScreen(String filename){
        try {                
            FXMLLoader loader = paneFactory(filename);
            playerInfoPane = loader.load();
            playerInfoController = loader.getController();
            searchScreenController.setRightPane(playerInfoPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    protected void loadFloatingScreen(String fileName){
        try {                
            FXMLLoader loader = paneFactory(fileName);
            searchMenuPane = loader.load();
            searchMenuController = loader.getController();
            searchScreenController.setFloatingPane(searchMenuPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private FXMLLoader paneFactory(String name){
        return switch(name){
            case "SearchScreen" ->    App.getFXMLLoader("SlidingScreen.fxml");
            case "SearchMenu"   -> App.getFXMLLoader("SearchMenu.fxml");
            case "PlayerTable"  -> App.getFXMLLoader("PlayerTable.fxml");
            case "PlayerInfos"  ->App.getFXMLLoader("PlayerInfos.fxml");
            default -> null;
        };
    }
    
    protected void animateSplitPane(SplitPane splitPane, double target){
        if(splitPane.getDividerPositions()[0] != target){
            KeyValue kv = new KeyValue(splitPane.getDividers().get(0).positionProperty(), target);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), kv));
            timeline.play();
        }
    }
    
    protected void initTableView(TableView table){
        ObservableList<TableColumn> columns = FXCollections.observableArrayList();
        
        for (var field : PlayerAttribute.values()) {
            TableColumn col = new TableColumn(field.toString());
            col.setCellValueFactory(new PropertyValueFactory<>(field.toString()));
            columns.add(col);
        }
        
        table.getColumns().addAll(columns);
    }
}
