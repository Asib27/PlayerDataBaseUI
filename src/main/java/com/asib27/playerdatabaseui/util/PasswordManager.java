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
public class PasswordManager implements Serializable, Comparable<PasswordManager>{
    private static final long serialVersionUID = 6283043156433381274L;
    
    private final String username;
    private final int hash;

    public PasswordManager(String username, String password) {
        this.username = username;
        this.hash = password.hashCode();
    }

   
    public boolean isUserNameValid(PasswordManager pm){
        return username.equals(pm.username);
    }
    
    public boolean isMatched(PasswordManager pm){
        return isUserNameValid(pm) && (hash == pm.hash);
    }

    public String getUsername() {
        return username;
    }

    public int getHash() {
        return hash;
    }
    
    
    
    public static void main(String[] args) {
        PasswordManager p1 = new PasswordManager("Asib", "abcde");
        PasswordManager p2 = new PasswordManager("Asib", "abcde");
        
        PasswordManager p3 = new PasswordManager("Asib", "Abcde");
        PasswordManager p4 = new PasswordManager("Asib27", "abcde");
        
        System.out.println(p1.isMatched(p2));
        System.out.println(p1.isMatched(p3));
        System.out.println(p1.isMatched(p4));
    }

    @Override
    public int compareTo(PasswordManager o) {
        return username.compareTo(o.username);
    }
}
