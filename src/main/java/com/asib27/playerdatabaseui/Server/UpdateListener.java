/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Server;

import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.NetworkData;
import com.asib27.playerdatabaseui.util.PlayerTransaction;
import java.util.Set;

/**
 *
 * @author USER
 */
public interface UpdateListener {
    public void update(PlayerDataBaseInt database);
    public void send(NetworkData networkData);
    public void updatePlayersOnSell(Set<PlayerTransaction> playerOnSell);
}
