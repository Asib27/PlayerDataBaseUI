/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Client;

import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.NetworkUtil;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author USER
 */
public class CollectorThread implements Runnable{
    private NetworkUtil networkUtil;
    private BlockingQueue<NetworkData> queue;

    public CollectorThread(NetworkUtil networkUtil, BlockingQueue<NetworkData> queue) {
        this.networkUtil = networkUtil;
        this.queue = queue;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                Object read = networkUtil.read();
                
                NetworkData nd = checkDataValidty(read);
                if(nd == null) continue;
                queue.put(nd);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private NetworkData checkDataValidty(Object read) {
        if(!(read instanceof NetworkData)){
            writeErrorMessage("Sent object is not NetworkData");
            return null;
        }
        
        NetworkData nd = (NetworkData) read;
        
        if(nd.getDataType().isServerRequest()){
            writeErrorMessage("Wrong type of request : " + nd.toString());
            return null;
        }
        
        if(!nd.checkDataValidity()){
            writeErrorMessage("Wrong data type : " + nd.toString());
            return null;
        }
        
        return nd;
    }

    private void writeErrorMessage(String msg) {
        System.out.println(msg);
    }
    
}
