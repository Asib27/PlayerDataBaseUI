/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

import com.asib27.playerdatabasesystem.Player;

/**
 *
 * @author USER
 */
public class PlayerTransaction {
    private Player player;
    private double price;

    public PlayerTransaction(Player player, double price) {
        this.player = player;
        this.price = price;
    }

    public PlayerTransaction(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public double getPrice() {
        return price;
    }
    
    
}
