/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Notification implements Serializable{
    private Type type;
    String message;
    Object ob;

    public Notification(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Notification(Type type, String message, Object ob) {
        this.type = type;
        this.message = message;
        this.ob = ob;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
    
    public enum Type{
        MESSAGE, BUY_REQUEST, SELL_SUCCEED, SELL_REQUEST, BUY_SECCESS;
    }
}
