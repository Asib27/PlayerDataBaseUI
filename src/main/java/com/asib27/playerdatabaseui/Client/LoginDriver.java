/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Client;

import com.asib27.playerdatabaseui.util.PasswordManager;

/**
 *
 * @author USER
 */
public interface LoginDriver {
    public String sendLoginInfo(PasswordManager passwordManager);
}
