/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.NetworkDataEnum;
import com.asib27.playerdatabaseui.util.NetworkUtil;
import com.asib27.playerdatabaseui.util.PasswordManager;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author USER
 */
public class Tester {
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) throws InterruptedException {
        try {
            Socket socket = new Socket(NetworkData.ipAddress, NetworkData.portNumber);
            NetworkUtil nu = new NetworkUtil(socket);
            
            loginCheck(nu);
            
            //database reception
            databaseReciveCheck(nu);
            
            //request validity check
            System.out.println("Not network data test");
            nu.write("ashdajshd");
            NetworkData nd = (NetworkData) nu.read();
            System.out.println(nd.getDataType() + " " + nd.getData());
            
            System.out.println("Wrong request test");
            nu.write(new NetworkData(NetworkDataEnum.DATABASE, nd));
            nd = (NetworkData) nu.read();
            System.out.println(nd.getDataType() + " " + nd.getData());
            
            System.out.println("Login resend test");
            nu.write(new NetworkData(NetworkDataEnum.LOGIN, nd));
            nd = (NetworkData) nu.read();
            System.out.println(nd.getDataType() + " " + nd.getData());
            
            //logout check
            System.out.println("logout\n");
            nu.write(new NetworkData(NetworkDataEnum.LOGOUT, nd));
            nd = (NetworkData) nu.read();
            System.out.println(nd.getDataType() + " " + nd.getData());
            
            //login check 2
            System.out.println("login check 2");
            loginCheck(nu);
            databaseReciveCheck(nu);
            
            
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Finish data");
    }
    
    static void loginCheck(NetworkUtil nu) throws IOException, ClassNotFoundException{
        System.out.println("User name");
        String userName = "abc";
        System.out.println("pass");
        String pass = "qwerty";

        System.out.println("Sending " + userName + "---/" +  pass +"/");
        NetworkData data = new NetworkData(NetworkDataEnum.LOGIN, new PasswordManager(userName, pass));
        nu.write(data);

        System.out.println("Waiting for server response..........");
        NetworkData read = (NetworkData) nu.read();
        System.out.println(read.getDataType() + "  " + read.getData());

        System.out.println("Finish login");
    }
    
    static void databaseReciveCheck(NetworkUtil nu) throws IOException, ClassNotFoundException{
        NetworkData read1 = (NetworkData) nu.read();

        System.out.println(read1.getDataType());
        DatabaseManager pdb = read1.getData();

        System.out.println(Arrays.toString(pdb.getDataBase().getAllRecords()));
    }
}
