/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.ControllerHelper;

import com.asib27.playerdatabaseui.Driver;
import javafx.concurrent.Task;

/**
 *
 * @author USER
 */
public interface MainControlDriverInt {
    public Driver GuiDriverFactory(String name);
    public Task<Driver> taskFactory(String name);
}
