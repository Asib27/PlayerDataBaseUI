/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import java.io.Serializable;
import java.net.URI;

/**
 *
 * @author USER
 */
public class NetworkData implements Serializable{
    public static String ipAddress = "127.0.0.1";
    public static int portNumber = 1927;
    
    NetworkDataEnum dataType;
    Object data;

    public NetworkData(NetworkDataEnum dataType, Object data) {
        this.dataType = dataType;
        this.data = data;
    }
    
    public boolean checkDataValidity(){
        return switch(dataType){
            case LOGIN-> data instanceof PasswordManager;
            case SUCCESS -> data instanceof String;
            case FAILED -> data instanceof String;
            case LOGOUT-> true;
            case DATABASE-> data instanceof PlayerDataBaseInt;
            case NOTIFICATION-> data instanceof Notification;
            case SELL_REQUEST->data instanceof PlayerTransaction;
            case BUY_REQUEST-> data instanceof PlayerTransaction;
            case BUY_REQUEST_APPROVED-> data instanceof PlayerTransaction;
        };
    }
    
    public <T> T getData(){
        return (T) data;
    }

    public NetworkDataEnum getDataType() {
        return dataType;
    }

    @Override
    public String toString() {
        return dataType + " " + data.toString();
    }
    
    
}
