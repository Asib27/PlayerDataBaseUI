/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Drivers;

import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.PasswordManager;
import javafx.scene.layout.Pane;

/**
 *
 * @author USER
 */
public interface Driver {
    public Pane getGuiPane();
    public void clearListener();
    public void update(DatabaseManager databaseManager);
    public String getDriverName();
}
