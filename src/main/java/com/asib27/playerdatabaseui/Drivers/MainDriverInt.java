/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.Drivers;

import com.asib27.playerdatabaseui.util.Notification;

/**
 *
 * @author USER
 */
public interface MainDriverInt extends Driver{
    void showNotification(Notification notification);
}
