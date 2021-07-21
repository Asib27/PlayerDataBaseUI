/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.NetworkDataEnum;
import com.asib27.playerdatabaseui.util.NetworkUtil;
import com.asib27.playerdatabaseui.util.PasswordManager;
import com.asib27.playerdatabaseui.util.UserInfo;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author USER
 */
public class LoginThread implements Runnable{
    private static final String passwordFile = "src\\main\\resources\\Server\\ServerPasswords.ser";
    private NetworkUtil networkUtil;
    private ServerInt server;

    public LoginThread(NetworkUtil networkUtil, ServerInt server) {
        this.networkUtil = networkUtil;
        this.server = server;
    }
    
    public LoginThread(Socket socket, ServerInt server) throws IOException {
        this(new NetworkUtil(socket), server);
    }

    @Override
    public void run() {
        while(true){
            try {
                Object read = networkUtil.read();
                
                if(!(read instanceof NetworkData)){
                    networkUtil.write(new NetworkData(NetworkDataEnum.LOGIN_FAILED, "Sent object is not NetworkData"));
                    continue;
                }
                
                NetworkData data = (NetworkData) read;
                
                if(data.getDataType() == NetworkDataEnum.LOGIN && data.checkDataValidity()){
                    PasswordManager pm = data.getData();
                    int verification = verify(pm);
                    
                    if(verification == 0){
                        networkUtil.write(new NetworkData(NetworkDataEnum.LOGIN_SUCCESS, "Login Succesful"));
                        server.handleNewLogin(new UserInfo(pm.getUsername(), pm.getUsername()), networkUtil);
                        break;
                    }
                    else if(verification == -1){
                        networkUtil.write(new NetworkData(NetworkDataEnum.LOGIN_FAILED, "Server Failure"));
                    }
                    else if(verification == 1){
                        networkUtil.write(new NetworkData(NetworkDataEnum.LOGIN_FAILED, "Wrong Password"));
                    }
                    else{
                        networkUtil.write(new NetworkData(NetworkDataEnum.LOGIN_FAILED, "User name didnot match"));
                    }
                }
                
                else{
                    if(data.getDataType() != NetworkDataEnum.LOGIN){
                        networkUtil.write(new NetworkData(NetworkDataEnum.LOGIN_FAILED, "Login request Expected"));
                    }
                    else{
                        networkUtil.write(new NetworkData(NetworkDataEnum.LOGIN_FAILED, "Login data is not valid"));
                    }
                }
            }catch(EOFException ex){
                System.out.println(ex);
                break;
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                break;
            }
        }
        
        //server.handleNewLogin(UserInfo, networkUtil);
    }
    

    public static int verify(PasswordManager pm) {
        int result = -1;
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(passwordFile)))){
            Map<String, Integer> passMap = new HashMap<>();
            passMap = (Map<String, Integer>) ois.readObject();
            
            if(passMap.containsKey(pm.getUsername())){
                if(passMap.get(pm.getUsername()) == pm.getHash())
                    result = 0; //login suuces
                else result = 1; // password didnt match
            }
            else{
                result = 2; //user name dose not exist
            }
            
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Map<String, Integer> pass = new HashMap<>();
        
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            PasswordManager[] pm = {new PasswordManager("Arsenal", "arsenal"),
                    new PasswordManager("Liverpool", "Liverpool"),
                    new PasswordManager("Chelsea", "Chelsea"),
                    new PasswordManager("Manchester City", "Manchester City"),
                    new PasswordManager("Manchester United", "Manchester United")
            };
            for (PasswordManager pManager : pm) {
                pass.put(pManager.getUsername(), pManager.getHash());
            }
            fos = new FileOutputStream(new File("src\\main\\resources\\Server\\ServerPasswords.ser"));
            
            oos = new ObjectOutputStream(fos);
            oos.writeObject(pass);
            System.out.println("Write succesful");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        
        try {
            FileInputStream fis = new FileInputStream(new File("src\\main\\resources\\Server\\ServerPasswords.ser"));
            ObjectInputStream oid = new ObjectInputStream(fis);
            
            Set<PasswordManager> p = (Set<PasswordManager>) oid.readObject();
            System.out.println(p);
            
            System.out.println(p.contains(new PasswordManager("abc", "qwerty")));
            
            
            System.out.println("Read Succes");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
