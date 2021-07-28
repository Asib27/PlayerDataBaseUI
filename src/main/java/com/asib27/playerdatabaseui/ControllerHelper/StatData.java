/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.ControllerHelper;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

/**
 *
 * @author USER
 */
public class StatData implements Predicate<String>{
    private StringProperty name = null;
    private String nm;
    private Map<String, Double> seriesValues;

    public StatData() {
        seriesValues = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public StatData(String name) {
        this();
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
    
    

    public Map<String, Double> getSeriesValues() {
        return seriesValues;
    }

    public void setSeriesValues(Map<String, Double> seriesValues) {
        this.seriesValues = seriesValues;
    }
    
    
    public void setSeriesValuesInt(Map<String, Integer> series) {
        seriesValues.clear();
        
        series.keySet().forEach(string -> {
            double val = series.get(string);
            seriesValues.put(string, val);
        });
    }
    
    public void addValue(String key, double val){
        seriesValues.put(key, val);
    }
    
    /*
    public StringProperty getValueProperty(String key){
        StringProperty sp = seriesValues.get(key);
        
        return new SimpleStringProperty(sp.getValue());
    }
    */
    public double getValue(String key){
        return seriesValues.getOrDefault(key, 0.0);
    }

    @Override
    public boolean test(String t) {
        return nm.toLowerCase().contains(t);
    }
}
