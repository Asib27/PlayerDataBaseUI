/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabaseui.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USER
 */
public class NotificationStore {
    ArrayList<Notification> allClub = new ArrayList<>();
    Map<String, ArrayList<Notification>> clubNotification = new HashMap<>();

    public NotificationStore() {
    }
    
    public void addNotification(Notification notification, UserInfo user){
        if(user == null)
            allClub.add(notification);
        else{
            if(clubNotification.containsKey(user.getClubName()))
                clubNotification.get(user.getClubName()).add(notification);
            else{
                ArrayList<Notification> arrayList= new ArrayList<>();
                arrayList.add(notification);
                clubNotification.put(user.getClubName(), arrayList);
            }
        }
            
    }
    
    public void addNotification(NetworkData data, UserInfo user){
        if(data.getDataType() != NetworkDataEnum.NOTIFICATION)
            return ;
        
        Notification notification = data.getData();
        addNotification(notification, user);
        
    }
    
    public void addNotification(String clubName, NetworkData data){
        if(data.getDataType() != NetworkDataEnum.NOTIFICATION)
            return ;
        
        Notification notification = data.getData();
        addNotification(clubName, notification);
    }
    
    public void addNotification(String clubName, Notification notification){
        if(clubNotification.containsKey(clubName))
           clubNotification.get(clubName).add(notification);
        else{
           ArrayList<Notification> arrayList= new ArrayList<>();
           arrayList.add(notification);
           clubNotification.put(clubName, arrayList);
        }
    }
    
    public ArrayList<Notification> getNotification(UserInfo user){
        return getNotification(user.getClubName());
    }
    
    public ArrayList<Notification> getNotification(String clubName){
        ArrayList<Notification> result = new ArrayList<>(allClub);
        
        if(clubNotification.containsKey(clubName))
            result.addAll(clubNotification.get(clubName));
        
        return result;
    }
    
    
}
