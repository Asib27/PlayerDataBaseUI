/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import java.util.ArrayList;

/**
 *
 * @author USER
 */
abstract class ObserverUtil<T>{
    private ArrayList<SearchObserver<T>> searchObservers = new ArrayList<>();
    
    /*Implementation of observer pattern <start>*/
    
    public void addSearchListener(SearchObserver<T> listener){
        searchObservers.add(listener);
    }
    
    protected void updateAll(){
        searchObservers.forEach((t) -> {
            updator(t);
        });
    }
    
    public void removeSearchListener(SearchObserver<T> listener){
        searchObservers.remove(listener);
    }
    
    abstract protected void updator(SearchObserver t);
    
    /*Implementation of observer pattern <finish>*/
}

