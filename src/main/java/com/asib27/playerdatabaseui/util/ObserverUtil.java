/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

import java.util.ArrayList;

/**
 *
 * @author USER
 */
public abstract class ObserverUtil<T>{
    private ArrayList<T> searchObservers = new ArrayList<>();
    
    /*Implementation of observer pattern <start>*/
    
    public void addSearchListener(T listener){
        searchObservers.add(listener);
    }
    
    protected void updateAll(){
        System.out.println("Called update all");
        System.out.println(searchObservers);
        searchObservers.forEach(this::updator);
    }
    
    public void removeSearchListener(T listener){
        searchObservers.remove(listener);
    }
    
    abstract protected void updator(T t);
    
    /*Implementation of observer pattern <finish>*/
}

