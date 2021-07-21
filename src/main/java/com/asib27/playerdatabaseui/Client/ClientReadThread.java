/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Client;

import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.NetworkDataEnum;
import com.asib27.playerdatabaseui.util.NetworkUtil;
import com.asib27.playerdatabaseui.util.Notification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author USER
 */
public class ClientReadThread implements Runnable{
    private ClientInt client;
    private BlockingQueue<NetworkData> blockingQueue;

    public ClientReadThread(ClientInt client, BlockingQueue<NetworkData> blockingQueue) {
        this.client = client;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                NetworkData nd = blockingQueue.take();
                processRequest(nd);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void processRequest(NetworkData nd) {
        switch(nd.getDataType()){
            case LOGIN_SUCCESS-> client.takeLoginResponse(true, nd.getData());
            case LOGIN_FAILED-> client.takeLoginResponse(false, nd.getData());
            case SUCCESS-> client.takeErrorMessage(nd.getData());
            case FAILED -> client.takeErrorMessage(nd.getData());
            case DATABASE-> client.takeDatabase(nd.getData());
            case NOTIFICATION->client.takeNotifications((Notification)nd.getData());
            case All_NOTIFICATIONS-> client.takeNotifications((ArrayList<Notification>) nd.getData());
        }
    }
    
    
}
