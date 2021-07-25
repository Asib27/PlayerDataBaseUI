/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import java.util.Stack;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Tab;

/**
 *
 * @author USER
 */
public class MyTab extends Tab{
    Stack<Driver> driverStack = new Stack<>();
    Stack<Driver> forwardDriverStack = new Stack<>();
    
    SimpleBooleanProperty backExists = new SimpleBooleanProperty(this, "back", false);
    SimpleBooleanProperty forwardExists = new SimpleBooleanProperty(this, "forward", false);

    public MyTab(String tabName) {
        super(tabName);
    }
    
    public void setContent(Driver driver){
        driverStack.push(driver);
        
        show();
    }
    
    public boolean back(){
        Driver top = driverStack.pop();
        
        forwardDriverStack.push(top);
        
        show();
        return true;
    }
    
    public boolean forward(){
        Driver top = forwardDriverStack.pop();
        driverStack.push(top);
        show();
        return true;
    }

    private void show() {
        Driver top = driverStack.peek();
        check();
        super.setContent(top.getGuiPane());
    }
    
    public boolean isBackExist(){
        return driverStack.size() > 1;
    }
    
    public boolean isForwardExist(){
        return forwardDriverStack.size() >= 1;
    }

    private void check(){
        backExists.set(isBackExist());
        forwardExists.set(isForwardExist());
    }

    public SimpleBooleanProperty BackExistsProperty() {
        return backExists;
    }

    public SimpleBooleanProperty ForwardExistsProperty() {
        return forwardExists;
    }
    
    public void update(DatabaseManager manager){
        driverStack.peek().update(manager);
    }
}
