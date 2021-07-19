package com.asib27.playerdatabaseui;

import com.asib27.playerdatabaseui.util.PasswordManager;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.controllers.SearchScreenController;
import com.asib27.playerdatabasesystem.*;
import com.asib27.playerdatabaseui.controllers.MainController;

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
        /*
        FXMLLoader fxmlLoader = getFXMLLoader("Main.fxml");
        BorderPane bp = fxmlLoader.load();
        Driver dv = new SearchMenuDriver(this);
        MainController mc = fxmlLoader.getController();
        mc.setContent(dv.getGuiPane());
        */
        MainDriver mainDriver = new MainDriver(this);
        
        scene = new Scene(mainDriver.getGuiPane(), 1040, 640);
        //scene = new Scene(getFXMLLoader("SlidingScreen.fxml").load());
        
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