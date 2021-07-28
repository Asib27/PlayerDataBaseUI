package com.asib27.playerdatabaseui;

import com.asib27.playerdatabaseui.Drivers.Service;
import com.asib27.playerdatabaseui.Drivers.MainDriver;
import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.util.PasswordManager;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.controllers.SearchScreenController;
import com.asib27.playerdatabasesystem.*;
import com.asib27.playerdatabaseui.Client.ClientInt;
import com.asib27.playerdatabaseui.Client.ClientReadThread;
import com.asib27.playerdatabaseui.Client.CollectorThread;
import com.asib27.playerdatabaseui.Client.LoginDriver;
import com.asib27.playerdatabaseui.Drivers.MainDriverInt;
import com.asib27.playerdatabaseui.controllers.LoginController;
import com.asib27.playerdatabaseui.controllers.MainController;
import com.asib27.playerdatabaseui.util.Feedback;
import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.NetworkDataEnum;
import com.asib27.playerdatabaseui.util.NetworkUtil;
import com.asib27.playerdatabaseui.util.Notification;
import com.asib27.playerdatabaseui.util.PlayerTransaction;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collector;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * JavaFX App
 */
public class App extends Application implements Service, ClientInt, LoginDriver{
    private static Scene scene;
    NetworkUtil networkUtil = null;
    LoginController loginController = null;
    
    DatabaseManager databaseManager;
    ArrayList<Notification> allNotifications;

    MainDriverInt mainDriver;
    String clubName;
    
    SimpleObjectProperty<Set<PlayerTransaction>> playerOnSell = new SimpleObjectProperty<>();
    
    @Override
    public void start(Stage stage) throws IOException {
        Thread netThread = new Thread(new ServerConnector());
        netThread.setDaemon(true);
        netThread.start();
        
        FXMLLoader fxmlLoader = getFXMLLoader("Login.fxml");
        BorderPane loginPage  = fxmlLoader.load();
        loginController = fxmlLoader.getController();
        loginController.setLoginDriver(this);
        
        //MainDriver mainDriver = new MainDriver(this);
        
        scene = new Scene(loginPage/*mainDriver.getGuiPane()*/, 1040, 640);
        //scene = new Scene(getFXMLLoader("SlidingScreen.fxml").load());
        
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icons/football_player_logo_dark.png")));
        stage.show();
    }    

    @Override
    public void stop() throws Exception {
        //super.stop();
        if(networkUtil != null){
            networkUtil.write(new NetworkData(NetworkDataEnum.LOGOUT, null));
        }
        
        mainDriver.clearListener();
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
    public DatabaseManager getDatabase() {
        return databaseManager;
    }

    @Override
    public SimpleObjectProperty playersOnSellProperty() {
        return playerOnSell;
    }

    @Override
    public Set<PlayerTransaction> getPlayersOnSell() {
        return playerOnSell.getValue();
    }

    @Override
    public String getClubName() {
        return clubName;
    }
    
    public void sendSellRequest(PlayerTransaction playerTransaction){
        NetworkData nd = new NetworkData(NetworkDataEnum.SELL_REQUEST, playerTransaction);
        try {
            networkUtil.write(nd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendBuyRequest(PlayerTransaction playerTransaction) {
        NetworkData nd = new NetworkData(NetworkDataEnum.BUY_REQUEST, playerTransaction);
        
        try {
            networkUtil.write(nd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void sendBuyRequestAproval(PlayerTransaction playerTransaction){
        NetworkData nd = new NetworkData(NetworkDataEnum.BUY_REQUEST_APPROVED, playerTransaction);
        
        try {
            networkUtil.write(nd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void sendBuyRequestDenial(PlayerTransaction playerTransaction){
        NetworkData nd = new NetworkData(NetworkDataEnum.BUY_REQUEST_DECLINED, playerTransaction);
        
        try {
            networkUtil.write(nd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendFeedback(Feedback feedback) {
        try {
            networkUtil.write(new NetworkData(NetworkDataEnum.FEEDBACK, feedback));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mainDriver.showMessage("Feedback Sent to the Surver Succesfully");
    }
    
    
    public static FXMLLoader getFXMLLoader(String fxml){
        return new FXMLLoader(App.class.getResource("/fxml/" + fxml));
    }

    @Override
    public void takeNotifications(Notification notification) {
        mainDriver.showNotification(notification);
        this.allNotifications.add(notification);
    }

    @Override
    public void takeNotifications(ArrayList<Notification> notifications) {
        this.allNotifications = notifications;
    }

    @Override
    public void takePlayerOnSell(Set<PlayerTransaction> playersOnSell) {
        this.playerOnSell.setValue(playersOnSell);
    }
    

    @Override
    public ArrayList<Notification> getNotification() {
        return allNotifications;
    }
    
    

    @Override
    public void takeLoginResponse(boolean isSuccess, String msg) {
        if(isSuccess){
            Platform.runLater(() -> {
                loginController.setStatus("Login Succesful");
                mainDriver = new MainDriver(this);
                scene.setRoot(mainDriver.getGuiPane());
            });
            // scene changing code
        }
        else{
            Platform.runLater(() -> {
                loginController.setStatus(msg);
            });
        }
    }

    @Override
    public void takeDatabase(DatabaseManager databasse) {
        if(databaseManager == null){
            this.databaseManager = databasse;
        }
        else{
            this.databaseManager = databasse;
            mainDriver.update(databaseManager);
        }
    }

    @Override
    public void takeErrorMessage(String msg) {
        mainDriver.showMessage(msg);
        System.out.println(msg);
    }

    @Override
    public String sendLoginInfo(PasswordManager passwordManager) {
        if(networkUtil == null){
            return "Cannot Connect to surver at this moment";
        }
        else{
            clubName = passwordManager.getUsername();
            NetworkData nd = new NetworkData(NetworkDataEnum.LOGIN, passwordManager);
            try {
                networkUtil.write(nd);
            } catch (IOException ex) {
                ex.printStackTrace();
                return "Some error occured connecting to the surver";
            }
            return null;
        }
    }
    
    public class ServerConnector implements Runnable{

        @Override
        public void run() {
            try {
                Socket sock = new Socket(NetworkData.ipAddress, NetworkData.portNumber);
                networkUtil = new NetworkUtil(sock);
                
                BlockingQueue<NetworkData> queue = new ArrayBlockingQueue(16);
                
                CollectorThread collectorThread = new CollectorThread(networkUtil, queue);
                ClientReadThread clientReadThread = new ClientReadThread(App.this, queue);
                
                Thread dataProcessor = new Thread(clientReadThread);
                dataProcessor.setDaemon(true);
                dataProcessor.start(); //running on new thread
                collectorThread.run(); //running on this same thread
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
}