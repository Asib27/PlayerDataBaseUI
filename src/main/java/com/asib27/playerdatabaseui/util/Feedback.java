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
public class Feedback implements Serializable{
    private String title;
    private String details;

    public Feedback(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public String getTitle() {
        return title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "title : " + title + "\nDetails : \n" + details;
    }
    
    
}
