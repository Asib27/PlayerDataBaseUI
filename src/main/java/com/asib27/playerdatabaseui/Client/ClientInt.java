/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Client;

import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.Notification;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public interface ClientInt {
    public void takeNotifications(Notification notification);
    public void takeNotifications(ArrayList<Notification> notifications);
    public void takeLoginResponse(boolean isSuccess, String msg);
    public void takeDatabase(DatabaseManager databaseManager);
    public void takeErrorMessage(String msg);
}
