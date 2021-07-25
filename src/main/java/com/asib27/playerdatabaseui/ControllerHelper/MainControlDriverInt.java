/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.ControllerHelper;

import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.Drivers.Service;
import com.asib27.playerdatabaseui.util.Notification;
import java.util.ArrayList;
import javafx.concurrent.Task;

/**
 *
 * @author USER
 */
public interface MainControlDriverInt {
    public Driver GuiDriverFactory(String name);
    public Task<Driver> taskFactory(String name);
    public ArrayList<Notification> getNotifications();
    public Service getService();
}
