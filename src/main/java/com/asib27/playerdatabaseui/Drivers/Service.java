/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Drivers;

import com.asib27.playerdatabaseui.util.PasswordManager;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.Notification;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import java.util.ArrayList;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author USER
 */
public interface Service {
    public DatabaseManager getDatabase();
    public ArrayList<Notification> getNotification();
    public SimpleObjectProperty playersOnSellProperty();
    public Set<PlayerTransaction> getPlayersOnSell();
    public String getClubName();
    public void sendSellRequest(PlayerTransaction playerTransaction);
    public void sendBuyRequest(PlayerTransaction playerTransaction);
    public void sendBuyRequestAproval(PlayerTransaction playerTransaction);
    public void sendBuyRequestDenial(PlayerTransaction playerTransaction);
}
