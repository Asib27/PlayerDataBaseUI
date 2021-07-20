/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

/**
 *
 * @author USER
 */
public class UserInfo implements Comparable<UserInfo>{
    private String userName;
    private String clubName;

    public UserInfo(String userName, String clubName) {
        this.userName = userName;
        this.clubName = clubName;
    }
    
   
    public String getUserName() {
        return userName;
    }

    public String getClubName() {
        return clubName;
    }
    

    @Override
    public int compareTo(UserInfo o) {
        return userName.compareTo(o.userName);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserInfo))
            return false;
        UserInfo info = (UserInfo) obj;
        return userName.equals(info.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
    
    
}
