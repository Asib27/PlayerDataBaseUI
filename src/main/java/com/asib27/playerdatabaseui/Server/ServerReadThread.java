/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.util.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
            sendNotifications();
            sendPlayersOnSell();
            
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
    
    private void sendNotifications() throws IOException{
        NotificationStore ns = server.getNotificationStore();
        ArrayList<Notification> notifications = ns.getNotification(userInfo);
        NetworkData nd = new NetworkData(NetworkDataEnum.All_NOTIFICATIONS, notifications);
        networkUtil.write(nd);
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
            case BUY_REQUEST_APPROVED-> processBuyRequestApprover(nd.getData());
            case BUY_REQUEST_DECLINED-> processBuyRequestDeclined(nd.getData());
            case FEEDBACK-> processFeedback(nd.getData());
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
            String message = pt.getPlayer().getClub() + " wants to sell " + player.getName();
            Notification notification = new Notification(Notification.Type.SELL_REQUEST, message, pt);

            NetworkData nd = new NetworkData(NetworkDataEnum.NOTIFICATION, notification);
            server.send(nd, null);
        }
        
    }

    private void processBuyRequest(PlayerTransaction pt) throws IOException {
        Player player = pt.getPlayer();
        
        if(!database.contains(player)){
            writeErrorMessage("PLayer info changed or does not belongs");
            return ;
        }
        
        if(pt.getBuyer() == null)
            pt.setBuyer(userInfo.getClubName());
        
        String clubname = player.getClub();
        
        String message = pt.getBuyer() + " wants to buy " + player.getName();
        Notification notification = new Notification(Notification.Type.BUY_REQUEST, message, pt);
        
        NetworkData data = new NetworkData(NetworkDataEnum.NOTIFICATION, notification);
        server.send(clubname, data);
    }

    private void processBuyRequestApprover(PlayerTransaction pt) throws IOException {
        String buyer = pt.getBuyer();
        
        if(buyer == null){
            writeErrorMessage("Sell request approval does not contain buyer name");
            return ;
        }
        
        Player oldPlayer = pt.getPlayer();
        if(!oldPlayer.getClub().equals(userInfo.getClubName())){
            writeErrorMessage("Request to sell player of other club");
            return ;
        }
        
        Player newPlayer = new Player(oldPlayer);
        newPlayer.setClub(buyer);

        server.removePlayerOnSell(pt);
        boolean changePlayerInfo = server.changePlayerInfo(oldPlayer, newPlayer);
        if(changePlayerInfo){
            String message = pt.getPlayer().getName() + " sold from " + pt.getSeller() + " to " + pt.getBuyer();
            Notification notification1 = new Notification(Notification.Type.BUY_SECCESS, message, pt);
            Notification notification2 = new Notification(Notification.Type.SELL_SUCCEED, message, pt);
            
            NetworkData nd1 = new NetworkData(NetworkDataEnum.NOTIFICATION, notification1);
            NetworkData nd2 = new NetworkData(NetworkDataEnum.NOTIFICATION, notification2);
            
            server.send(buyer, nd1);
            networkUtil.write(nd2);
        }
        else{
            writeErrorMessage("Some Error Occured, Player not selled");
        }
    }

    private void processBuyRequestDeclined(PlayerTransaction pt) throws IOException {
        String buyer = pt.getBuyer();
        
        if(buyer == null){
            writeErrorMessage("Sell request denial does not contain buyer name");
            return ;
        }
        
        
        Player player = pt.getPlayer();
        if(!player.getClub().equals(userInfo.getClubName())){
            writeErrorMessage("declinig buy request of other");
            return ;
        }
        
        
        String message = userInfo.getClubName() + " declined your buy request of " + pt.getPlayer().getName();
        Notification n = new Notification(Notification.Type.BUY_DECLINED, message, pt);
        NetworkData nd = new NetworkData(NetworkDataEnum.NOTIFICATION, n);
        server.send(buyer, nd);
    }

    private void sendPlayersOnSell() throws IOException {
        NetworkData nd = new NetworkData(NetworkDataEnum.PLAYER_ON_SELL, server.getPlayerOnSell());
        networkUtil.write(nd);
    }

    @Override
    public void updatePlayersOnSell(Set<PlayerTransaction> playerOnSell) {
        NetworkData nd = new NetworkData(NetworkDataEnum.PLAYER_ON_SELL, playerOnSell);
        
        try {
            networkUtil.write(nd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void processFeedback(Feedback feedback) {
        System.out.println("User " + userInfo.getClubName() + "send a feedback : ");
        System.out.println(feedback);
    }
}

