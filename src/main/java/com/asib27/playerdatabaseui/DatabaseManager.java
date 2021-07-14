/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

import com.asib27.playerdatabasesystem.*;
import java.io.Serializable;
import java.security.Timestamp;

/**
 *
 * @author USER
 */
public class DatabaseManager implements Serializable{
    PlayerDataBaseInt dataBase;
    Timestamp timestamp;

    public DatabaseManager(PlayerDataBaseInt dataBase, Timestamp timestamp) {
        this.dataBase = dataBase;
        this.timestamp = timestamp;
    }

    public PlayerDataBaseInt getDataBase() {
        return dataBase;
    }
}
