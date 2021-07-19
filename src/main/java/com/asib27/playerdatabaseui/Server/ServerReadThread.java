/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.NetworkDataEnum;
import com.asib27.playerdatabaseui.util.NetworkUtil;
import com.asib27.playerdatabaseui.util.Notification;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import com.asib27.playerdatabaseui.util.UserInfo;
import java.io.IOException;
import java.net.Socket;
import java.util.Set;

/**
 *
 * @author USER
 */
public class ServerReadThread implements Runnable, UpdateListener{
    private UserInfo userInfo;
    private NetworkUtil networkUtil;
    private ServerInt server;
    private PlayerDataBaseInt database;

    public ServerReadThread(UserInfo userInfo, NetworkUtil networkUtil, ServerInt serverInt) {
        this.userInfo = userInfo;
        this.networkUtil = networkUtil;
        this.server = serverInt;
        database = server.getDatabase();
    }
    
    public ServerReadThread(UserInfo userInfo, Socket socket, ServerInt serverInt) throws IOException {
        this(userInfo, new NetworkUtil(socket), serverInt);
    }

    @Override
    public void run() {
        try {
            sendDataBase();
            
            while(true){
                Object read = networkUtil.read();
                NetworkData nd = validityCheck(read);
                
                if(nd == null)
                    continue;
                
                processRequest(nd);
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(PlayerDataBaseInt database) {
        this.database = database;
        NetworkData nd = new NetworkData(NetworkDataEnum.DATABASE, new DatabaseManager(database));
        
        try {
            networkUtil.write(nd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendDataBase() throws IOException {
        DatabaseManager db = new DatabaseManager(database);
        networkUtil.write(new NetworkData(NetworkDataEnum.DATABASE, db));
    }
    
    private NetworkData validityCheck(Object read) throws IOException{
        if(!(read instanceof NetworkData)){
            writeErrorMessage("Sent object is not NetworkData");
            return null;
        }
        
        NetworkData nd = (NetworkData) read;
        
        if(!nd.getDataType().isServerRequest()){
            writeErrorMessage("Wrong type of request");
            return null;
        }
        
        if(nd.getDataType() == NetworkDataEnum.LOGIN){
            writeErrorMessage("Login request on a logged in account");
            return null;
        }
        
        if(!nd.checkDataValidity()){
            writeErrorMessage("Wrong data type");
            return null;
        }
        
        return nd;
    }
    
    private void writeErrorMessage(String msg) throws IOException{
        NetworkData nd = new NetworkData(NetworkDataEnum.FAILED, msg);
        networkUtil.write(nd);
    }

    private void processRequest(NetworkData nd) throws IOException {
        switch(nd.getDataType()){
            case LOGOUT-> processLogout();
            case SELL_REQUEST-> processSellRequest(nd.getData());
            case BUY_REQUEST-> processBuyRequest(nd.getData());
        }
    }

    private void processLogout() throws IOException {
        server.handleLogout(userInfo, networkUtil);
        networkUtil.write(new NetworkData(NetworkDataEnum.SUCCESS, "Logout Succesful"));
        new LoginThread(networkUtil, server).run();
    }

    @Override
    public void send(NetworkData networkData) {
        try {
            networkUtil.write(networkData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void processSellRequest(PlayerTransaction pt) throws IOException {
        Player player = pt.getPlayer();
        
        if(!database.contains(player)){
            writeErrorMessage("Player with sell request does not exist");
        }
        else if(!player.getClub().equals(userInfo.getClubName())){
            writeErrorMessage("Player does not belongs to your club");
        }
        
        else{
            server.addPlayerOnSell(pt);
            String message = pt.getPlayer().getClub() + "wants to sell player";
            Notification notification = new Notification(Notification.Type.SELL_REQUEST, message, pt);

            NetworkData nd = new NetworkData(NetworkDataEnum.NOTIFICATION, notification);
            server.send(nd, null);
        }
        
    }

    private void processBuyRequest(PlayerTransaction pt) {
        Set<PlayerTransaction> playerOnSell = server.getPlayerOnSell();
    }
}
