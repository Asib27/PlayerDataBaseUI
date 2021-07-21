/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.NetworkDataEnum;
import com.asib27.playerdatabaseui.util.NetworkUtil;
import com.asib27.playerdatabaseui.util.Notification;
import com.asib27.playerdatabaseui.util.PasswordManager;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author USER
 */
public class TesterChelsea {
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) throws InterruptedException {
        try {
            Socket socket = new Socket(NetworkData.ipAddress, NetworkData.portNumber);
            NetworkUtil nu = new NetworkUtil(socket);
            
            loginCheck(nu);
            
            //database reception
            DatabaseManager databaseReciveCheck = databaseReciveCheck(nu);
            notificationReciveCheck(nu);
            
            //request validity check
            System.out.println("Not network data test");
            nu.write("ashdajshd");
            NetworkData nd = (NetworkData) nu.read();
            System.out.println(nd.getDataType() + " " + nd.getData());
            
            while(true){
                nd = (NetworkData) nu.read();
                System.out.println(nd.getDataType() + "  ");
                Notification n = nd.getData();
                System.out.println(n);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Finish data");
    }
    
    static void loginCheck(NetworkUtil nu) throws IOException, ClassNotFoundException{
        System.out.println("User name");
        String userName = "Chelsea";
        System.out.println("pass");
        String pass = "Chelsea";

        System.out.println("Sending " + userName + "---/" +  pass +"/");
        NetworkData data = new NetworkData(NetworkDataEnum.LOGIN, new PasswordManager(userName, pass));
        nu.write(data);

        System.out.println("Waiting for server response..........");
        NetworkData read = (NetworkData) nu.read();
        System.out.println(read.getDataType() + "  " + read.getData());

        System.out.println("Finish login");
    }
    
    static DatabaseManager databaseReciveCheck(NetworkUtil nu) throws IOException, ClassNotFoundException{
        NetworkData read1 = (NetworkData) nu.read();

        System.out.println(read1.getDataType());
        DatabaseManager pdb = read1.getData();

        System.out.println(Arrays.toString(pdb.getDataBase().getAllRecords()));
        
        return pdb;
    }
    
     private static void notificationReciveCheck(NetworkUtil nu) throws IOException, ClassNotFoundException {
        NetworkData read1 = (NetworkData) nu.read();

        System.out.println(read1.getDataType());
        ArrayList<Notification> noti = read1.getData();

        System.out.println(noti);
    }
}
