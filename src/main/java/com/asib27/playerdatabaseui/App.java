package com.asib27.playerdatabaseui;

import com.asib27.playerdatabaseui.controllers.SearchScreenController;
import com.asib27.playerdatabasesystem.*;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * JavaFX App
 */
public class App extends Application implements Service{

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane bp = (BorderPane) loadFXML("/fxml/Main.fxml");
        Driver dv = new SearchMenu(this);
        bp.setCenter(dv.getGuiPane());
        
//        FXMLLoader searchScreenLoader = new FXMLLoader(App.class.getResource("/fxml/SearchScreen.fxml"));
//        //searchScreenLoader.load();
//        SplitPane ap = searchScreenLoader.load();
//        SearchScreenController searchScreenController = searchScreenLoader.getController();
//
//        //searchScreenController.setPaneRight((AnchorPane) loadFXML("/fxml/PlayerInfos.fxml"));
//        searchScreenController.setPaneLeftDown((AnchorPane) loadFXML("/fxml/SearchMenu.fxml"));
//        searchScreenController.setPaneRight((AnchorPane) loadFXML("/fxml/Chart.fxml"));
//        
//        try{
//            searchScreenController.setPaneLeftUp((AnchorPane) loadFXML("/fxml/PlayerTable.fxml"));
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        
        scene = new Scene(bp, 1040, 640);
        
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public boolean authenticate(PasswordManager pass) {
        return true;
    }

    @Override
    public DatabaseManager getDatabase() {
        PlayerDataBaseInt pdb = new PlayerDataBase(new File("G:\\Java\\PlayerDataBaseSystem\\src\\main\\java\\com\\asib27\\playerdatabasesystem\\playersData.txt"));
        return new DatabaseManager(pdb, null);
    }
    
    public static FXMLLoader getFXMLLoader(String fxml){
        return new FXMLLoader(App.class.getResource("/fxml/" + fxml));
    }
}