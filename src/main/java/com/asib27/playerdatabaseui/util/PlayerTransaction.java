/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

import com.asib27.playerdatabasesystem.Player;
import java.io.Serializable;

/**
 *
 * @author USER
 */
public class PlayerTransaction implements Serializable, Comparable<PlayerTransaction>{
    private static final long serialVersionUID = 6283043156433381274L;
    
    private Player player;
    private double price;
    
    private String buyer;
    private String seller;

    public PlayerTransaction(Player player, double price, String buyer) {
        this(player, buyer);
        this.price = price;
    }

    public PlayerTransaction(Player player, String buyer) {
        this(player);
        this.buyer = buyer;
    }

    public PlayerTransaction(Player player, double price) {
        this(player);
        this.price = price;
    }

    public PlayerTransaction(Player player) {
        this.player = player;
        seller = player.getClub();
    }
    
    

    public Player getPlayer() {
        return player;
    }

    public double getPrice() {
        return price;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }    

    public void setSeller(String seller) {
        this.seller = seller;
    }


    @Override
    public int compareTo(PlayerTransaction o) {
        return player.compareTo(o.player);
    }

    @Override
    public String toString() {
        return player.toString() + " " + String.valueOf(price);
    }
    
    
}
