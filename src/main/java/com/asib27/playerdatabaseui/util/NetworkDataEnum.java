/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.util;

/**
 *
 * @author USER
 */
public enum NetworkDataEnum {
    SUCCESS(false), FAILED(false), LOGIN(true), LOGOUT(true), DATABASE(false),
    NOTIFICATION(false), SELL_REQUEST(true), BUY_REQUEST(true), BUY_REQUEST_APPROVED(true),
    BUY_REQUEST_DECLINED(true), All_NOTIFICATIONS(false), LOGIN_SUCCESS(false), 
    LOGIN_FAILED(false), PLAYER_ON_SELL(false), FEEDBACK(true);
    
    private final boolean serverRequest;

    private NetworkDataEnum(boolean serverRequest) {
        this.serverRequest = serverRequest;
    }

    public boolean isServerRequest() {
        return serverRequest;
    }
}
