/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author USER
 */
public class Notification implements Serializable{
    private Type type;
    private String message;
    private LocalDateTime time;
    Object ob;

    public Notification(Type type, String message) {
        this.type = type;
        this.message = message;
        time = LocalDateTime.now();
    }

    public Notification(Type type, String message, Object ob) {
        this.type = type;
        this.message = message;
        this.ob = ob;
        time = LocalDateTime.now();
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public <T>T getData() {
        return (T)ob;
    }
    
    

    @Override
    public String toString() {
        return type.toString() + " " + message + " " + time.toString() + " " + ob.toString();
    }
    
    
    
    public enum Type{
        MESSAGE, BUY_REQUEST, SELL_SUCCEED, SELL_REQUEST, BUY_SECCESS;
    }
}
