/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabasesystem.*;
import com.asib27.playerdatabaseui.util.*;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author USER
 */
public interface ServerInt {
    public PlayerDataBaseInt getDatabase();
    public Set<PlayerTransaction> getPlayerOnSell();
    public void addPlayerOnSell(PlayerTransaction player);
    public void removePlayerOnSell(PlayerTransaction player);
    public boolean changePlayerInfo(Player oldPlayerInfo, Player newPlayerInfo);
    public void handleNewLogin(UserInfo info, NetworkUtil networkUtil);
    public void handleLogout(UserInfo info, NetworkUtil networkUtil);
    public void send(NetworkData data, UserInfo user);
    public void send(String clubName, NetworkData data);
    public NotificationStore getNotificationStore();
}
