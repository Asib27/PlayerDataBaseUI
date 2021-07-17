/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabasesystem.PlayerDataBaseInt;

/**
 *
 * @author USER
 */
public interface DataProcessHelper<T> {
    T[] getData(PlayerDataBaseInt dataBase);
}
