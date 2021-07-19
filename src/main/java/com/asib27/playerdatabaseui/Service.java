/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui;

import com.asib27.playerdatabaseui.util.PasswordManager;
import com.asib27.playerdatabaseui.util.DatabaseManager;

/**
 *
 * @author USER
 */
public interface Service {
    public boolean authenticate(PasswordManager pass);
    public DatabaseManager getDatabase();
}
