/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerDataBase;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.NetworkUtil;
import com.asib27.playerdatabaseui.util.ObserverUtil;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import com.asib27.playerdatabaseui.util.UserInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 *
 * @author USER
 */
public class ServerMain extends ObserverUtil<UpdateListener> implements ServerInt{
    private PlayerDataBaseInt database;
    private final String databaseLocation = "src\\main\\resources\\Server\\database.ser";
    
    ConcurrentHashMap<UserInfo, UpdateListener> activeUsers= new ConcurrentHashMap<>();
    ConcurrentSkipListSet<PlayerTransaction> playersOnSell = new ConcurrentSkipListSet<>();
    
    public static void main(String[] args) {
        //databaseWriter();
        new ServerMain().run();
    }
    
    public void run(){
        database = readDatabase(new File(databaseLocation));
        
        try {
            ServerSocket serverSocket = new ServerSocket(NetworkData.portNumber);
            System.out.println("Server is up.. waiting for client");
            
            while(true){
                Socket socket = serverSocket.accept();
                Thread t = new Thread(new LoginThread(socket, this));
                t.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public PlayerDataBaseInt getDatabase() {
        return database;
    }

    @Override
    public Set<PlayerTransaction> getPlayerOnSell() {
        return playersOnSell;
    }

    @Override
    public void addPlayerOnSell(PlayerTransaction player) {
        playersOnSell.add(player);
    }
    
    

    @Override
    public boolean changePlayerInfo(Player oldPlayerInfo, Player newPlayerInfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //check player infos
    }

    @Override
    public void handleNewLogin(UserInfo userInfo, NetworkUtil networkUtil) {
        ServerReadThread serverReadThread = new ServerReadThread(userInfo, networkUtil, this);
        serverReadThread.run();
        
        activeUsers.put(userInfo, serverReadThread);
    }

    @Override
    public void handleLogout(UserInfo info, NetworkUtil networkUtil) {
        activeUsers.remove(info);
    }

    @Override
    public void send(NetworkData data, UserInfo user) {
        if(user != null){
            UpdateListener listener = activeUsers.get(user);
            listener.send(data);
        }
        else{
            activeUsers.keySet().forEach((t) -> {
                activeUsers.get(t).send(data);
            });
        }
    }
    
    

    @Override
    protected void updator(UpdateListener t) {
        t.update(database);
    }
    
    

    private PlayerDataBaseInt readDatabase(File file) {
        PlayerDataBaseInt pdb = null;
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(databaseLocation)))){
            pdb = (PlayerDataBaseInt) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            System.exit(1);
        }
        
        return pdb;
    }
    
    static private void databaseWriter(){
        PlayerDataBaseInt pdb = new PlayerDataBase(new File("G:\\Java\\PlayerDataBaseSystem\\src\\main\\java\\com\\asib27\\playerdatabasesystem\\playersData.txt"));
        
        
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("src\\main\\resources\\Server\\database.ser")))){
            oos.writeObject(pdb);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
