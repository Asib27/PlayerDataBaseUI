/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import java.util.Map;
import java.util.TreeMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

/**
 *
 * @author USER
 */
public class StatData {
    private StringProperty name = null;
    private String nm;
    private Map<String, Integer> seriesValues;

    public StatData() {
        seriesValues = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public StatData(String name) {
        nm = name;
    }
    
    
    public StringProperty NameProperty() {
        if(name == null){
            return new SimpleStringProperty(nm);
        }
        return name;
    }

    public void setName(String name) {
        nm = name;
    }

    public String getName() {
        return nm;
    }
    
    

    public Map<String, Integer> getSeriesValues() {
        return seriesValues;
    }

    public void setSeriesValues(Map<String, Integer> seriesValues) {
        this.seriesValues = seriesValues;
    }
    
    public void addValue(String key, int val){
        seriesValues.put(key, val);
    }
    
    /*
    public StringProperty getValueProperty(String key){
        StringProperty sp = seriesValues.get(key);
        
        return new SimpleStringProperty(sp.getValue());
    }
    */
    public int getValue(String key){
        return seriesValues.get(key);
    }
}
